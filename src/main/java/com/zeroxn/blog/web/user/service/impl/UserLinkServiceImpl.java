package com.zeroxn.blog.web.user.service.impl;

import com.zeroxn.blog.entity.UserLink;
import com.zeroxn.blog.core.exception.CustomException;
import com.zeroxn.blog.web.user.mapper.UserLinkMapper;
import com.zeroxn.blog.web.user.service.UserLinkService;
import com.zeroxn.blog.core.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ran
 * @date
 * UserLinkService 的实现类
 */
@Service
public class UserLinkServiceImpl implements UserLinkService {
    private final UserLinkMapper userLinkMapper;
    public UserLinkServiceImpl(UserLinkMapper userLinkMapper){
        this.userLinkMapper = userLinkMapper;
    }
    @Override
    public List<UserLink> getUserLinkList(Integer status) {
        return userLinkMapper.getUserLinkList(status);
    }

    @Override
    public void addUserLink(UserLink userLink) {
        userLink.setCreateTime(LocalDateTime.now());
        userLink.setUpdateTime(LocalDateTime.now());
        userLinkMapper.addUserLink(userLink);
    }

    @Override
    public void updateUserLink(UserLink userLink) {
        userLink.setUpdateTime(LocalDateTime.now());
        userLinkMapper.updateUserLink(userLink);
    }

    @Override
    public void updateUserLinkStatus(Integer id, Integer status) {
        //检查status属性是否符合要求
        if (BaseUtil.checkStatus(status)){
            userLinkMapper.updateUserLinkStatus(id, status);
        }else {
            throw new CustomException("参数错误");
        }

    }

    @Override
    public void deleteUserLink(Integer id) {
        userLinkMapper.deleteUserLink(id);
    }
}
