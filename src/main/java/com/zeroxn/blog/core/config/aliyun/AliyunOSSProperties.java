package com.zeroxn.blog.core.config.aliyun;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @Author: lisang
 * @DateTime: 2023/7/11 下午5:41
 * @Description: 阿里云OSS配置类
 */
@ConfigurationProperties("aliyun.oss")
public class AliyunOSSProperties {
    private final String endpoint;
    private final String accessKey;
    private final String accessSecret;
    private final String bucketName;
    private final String prefix;

    @ConstructorBinding
    public AliyunOSSProperties(String endpoint, String accessKey, String accessSecret, String bucketName, String prefix) {
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.bucketName = bucketName;
        this.prefix = prefix;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getPrefix() {
        return prefix;
    }
}
