package com.zeroxn.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ran
 * @date
 * 文章分类关系表的实体类 对应数据库中的 tb_article_category 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCategory {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 文章id
     */
    private Integer articleId;
    /**
     * 分类id
     */
    private Integer categoryId;
}
