package com.meeting_smile.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

//一个web应用中，可以配置多个过滤器，这多个过滤器就形成了一个过滤器链
//顺序：注解配置的Filter，优先级是按照过滤器名的自然排序(按照字符串排序)
//@WebFilter(urlPatterns = "/*")
public class AFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("A放行前的逻辑");

        //放行（之后前端才能访问到后端的资源)
        chain.doFilter(request,response);

        System.out.println("A放行后的逻辑");//放行之后访问对应的资源，再回到doFilter之前执行放行前的逻辑
    }
}
