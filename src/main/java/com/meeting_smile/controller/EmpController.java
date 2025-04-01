package com.meeting_smile.controller;

import com.meeting_smile.pojo.Emp;
import com.meeting_smile.pojo.PageBean;
import com.meeting_smile.pojo.Result;
import com.meeting_smile.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.net.Inet4Address;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    //批量删除
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("批量删除操作, ids:{}",ids);
        empService.delete(ids);
        return Result.success();
    }

    //条件分页查询
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, Short gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        //记录日志
        log.info("分页查询，参数：{},{},{},{},{},{}", page, pageSize,name, gender, begin, end);
        //调用业务层分页查询功能
        PageBean pageBean = empService.page(page, pageSize, name, gender, begin, end);
        //响应
        return Result.success(pageBean);
    }

    //新增员工
    @PostMapping
    public Result save(@RequestBody Emp emp){//@RequestBody:将json格式的数据转为为java对象
        log.info("新增员工, emp: {}",emp);
        empService.save(emp);
        return Result.success();
    }

    //修改员工信息时查询回显
    @GetMapping("/{id}")
    //由于id是路径参数，故需要注解@PathVariable将路径中的{id}绑定到方法中的参数id的上
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询员工信息, id:{}",id);
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }

    //修改员工信息
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("更新员工信息 : {}",emp);
        empService.update(emp);
        return Result.success();
    }
}