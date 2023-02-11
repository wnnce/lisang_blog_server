package com.zeroxn.blog.entity.artalk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author ran
 * @date
 * Artalk用户实体类 对应数据库中的 artalk_user 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArtalkUser {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 用户最后发表评论的时间
     */
    private LocalDateTime updatedAt;
    /**
     * 用户名
     */
    private String name;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户留下的网址
     */
    private String link;
    /**
     * 用户自定义标签名
     */
    private String badgeName;
    /**
     * 用户自定义标签的背景色
     */
    private String badgeColor;
    /**
     * 最后一次发表评论的ip
     */
    private String lastIp;
    /**
     * 最后发表评论的ua
     */
    private String lastUa;
    /**
     * 是否是管理员
     */
    private Boolean isAdmin;
    /**
     * 是否有邮箱
     */
    private Boolean receiveEmail;
    /**
     * 是否可以修改配置
     */
    private Boolean isInConf;
}
