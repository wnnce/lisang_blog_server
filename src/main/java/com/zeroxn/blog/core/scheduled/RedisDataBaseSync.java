package com.zeroxn.blog.core.scheduled;

import com.zeroxn.blog.entity.Article;
import com.zeroxn.blog.web.article.service.ArticleService;
import com.zeroxn.blog.core.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author ran
 * @date
 * redis 和 数据库 数据同步
 */
@Component
public class RedisDataBaseSync {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 同步数据库中的数据到redis
     */
    public void pullDataBaseToRedis(){
        List<Article> articleList = articleService.getArticleClickNumAndTitleList();
        articleList.forEach(article -> {
            redisUtil.zSetAdd(RedisUtil.BLOG_TOP_REDIS_KEY_PREFIX, article.getTitle(),
                    Double.valueOf(article.getClickNum()));
        });
    }

    /**
     * 同步redis中的数据到数据库
     */
    public void pushRedisToDataBase(){
        Set<ZSetOperations.TypedTuple<Object>> set = redisUtil.zRange(RedisUtil.BLOG_TOP_REDIS_KEY_PREFIX, 0, -1);
        set.forEach(s -> {
            String title = String.valueOf(s.getValue());
            Integer clickNum = s.getScore().intValue();
            articleService.updateArticleClickNumByTitle(title, clickNum);
        });
    }
}
