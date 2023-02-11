package com.zeroxn.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ran
 * @date
 * 文章标签表的实体类 对应数据库中的 tb_article_tag 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTag {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 文章id
     */
    private Integer articleId;
    /**
     * 标签id
     */
    private Integer tagId;
}
