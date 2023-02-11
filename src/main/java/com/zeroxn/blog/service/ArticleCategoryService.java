package com.zeroxn.blog.service;

import com.zeroxn.blog.entity.ArticleCategory;
import com.zeroxn.blog.entity.Category;

import java.util.List;

public interface ArticleCategoryService {
    Integer getArticleCategoryNum(Integer categoryId);
    List<Integer> getCategoryIdListByArticleId(Integer articleId);
    List<Integer> getArticleIdListByCategoryId(Integer categoryId);
    void saveArticleCategoryList(List<ArticleCategory> articleCategoryList);
    void deleteArticleCategoryByArticleId(Integer articleId);
}