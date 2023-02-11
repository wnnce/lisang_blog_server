package com.zeroxn.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ran
 * @date
 * 文章分类实体类 对应数据库中的 tb_category 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
    /**
     * 分类id
     */
    private Integer id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类创建时间
     */
    private LocalDateTime createTime;
    /**
     * 分类状态 1 启用 0 禁用
     */
    private Integer status;
    /**
     * 关联的文章数量 数据库中没有该字段
     */
    private Integer articleNum;

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(Integer id) {
        this.id = id;
    }
}
