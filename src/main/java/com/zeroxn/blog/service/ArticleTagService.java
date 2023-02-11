package com.zeroxn.blog.service;

import com.zeroxn.blog.entity.ArticleTag;
import com.zeroxn.blog.entity.Tag;

import java.util.List;

public interface ArticleTagService {
    Integer getTagByArticleNum(Integer tagId);
    List<Integer> getTagIdListByArticleId(Integer articleId);
    List<Integer> getArticleIdListByTagId(Integer tagId);
    void saveArticleTagList(List<ArticleTag> articleTagList);
    void deleteArticleTagByArticleId(Integer articleId);
}
