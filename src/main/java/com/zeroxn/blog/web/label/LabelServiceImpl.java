package com.zeroxn.blog.web.label;

import com.zeroxn.blog.entity.Label;
import com.zeroxn.blog.core.exception.CustomException;
import com.zeroxn.blog.web.article.service.ArticleLabelService;
import com.zeroxn.blog.core.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author lisang
 * @Date 2023/3/10 下午7:05
 * @Version 1.0
 */
@Service
public class LabelServiceImpl implements LabelService {
    private final LabelMapper labelMapper;
    private final ArticleLabelService articleLabelService;

    public LabelServiceImpl(LabelMapper labelMapper, ArticleLabelService articleLabelService){
        this.labelMapper = labelMapper;
        this.articleLabelService = articleLabelService;
    }
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "labelList", condition = "#status == 1", key = "0")
    public List<Label> listLabel(Integer flag, Integer status) {
        List<Label> labelList = labelMapper.listLabel(flag, status);
        labelList.forEach(label -> {
            label.setArticleNum(articleLabelService.countArticleByLabelId(label.getId()));
        });
        return labelList;
    }

    @Override
    public Label getLabelById(Integer id) {
        Label label = labelMapper.getLabelById(id);
        if (label != null){
            label.setArticleNum(articleLabelService.countArticleByLabelId(id));
        }
        return label;
    }

    @Override
    @CacheEvict(cacheNames = "labelList", key = "0")
    public void saveLabel(Label label) {
        label.setCreateTime(LocalDateTime.now());
        labelMapper.saveLabel(label);
    }

    @Override
    @CacheEvict(cacheNames = "labelList", key = "0")
    public void updateLabelStatus(Integer id, Integer status) {
        if (BaseUtil.checkStatus(status)){
            labelMapper.updateLabelStatus(id, status);
        }else{
            throw new CustomException("参数错误");
        }

    }

    @Override
    @CacheEvict(cacheNames = "labelList", key = "0")
    public void deleteLabel(Integer id) {
        int articleNum = articleLabelService.countArticleByLabelId(id);
        if(articleNum > 0){
            throw new CustomException("当前分类/标签关联了文章，无法删除！");
        }
        labelMapper.deleteLabel(id);
    }

    @Override
    public List<Label> listLabelByIdList(List<Integer> labelIdList) {
        return labelMapper.listLabelByIdList(labelIdList);
    }
}
