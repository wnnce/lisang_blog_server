package com.zeroxn.blog.web.article.mapper;

import com.zeroxn.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ran
 * @date
 * 操作数据库中的 tb_article表
 */
@Mapper
public interface ArticleMapper {
    /**
     * 通过title和status参数获取文章列表 排序通过文章创建时间逆序排序
     * @param status 文章status属性 可以为空 为空则不添加条件
     * @param title 文章title属性 可以为空 为空则不添加条件
     * @return 返回符合条件的文章列表
     */
    List<Article> getArticleList(@Param("status") Integer status, @Param("title") String title);

    /**
     * 通过id列表获取返回包含详细信息的文章列表
     * @param articleIdList 文章id列表
     * @return 文章列表详细信息包含id、title、summary、cover
     */
    List<Article> getArticleListByIdList(@Param("articleIdList") List<Integer> articleIdList);

    /**
     * 通过id获取文章详情
     * @param id 文章id
     * @param isGetContent 因为文章内容可能非常长 在一些不需要文章内容的场景下可能会影响性能 true则为获取正文 false则不获取正文
     * @return Article对象
     */
    Article getArticleById(@Param("id") Integer id, @Param("isGetContent") boolean isGetContent);

    /**
     * 保存文章
     * @param article
     */
    void saveArticle(Article article);

    /**
     * 更新文章
     * @param article
     */
    void updateArticle(Article article);

    /**
     * 通过id更新文章isComment和status属性
     * @param id 文章id
     * @param isComment 文章是否开启评论
     * @param status 文章状态
     */
    void updateArticleType(@Param("id") Integer id, @Param("isComment") Boolean isComment, @Param("status") Integer status);

    /**
     * 通过id删除文章
     * @param id 文章id
     */
    void deleteArticle(@Param("id") Integer id);

    /**
     * 通过文章创建时间获取当前文章的上一篇文章
     * @param createTime 文章创建时间
     * @return 返回上一篇文章
     */
    Article getCurrentArticleUpArticle(@Param("createTime") LocalDateTime createTime);

    /**
     * 通过文章创建时间获取当前文章的下一篇文章
     * @param createTime 文章创建时间
     * @return 返回下一篇文章
     */
    Article getCurrentArticleDownArticle(@Param("createTime") LocalDateTime createTime);

    /**
     * 通过title模糊匹配文章 通过文章创建时间逆序排序返回前7条数据
     * @param title 文章标题
     * @return
     */
    List<Article> searchArticleByTitle(@Param("title") String title);

    /**
     * 获取全部文章的点击数和title
     * @return
     */
    List<Article> getArticleClickNumAndTitleList();

    /**
     * 通过文章title更新文章的点击数
     * @param title 文章标题
     * @param clickNum 文章点击数
     */
    void updateArticleClickNumByTitle(@Param("title") String title, @Param("clickNum") Integer clickNum);
}
