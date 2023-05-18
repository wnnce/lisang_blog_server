package com.zeroxn.blog.web.user.service;

import com.zeroxn.blog.entity.UserLink;

import java.util.List;

/**
 * @author ran
 * @date
 */
public interface UserLinkService {
    /**
     * 通过status属性获取联系方式列表 可以为空 为空则获取所有
     * @param status 联系方式的status属性
     * @return 返回联系方式列表或空
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

    /**
     * 通过id更新联系方式的status属性
     * @param id 联系方式id
     * @param status 联系方式status
     */
    void updateUserLinkStatus(Integer id, Integer status);

    /**
     * 通过id删除联系方式
     * @param id 联系方式ID
     */
    void deleteUserLink(Integer id);
}
