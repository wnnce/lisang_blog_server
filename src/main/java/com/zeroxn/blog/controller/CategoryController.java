package com.zeroxn.blog.controller;

import com.zeroxn.blog.entity.Category;
import com.zeroxn.blog.service.ArticleCategoryService;
import com.zeroxn.blog.service.CategoryService;
import com.zeroxn.blog.utils.BaseUtil;
import com.zeroxn.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ran
 * @date
 * Category分类控制层
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired ArticleCategoryService articleCategoryService;

    /**
     * 后台管理页面获取所有分类
     * @return 分类列表或空
     */
    @GetMapping
    public Result<List<Category>> getCategoryList(){
        List<Category> categoryList = categoryService.getCategoryList(null);
        return Result.success(categoryList);
    }

    /**
     * 添加分类
     * @param category 入参的封装对象
     * @return 返回成功或失败
     */
    @PostMapping
    public Result<String> addCategory(@RequestBody Category category){
        //name属性必需 检查是否为空
        if (!BaseUtil.checkObjectFieldIsNull(category, "name")){
            categoryService.addCategory(category);
            return Result.success("添加成功！");
        }
        return Result.error("参数错误");
    }

    /**
     * 通过ID更新分类状态
     * @param category 入参封装的category对象
     * @return 返回操作成功或失败
     */
    @PutMapping
    public Result<String> updateCategoryStatus(@RequestBody Category category){
        //检查id和status是否为空
        if (!BaseUtil.checkObjectFieldIsNull(category, "id", "status")){
            categoryService.updateCategoryStatus(category.getId(), category.getStatus());
            return Result.success("操作成功");
        }
        return Result.error("参数错误");
    }

    /**
     * 通过id删除分类
     * @param id 分类id
     * @return 返回成功或失败
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteCategory(@PathVariable("id") Integer id){
        categoryService.deleteCategory(id);
        return Result.success("删除成功");
    }

    /**
     * 前台页面获取所有状态为正常的分类列表
     * @return 返回分类列表或空
     */
    @GetMapping("/list")
    public Result<List<Category>> getCategoryForIdAndName(){
        //获取状态为正常的分类
        List<Category> categoryList = categoryService.getCategoryList(1);
        return Result.success(categoryList);
    }

    /**
     * 前台页面通过id获取分类详情
     * @param id 分类id
     * @return 返回分类对象或空
     */
    @GetMapping("/info/{id}")
    public Result<Category> getCategoryInfo(@PathVariable("id") Integer id){
        Category category = categoryService.getCategoryById(id);
        return Result.success(category);
    }
}