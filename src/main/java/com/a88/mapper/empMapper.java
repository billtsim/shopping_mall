package com.a88.mapper;

import com.a88.Pojo.employee;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface empMapper {
/*
    // all data
    @Select("select id, username, fullName, gender, entrydate, job, create_time, update_time, department from db_01.tb_employee")
    public ArrayList<employee> list();

    // delete data
    @Delete("delete from db_01.tb_employee where id = #{id}")
    public int delete(Integer id);

    // add data
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into db_01.tb_employee (username, fullName, gender, entrydate, job, create_time, update_time, department)" +
            "values (#{username}, #{name}, #{gender}, #{entryDate}, #{job}, #{createTime}, #{updateTime}, #{department});")
    public void insert(employee emp);

    // adjust data
    @Update("update db_01.tb_employee set username = #{username}, fullName = #{name}, gender = #{gender}," +
            "entrydate = #{entryDate}, job = #{job}, update_time = #{updateTime}, department = #{department} where id = #{id}")
    public void update(employee emp);

    // get data by id
    @Select("select * from db_01.tb_employee where id = #{id}")
    public employee IDCheck(Integer id);

    // get data by condition
    //@Select("select id, username, fullName, gender, entrydate, job, create_time, update_time, department from db_01.tb_employee where username like concat('%', #{name},'%') and gender = #{gender} and " +
    //        "entrydate between #{begin} and #{end}")
    public ArrayList<employee> conditionCheck( String name, Short gender,LocalDate begin, LocalDate end);

    // 動態update
    public void update2(employee emp);

    // 批量delete
    public void deleteById(ArrayList<Integer> ids);

 */

    // case

    @Select("select count(*) from db_01.tb_employee")
    public Long count();

    //@Select("select * from db_01.tb_employee limit #{start}, #{pageSize} ")
    public List<employee> page(Integer start,
                               Integer pageSize,
                               String name,
                               Short gender,
                               LocalDate begin,
                               LocalDate end);

    void delete(ArrayList<Integer> ids);

    @Select("select * from db_01.tb_employee where id = #{id}")
    employee getById(Integer id);

    void update(employee emp);

    @Select("select * from db_01.tb_employee where username = #{username} and password = #{password}")
    employee getByUsernameAndPassword(employee emp);
}


