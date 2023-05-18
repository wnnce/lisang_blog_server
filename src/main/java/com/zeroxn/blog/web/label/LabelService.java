package com.zeroxn.blog.web.label;

import com.zeroxn.blog.entity.Label;

import java.util.List;

/**
 * @Author lisang
 * @Date 2023/3/10 下午7:04
 * @Version 1.0
 */
public interface LabelService {
    /**
     * 通过标志和分类/标签状态来获取标签/分类列表 条件为空则获取所有
     * @param flag 标志 1：分类 2：标签
     * @param status 状态 1：启用 0：禁用
     * @return 符合条件的分类/标签列表
     */
    List<Label> listLabel(Integer flag, Integer status);

    /**
     * 通过id获取指定分类/标签详情
     * @param id 分类/标签id
     * @return 指定的分类/标签或空
     */
    Label getLabelById(Integer id);

    /**
     * 保存分类/标签
     * @param label 需要保存的分类/标签对象
     */
    void saveLabel(Label label);

    /**
     * 通过id更新指定分类/标签状态
     * @param id 分类/标签的id
     * @param status 状态 0：禁用 1：启用
     */
    void updateLabelStatus(Integer id, Integer status);

    /**
     * 通过id删除指定的分类/标签
     * @param id 分类/标签id
     */
    void deleteLabel(Integer id);

    /**
     * 通过分类/标签id列表或者指定的分类/标签详情
     * @param labelIdList 分类/标签id列表
     * @return 指定的分类/标签详情
     */
    List<Label> listLabelByIdList(List<Integer> labelIdList);
}
