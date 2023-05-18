package com.zeroxn.blog.web.link;

import com.zeroxn.blog.entity.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ran
 * @date
 * LinkService的实现类
 */
@Service
public class LinkServiceImpl implements LinkService {
    private final LinkMapper linkMapper;
    public LinkServiceImpl(LinkMapper linkMapper){
        this.linkMapper = linkMapper;
    }
    @Override
    public List<Link> getLinkListByIsShow(Boolean isShow) {
        return linkMapper.getLinkListByIsShow(isShow);
    }

    /**
     * 保存友情链接 同时设置一些属性
     * @param link
     */
    @Override
    public void saveLink(Link link) {
        //友情链接默认展示
        link.setIsShow(true);
        //设置创建时间
        link.setCreateTime(LocalDateTime.now());
        //设置更新时间
        link.setUpdateTime(LocalDateTime.now());
        linkMapper.saveLink(link);
    }

    @Override
    public void updateLinkById(Link link) {
        //设置更新时间
        link.setUpdateTime(LocalDateTime.now());
        linkMapper.updateLinkById(link);
    }

    @Override
    public void updateLinkIsShowById(Integer id, Boolean isShow) {
        linkMapper.updateLinkIsShowById(id, isShow);
    }

    @Override
    public void deleteLinkById(Integer id) {
        linkMapper.deleteLinkById(id);
    }
}
