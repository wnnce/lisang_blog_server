package com.zeroxn.blog.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * redis工具类 封装了操作zSet和普通 key value 的方法
 */
@Component
public class RedisUtil {
    //博客文章排行榜 redis 数据库中的 key 前缀
    public static final String BLOG_TOP_REDIS_KEY_PREFIX = "BLOG:TOP";
    //博客文章在 redis 数据库中的前缀
    public static final String BLOG_ARTICLE_REDIS_KEY_PREFIX = "BLOG:ARTICLE:";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加数据到redis zSet
     * @param key 唯一键
     * @param value 该数据的值
     * @param score 分数 这里代表博客文章被点击的次数
     */
    public void zSetAdd(String key, Object value, Double score){
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 添加数据到redis zSet 如果key已经存在那么就更新scope
     * @param key 唯一键
     * @param value 该数据的值
     * @param scope 分数 这里代表博客文章被点击的次数
     */
    public void zSetIncrement(String key, Object value, Double scope){
        redisTemplate.opsForZSet().incrementScore(key, value, scope);
    }

    /**
     * 根据 key 和 value 获取在zSet集合中的分数
     * @param key
     * @param value
     * @return 返回博客的点击数
     */
    public Double zScope(String key, Object value){
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 根据点击数安装从大到小 遍历zSet集合
     * @param key 唯一键 可以使用通配符
     * @param start 开始遍历的下标
     * @param end 遍历结束的下标
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRange(String key, long start, long end){
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }
    public Long zSetDel(String key, Object value){
        return redisTemplate.opsForZSet().remove(key, value);
    }


    /**
     * 保存数据到redis
     * @param key
     * @param value
     */
    public void set(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 根据key 从redis中获取数据
     * @param key
     * @return 返回数据
     */
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }
}
