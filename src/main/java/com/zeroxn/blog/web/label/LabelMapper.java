package com.zeroxn.blog.web.label;

import com.zeroxn.blog.entity.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lisang
 * @Date 2023/3/10 下午7:00
 * @Version 1.0
 * 对应数据库中 tb_label 表
 */
@Mapper
public interface LabelMapper {
    /**
     * 根据条件查询所有的分类/标签列表 条件为空则查询所有 默认按创建时间倒叙排序
     * @param flag 标志 1:查询标签 2：查询分类
     * @param status 状态 0：禁用 1：正常
     * @return 符合条件的分类/标签列表
     */
    List<Label> listLabel(@Param("flag") Integer flag, @Param("status") Integer status);

    /**
     * 通过id获取详情
     * @param id 分类/标签id
     * @return 返回实体类或空
     */
    Label getLabelById(@Param("id") Integer id);

    /**
     * 保存分类/标签
     * @param label 封装对象
     */
    void saveLabel(Label label);

    /**
     * 更新分类/标签的状态
     * @param id 分类/标签的id
     * @param status 状态 0：禁用 1：启用
     */
    void updateLabelStatus(@Param("id") Integer id, @Param("status") Integer status);

    /**
     * 通过id删除分类/标签
     * @param id 分类/标签id
     */
    void deleteLabel(@Param("id") Integer id);

    /**
     * 通过分类/标签的id列表来或者列表中指定的分类/标签详情
     * @param labelIdList 分类/标签id列表
     * @return 指定的分类/标签详情列表
     */
    List<Label> listLabelByIdList(List<Integer> labelIdList);
}
