package com.zeroxn.blog.web.user.service.impl;

import com.zeroxn.blog.entity.User;
import com.zeroxn.blog.core.exception.CustomException;
import com.zeroxn.blog.web.user.mapper.UserMapper;
import com.zeroxn.blog.web.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author ran
 * @date
 * UserService的实现类
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    public UserServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }
    @Override
    public Integer login(String username, String passwd) {
        return userMapper.login(username, passwd);
    }

    @Override
    public String getEmail() {
        return userMapper.getEmail();
    }

    @Override
    public User getUserAll() {
        return userMapper.getUserAll();
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    /**
     * 更新密码之前需要先判断密码格式是否符合要求、确认密码和新密码是否一致
     * @param id 用户id
     * @param nowPasswd 旧密码
     * @param newPasswd 新密码
     * @param rePasswd 重复密码
     */
    @Override
    public void updateUserPasswd(Integer id, String nowPasswd, String newPasswd, String rePasswd) {
        if (nowPasswd.length() < 6 || newPasswd.length() < 6){
            throw new CustomException("密码格式错误");
        }
        if (!newPasswd.equals(rePasswd)){
            throw new CustomException("确认密码和新密码不一致");
        }
        //先判断通过id和旧密码能不能查询到用户 查询不到就说明旧密码错误 抛出异常
        nowPasswd = DigestUtils.md5DigestAsHex(nowPasswd.getBytes());
        Integer findUserId = userMapper.getUserByIdAndPasswd(id, nowPasswd);
        if (findUserId == null){
            throw new CustomException("旧密码错误");
        }
        newPasswd = DigestUtils.md5DigestAsHex(newPasswd.getBytes());
        //更新密码
        userMapper.updateUserPasswd(id, newPasswd);
    }

    @Override
    public User getUser() {
        return userMapper.getUser();
    }
}
