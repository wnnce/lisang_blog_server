package com.zeroxn.blog.web.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.zeroxn.blog.entity.User;
import com.zeroxn.blog.web.user.service.UserService;
import com.zeroxn.blog.core.utils.BaseUtil;
import com.zeroxn.blog.core.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author ran
 * @date
 * User 用户控制层
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * 调用阿里云邮箱api发送验证码邮件  弃用
     * @return 返回发送成功或失败
    @GetMapping("/sendCode")
    public Result<String> sendCode(){
        //获取6位的随机验证码
        String code = BaseUtil.randomCode();
        //获取email 数据库中就一个用户
        String email = userService.getEmail();
        try{
            log.info(email + "请求验证码，验证码为：" + code);
            AliyunUtil.sendCode(email, code);
            //每次获取验证码之后保存验证码之前先清空之前保存的验证码
            StpUtil.getAnonTokenSession().delete("checkCode");
            StpUtil.getAnonTokenSession().set("checkCode", code);
            return Result.success("验证码发送成功");
        }catch (Exception ex){
            throw new CustomException("验证码发送失败！");
        }
    }*/

    /**
     * 用户登录方法
     * @param user 封装的user对象
     * @return 返回登录成功或失败原因
     */
    @PostMapping("/login")
    public Result<String> userLogin(@RequestBody User user){
        //先判断验证码是否一致 弃用
        /*Object code = StpUtil.getAnonTokenSession().get("checkCode");
        if(Integer.parseInt(code.toString()) != user.getCode()){
            return Result.error("验证码错误");
        }*/
        //通过username和password来判断是否登录成功
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        Integer userId = userService.login(user.getUsername(), user.getPassword());
        if(userId == null){
            return Result.error("账号或者密码错误");
        }
        StpUtil.login(userId);
        //登录成功后销毁验证码
        StpUtil.getAnonTokenSession().delete("checkCode");
        log.info("ID为" + userId + "的用户成功登录");
        return Result.success("登录成功");
    }

    /**
     * 后台管理页面获取所有用户信息
     * @return 返回用户信息
     */
    @GetMapping
    public Result<User> getUserAll(){
        User user = userService.getUserAll();
        return Result.success(user);
    }

    /**
     * 更新用户信息
     * @param user 入参封装的user对象
     * @return 返回操作成功或失败
     */
    @PutMapping
    public Result<String> updateUser(@RequestBody User user){
        //判断必需的参数是否为空
        if (!BaseUtil.checkObjectFieldIsNull(user, "id", "author", "username", "email", "avatar")){
            userService.updateUser(user);
            return Result.success("更新成功！");
        }
        return Result.error("用户信息不能为空");
    }

    /**
     * 更新用户密码
     * @param id 用户id
     * @param nowPasswd 旧密码
     * @param newPasswd 新密码
     * @param rePasswd 确认密码
     * @return
     */
    @PutMapping("/repass")
    public Result<String> updateUserPasswd(@RequestParam("id") Integer id, @RequestParam("nowPasswd") String nowPasswd,
                                           @RequestParam("newPasswd") String newPasswd,
                                           @RequestParam("rePasswd") String rePasswd){
        userService.updateUserPasswd(id, nowPasswd, newPasswd, rePasswd);
        //密码更新后要求重新登录
        StpUtil.logout(id);
        return Result.success("更新成功");
    }

    /**
     * 前台页面获取用户信息
     * @return 返回用户信息 只返回author、avatar
     */
    @GetMapping("/info")
    public Result<User> getUserInfo(){
        User user = userService.getUser();
        return Result.success(user);
    }

    /**
     * 用户退出登录
     * @param user 入参封装的user对象
     * @return
     */
    @PostMapping("/logout")
    public Result<String> userSignOut(@RequestBody User user){
        if (user.getId() != null){
            StpUtil.logout(user.getId());
            log.info("ID为" + user.getId() + "的用户退出登录");
            return Result.success("退出登录成功");
        }
        return Result.error("参数错误");
    }
}
