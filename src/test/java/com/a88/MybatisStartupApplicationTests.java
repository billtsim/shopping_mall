package com.a88;

import com.a88.Pojo.employee;
import com.a88.mapper.empMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static java.time.LocalDate.parse;

@SpringBootTest
class MybatisStartupApplicationTests {
    //public void main(String[] args) {
    //    userDataList();
    //    deleteData();
    //}

    /*@Autowired(required = true)
    private empMapper target;

    @Test
    public void userDataList() {
        ArrayList<employee> list = target.list();
        for (employee data : list) {
            System.out.println(data);
        }
    }

    @Test
    public void deleteData() {
        int delete = target.delete(5);
        System.out.println(delete);
    }

    @Test
    public void insertData() {
        employee emp = new employee();
        emp.setUsername("coco3");
        emp.setFullName("coco1233");
        emp.setGender((short) 2);
        emp.setEntryDate(LocalDate.of(2020, 10, 10));
        emp.setJob((short) 1);
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        emp.setDepartment(2);

        target.insert(emp);
        System.out.println(emp.getId());
    }

    @Test
    public void updateData() {
        employee emp = new employee();
        emp.setId(2);
        emp.setUsername("ben");
        emp.setFullName("ben123");
        emp.setGender((short) 1);
        emp.setEntryDate(LocalDate.of(2019, 7, 1));
        emp.setJob((short) 3);
        emp.setUpdateTime(LocalDateTime.now());
        emp.setDepartment(1);

        target.update(emp);
        // System.out.println(emp.getId());
    }

    @Test
    public void getById() {
        employee employee = target.IDCheck(2);
        System.out.println(employee);

    }

    @Test
    public void getByCondition() {
        LocalDate start = LocalDate.of(2000, 1, 1);
        LocalDate less = LocalDate.of(2020, 1, 1);
        ArrayList<employee> employees = target.conditionCheck(null, (short)1, null,null );
        System.out.println(employees);
    }

    @Test
    public void updateData2() {
        employee emp = new employee();
        emp.setId(2);
        emp.setUsername("tom");
        emp.setGender((short) 1);


        target.update2(emp);
    }

    @Test
    public void delete2() {
        ArrayList<Integer> ids = new ArrayList<>();
        Collections.addAll(ids, 1,2,3);
        target.deleteById(ids);
    }

     */
}
