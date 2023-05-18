package com.zeroxn.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ran
 * @date
 * 文章实体类 对应数据库中的 tb_article 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article implements Serializable {
    /**
     * 文章id
     */
    private Integer id;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章简介
     */
    private String summary;
    /**
     * 文章封面
     */
    private String cover;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章是否开启评论
     */
    private Boolean isComment;
    /**
     * 文章被点击数
     */
    private Integer clickNum;
    /**
     * 文章创建时间
     */
    private LocalDateTime createTime;
    /**
     * 文章最后更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 文章状态 1 已发布 0 待发布
     */
    private Integer status;
    /**
     * 文章对应的分类/标签列表
     */
    private List<Label> labelList;

    public Article(Integer id, String title, String summary, String cover) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.cover = cover;
    }

    public Article(String title, Integer clickNum) {
        this.title = title;
        this.clickNum = clickNum;
    }
}
