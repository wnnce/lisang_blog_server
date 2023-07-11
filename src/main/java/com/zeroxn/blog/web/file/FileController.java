package com.zeroxn.blog.web.file;

import com.aliyuncs.utils.StringUtils;
import com.zeroxn.blog.core.utils.AliyunOssService;
import com.zeroxn.blog.core.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author ran
 * @date
 * 文件上传控制器
 */
@RestController
@Slf4j
@RequestMapping("/file")
public class FileController {
    private final AliyunOssService aliyunOssService;
    public FileController(AliyunOssService aliyunOssService) {
        this.aliyunOssService = aliyunOssService;
    }
    /**
     * 调用Aliyun OSS进行文件上传
     * @param file 前端传过来的文件流
     * @return 返回文件在OSS中的链接
     */
    @PostMapping("/upload")
    public Result<String> fileUpload(MultipartFile file) {
        //获取文件名
        String name = file.getOriginalFilename();
        if (!StringUtils.isEmpty(name)){
            //通过uuid生成文件名
            StringBuilder fileName = new StringBuilder(UUID.randomUUID().toString().replaceAll("-", ""));
            //新的文件名等于 uuid文件名 + 原文件的后缀
            fileName.append(name.substring(name.lastIndexOf(".")));
            try{
                //调用阿里云的上传api 返回文件链接
                String fileUrl = aliyunOssService.fileUpload(String.valueOf(fileName), file.getInputStream());
                //如果不为空就返回链接给前端
                if(!StringUtils.isEmpty(fileUrl)){
                    log.info("图片上传成功，链接：" + fileUrl);
                    return Result.success(fileUrl);
                }
            }catch (IOException ex){
                throw new RuntimeException(ex);
            }
            return Result.error("文件上传失败，请重试！");
        }
        return Result.error("文件不存在");
    }
}
