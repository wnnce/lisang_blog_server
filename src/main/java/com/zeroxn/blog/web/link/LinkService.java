package com.zeroxn.blog.web.link;

import com.zeroxn.blog.entity.Link;

import java.util.List;

/**
 * @author ran
 * @date
 */
public interface LinkService {
    /**
     * 通过isShow属性获取友情链接列表 可以为空 为空则获取所有
     * @param isShow 友情连接的isShow属性
     * @return 返回友情链接列表或空
     */
    List<Link> getLinkListByIsShow(Boolean isShow);

    /**
     * 添加友情链接
     * @param link
     */
    void saveLink(Link link);

    /**
     * 更新友情链接
     * @param link
     */
    void updateLinkById(Link link);

    /**
     * 通过id更新友情链接的isShow属性
     * @param id 友情链接id
     * @param isShow 友情链接的isShow属性
     */
    void updateLinkIsShowById(Integer id, Boolean isShow);

    /**
     * 通过id删除友情链接
     * @param id 友情链接id
     */
    void deleteLinkById(Integer id);
}
