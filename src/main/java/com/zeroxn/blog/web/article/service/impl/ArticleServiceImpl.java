package com.zeroxn.blog.web.article.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeroxn.blog.core.exception.CustomException;
import com.zeroxn.blog.web.article.mapper.ArticleMapper;
import com.zeroxn.blog.core.utils.BaseUtil;
import com.zeroxn.blog.core.utils.RedisUtil;
import com.zeroxn.blog.web.artalk.ArtalkService;
import com.zeroxn.blog.entity.Article;
import com.zeroxn.blog.entity.ArticleLabel;
import com.zeroxn.blog.web.article.service.ArticleLabelService;
import com.zeroxn.blog.web.article.service.ArticleService;
import com.zeroxn.blog.entity.Label;
import com.zeroxn.blog.web.label.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ran
 * @date
 * ArticleService实现类
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;
    private final LabelService labelService;
    private final ArticleLabelService articleLabelService;
    private final RedisUtil redisUtil;
    private final ArtalkService artalkService;

    public ArticleServiceImpl(ArticleMapper articleMapper,LabelService labelService, ArticleLabelService articleLabelService,
                              RedisUtil redisUtil, ArtalkService artalkService){
        this.articleMapper = articleMapper;
        this.labelService = labelService;
        this.articleLabelService = articleLabelService;
        this.redisUtil = redisUtil;
        this.artalkService = artalkService;
    }


    /**
     * 获取到文章列表后 需要为文章列表中的每一条文章设置它的标签列表和分类列表
     * 如果文章的状态是已发布(status == 1)那么还需要通过文章的title从redis中获取最新的点击数
     *
     * @param status   文章状态 来判断是需要获取全部状态的文章还是获取指定状态的文章 可以为null
     * @param pageNum  页码
     * @param pageSize 每页记录条数
     * @param title    文章标题 后台页面搜索文章需要 可以为null
     * @return 返回封装好的ArticleDTO列表或空
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<Article> getArticleList(Integer status, Integer pageNum, Integer pageSize, String title) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articleList = articleMapper.getArticleList(status, title);
        for(Article article : articleList){
            //为 article 设置最新的点击数 只为已经发布的文章设置 未发布的文章在redis中也没有 数据库中的值就是最新值
            if (article.getStatus() == 1){
                Integer clickNum = redisUtil.zScope(RedisUtil.BLOG_TOP_REDIS_KEY_PREFIX, article.getTitle()).intValue();
                article.setClickNum(clickNum);
            }
            //获取文章的标签和分类列表
            getArticleLabelList(article);
        }
        return new PageInfo<>(articleList);
    }

    /**
     * 通过id来获取文章详情 如果isGetContent为true的话 就将返回结果添加到缓存中
     * @param id 文章id
     * @param isGetContent 判断是否获取文章正文 某些时候需要判断文章是否存在 而文章正文可能非常长 可能会影响性能
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "article", condition = "#isGetContent", key = "#id")
    public Article getArticleById(Integer id, boolean isGetContent) {
        Article article = articleMapper.getArticleById(id, isGetContent);
        if(article == null){
            throw new CustomException("参数不存在");
        }
        //为articleDTO添加分类和标签列表后再返回
        return getArticleLabelList(article);
    }

    /**
     * 保存文章 先保存article对象 再分别保存包含的文章列表和标签列表
     * @param article
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveArticle(Article article) {
        //设置状态为待发布
        article.setStatus(0);
        //设置创建时间为当前时间
        article.setCreateTime(LocalDateTime.now());
        //设置更新时间为当前时间
        article.setUpdateTime(LocalDateTime.now());
        //保存文章对象到数据库 保存成功后将id返回
        articleMapper.saveArticle(article);
        //如果id为空的话 说明数据库插入失败 抛出异常
        if (article.getId() == null){
            throw new CustomException("数据库插入错误");
        }
        //保存其中的分类列表和标签列表
        saveArticleLabel(article);
    }

    /**
     * 更新文章先更新articleDTO中的article对象到数据库 然后再通过文章id删除文章标签关系表和文章分类关系中所有关联该id的数据
     * 然后再调用保存分类、标签列表的方法 保存新的分类和标签列表 操作成功后再删除redis中的文章缓存
     * @param article
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "article", key = "#article.id")
    public void updateArticle(Article article) {
        //先通过id获取旧的文章数据 用来更新redis中的zSet数据
        Article oldArticle = getArticleById(article.getId(), false);
        //设置更新时间为当前时间
        article.setUpdateTime(LocalDateTime.now());
        //更新文章表
        articleMapper.updateArticle(article);
        //先删除与当前文章关联的所有分类/标签
        articleLabelService.deleteArticleLabelByArticleId(article.getId());
        //保存新的分类和标签列表
        saveArticleLabel(article);
        //如果更新了article title  那么同步更新redis中到数据 获取分数 删除旧数据 保存新数据
        if (!article.getTitle().equals(oldArticle.getTitle())){
            Double scope = redisUtil.zScope(RedisUtil.BLOG_TOP_REDIS_KEY_PREFIX, oldArticle.getTitle());
            //文章可能在未发布的状态下修改了标题 那么此时scope == null 添加判断 避免空指针异常
            if (scope == null){
                return;
            }
            //通过旧文章数据的title来删除
            redisUtil.zSetDel(RedisUtil.BLOG_TOP_REDIS_KEY_PREFIX, oldArticle.getTitle());
            //添加新的数据
            redisUtil.zSetAdd(RedisUtil.BLOG_TOP_REDIS_KEY_PREFIX, article.getTitle(), scope);
        }
    }

    /**
     * 更新之前 先检查status参数 是否符合要求 再通过id判断文章是否为空 条件都成立的情况下才更新
     * 更新完了之后 需要通过status参数和所查询到的文章的title属性 来判断是否需要向redis zSet中添加或删除数据
     * @param id 文章id
     * @param isComment 文章的isComment属性 是否开启评论
     * @param status 文章状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleType(Integer id, Boolean isComment, Integer status) {
        if(!BaseUtil.checkStatus(status)){
            throw new CustomException("状态参数错误");
        }
        Article findArticle = getArticleById(id, false);
        if (findArticle == null){
            throw new CustomException("参数错误");
        }
        articleMapper.updateArticleType(id, isComment, status);
        //状态为 1 表示需要发布文章 这时将文章的点击数添加到redis排行榜
        //否则就将redis中该文章的最新点击数保存到 db 然后从redis中删除该文章
        Double scope = redisUtil.zScope(RedisUtil.BLOG_TOP_REDIS_KEY_PREFIX, findArticle.getTitle());
        //只有status == 1 并且通过title获取分数为空的情况下 才向redis中添加
        if (status == 1 && scope == null){
            redisUtil.zSetAdd(RedisUtil.BLOG_TOP_REDIS_KEY_PREFIX, findArticle.getTitle(),
                    Double.valueOf(findArticle.getClickNum()));
            //如果status == 0 并且分数不为空的话 则需要从redis中删除数据
        }else if(status == 0 && scope != null){
            updateArticleClickNumByTitle(findArticle.getTitle(), scope.intValue());
            redisUtil.zSetDel(RedisUtil.BLOG_TOP_REDIS_KEY_PREFIX, findArticle.getTitle());
        }
    }

    /**
     * 删除文章需要同时删除所关联的标签列表、分类列表 和Artalk评论的评论页 删除成功后 同时也要删除redis中的缓存
     * @param id 文章id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "article", key = "#id")
    public void deleteArticle(Integer id) {
        //删除文章
        articleMapper.deleteArticle(id);
        //删除关联的分类/标签
        articleLabelService.deleteArticleLabelByArticleId(id);
        //通过key来删除Artalk评论的评论页 key由 ARTALK_PAGE_KEY_PREFIX 前缀加上文章id组成
        artalkService.deleteArtalkPageByKey(ArtalkService.ARTALK_PAGE_KEY_PREFIX + id);
    }

    /**
     * 通过文章的创建时间获取当前文章的上一篇文章
     * @param createTime 文章的创建时间
     * @return 返回上一篇文章或空
     */
    @Override
    public Article getCurrentArticleUpArticle(LocalDateTime createTime) {
        return articleMapper.getCurrentArticleUpArticle(createTime);
    }

    /**
     * 通过文章的创建时间获取当前文章的下一篇文章
     * @param createTime 文章的创建时间
     * @return 放回下一篇文章或空
     */
    @Override
    public Article getCurrentArticleDownArticle(LocalDateTime createTime) {
        return articleMapper.getCurrentArticleDownArticle(createTime);
    }

    /**
     * 通过文章标题模糊匹配数据库进行搜索
     * @param title 文章标题
     * @return 返回符合条件的前7条数据 文章对象只包含id和title属性
     */
    @Override
    public List<Article> searchArticleByTitle(String title) {
        return articleMapper.searchArticleByTitle(title);
    }

    /**
     * 获取所有文章的点击数和title 用于redis的数据同步
     * @return
     */
    @Override
    public List<Article> getArticleClickNumAndTitleList() {
        return articleMapper.getArticleClickNumAndTitleList();
    }

    /**
     * 通过文章title更新文章的点击数 用户将redis zSet中的排行榜数据同步到数据库
     * @param title 文章title
     * @param clickNum 文章clickNum 点击数
     */
    @Override
    public void updateArticleClickNumByTitle(String title, Integer clickNum) {
        articleMapper.updateArticleClickNumByTitle(title, clickNum);
    }

    /**
     * 从redis zSet中获取排行榜数据
     * @return 返回redis中点击数排名前5的文章标题
     */
    @Override
    public List<String> getArticleTop() {
        //获取排名前5的数据
        Set<ZSetOperations.TypedTuple<Object>> set = redisUtil.zRange(RedisUtil.BLOG_TOP_REDIS_KEY_PREFIX, 0, 4);
        List<String> titleList = new ArrayList<>();
        set.forEach(s -> {
            //获取title
            String title = String.valueOf(s.getValue());
            titleList.add(title);
        });
        return titleList;
    }

    /**
     * 通过分类id或标签id获取文章列表
     * @param labelId 分类或者标签的id
     * @param pageNum 页码
     * @param pageSize 每页记录条数
     * @return
     */
    @Override
    public List<Article> getArticleListByLabelId(Integer labelId, Integer pageNum, Integer pageSize) {
        List<Integer> articleIdList = articleLabelService.listArticleIdByLabelId(labelId);
        //通过文章id列表获取包含详细信息的文章列表 详细信息包含id、title、summary、cover
        return getArticleListByIdList(articleIdList, pageNum, pageSize);
    }

    /**
     * 保存文章所关联的分类/标签列表
     * @param article 包含分类/标签列表的文章对象
     */
    private void saveArticleLabel(Article article){
        List<Label> labelList = article.getLabelList();
        if (labelList != null && labelList.size() > 0){
            List<ArticleLabel> articleLabelList = new ArrayList<>();
            labelList.forEach(label -> {
                ArticleLabel articleLabel = new ArticleLabel(null, article.getId(), label.getId());
                articleLabelList.add(articleLabel);
            });
            articleLabelService.saveArticleLabelList(articleLabelList);
        }
    }
    //通过文章id列表返回包含详细信息的文章列表
    private List<Article> getArticleListByIdList(List<Integer> articleIdList, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.getArticleListByIdList(articleIdList);
    }
    //通过文章id获取关联的文章分类/标签列表
    private Article getArticleLabelList(Article article){
        List<Integer> labelIdList = articleLabelService.listLabelIdByArticleId(article.getId());
        article.setLabelList(labelService.listLabelByIdList(labelIdList));
        return article;
    }
}
