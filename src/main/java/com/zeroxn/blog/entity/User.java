package com.zeroxn.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author ran
 * @date
 * 用户实体类 对应数据库中 tb_user 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 作者名称
     */
    private String author;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     *  登录操作的验证码参数 数据库中没有该字段 弃用
     */
    //private Integer code;

    public User(String author, String avatar) {
        this.author = author;
        this.avatar = avatar;
    }
}
