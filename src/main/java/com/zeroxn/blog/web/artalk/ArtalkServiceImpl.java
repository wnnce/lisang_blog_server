package com.zeroxn.blog.web.artalk;

import com.github.pagehelper.PageHelper;
import com.zeroxn.blog.entity.artalk.ArtalkComment;
import com.zeroxn.blog.entity.artalk.ArtalkPage;
import com.zeroxn.blog.entity.artalk.ArtalkUser;
import com.zeroxn.blog.core.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author ran
 * @date
 * ArtalkService的实现类
 */
@Service
public class ArtalkServiceImpl implements ArtalkService {
    private final ArtalkMapper artalkMapper;
    private final ArtalkAsyncTask artalkAsyncTask;
    public ArtalkServiceImpl(ArtalkMapper artalkMapper, ArtalkAsyncTask artalkAsyncTask){
        this.artalkMapper = artalkMapper;
        this.artalkAsyncTask =artalkAsyncTask;
    }

    /**
     * 获取评论列表之后 需要为列表中的每条评论设置当前评论的页面标题和发表该评论的用户名
     * 如果评论是二级评论 那么还需要获取上一级评论的信息
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    @Override
    public List<ArtalkComment> getArtalkCommentList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ArtalkComment> artalkCommentList = artalkMapper.getArtalkCommentList();
        artalkCommentList.forEach(artalkComment -> {
            // 查询页面标题和发表评论的用户名
            CompletableFuture<String> pageTitle = artalkAsyncTask.getArtalkPageTitleByPageKey(artalkComment.getPageKey());
            CompletableFuture<String> username = artalkAsyncTask.getArtalkUserNameById(artalkComment.getUserId());
            // 主线程等待子线程执行完后
            CompletableFuture.allOf(pageTitle, username).join();
            // 使用join方法 无需catch异常
            artalkComment.setPageTitle(pageTitle.join());
            artalkComment.setUsername(username.join());
            //如果是二级评论的话 继续查询上一级评论和上一级评论的用户名
            if (artalkComment.getRid() != 0){
                //通过id获取上一级评论
                ArtalkComment superComment = artalkMapper.getArtalkCommentContentAndUserIdById(artalkComment.getRid());
                //为上一级评论设置用户名
                superComment.setUsername(artalkMapper.getArtalkUserNameById(superComment.getUserId()));
                //设置上一级评论
                artalkComment.setSuperComment(superComment);
            }
        });
        return artalkCommentList;
    }

    @Override
    public void updateArtalkCommentType(ArtalkComment artalkComment) {
        artalkMapper.updateArtalkCommentType(artalkComment);
    }

    /**
     * 删除评论后需要判断这条评论下面是否还有子评论 如果有 那么需要一同删除
     * @param id 评论id
     * @param isCheckId 是否检查id 值为true时会先从数据库中获取这个id的对象 为空则抛出异常 如为false则不检查
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArtalkCommentById(Integer id, boolean isCheckId) {
        //通过isCheckId参数来判断id是否有对应对象
        if (isCheckId){
            ArtalkComment artalkComment = artalkMapper.getArtalkCommentById(id);
            if (artalkComment == null){
                throw new CustomException("参数错误");
            }
        }
        artalkMapper.deleteArtalkCommentByIdOrPageKey(id, null);
        //删除评论之后 判断这条评论下面是否有子评论
        List<ArtalkComment> artalkCommentList = artalkMapper.getArtalkCommentListByUserIdOrRid(null, id);
        //如果list的size大于0则代表有对象 forEach删除
        if (artalkCommentList.size() > 0){
            artalkCommentList.forEach(comment -> {
                //这里的id肯定有对象就不用判断了
                deleteArtalkCommentById(comment.getId(), false);
            });
        }

    }

    @Override
    public List<ArtalkPage> getArtalkPageList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return artalkMapper.getArtalkPageList();
    }

    @Override
    public void updateArtalkPageType(Integer id, Boolean adminOnly) {
        artalkMapper.updateArtalkPageType(id, adminOnly);
    }

    /**
     * 删除评论页面页之前 需要先通过评论页的key属性来删除该页下所有的评论
     * @param id 评论页id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArtalkPageById(Integer id) {
        ArtalkPage artalkPage = artalkMapper.getArtalkPageById(id);
        if (artalkPage == null){
            throw new CustomException("参数错误");
        }
        artalkMapper.deleteArtalkCommentByIdOrPageKey(null, artalkPage.getKey());
        artalkMapper.deleteArtalkPageByIdOrKey(id, null);
    }

    /**
     * 供ArticleService调用的方法 在ArticleService中调用删除删除文章的方法时 需要同步删除评论页和该页的所有评论
     * 只有通过key属性来删除
     * @param key 评论页的key属性
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArtalkPageByKey(String key) {
        artalkMapper.deleteArtalkCommentByIdOrPageKey(null, key);
        artalkMapper.deleteArtalkPageByIdOrKey(null, key);
    }

    @Override
    public List<ArtalkUser> getArtalkUserList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return artalkMapper.getArtalkUserList();
    }

    /**
     * 删除评论用户需要先通过入参id获取评论用户对象 如果为空则抛出异常
     * 如果要删除的是管理员账户也抛出异常
     * 以上判断都不成立的话 用户才可以被删除 删除的同时也会删除该用户在这个网站内发表的所有评论
     * @param id 评论用户id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArtalkUserById(Integer id) {
        ArtalkUser artalkUser = artalkMapper.getArtalkUserById(id);
        if (artalkUser == null){
            throw new CustomException("参数错误");
        }
        if (artalkUser.getIsAdmin()){
            throw new CustomException("管理员账户不可删除！");
        }
        artalkMapper.deleteArtalkUserById(id);
        List<ArtalkComment> artalkCommentList = artalkMapper.getArtalkCommentListByUserIdOrRid(id, null);
        artalkCommentList.forEach(comment -> {
            deleteArtalkCommentById(comment.getId(), false);
        });
    }
}
