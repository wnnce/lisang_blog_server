package com.zeroxn.blog.entity.artalk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author ran
 * @date
 * Artali评论实体类 对于数据库中的 artalk_comments表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArtalkComment {
    /**
     * 评论的id
     */
    private Integer id;
    /**
     * 评论时间
     */
    private LocalDateTime createdAt;
    /**
     * 评论的更新时间
     */
    private LocalDateTime updatedAt;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论对应评论页中的key属性
     */
    private String pageKey;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 发表评论的用户id
     */
    private Integer userId;
    /**
     * 用户发表评论时使用的ua
     */
    private String ua;
    /**
     * 用户评论时的ip地址
     */
    private String ip;
    /**
     * 是否是二级评论 0代表不是 否则代表上级评论的id
     */
    private Integer rid;
    /**
     * 评论是否被折叠
     */
    private Boolean isCollapsed;
    /**
     * 评论是否处于审核
     */
    private Boolean isPending;
    /**
     * 评论是否置顶
     */
    private Boolean isPinned;
    /**
     * 评论被点赞数
     */
    private Integer voteUp;
    /**
     * 评论被点踩数
     */
    private Integer voteDown;
    /**
     * 页面标题 数据中没有这个字段 便于封装
     */
    private String pageTitle;
    /**
     * 评论用户名 数据中没有这个字段
     */
    private String username;
    /**
     * 如果是二级评论的话 便于封装上级评论
     */
    private ArtalkComment superComment;
}
