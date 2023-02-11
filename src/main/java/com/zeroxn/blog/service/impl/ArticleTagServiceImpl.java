package com.zeroxn.blog.service.impl;

import com.zeroxn.blog.entity.ArticleTag;
import com.zeroxn.blog.entity.Tag;
import com.zeroxn.blog.mapper.ArticleTagMapper;
import com.zeroxn.blog.service.ArticleTagService;
import com.zeroxn.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTagServiceImpl implements ArticleTagService {
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Override
    public Integer getTagByArticleNum(Integer tagId) {
        return articleTagMapper.getTagByArticleNum(tagId);
    }

    @Override
    public List<Integer> getTagIdListByArticleId(Integer articleId) {
        return articleTagMapper.getTagIdListByArticleId(articleId);
    }

    @Override
    public List<Integer> getArticleIdListByTagId(Integer tagId) {
        return articleTagMapper.getArticleIdListByTagId(tagId);
    }

    @Override
    public void saveArticleTagList(List<ArticleTag> articleTagList) {
        articleTagMapper.saveArticleTagList(articleTagList);
    }

    @Override
    public void deleteArticleTagByArticleId(Integer articleId) {
        articleTagMapper.deleteArticleTagByArticleId(articleId);
    }
}
