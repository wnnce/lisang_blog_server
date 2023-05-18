package com.zeroxn.blog.web.link;

import com.zeroxn.blog.core.utils.BaseUtil;
import com.zeroxn.blog.core.utils.Result;
import com.zeroxn.blog.entity.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ran
 * @date
 * Link 友情链接控制层
 */
@RestController
@RequestMapping("/links")
public class LinkController {
    private final LinkService linkService;
    public LinkController(LinkService linkService){
        this.linkService = linkService;
    }

    /**
     * 后台管理页面获取所有友情链接
     * @return 返回所有友情链接或空
     */
    @GetMapping
    public Result<List<Link>> getLinkListAll(){
        List<Link> linkList = linkService.getLinkListByIsShow(null);
        return Result.success(linkList);
    }

    /**
     * 添加友情链接
     * @param link 入参封装的对象
     * @return 返回操作成功或失败
     */
    @PostMapping
    public Result<String> saveLink(@RequestBody Link link){
        //title和url参数是必需的 判断是否为空
        if (!BaseUtil.checkObjectFieldIsNull(link, "title", "url")){
            linkService.saveLink(link);
            return Result.success("保存成功");
        }
        return Result.error("必填项不能为空");
    }

    /**
     * 更新友情链接
     * @param link 封装的对象
     * @return 返回操作成功或失败
     */
    @PutMapping
    public Result<String> updateLink(@RequestBody Link link){
        //更新友情链接需要id、title、url字段 判断是否为空
        if (!BaseUtil.checkObjectFieldIsNull(link, "id", "title", "url")){
            linkService.updateLinkById(link);
            return Result.success("更新成功");
        }
        return Result.error("必填项不能为空");
    }

    /**
     * 更新友情链接的isShow属性 （是否展示）
     * @param link 封装的对象
     * @return 返回操作成功或失败
     */
    @PutMapping("/type")
    public Result<String> updateLinkIsShow(@RequestBody Link link){
        //检查必需属性是否为空
        if (!BaseUtil.checkObjectFieldIsNull(link, "id", "isShow")){
            linkService.updateLinkIsShowById(link.getId(), link.getIsShow());
            return Result.success("更新成功");
        }
        return Result.error("参数错误");
    }

    /**
     * 通过id删除指定的友情链接
     * @param id 友情链接id
     * @return 返回操作成功或异常
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteLinkById(@PathVariable("id") Integer id){
        linkService.deleteLinkById(id);
        return Result.success("删除成功");
    }

    /**
     * 前台页面获取所有需要展示的友情链接
     * @return 需要展示的友情链接列表
     */
    @GetMapping("/list")
    public Result<List<Link>> getLinkList(){
        //指定获取isShow参数为true的友情链接
        List<Link> linkList = linkService.getLinkListByIsShow(true);
        return Result.success(linkList);
    }
}
