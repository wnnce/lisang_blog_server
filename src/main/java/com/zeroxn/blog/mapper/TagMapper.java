package com.zeroxn.blog.mapper;

import com.zeroxn.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ran
 * @date
 * 操作数据库中的 tb_tag 表
 */
@Mapper
public interface TagMapper {
    List<Tag> getTagList(@Param("status") Integer status);
    List<Tag> getTagListForIdAndName();
    void addTag(Tag tag);
    @Select("select * from tb_tag where id = #{id} and status = 1")
    Tag getTagById(@Param("id") Integer id);
    void updateTagStatus(Integer id, Integer status);
    void deleteTag(@Param("id") Integer id);
    List<Tag> getTagListByIdList(@Param("tagIdList") List<Integer> tagIdList);
}
