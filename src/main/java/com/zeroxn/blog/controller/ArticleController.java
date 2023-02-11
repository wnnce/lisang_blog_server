package com.zeroxn.blog.controller;

import com.aliyuncs.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import com.zeroxn.blog.DTO.ArticleDTO;
import com.zeroxn.blog.entity.Article;
import com.zeroxn.blog.exception.CustomException;
import com.zeroxn.blog.service.ArticleService;
import com.zeroxn.blog.utils.BaseUtil;
import com.zeroxn.blog.utils.RedisUtil;
import com.zeroxn.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ran
 * @date
 * Article文章控制层
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 后台管理页面获取所有文章列表
     * @param pageNum 页码 用户文章翻页
     * @param pageSize 每页记录条数
     * @param title 用户搜索文章 可以不传
     * @return 返回文章列表和详细分页信息或空
     */
    @GetMapping
    public Result<PageInfo<ArticleDTO>> getArticleListAll(@RequestParam("page") Integer pageNum,
                                                          @RequestParam("size") Integer pageSize,
                                                          @RequestParam(value = "key", required = false) String title){
        PageInfo<ArticleDTO> pageInfo = articleService.getArticleList(null, pageNum, pageSize, title);
        return Result.success(pageInfo);
    }

    /**
     * 通过文章id获取文章对象
     * @param id 文章id
     * @return 返回文章对象
     */
    @GetMapping("/{id}")
    public Result<ArticleDTO> getArticle(@PathVariable("id") Integer id){
        ArticleDTO articleDTO = articleService.getArticleById(id, true);
        return Result.success(articleDTO);
    }

    /**
     * 添加文章
     * @param articleDTO 封装的文章DTO对象 里面包含文章对象和分类以及标签列表
     * @return 反正操作成功或者失败
     */
    @PostMapping
    public Result<String> addArticle(@RequestBody ArticleDTO articleDTO){
        if (articleDTO.getTitle() != null){
            articleService.saveArticle(articleDTO);
            return Result.success("添加成功");
        }
        return Result.error("标题不能为空");
    }

    /**
     * 通过id更新文章
     * @param articleDTO 封装好的对象
     * @return 返回操作成功或者失败
     */
    @PutMapping
    public Result<String> updateArticle(@RequestBody ArticleDTO articleDTO){
        if(articleDTO.getTitle() != null && articleDTO.getId() != null){
            articleService.updateArticle(articleDTO);
            return Result.success("更新成功");
        }
        return Result.error("ID和标题不能为空");
    }

    /**
     * 通过id更新文章的 isComment或status属性
     * @param article 入参的封装对象 必需参数为[id, isComment, status]
     * @return 返回操作成功或失败
     */
    @PutMapping("/type")
    public Result<String> updateArticleStatus(@RequestBody Article article){
        if (!BaseUtil.checkObjectFieldIsNull(article, "id", "status", "isComment")){
            articleService.updateArticleType(article.getId(), article.getIsComment(), article.getStatus());
            return Result.success("修改成功");
        }
        return Result.error("参数错误");
    }

    /**
     * 通过文章id删除文章
     * @param id 文章id
     * @return 返回成功或失败
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteArticle(@PathVariable("id") Integer id){
        articleService.deleteArticle(id);
        return Result.success("删除成功");
    }

    /**
     * 前台页面获取所有状态正常的文章列表
     * @param pageNum 页码 用于文章翻页
     * @return 返回文章列表或空
     */
    @GetMapping("/list")
    public Result<PageInfo<ArticleDTO>> getArticleList(@RequestParam("page") Integer pageNum){
        PageInfo<ArticleDTO> pageInfo = articleService.getArticleList(1, pageNum, 5, null);
        return Result.success(pageInfo);
    }

    /**
     * 通过分类id获取所关联的文章列表
     * @param categoryId 分类id
     * @param page 页码
     * @return 返回关联的文章列表和详细分页信息
     */
    @GetMapping("/list/category")
    public Result<PageInfo<Article>> getArticleListByCategoryId(@RequestParam("id") Integer categoryId,
                                                                @RequestParam("page") Integer page){
        List<Article> articleList = articleService.getArticleListByTagIdOrCategoryId(null, categoryId, page, 10);
        PageInfo<Article> info = new PageInfo<>(articleList);
        return Result.success(info);
    }

    /**
     * 通过标签id获取所关联的文章列表
     * @param tagId 标签id
     * @param page 页码
     * @return 返回关联的文章列表和详细分页信息
     */
    @GetMapping("/list/tag")
    public Result<PageInfo<Article>> getArticleListByTagId(@RequestParam("id") Integer tagId,
                                                                @RequestParam("page") Integer page){
        List<Article> articleList = articleService.getArticleListByTagIdOrCategoryId(tagId, null, page, 10);
        PageInfo<Article> info = new PageInfo<>(articleList);
        return Result.success(info);
    }

    /**
     * 前台页码通过id获取文章详细信息
     * @param id 文章id
     * @return 返回封装的文章DTO对象 和当前文章的上一篇以及下一篇
     */
    @GetMapping("/info/{id}")
    public Result<Map<String, Object>> getArticleInfo(@PathVariable("id") Integer id){
        ArticleDTO articleDTO = articleService.getArticleById(id, true);
        if (articleDTO == null){
            throw new CustomException("对象不存在");
        }
        //从redis中获取文章最新的点击数 因为service层加了缓存 所以放在controller层来设置
        redisUtil.zSetIncrement(RedisUtil.BLOG_TOP_REDIS_KEY_PREFIX, articleDTO.getTitle(), 1.0);
        Double clickNum = redisUtil.zScope(RedisUtil.BLOG_TOP_REDIS_KEY_PREFIX, articleDTO.getTitle());
        articleDTO.setClickNum(clickNum.intValue());
        //设置文章的上一篇和下一篇
        Map<String, Object> returnMap = new HashMap<>(3);
        //往map中添加当前文章
        returnMap.put("article", articleDTO);
        //添加当前文章的上一篇文章
        Article upArticle = articleService.getCurrentArticleUpArticle(articleDTO.getCreateTime());
        returnMap.put("up", upArticle);
        //添加当前文章是下一篇文章
        Article downArticle = articleService.getCurrentArticleDownArticle(articleDTO.getCreateTime());
        returnMap.put("down", downArticle);
        return Result.success(returnMap);
    }

    /**
     * 前台页面搜索文章发送文章的title关键字 通过title去模糊匹配数据库并返回文章列表 列表中的文章对象属性只有title和id
     * @param title 文章标题
     * @return 返回符合的文章列表或空
     */
    @GetMapping("/search")
    public Result<List<Article>> searchArticle(@RequestParam("title") String title){
        List<Article> articleList = articleService.searchArticleByTitle(title);
        return Result.success(articleList);
    }

    /**
     * 获取文章排行榜 根据文章点击数排序
     * @return 返回排行前5的文章标题
     */
    @GetMapping("/top")
    public Result<List<String>> getArticleTop(){
        List<String> titleList = articleService.getArticleTop();
        return Result.success(titleList);
    }
}
