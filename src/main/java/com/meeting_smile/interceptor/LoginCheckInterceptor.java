package com.meeting_smile.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.meeting_smile.pojo.Result;
import com.meeting_smile.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component  //交给IOC容器管理
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override   //目标资源方法运行前运行,返回为true代表放行，反之代表拦截
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        System.out.println("preHandle...");
        //此时不需要将ServletRequest和ServletRequest强转为HttpServletRequest和HttpServletRequest类型
        //因为preHandle方法中传进来的就是HttpServletRequest和HttpServletRequest这两种类型

        //1.获取请求url
        String url = req.getRequestURI().toString();
        log.info("请求的url: {}",url);

        //2.判断请求url中是否包含login,如果包含，说明是登录操作，放行
        if(url.contains("login")){
            log.info("登录操作, 放行...");
            return true;//不再执行之后的方法 故不放行（与Filter中的拦截、放行格式不同）
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
            return false;//不再执行之后的方法
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
            return false;//不再执行之后的方法
        }

        //6.jwt令牌解析成功，放行 故return true放行
        log.info("令牌合法, 放行");
        return true;

    }

    //以下两个方法均在Controller方法之后才运行
    @Override   //目标资源方法运行后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle...");
    }

    @Override   //视图渲染完毕后运行，最后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion...");
    }
}
