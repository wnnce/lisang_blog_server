package com.zeroxn.blog.service;

import com.zeroxn.blog.entity.Tag;

import java.util.List;

/**
 * @author ran
 * @date
 * 逻辑和CategoryService一致
 */
public interface TagService {
    List<Tag> getTagList(Integer status);
    void addTag(Tag tag);
    Tag getTagById(Integer id);
    void updateTagStatus(Integer id, Integer status);
    void deleteTag(Integer id);
    List<Tag> getTagListByIdList(List<Integer> tagIdList);
}
