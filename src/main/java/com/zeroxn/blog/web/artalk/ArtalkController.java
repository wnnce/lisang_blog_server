package com.zeroxn.blog.web.artalk;

import com.github.pagehelper.PageInfo;
import com.zeroxn.blog.entity.artalk.ArtalkComment;
import com.zeroxn.blog.entity.artalk.ArtalkPage;
import com.zeroxn.blog.entity.artalk.ArtalkUser;
import com.zeroxn.blog.core.utils.BaseUtil;
import com.zeroxn.blog.core.utils.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ran
 * @date 2023-2-3
 * Artalk评论控制层
 */
@RestController
@RequestMapping("/artalk")
public class ArtalkController {
    private final ArtalkService artalkService;
    public ArtalkController(ArtalkService artalkService){
        this.artalkService = artalkService;
    }

    /**
     * 获取所有评论列表
     * @param pageNum 页码 用户评论翻页
     * @param pageSize 每页记录数
     * @param key 搜索关键字 不是必须的
     * @return 返回空或者评论列表的详细分页信息
     */
    @GetMapping("/comments")
    public Result<PageInfo<ArtalkComment>> getArtalkCommentList(@RequestParam("page") Integer pageNum,
                                                            @RequestParam("size") Integer pageSize,
                                                            @RequestParam(value = "key", required = false) String key){
        List<ArtalkComment> artalkCommentList = artalkService.getArtalkCommentList(pageNum, pageSize);
        PageInfo<ArtalkComment> info = new PageInfo<>(artalkCommentList);
        return Result.success(info);
    }

    /**
     * 更新评论的一些属性
     * @param artalkComment 入参封装的实体类 必需参数为[id, isCollapsed, isPending, isPinned]
     * @return 返回成功或者错误
     */
    @PutMapping("/comments/type")
    public Result<String> updateArtalkCommentType(@RequestBody ArtalkComment artalkComment){
        if (!BaseUtil.checkObjectFieldIsNull(artalkComment, "id", "isCollapsed", "isPending", "isPinned")){
            artalkService.updateArtalkCommentType(artalkComment);
            return Result.success("更新成功");
        }
        return Result.error("参数错误");
    }

    /**
     * 通过评论的id删除单个评论
     * @param id 评论的ID 对应数据库的id字段
     * @return 删除成功就返回成功 删除失败则会抛出异常 由异常处理器负责返回
     */
    @DeleteMapping("/comments/{id}")
    public Result<String> deleteArtalkComment(@PathVariable("id") Integer id){
        artalkService.deleteArtalkCommentById(id, true);
        return Result.success("删除成功");
    }

    /**
     * 获取所有评论页
     * @param pageNum 页码 用户翻页
     * @param pageSize 每页的记录条数
     * @return 返回评论页列表的详细分页信息或者空
     */
    @GetMapping("/pages")
    public Result<PageInfo<ArtalkPage>> getArtalkPageList(@RequestParam("page") Integer pageNum,
                                                      @RequestParam("size") Integer pageSize){
        List<ArtalkPage> artalkPageList = artalkService.getArtalkPageList(pageNum, pageSize);
        PageInfo<ArtalkPage> info = new PageInfo<>(artalkPageList);
        return Result.success(info);
    }

    /**
     * 更新评论页的 adminOnly 属性
     * @param artalkPage 入参封装的实体类对象 必需参数为[id, adminOnly]
     * @return 返回成功或者失败
     */
    @PutMapping("/pages/type")
    public Result<String> updateArtalkPageType(@RequestBody ArtalkPage artalkPage){
        if(!BaseUtil.checkObjectFieldIsNull(artalkPage, "id", "adminOnly")){
            artalkService.updateArtalkPageType(artalkPage.getId(), artalkPage.getAdminOnly());
            return Result.success("更新成功");
        }
        return Result.error("参数错误");
    }

    /**
     * 通过评论页的id删除单个评论页
     * @param id 评论页的id
     * @return 返回成功或者异常
     */
    @DeleteMapping("/pages/{id}")
    public Result<String> deleteArtalkPage(@PathVariable("id") Integer id){
        artalkService.deleteArtalkPageById(id);
        return Result.success("操作成功");
    }

    /**
     * 获取所有评论用户
     * @param pageNum 页码 用户翻页
     * @param pageSize 每页的记录条数
     * @return 返回评论用户列表的详细分页信息或者空
     */
    @GetMapping("/users")
    public Result<PageInfo<ArtalkUser>> getArtalkUserList(@RequestParam("page") Integer pageNum,
                                                      @RequestParam("size") Integer pageSize){
        List<ArtalkUser> artalkUserList = artalkService.getArtalkUserList(pageNum, pageSize);
        PageInfo<ArtalkUser> info = new PageInfo<>(artalkUserList);
        return Result.success(info);
    }

    /**
     * 通过评论用户的id删除单个用户
     * @param id 评论用户的id
     * @return 返回成功或者异常
     */
    @DeleteMapping("/users/{id}")
    public Result<String> deleteArtalkUser(@PathVariable("id") Integer id){
        artalkService.deleteArtalkUserById(id);
        return Result.success("删除成功");
    }
}
