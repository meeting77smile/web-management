package com.meeting_smile.controller;

import com.meeting_smile.pojo.Emp;
import com.meeting_smile.pojo.Result;
import com.meeting_smile.service.EmpService;
import com.meeting_smile.utils.JwtUtils;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping("/*")
    public Result login(@RequestBody Emp emp){
        log.info("员工登录: {}", emp);
        Emp e = empService.login(emp);

        //登录成功,生成令牌,下发令牌
        if(e != null){
            Map<String,Object> claims = new HashMap<String,Object>();
            //往jwt令牌中传入以下用户信息：
            claims.put("id",e.getId());
            claims.put("name",e.getName());
            claims.put("username",e.getUsername());

            String jwt = JwtUtils.generateJwt(claims);//jwt令牌已包含了当前登录的员工信息
            return Result.success(jwt);
        }

        //登录失败，返回错误信息
        return  Result.error("用户名或密码错误");
    }
}