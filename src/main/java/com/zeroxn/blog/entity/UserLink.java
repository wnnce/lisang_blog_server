package com.zeroxn.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author ran
 * @date
 * 联系方式实体类 对应数据库中 tb_user_link 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLink {
    /**
     * 联系方式id
     */
    private Integer id;
    /**
     * 联系方式名称
     */
    private String name;
    /**
     * 联系方式指向的url
     */
    private String url;
    /**
     * 联系方式logo的地址
     */
    private String logoUrl;
    /**
     * 联系方式的创建时间
     */
    private LocalDateTime createTime;
    /**
     * 联系方式的更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 联系方式的状态 1 启用 0 禁用
     */
    private Integer status;
}
