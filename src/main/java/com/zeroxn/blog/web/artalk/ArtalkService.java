package com.zeroxn.blog.web.artalk;

import com.zeroxn.blog.entity.artalk.ArtalkComment;
import com.zeroxn.blog.entity.artalk.ArtalkPage;
import com.zeroxn.blog.entity.artalk.ArtalkUser;

import java.util.List;

/**
 * @author ran
 * @date 2023-2-3
 */
public interface ArtalkService {
    /**
     * 评论页的固定前缀 后面加上文章的id就是评论页的key属性
     */
    static final String ARTALK_PAGE_KEY_PREFIX = "/article/";

    /**
     * 返回所有评论
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 返回评论列表或者空
     */
    List<ArtalkComment> getArtalkCommentList(Integer pageNum, Integer pageSize);

    /**
     * 更新评论的一些属性 用法同controller层
     * @param artalkComment
     */
    void updateArtalkCommentType(ArtalkComment artalkComment);

    /**
     * 通过id删除一条评论
     * @param id 评论id
     * @param isCheckId 是否检查id 值为true时会先从数据库中获取这个id的对象 为空则抛出异常 如为false则不检查
     */
    void deleteArtalkCommentById(Integer id, boolean isCheckId);

    /**
     * 获取评论页列表
     * @param pageNum 页码
     * @param pageSize 每页记录条数
     * @return 返回评论页列表或者空
     */
    List<ArtalkPage> getArtalkPageList(Integer pageNum, Integer pageSize);

    /**
     * 更新评论页的 adminOnly 属性
     * @param id 评论页id
     * @param adminOnly 评论实体类的adminOnly属性
     */
    void updateArtalkPageType(Integer id, Boolean adminOnly);

    /**
     * 通过ID删除单个评论页
     * @param id 评论页id
     */
    void deleteArtalkPageById(Integer id);

    /**
     * 通过评论页对象的key属性来删除单个评论页
     * @param key 评论页的key属性
     */
    void deleteArtalkPageByKey(String key);

    /**
     * 获取所有评论用户
     * @param pageNum 页码
     * @param pageSize 每页记录条数
     * @return 评论用户列表或者空
     */
    List<ArtalkUser> getArtalkUserList(Integer pageNum, Integer pageSize);

    /**
     * 通过id删除单个评论用户
     * @param id 评论用户id
     */
    void deleteArtalkUserById(Integer id);
}
