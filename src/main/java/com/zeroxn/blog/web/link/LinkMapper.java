package com.zeroxn.blog.web.link;

import com.zeroxn.blog.entity.Link;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ran
 * @date
 */
@Mapper
public interface LinkMapper {
    /**
     * 通过isShow属性获取友情链接列表 为空则获取所有 结果按照创建时间正序排序
     * @param isShow 友情链接isShow属性
     * @return
     */
    List<Link> getLinkListByIsShow(@Param("isShow") Boolean isShow);

    /**
     * 添加友情链接
     * @param link
     */
    void saveLink(Link link);

    /**
     * 通过ID更新友情链接
     * @param link
     */
    void updateLinkById(Link link);

    /**
     * 通过友情链接是否显示
     * @param id 友情链接id
     * @param isShow 友情链接isShow属性
     */
    void updateLinkIsShowById(@Param("id") Integer id, @Param("isShow") Boolean isShow);

    /**
     * 通过id删除指定的友情链接
     * @param id 友情链接id
     */
    void deleteLinkById(@Param("id") Integer id);
}
