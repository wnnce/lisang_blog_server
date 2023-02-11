package com.zeroxn.blog.mapper;

import com.zeroxn.blog.entity.ArticleCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleCategoryMapper {
    Integer getCategoryByArticleNum(@Param("categoryId") Integer categoryId);
    List<Integer> getCategoryIdListByArticleId(@Param("articleId") Integer articleId);
    List<Integer> getArticleIdListByCategoryId(@Param("categoryId") Integer categoryId);
    void saveArticleCategoryList(List<ArticleCategory> articleCategoryList);
    void deleteArticleCategoryByArticleId(@Param("articleId") Integer articleId);
}
