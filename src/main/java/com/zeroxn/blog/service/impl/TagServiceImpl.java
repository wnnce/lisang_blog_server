package com.zeroxn.blog.service.impl;

import com.zeroxn.blog.entity.Tag;
import com.zeroxn.blog.exception.CustomException;
import com.zeroxn.blog.mapper.TagMapper;
import com.zeroxn.blog.service.ArticleTagService;
import com.zeroxn.blog.service.TagService;
import com.zeroxn.blog.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ran
 * @date
 * TagService的实现类
 * 逻辑和CategoryServiceImpl一致
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagService articleTagService;
    @Override
    @Transactional(readOnly = true)
    public List<Tag> getTagList(Integer status) {
        List<Tag> tagList = tagMapper.getTagList(status);
        tagList.forEach(tag -> {
            Integer articleNum = articleTagService.getTagByArticleNum(tag.getId());
            tag.setArticleNum(articleNum);
        });
        return tagList;
    }

    @Override
    public void addTag(Tag tag) {
        tag.setCreateTime(LocalDateTime.now());
        tagMapper.addTag(tag);
    }

    @Override
    public Tag getTagById(Integer id) {
        Tag tag = tagMapper.getTagById(id);
        if (tag == null){
            throw new CustomException("对象为空");
        }
        tag.setArticleNum(articleTagService.getTagByArticleNum(id));
        return tag;
    }

    @Override
    public void updateTagStatus(Integer id, Integer status) {
        if (BaseUtil.checkStatus(status)){
            tagMapper.updateTagStatus(id, status);
        }else {
            throw new CustomException("参数错误");
        }
    }

    @Override
    public void deleteTag(Integer id) {
        int articleForTagNum = articleTagService.getTagByArticleNum(id);
        if (articleForTagNum > 0){
            throw new CustomException("当前标签关联了文章，无法删除！");
        }
        tagMapper.deleteTag(id);
    }

    @Override
    public List<Tag> getTagListByIdList(List<Integer> tagIdList) {
        return tagMapper.getTagListByIdList(tagIdList);
    }

}
