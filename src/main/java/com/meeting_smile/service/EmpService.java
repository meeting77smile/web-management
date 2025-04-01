package com.meeting_smile.service;

import com.meeting_smile.pojo.Emp;
import com.meeting_smile.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    /**
     * 条件分页查询
     * @param page     页码
     * @param pageSize 每页展示记录数
     * @param name     姓名
     * @param gender   性别
     * @param begin   开始时间
     * @param end     结束时间
     * @return
     */
    PageBean page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end);

    /**
     * 批量删除操作
     * @param ids id集合
     */
    void delete(List<Integer> ids);

    /**
     * 新增员工
     * @param emp
     */
    void save(Emp emp);

    /**
     * 根据ID来查询员工
     * @param id
     * @return
     */
    Emp getById(Integer id);

    /**
     * 用户登录
     * @param emp
     * @return
     */
    public Emp login(Emp emp);

    /**
     * 更新员工
     * @param emp
     */
    void update(Emp emp);
}