package com.zeroxn.blog.DTO;

import com.zeroxn.blog.entity.Article;
import com.zeroxn.blog.entity.Category;
import com.zeroxn.blog.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author ran
 * @date
 * Article的数据传输对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//调用toString方法时 也输出父类属性
@ToString(callSuper = true)
public class ArticleDTO extends Article implements Serializable {
    /**
     * 分类列表
     */
    private List<Category> categoryList;
    /**
     * 标签列表
     */
    private List<Tag> tagList;

}
