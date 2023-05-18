package com.zeroxn.blog.web.article.mapper;

import com.zeroxn.blog.entity.ArticleLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lisang
 * @Date 2023/3/10 下午8:02
 * @Version 1.0
 */
@Mapper
public interface ArticleLabelMapper {
    /**
     * 通过分类/标签id获取所关联的文章数量
     * @param labelId 分类/标签id
     * @return 该分类/标签关联的文章数量
     */
    Integer countArticleByLabelId(@Param("labelId") Integer labelId);

    /**
     * 通过文章id获取该文章关联的所有分类/标签的id
     * @param articleId 文章id
     * @return 该文章所关联的所有分类/标签id列表
     */
    List<Integer> listLabelIdByArticleId(@Param("articleId") Integer articleId);

    /**
     * 通过分类/标签id获取所关联的所有文章id
     * @param labelId 分类/标签id
     * @return 所关联的所有文章id列表
     */
    List<Integer> listArticleIdByLabelId(@Param("labelId") Integer labelId);

    /**
     * 保存文章和分类/标签的对应关系
     * @param articleLabelList 实体类列表
     */
    void saveArticleLabelList(List<ArticleLabel> articleLabelList);

    /**
     * 通过文章id删除该文章在表中所有的分类/标签对应关系
     * @param articleId 文章id
     */
    void deleteArticleLabelByArticleId(@Param("articleId") Integer articleId);
}
