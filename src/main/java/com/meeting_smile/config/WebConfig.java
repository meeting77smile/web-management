package com.meeting_smile.config;

import com.meeting_smile.filter.LoginCheckFilter;
import com.meeting_smile.interceptor.LoginCheckInterceptor;
import org.aopalliance.intercept.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  //配置类
public class WebConfig implements WebMvcConfigurer {
    //注入(配置)拦截器
    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //addPathPatterns表示拦截哪些路径下的请求   excludePathPatterns表示不拦截哪些路径下的请求
        // "/*"表示拦截任意一级路径，但不拦截二级路径  "/**"表示拦截任意级路径
        // "/depts/*"表示拦截depts下的一级路径，但不拦截depts下的二级路径(如depts/1/2)
        // "/depts/**"表示拦截depts下的任意级路径，但不能拦截如/emps/1的其他路径

        //写发，如registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/**").excludePathPatterns("/login")
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/**");//拦截所有资源
    }

}
