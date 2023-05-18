package com.zeroxn.blog.web.user.controller;

import com.zeroxn.blog.entity.UserLink;
import com.zeroxn.blog.web.user.service.UserLinkService;
import com.zeroxn.blog.core.utils.BaseUtil;
import com.zeroxn.blog.core.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ran
 * @date
 * UserLink 联系方式控制层
 */
@RestController
@RequestMapping("/user/link")
public class UserLinkController {
    private final UserLinkService userLinkService;
    public UserLinkController(UserLinkService userLinkService){
        this.userLinkService = userLinkService;
    }

    /**
     * 后台管理页面获取全部联系方式列表
     * @return 返回联系方式列表或空
     */
    @GetMapping
    public Result<List<UserLink>> getUserLinkList(){
        List<UserLink> userLinkList = userLinkService.getUserLinkList(null);
        return Result.success(userLinkList);
    }

    /**
     * 添加联系方式
     * @param userLink 入参封装的对象
     * @return 返回操作成功或失败
     */
    @PostMapping
    public Result<String> addUserLink(@RequestBody UserLink userLink){
        //判断必需参数是否为空
        if (!BaseUtil.checkObjectFieldIsNull(userLink, "name", "url", "logoUrl")){
            userLinkService.addUserLink(userLink);
            return Result.success("添加成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 更新联系方式
     * @param userLink 入参封装的对象
     * @return 返回操作成功或对象
     */
    @PutMapping
    public Result<String> updateUserLink(@RequestBody UserLink userLink){
        //检查必需参数是否为空
        if (!BaseUtil.checkObjectFieldIsNull(userLink, "id", "name", "url", "logoUrl")){
            userLinkService.updateUserLink(userLink);
            return Result.success("更新成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 更新联系方式状态
     * @param userLink
     * @return
     */
    @PutMapping("/status")
    public Result<String> updateUserLinkStatus(@RequestBody UserLink userLink){
        //检查必需参数是否为空
        if (!BaseUtil.checkObjectFieldIsNull(userLink, "id", "status")){
            userLinkService.updateUserLinkStatus(userLink.getId(), userLink.getStatus());
            return Result.success("操作成功！");
        }
        return Result.error("参数错误");
    }

    /**
     * 通过联系方式id删除联系方式
     * @param id 联系方式id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteUserLink(@PathVariable("id") Integer id){
        userLinkService.deleteUserLink(id);
        return Result.success("删除成功");
    }

    /**
     * 前台页面获取联系方式列表
     * @return
     */
    @GetMapping("/list")
    public Result<List<UserLink>> listUserLink(){
        //获取所有status == 1的联系方式
        List<UserLink> userLinkList = userLinkService.getUserLinkList(1);
        return Result.success(userLinkList);
    }
}
