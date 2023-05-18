package com.zeroxn.blog.web.artalk;

import com.zeroxn.blog.entity.artalk.ArtalkComment;
import com.zeroxn.blog.entity.artalk.ArtalkPage;
import com.zeroxn.blog.entity.artalk.ArtalkUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ran
 * @date
 */
@Mapper
public interface ArtalkMapper {
    /**
     * 获取评论列表
     * @return 所有评论列表或者空
     */
    List<ArtalkComment> getArtalkCommentList();

    /**
     * 通过评论用户id或上级评论的id来获取评论列表
     * 两个参数只能传一个 如果同时传 userId的优先级更高 只传一个参数时 另一个参数需要声明为null
     * @param userId 评论用户id 对应数据库中的 user_id 字段
     * @param rid 如果不为0  则表示上级评论的id
     * @return 返回符合条件的评论列表或空
     */
    List<ArtalkComment> getArtalkCommentListByUserIdOrRid(@Param("userId") Integer userId, @Param("rid") Integer rid);

    /**
     * 通过评论id获取单条评论的评论内容和评论用户id
     * @param id 评论id
     * @return 返回包含评论内容和用户id的评论对象
     */
    ArtalkComment getArtalkCommentContentAndUserIdById(@Param("id") Integer id);

    /**
     * 通过评论id获取当条评论
     * @param id 评论id
     * @return 单条评论对象或空
     */
    ArtalkComment getArtalkCommentById(@Param("id") Integer id);

    /**
     * 更新评论的 isCollapsed(是否折叠) isPending(是否审核) isPinned(是否指定)属性 参数已经被上层校验了
     * @param artalkComment 封装的评论对象
     */
    void updateArtalkCommentType(ArtalkComment artalkComment);

    /**
     * 通过评论id或评论页的key删除单条评论
     * @param id 评论id
     * @param pageKey 评论的评论页key属性 对应数据库中的 pageKey
     */
    void deleteArtalkCommentByIdOrPageKey(@Param("id") Integer id, @Param("pageKey") String pageKey);

    /**
     * 获取评论页列表
     * @return 返回评论页列表或空
     */
    List<ArtalkPage> getArtalkPageList();

    /**
     * 通过评论页id获取单条评论页
     * @param id 评论页id
     * @return 返回单条评论页对象或空
     */
    ArtalkPage getArtalkPageById(@Param("id") Integer id);

    /**
     * 通过评论页的key属性获取单个评论页
     * @param pageKey 评论页的key属性 对应数据库中的pageKey字段
     * @return 返回单个评论页对象或空
     */
    String getArtalkPageTitleByPageKey(@Param("pageKey") String pageKey);

    /**
     * 通过id更新评论页的 adminOnly 属性
     * @param id 评论页id
     * @param adminOnly 评论页的adminOnly属性
     */
    void updateArtalkPageType(@Param("id") Integer id, @Param("adminOnly") Boolean adminOnly);

    /**
     * 通过评论页的id或key属性删除单条评论
     * @param id 评论页id
     * @param key 评论页key
     */
    void deleteArtalkPageByIdOrKey(@Param("id") Integer id, @Param("key") String key);

    /**
     * 获取评论用户列表
     * @return 返回全部评论用户列表或空
     */
    List<ArtalkUser> getArtalkUserList();

    /**
     * 通过评论用户ID获取单个评论用户
     * @param id 评论用户id
     * @return 返回评论用户或空
     */
    ArtalkUser getArtalkUserById(@Param("id") Integer id);

    /**
     * 通过评论用户id获取评论用户的用户名
     * @param Id 评论用户id
     * @return 返回用户名或空
     */
    String getArtalkUserNameById(@Param("id") Integer Id);

    /**
     * 通过评论用户id删除用户
     * @param id 评论用户id
     */
    void deleteArtalkUserById(@Param("id") Integer id);
}
