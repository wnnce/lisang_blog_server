package com.zeroxn.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author lisang
 * @Date 2023/3/10 下午6:52
 * @Version 1.0
 *
 * 文章对应的标签和分类 对应数据库的 tb_table 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Label implements Serializable {
    /**
     * id 主键
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 状态 0 禁用 1启用
     */
    private Integer status;
    /**
     * 类型 1：标签 2：分类
     */
    private Integer flag;
    /**
     * 该标签/分类对应的文章数量
     */
    private Integer articleNum;

    public Label(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Label(Integer id) {
        this.id = id;
    }
}
