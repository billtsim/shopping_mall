package com.a88.mapper;

import com.a88.Pojo.dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


import java.util.ArrayList;

@Mapper
public interface deptMapper {
    // check all department data
    @Select("select * from db_01.tb_dept")
    ArrayList<dept> list();


    @Delete("delete from db_01.tb_dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into db_01.tb_dept (department, create_time, update_time) VALUES (#{department}, #{createTime}, #{updateTime})")
    void insert(dept d);
}
