package com.meeting_smile.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.UUID;

/**
 * 阿里云 OSS 工具类
 */

//由于AliOSSUtils类为非静态方法，故通常要使用它需要new该对象
//因为是基于spring的开发，故可通过注解将该类将给IOC容器
//声明bean的注解有四个：@Component, @Control, @Service, @Repository
//后三个注解分别加在Control、Service、DAO(数据访问)层
//而工具类Utils不属与这三层，故使用@Component注解

@Component
public class AliOSSUtils {
    //原始方法：通过Value注解来为bean对象的属性注入值
    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
//    @Value("${aliyun.oss.endpoint}")
//    String endpoint;
//    @Value("${aliyun.oss.accessKeyId}")
//    String accessKeyId;
//    @Value("${aliyun.oss.accessKeySecret}")
//    String accessKeySecret;
//    // 填写Bucket名称，例如examplebucket。
//    @Value("${aliyun.oss.bucketName}")
//    String bucketName;

    //注入对象，通过get()方法获取属性值
    @Autowired
    private AliOSSProperties aliOSSProperties;

    /**
     * 实现上传图片到OSS
     */
    public String upload(MultipartFile file) throws IOException {//file:在网页中上传的文件
        //获取阿里云oss参数
        String endpoint = aliOSSProperties.getEndpoint();
        String accessKeyId = aliOSSProperties.getAccessKeyId();
        String accessKeySecret = aliOSSProperties.getAccessKeySecret();
        String bucketName = aliOSSProperties.getBucketName();

        // 获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();

        // 通过使用UUID生成文件名，避免文件覆盖
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        //上传文件到 OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);

        //组装文件访问路径
        //split("//") 方法会按照字符串 "//" 将原始字符串分割成一个不包含"//"的数组。
        //split("//")[0]和split("//")[1]分别表示数组的第一、第二个元素
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;

        // 关闭ossClient
        ossClient.shutdown();
        return url;// 把上传到oss的路径返回
    }

}
