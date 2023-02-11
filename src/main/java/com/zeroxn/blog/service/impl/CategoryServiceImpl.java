package com.zeroxn.blog.service.impl;

import com.zeroxn.blog.entity.Category;
import com.zeroxn.blog.exception.CustomException;
import com.zeroxn.blog.mapper.CategoryMapper;
import com.zeroxn.blog.service.ArticleCategoryService;
import com.zeroxn.blog.service.CategoryService;
import com.zeroxn.blog.utils.BaseUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ran
 * @date
 * CategoryService实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCategoryService articleCategoryService;

    /**
     * 获取到分类列表后，再使用分类列表中的分类id获取每一条分类所关联的文章数
     * @param status 分类状态
     * @return 返回封装好的分类列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<Category> getCategoryList(Integer status){
        List<Category> categoryList = categoryMapper.getCategoryList(status);
        //遍历分类列表 通过id获取所关联的文章数量
        categoryList.forEach(category -> {
            Integer articleNum = articleCategoryService.getArticleCategoryNum(category.getId());
            category.setArticleNum(articleNum);
        });
        return categoryList;
    }

    /**
     * 添加分类
     * @param category
     */
    @Override
    public void addCategory(Category category) {
        //设置创建时间
        category.setCreateTime(LocalDateTime.now());
        categoryMapper.addCategory(category);
    }

    /**
     * 获取分类详情 分类详情不为空的话再获取该分类关联的文章数量
     * @param id 分类id
     * @return
     */
    @Override
    public Category getCategoryById(Integer id) {
        Category category = categoryMapper.getCategoryById(id);
        if (category == null){
            throw new CustomException("对象为空");
        }
        category.setArticleNum(articleCategoryService.getArticleCategoryNum(id));
        return category;
    }

    /**
     * 更新分类状态
     * @param id 分类id
     * @param status 分类状态
     */
    @Override
    public void updateCategoryStatus(Integer id, Integer status) {
        //检查status是否符合要求
        if (BaseUtil.checkStatus(status)){
            categoryMapper.updateCategoryStatus(id, status);
        }else {
            throw new CustomException("参数错误");
        }

    }

    /**
     * 删除分类 删除分类之前先判断还有没有关联的文章 若有则无法删除并抛出异常
     * @param id 分类id
     */
    @Override
    public void deleteCategory(Integer id) {
        int articleForCategoryNum = articleCategoryService.getArticleCategoryNum(id);
        if (articleForCategoryNum > 0){
            throw new CustomException("当前分类关联了文章，无法删除！");
        }
        categoryMapper.deleteCategory(id);
    }

    /**
     * 通过分类id列表获取指定的分类列表
     * @param categoryIdList 分类id列表
     * @return
     */
    @Override
    public List<Category> getCategoryListByIdList(List<Integer> categoryIdList) {
        return categoryMapper.getCategoryListByIdList(categoryIdList);
    }
}
