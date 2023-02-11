package com.zeroxn.blog.controller;

import com.github.pagehelper.PageInfo;
import com.zeroxn.blog.entity.Article;
import com.zeroxn.blog.entity.Tag;
import com.zeroxn.blog.service.ArticleService;
import com.zeroxn.blog.service.ArticleTagService;
import com.zeroxn.blog.service.TagService;
import com.zeroxn.blog.utils.BaseUtil;
import com.zeroxn.blog.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ran
 * @date
 * Tag 分类控制层
 * 逻辑与CategoryController一致
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping
    public Result<List<Tag>> getTagListAll(){
        List<Tag> tagList = tagService.getTagList(null);
        return Result.success(tagList);
    }
    @PostMapping
    public Result<String> addTag(@RequestBody Tag tag){
        if (!BaseUtil.checkObjectFieldIsNull(tag, "name")){
            tagService.addTag(tag);
            return Result.success("添加成功！");
        }
        return Result.error("参数错误");
    }
    @PutMapping
    public Result<String> updateTagStatus(@RequestBody Tag tag){
        if (!BaseUtil.checkObjectFieldIsNull(tag, "id", "status")){
            tagService.updateTagStatus(tag.getId(), tag.getStatus());
            return Result.success("操作成功");
        }
        return Result.error("参数错误");
    }
    @DeleteMapping("/{id}")
    public Result<String> deleteTag(@PathVariable("id") Integer id){
        tagService.deleteTag(id);
        return Result.success("删除成功");
    }
    @GetMapping("/list")
    public Result<List<Tag>> getTagList(){
        List<Tag> tagList = tagService.getTagList(1);
        return Result.success(tagList);
    }
    @GetMapping("/info/{id}")
    public Result<Tag> getTagInfo(@PathVariable("id") Integer id){
        Tag tag = tagService.getTagById(id);
        return Result.success(tag);
    }
}
