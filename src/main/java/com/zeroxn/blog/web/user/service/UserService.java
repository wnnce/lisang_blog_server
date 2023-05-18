package com.zeroxn.blog.web.user.service;

import com.zeroxn.blog.entity.User;

/**
 * @author ran
 * @date
 */
public interface UserService {
    /**
     * 用户登录
     * @param username 用户名
     * @param passwd md5加密后的密码
     * @return 返回用户id
     */
    Integer login(String username, String passwd);

    /**
     * 获取用户email
     * @return 用户邮箱
     */
    String getEmail();

    /**
     * 获取所有用户信息
     * @return 返回用户信息
     */
    User getUserAll();

    /**
     * 更新用户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 更新用户密码
     * @param id 用户id
     * @param nowPasswd 旧密码
     * @param newPasswd 新密码
     * @param rePasswd 重复密码
     */
    void updateUserPasswd(Integer id, String nowPasswd, String newPasswd, String rePasswd);

    /**
     * 获取部分用户信息
     * @return
     */
    User getUser();
}
