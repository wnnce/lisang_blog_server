package com.zeroxn.blog.core.runner;

import com.zeroxn.blog.core.scheduled.RedisDataBaseSync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author ran
 * @date
 * SpringBoot的Runner 在程序启动和销毁时执行任务
 */
@Component
@Slf4j
public class RedisInitRunner implements ApplicationRunner, DisposableBean {
    @Autowired
    private RedisDataBaseSync redisDataBaseSync;

    /**
     * SpringBoot启动后 同步数据库中的文章标题和文章点击数到redis中
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("SpringBoot启动，开始同步数据库中的数据到redis");
        redisDataBaseSync.pullDataBaseToRedis();
    }

    /**
     * SpringBoot销毁时 同步redis排行榜中的点击数到数据库
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        log.info("SpringBoot销毁，同步redis中的数据到数据库...");
        redisDataBaseSync.pushRedisToDataBase();
    }
}
