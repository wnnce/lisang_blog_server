package com.zeroxn.blog.web.user.mapper;

import com.zeroxn.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ran
 * @date
 * 操作数据库中的 tb_user 表
 */
@Mapper
public interface UserMapper {
    /**
     * 通过用户名和密码获取用户id 判断用户名和密码是否正确
     * @param username 用户名
     * @param passwd 密码
     * @return 返回用户ID或空
     */
    Integer login(@Param("username") String username, @Param("passwd") String passwd);

    /**
     * 获取用户邮箱
     * @return 返回邮箱
     */
    String getEmail();

    /**
     * 获取全部用户信息
     * @return 返回用户信息
     */
    User getUserAll();

    /**
     * 获取用户信息 只获取 author和avatar
     * @return 返回用户信息
     */
    User getUser();

    /**
     * 通过id和passwd来获取用户id 判断就密码是否正确
     * @param id 用户id
     * @param passwd 密码
     * @return
     */
    Integer getUserByIdAndPasswd(@Param("id") Integer id, @Param("passwd") String passwd);

    /**
     * 更新用户密码
     * @param id 用户id
     * @param passwd 新密码
     */
    void updateUserPasswd(@Param("id") Integer id, @Param("passwd") String passwd);

    /**
     * 更新用户信息
     * @param user
     */
    void updateUser(User user);
}
