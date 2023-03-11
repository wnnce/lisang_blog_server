package com.zeroxn.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lisang
 * @Date 2023/3/10 下午7:58
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleLabel {
    private Integer id;
    private Integer articleId;
    private Integer labelId;
}
