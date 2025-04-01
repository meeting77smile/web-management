package com.meeting_smile.filter;

import com.alibaba.fastjson.JSONObject;
import com.meeting_smile.pojo.Result;
import com.meeting_smile.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //1.获取请求url
        String url = req.getRequestURI().toString();
        log.info("请求的url: {}",url);

        //2.判断请求url中是否包含login,如果包含，说明是登录操作，放行
        if(url.contains("login")){
            log.info("登录操作, 放行...");
            chain.doFilter(request,response);
            return;//不再执行之后的方法
        }

        //3.获取请求头中的令牌(token)
        String jwt = req.getHeader("token");

        //4.判断令牌是否存在，如果不存在，返回错误结果: 未登录
        if(jwt.length()==0){
            log.info("请求头token为空,将返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");//该错误信息是返回给前端的，前端会根据该信息强制跳转至登录界面
            //手动将 对象 转换为 json格式 ------>阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;//不再执行之后的方法
        }

        //5.jwt令牌存在 解析token，如果解释失败，返回错误结果(未登录)
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {//jwt解析失败
            e.printStackTrace();
            log.info("解析令牌失败，返回未登录错误信息");
            Result error = Result.error("NOT_LOGIN");
            //手动将 对象 转换为 json格式 ------>阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;//不再执行之后的方法
        }

        //6.jwt令牌解析成功，放行
        log.info("令牌合法, 放行");
        chain.doFilter(request,response);
    }
}
