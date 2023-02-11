package com.zeroxn.blog.mapper;

import com.zeroxn.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author
 * @date
 * 操作数据库中的 tb_category表
 */
@Mapper
public interface CategoryMapper {
    /**
     * 通过分类status属性获取分类列表 若为空则获取全部
     * @param status 分类status属性
     * @return
     */
    List<Category> getCategoryList(@Param("status") Integer status);

    /**
     * 添加分类
     * @param category
     */
    void addCategory(Category category);

    /**
     * 通过分类ID获取指定分类
     * @param id 分类id
     * @return
     */
    @Select("select * from tb_category where id = #{id} and status = 1")
    Category getCategoryById(@Param("id") Integer id);

    /**
     * 通过id更新指定分类的状态
     * @param id 分类id
     * @param status 分类状态
     */
    void updateCategoryStatus(Integer id, Integer status);

    /**
     * 通过id删除指定分类
     * @param id
     */
    void deleteCategory(@Param("id") Integer id);

    /**
     * 通过分类id列表获取指定的分类列表
     * @param categoryIdList 分类id列表
     * @return 返回指定的分类列表 每项分类只包含id和name属性
     */
    List<Category> getCategoryListByIdList(@Param("categoryIdList") List<Integer> categoryIdList);
}
