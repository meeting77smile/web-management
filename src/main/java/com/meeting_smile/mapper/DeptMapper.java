package com.meeting_smile.mapper;

import com.meeting_smile.pojo.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper//每个Mapper接口都要加，别漏了
public interface DeptMapper {
    //查询所有部门数据
    @Select("select id, name, create_time, update_time from tlias.dept")
    List<Dept> list();

    /**
     * 根据id删除部门信息
     * @param id   部门id
     */
    @Delete("delete from tlias.dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into tlias.dept (name, create_time, update_time) values (#{name},#{createTime},#{updateTime})")
    void inser(Dept dept);
}
