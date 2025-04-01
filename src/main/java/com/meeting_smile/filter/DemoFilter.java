package com.meeting_smile.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

//过滤器Filter拦截范围（会拦截所有资源）比拦截器Interceptor（只会拦截进入Spring环境中的这部分资源）范围大
//@WebFilter(urlPatterns = "/*") // 当前过滤器拦截的路径 "*" 表示拦截所有请求 "login"表示拦截具体的login请求 "emps/*"表示拦截"/emps"下的所有资源
public class DemoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("init 初始化方法执行了");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println("拦截到了请求");
        System.out.println("Demo放行前的逻辑");

        //放行（之后前端才能访问到后端的资源)
        chain.doFilter(request,response);

        System.out.println("Demo放行后的逻辑");//放行之后访问对应的资源，再回到doFilter之前执行放行前的逻辑
    }

    @Override
    public void destroy() {
        //System.out.println("destroy 销毁方法执行了");
        Filter.super.destroy();
    }
}
