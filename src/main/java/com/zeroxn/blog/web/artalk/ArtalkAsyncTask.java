package com.zeroxn.blog.web.artalk;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: lisang
 * @DateTime: 2023/5/18 下午1:54
 * @Description: Artalk的异步方法调用类
 */
@Component
@Async
public class ArtalkAsyncTask {
    private final ArtalkMapper artalkMapper;
    public ArtalkAsyncTask(ArtalkMapper artalkMapper){
        this.artalkMapper = artalkMapper;
    }
    public CompletableFuture<String> getArtalkPageTitleByPageKey(String pageKey){
        return CompletableFuture.completedFuture(artalkMapper.getArtalkPageTitleByPageKey(pageKey));
    }
    public CompletableFuture<String> getArtalkUserNameById(Integer userId){
        return CompletableFuture.completedFuture(artalkMapper.getArtalkUserNameById(userId));
    }
}
