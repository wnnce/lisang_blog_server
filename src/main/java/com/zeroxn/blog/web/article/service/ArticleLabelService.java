package com.zeroxn.blog.web.article.service;

import com.zeroxn.blog.entity.ArticleLabel;

import java.util.List;

/**
 * @Author lisang
 * @Date 2023/3/10 下午7:54
 * @Version 1.0
 */
public interface ArticleLabelService {
    /**
     * 通过分类/标签id获取该分类/标签所关联的文章数量
     * @param labelId 分类/标签id
     * @return 返回所关联的文章数量或空
     */
    Integer countArticleByLabelId(Integer labelId);

    /**
     * 通过文章id获取所关联的分类/标签 id列表
     * @param articleId 文章id
     * @return 该文章所关联的分类/标签列表
     */
    List<Integer> listLabelIdByArticleId(Integer articleId);

    /**
     * 通过分类/标签id获取所关联的文章id列表
     * @param labelId 分类/标签id
     * @return 返回所有包含该分类/标签的文章id列表
     */
    List<Integer> listArticleIdByLabelId(Integer labelId);

    /**
     * 保存文章和分类/标签的对应关系
     * @param articleLabelList 文章，分类/标签对应关系的封装对象列表
     */
    void saveArticleLabelList(List<ArticleLabel> articleLabelList);

    /**
     * 通过文章id删除该文章所有的关联关系
     * @param articleId 文章id
     */
    void deleteArticleLabelByArticleId(Integer articleId);
}
