package com.zeroxn.blog.core.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.zeroxn.blog.core.config.aliyun.AliyunOSSProperties;

import java.io.InputStream;
import java.time.LocalDate;

/**
 * @author ran
 * @date
 * 阿里云工具类
 */
public class AliyunOssService {
    private final AliyunOSSProperties properties;
    private final OSS ossClient;
    public AliyunOssService(AliyunOSSProperties properties, OSS ossClient) {
        this.properties = properties;
        this.ossClient = ossClient;
    }
    /**
     * 调用阿里云oss的上传api进行文件上传
     * @param fileName 文件名称
     * @param inputStream 文件流
     * @return 返回文件上传后的链接
     */
    public String fileUpload(String fileName, InputStream inputStream){
        fileName = properties.getPrefix() + LocalDate.now() + "/" + fileName;
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucketName(), fileName, inputStream);
            // 设置该属性可以返回response。如果不设置，则返回的response为空。
            putObjectRequest.setProcess("true");
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 如果上传成功，则返回200。
            if (result.getResponse().getStatusCode() == 200){
                //返回文件链接
                return "https://" + properties.getBucketName() + "." + properties.getEndpoint() + "/" + fileName;
            }
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        }
        return null;
    }
}
