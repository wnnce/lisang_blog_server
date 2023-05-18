package com.zeroxn.blog.entity.artalk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author ran
 * @date
 * Artalk评论页实体类 对应数据库中的 artalk_pages 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArtalkPage {
    /**
     * 评论页id
     */
    private Integer id;
    /**
     * 评论页创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 评论页最后发表评论的时间
     */
    private LocalDateTime updatedAt;
    /**
     * 评论页 key  默认为网址uri
     */
    private String key;
    /**
     * 评论页标题
     */
    private String title;
    /**
     * 是否仅管理员可评
     */
    private Boolean adminOnly;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 评论页点赞数
     */
    private Integer voteUp;
    /**
     * 评论页点踩数
     */
    private Integer voteDown;
    /**
     * 评论页浏览量
     */
    private Integer pv;
}
