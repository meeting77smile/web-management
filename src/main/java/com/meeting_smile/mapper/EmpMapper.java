package com.meeting_smile.mapper;

import com.meeting_smile.pojo.Emp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {
    //获取当前页的结果列表
    public List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);

    //批量删除
    void delete(List<Integer> ids);

    /**
     * 新增员工
     * @param emp
     * @return
     */
    @Insert("insert into tlias.emp(username, name, gender, image, job, entrydate, dept_id, create_time, update_time)" +
            " values(#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    @Select("select id, username, password, name, gender, image, job, entrydate, dept_id, create_time, update_time " +
            "from tlias.emp " +
            "where username=#{username} and password =#{password}")
    public Emp getByUsernameAndPassword(Emp emp);

    /**
     * 根据ID查询员工
     * @param id
     * @return
     */
    @Select("select * from tlias.emp where id = #{id}")
    Emp getById(Integer id);

    /**
     * 更新员工(基于动态sql来实现)
     * @param emp
     */
    void update(Emp emp);
}