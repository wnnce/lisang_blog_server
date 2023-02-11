package com.zeroxn.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author ran
 * @date
 * 站点设置实体类 对应数据库中 tb_setting 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Setting {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 站点设置的最后更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 公告
     */
    private String notice;
    /**
     * 备案信息
     */
    private String record;
    /**
     * 站点开始运行时间
     */
    private LocalDate date;
    private String protocol;
    /**
     * 底栏自定义消息
     */
    private String explanation;
    /**
     * 关于我
     */
    private String about;
}
