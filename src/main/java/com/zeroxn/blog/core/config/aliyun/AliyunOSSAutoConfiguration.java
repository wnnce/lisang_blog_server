package com.zeroxn.blog.core.config.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zeroxn.blog.core.utils.AliyunOssService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lisang
 * @DateTime: 2023/7/11 下午5:45
 * @Description: 阿里云OSS自动配置类
 */
@Configuration
@EnableConfigurationProperties(AliyunOSSProperties.class)
@ConditionalOnClass(OSS.class)
public class AliyunOSSAutoConfiguration {

    @Bean
    public OSS ossClient(AliyunOSSProperties properties) {
        return new OSSClientBuilder().build(properties.getEndpoint(), properties.getAccessKey(), properties.getAccessSecret());
    }

    @Bean
    public AliyunOssService aliyunOssService(AliyunOSSProperties properties, OSS ossClient) {
        return new AliyunOssService(properties, ossClient);
    }
}
