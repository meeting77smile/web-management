package com.meeting_smile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //开启了springboot对javaWeb组件的支持（因为filter是javaWeb三大组件之一，并不是springboot当中提供的功能）
@SpringBootApplication
public class WebManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebManagementApplication.class, args);
    }

}
