package com.zeroxn.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author ran
 * @date
 * 友情链接实体类 对应数据库中 tb_links 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Link {
    /**
     * 友情链接id
     */
    private Integer id;
    /**
     * 友情链接标题
     */
    private String title;
    /**
     * 友情链接简介
     */
    private String summary;
    /**
     * 此友情链接的用户名
     */
    private String username;
    /**
     * 友情链接的url
     */
    private String url;
    /**
     * 友情链接的logo地址
     */
    private String logoUrl;
    /**
     * 友情链接的创建时间
     */
    private LocalDateTime createTime;
    /**
     * 友情链接的最后更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 友情链接是否展示
     */
    private Boolean isShow;
}
