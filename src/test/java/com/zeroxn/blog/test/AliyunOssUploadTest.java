package com.zeroxn.blog.test;

import com.zeroxn.blog.core.utils.AliyunOssService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;

/**
 * @Author: lisang
 * @DateTime: 2023/7/11 下午6:05
 * @Description:
 */
@SpringBootTest
public class AliyunOssUploadTest {
    @Autowired
    private AliyunOssService aliyunOssService;

    @Test
    public void testFileUpload() throws Exception{
        File file = new File("/home/lisang/Pictures/spring-cloud.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        String url = aliyunOssService.fileUpload("spring-cloud.png", fileInputStream);
        System.out.println(url);
    }
}
