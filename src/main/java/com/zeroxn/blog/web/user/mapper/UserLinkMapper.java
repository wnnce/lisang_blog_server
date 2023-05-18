package com.zeroxn.blog.web.user.mapper;

import com.zeroxn.blog.entity.UserLink;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ran
 * @date
 * 操作数据库中的 tb_user_link 表
 */
@Mapper
public interface UserLinkMapper {
    /**
     * 通过status属性获取联系方式列表 可以为空 为空则获取所有
     * @param status
     * @return
     */
    List<UserLink> getUserLinkList(Integer status);

    /**
     * 添加联系方式
     * @param userLink
     */
    void addUserLink(UserLink userLink);

    /**
     * 更新联系方式
     * @param userLink
     */
    void updateUserLink(UserLink userLink);
    void updateUserLinkStatus(@Param("id") Integer id, @Param("status") Integer status);
    @Delete("delete from tb_user_link where id = #{id}")
    void deleteUserLink(@Param("id") Integer id);
}
