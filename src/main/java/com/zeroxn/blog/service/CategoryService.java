package com.zeroxn.blog.service;

import com.zeroxn.blog.entity.Category;

import java.util.List;

/**
 * @author ran
 * @date
 */
public interface CategoryService {
    /**
     * 通过status来判断获取什么状态的分类列表 可以为空 为空则获取全部状态
     * @param status 分类状态
     * @return 返回符合状态的分类列表或空
     */
    List<Category> getCategoryList(Integer status);

    /**
     * 添加分类
     * @param category
     */
    void addCategory(Category category);

    /**
     * 通过id获取分类详情
     * @param id 分类id
     * @return 返回分类对象或空
     */
    Category getCategoryById(Integer id);

    /**
     * 通过分类id更新分类状态
     * @param id 分类id
     * @param status 分类状态
     */
    void updateCategoryStatus(Integer id, Integer status);

    /**
     * 通过id删除分类
     * @param id 分类id
     */
    void deleteCategory(Integer id);

    /**
     * 通过分类id列表返回包含详细信息的分类列表
     * @param categoryIdList 分类id列表
     * @return 返回分类列表
     */
    List<Category> getCategoryListByIdList(List<Integer> categoryIdList);
}
