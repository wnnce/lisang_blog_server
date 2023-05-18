package com.zeroxn.blog.core.scheduled.task;

import com.zeroxn.blog.core.scheduled.RedisDataBaseSync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ran
 * @date
 * 定时任务类
 */
@Component
@Slf4j
public class SyncTask {
    private final RedisDataBaseSync redisDataBaseSync;
    public SyncTask(RedisDataBaseSync redisDataBaseSync){
        this.redisDataBaseSync = redisDataBaseSync;
    }

    /**
     * 每30分钟执行一次定时任务
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void execute(){
        log.info("开始执行定时任务，同步数据到数据库...");
        redisDataBaseSync.pushRedisToDataBase();
    }
}
