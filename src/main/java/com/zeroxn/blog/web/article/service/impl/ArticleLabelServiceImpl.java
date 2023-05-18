package com.zeroxn.blog.web.article.service.impl;

import com.zeroxn.blog.entity.ArticleLabel;
import com.zeroxn.blog.web.article.mapper.ArticleLabelMapper;
import com.zeroxn.blog.web.article.mapper.ArticleMapper;
import com.zeroxn.blog.web.article.service.ArticleLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lisang
 * @Date 2023/3/10 下午8:00
 * @Version 1.0
 */
@Service
public class ArticleLabelServiceImpl implements ArticleLabelService {
    private final ArticleLabelMapper articleLabelMapper;
    public ArticleLabelServiceImpl(ArticleLabelMapper articleLabelMapper){
        this.articleLabelMapper = articleLabelMapper;
    }

    @Override
    public Integer countArticleByLabelId(Integer labelId) {
        return articleLabelMapper.countArticleByLabelId(labelId);
    }

    @Override
    public List<Integer> listLabelIdByArticleId(Integer articleId) {
        return articleLabelMapper.listLabelIdByArticleId(articleId);
    }

    @Override
    public List<Integer> listArticleIdByLabelId(Integer labelId) {
        return articleLabelMapper.listArticleIdByLabelId(labelId);
    }

    @Override
    public void saveArticleLabelList(List<ArticleLabel> articleLabelList) {
        articleLabelMapper.saveArticleLabelList(articleLabelList);
    }

    @Override
    public void deleteArticleLabelByArticleId(Integer articleId) {
        articleLabelMapper.deleteArticleLabelByArticleId(articleId);
    }
}
