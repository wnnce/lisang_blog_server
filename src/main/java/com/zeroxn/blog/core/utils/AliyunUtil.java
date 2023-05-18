package com.zeroxn.blog.core.utils;

import com.aliyun.dm20151123.Client;
import com.aliyun.dm20151123.models.SingleSendMailRequest;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;

import java.io.InputStream;
import java.time.LocalDate;

/**
 * @author ran
 * @date
 * 阿里云工具类
 */
public class AliyunUtil {
    private static final String endpoint = "";
    private static final String accessKeyId = "";
    private static final String accessKeySecret = "";
    private static final String bucketName = "";
    private static final String prefix = "";

    /**
     * 调用阿里云oss的上传api进行文件上传
     * @param fileName 文件名称
     * @param inputStream 文件流
     * @return 返回文件上传后的链接
     */
    public static String fileUpload(String fileName, InputStream inputStream){
        String date = LocalDate.now().toString();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        fileName = prefix + LocalDate.now().toString() + "/" + fileName;
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream);
            // 设置该属性可以返回response。如果不设置，则返回的response为空。
            putObjectRequest.setProcess("true");
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 如果上传成功，则返回200。
            if (result.getResponse().getStatusCode() == 200){
                //返回文件链接
                return "https://" + bucketName + "." + endpoint + "/" + fileName;
            }else{
                return null;
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
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
    public static Client createClient() throws Exception {
        Config config = new Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dm.aliyuncs.com";
        return new Client(config);
    }

    /**
     * 调用阿里云的短信api发送验证码
     * @param emailAddress 邮箱地址
     * @param code 验证码
     * @throws Exception
     */
    public static void sendCode(String emailAddress, String code) throws Exception {
        // 工程代码泄露可能会导致AccessKey泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        Client client = AliyunUtil.createClient();
        SingleSendMailRequest singleSendMailRequest = new SingleSendMailRequest()
                .setAccountName("admin@email.zeroxn.com")
                .setAddressType(1)
                .setTagName("checkCode")
                .setToAddress(emailAddress)
                .setSubject("网站验证码")
                .setFromAlias("admin")
                .setReplyAddress("2775714150@qq.com")
                .setReplyToAddress(true)
                .setTextBody("您的博客登录验证码为：code，5分钟内有效！".replaceAll("code", code));
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            client.singleSendMailWithOptions(singleSendMailRequest, runtime);
        } catch (TeaException error) {
            // 如有需要，请打印 error
            Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            Common.assertAsString(error.message);
        }
    }
}
