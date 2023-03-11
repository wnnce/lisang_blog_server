package com.zeroxn.blog.controller;

import com.zeroxn.blog.entity.Label;
import com.zeroxn.blog.service.LabelService;
import com.zeroxn.blog.utils.BaseUtil;
import com.zeroxn.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lisang
 * @Date 2023/3/10 下午6:59
 * @Version 1.0
 */
@RestController
@RequestMapping("/label")
public class LabelController {
    @Autowired
    private LabelService labelService;
    @GetMapping
    public Result<List<Label>> getLabelListAll(@RequestParam("status") Integer flag){
        List<Label> labelList = labelService.listLabel(flag, null);
        return Result.success(labelList);
    }
    @PostMapping
    public Result<String> addLabel(@RequestBody Label label){
        if(!BaseUtil.checkObjectFieldIsNull(label, "name", "flag")){
            labelService.saveLabel(label);
            return Result.success("添加成功");
        }
        return Result.error("参数错误");
    }
    @PutMapping
    public Result<String> updateLabelStatus(@RequestBody Label label){
        if(!BaseUtil.checkObjectFieldIsNull(label, "id", "status")){
            labelService.updateLabelStatus(label.getId(), label.getStatus());
            return Result.success("更新成功");
        }
        return Result.error("参数错误");
    }
    @DeleteMapping("/{id}")
    public Result<String> deleteLabel(@PathVariable("id") Integer id){
        labelService.deleteLabel(id);
        return Result.success("删除成功");
    }
    @GetMapping("/list")
    public Result<List<Label>> getLabelListByStatus(){
        List<Label> labelList = labelService.listLabel(null, 1);
        return Result.success(labelList);
    }
    @GetMapping("/info/{id}")
    public Result<Label> getLabelInfo(@PathVariable("id") Integer id){
        Label label = labelService.getLabelById(id);
        return Result.success(label);
    }
}
