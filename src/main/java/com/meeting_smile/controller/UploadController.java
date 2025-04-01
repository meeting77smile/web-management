package com.meeting_smile.controller;

import com.meeting_smile.pojo.Result;
import com.meeting_smile.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j  //用于记录日志
@RestController
public class UploadController {

    /*  //将上传的文件存储在本地的方式
    @PostMapping("/upload")
    public Result upload(String username , Integer age , MultipartFile image) throws IOException {
        log.info("文件上传: {}, {}, {}",username, age, image);
        //获取原始文件名
        String originalFilename = image.getOriginalFilename();

        //使用uuid（通用唯一识别码）来构造唯一的文件名，避免重复
        int index = originalFilename.lastIndexOf(".");//获取文件名最后一个"."的位置
        String extname = originalFilename.substring(index);//从index开始截取至文件名末尾处的子串，即文件名后缀
        String newFileName = UUID.randomUUID().toString() + extname;

        //用MultipartFile的transferTo方法将接收到的文件存储在服务器的磁盘目录中 G:\images
        image.transferTo(new File("G:\\oss_images\\"+ newFileName));
        return Result.success();
    }*/

    //使用Autowired注解注入该工具类
    @Autowired
    private AliOSSUtils aliOSSUtils;
    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException {
        log.info("文件上传阿里云成功, 文件名: {}",image.getOriginalFilename());
        //调用阿里云oss工具类进行文件上传
        String url = aliOSSUtils.upload(image);//文件上传之后返回的url
        log.info("文件上传完成，文件访问的url为: {}",url);
        return Result.success(url);
    }
}
