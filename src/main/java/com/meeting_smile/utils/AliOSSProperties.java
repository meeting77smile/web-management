package com.meeting_smile.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//通过下方的操作，能使得当程序运行时将yml;文件中对应属性
//的值注入到下方bean对象的属性中
@Data   //加上@Data注解则可为下面的属性加上get和set方法
@Component
//比起@Value, @ConfigurationProperties能批量将外部属性注入到bean对象的属性中
@ConfigurationProperties(prefix = "aliyun.oss") //指定前缀
public class AliOSSProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
