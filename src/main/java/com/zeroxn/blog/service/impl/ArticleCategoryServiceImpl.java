package com.zeroxn.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.zeroxn.blog.entity.ArticleCategory;
import com.zeroxn.blog.mapper.ArticleCategoryMapper;
import com.zeroxn.blog.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {
    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;
    @Override
    public Integer getArticleCategoryNum(Integer categoryId) {
        return articleCategoryMapper.getCategoryByArticleNum(categoryId);
    }

    @Override
    public List<Integer> getCategoryIdListByArticleId(Integer articleId) {
        return articleCategoryMapper.getCategoryIdListByArticleId(articleId);
    }

    @Override
    public List<Integer> getArticleIdListByCategoryId(Integer categoryId) {
        return articleCategoryMapper.getArticleIdListByCategoryId(categoryId);
    }

    @Override
    public void saveArticleCategoryList(List<ArticleCategory> articleCategoryList) {
        articleCategoryMapper.saveArticleCategoryList(articleCategoryList);
    }

    @Override
    public void deleteArticleCategoryByArticleId(Integer articleId) {
        articleCategoryMapper.deleteArticleCategoryByArticleId(articleId);
    }
}
