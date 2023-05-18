package com.zeroxn.blog.web.article.service;

import com.github.pagehelper.PageInfo;
import com.zeroxn.blog.entity.Article;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ran
 * @date
 */
public interface ArticleService {
    /**
     * 获取文章列表 因为文章列表中的文章需要设置分类和标签 所有这里的返回值使用PageInfo(ArticleDTO)
     * PageInfo用来返回详细的分页信息
     * @param status 文章状态 来判断是需要获取全部状态的文章还是获取指定状态的文章 可以为null
     * @param pageNum 页码
     * @param pageSize 每页记录条数
     * @param title 文章标题 后台页面搜索文章需要 可以为null
     * @return 返回ArticleDTO列表和详细分页数据
     */
    PageInfo<Article> getArticleList(Integer status, Integer pageNum, Integer pageSize, String title);

    /**
     * 通过id获取文章详情包含分类列表和标签列表
     * @param id 文章id
     * @param isGetContent 判断是否获取文章正文 某些时候需要判断文章是否存在 而文章正文可能非常长 可能会影响性能
     * @return 返回文章对象
     */
    Article getArticleById(Integer id, boolean isGetContent);

    /**
     * 保存文章 封装对象是DTO 里面除了文章对象外还有分类列表和标签列表
     * @param article
     */
    void saveArticle(Article article);

    /**
     * 更新文章
     * @param article
     */
    void updateArticle(Article article);

    /**
     * 通过更新文章状态 更新文章的status和isComment属性
     * @param id 文章id
     * @param isComment 文章的isComment属性 是否开启评论
     * @param status 文章状态
     */
    void updateArticleType(Integer id, Boolean isComment, Integer status);

    /**
     * 通过id删除单条文章
     * @param id 文章id
     */
    void deleteArticle(Integer id);

    /**
     * 通过文章的创建时间获取当前文章的上一篇文章
     * @param createTime 文章的创建时间
     * @return 返回上一篇文章或空
     */
    Article getCurrentArticleUpArticle(LocalDateTime createTime);

    /**
     * 通过文章的创建时间获取当前文章的下一篇文章
     * @param createTime 文章的创建时间
     * @return 返回下一篇文章或空
     */
    Article getCurrentArticleDownArticle(LocalDateTime createTime);

    /**
     * 通过文章title来模糊匹配搜索文章 一次返回7条数据
     * @param title 文章标题
     * @return 返回符合条件的文章列表或空 列表中的文章对象只有id和title属性
     */
    List<Article> searchArticleByTitle(String title);

    /**
     * 获取所有文章的点击数和title用做redis同步数据库中数据 已做为redis排行榜的基础数据
     * @return 所有所有文章的clickNum和title
     */
    List<Article> getArticleClickNumAndTitleList();

    /**
     * 通过文章title更新文章clickNum 用于定时任务同步redis中的排行榜数据到数据库
     * @param title 文章title
     * @param clickNum 文章clickNum 点击数
     */
    void updateArticleClickNumByTitle(String title, Integer clickNum);

    /**
     * 获取文章排行榜
     * @return 返回redis zSet排行榜中点击数前5的文章
     */
    List<String> getArticleTop();

    /**
     * 根据标签id或分类id获取所关联的文章列表
     * @param labelId 分类或者标签的id
     * @param pageNum 页码
     * @param pageSize 每页记录条数
     * @return 返回关联的文章列表或空
     */
    List<Article> getArticleListByLabelId(Integer labelId, Integer pageNum, Integer pageSize);
}