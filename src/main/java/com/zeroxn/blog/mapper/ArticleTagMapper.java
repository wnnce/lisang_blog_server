package com.zeroxn.blog.mapper;

import com.zeroxn.blog.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleTagMapper {
    Integer getTagByArticleNum(@Param("tagId") Integer tagId);
    List<Integer> getTagIdListByArticleId(@Param("articleId") Integer articleId);
    List<Integer> getArticleIdListByTagId(@Param("tagId") Integer tagId);
    void saveArticleTagList(List<ArticleTag> articleTagList);
    void deleteArticleTagByArticleId(@Param("articleId") Integer articleId);
}
