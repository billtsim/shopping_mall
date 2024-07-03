package com.a88.service.impl;

import com.a88.Pojo.PageBean;
import com.a88.Pojo.employee;
import com.a88.mapper.empMapper;
import com.a88.service.inter.empService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class empServiceImpl implements empService {

    @Autowired
    private empMapper empMapper;

    @Override
    public PageBean page(Integer page,
                         Integer pageSize,
                         String name,
                         Short gender,
                         LocalDate begin,
                         LocalDate end) {
        // get total count
        Long count = empMapper.count();
        // get page size
        Integer start = (page - 1) * pageSize;
        List<employee> list = empMapper.page(start, pageSize, name, gender, begin, end);

        // 封裝到PageBean class
        return new PageBean(count, list);

    }

    @Override
    public void delete(ArrayList<Integer> ids) {
        empMapper.delete(ids);
    }

    @Override
    public employee getById(Integer id) {
        return empMapper.getById(id);
    }

    @Override
    public void update(employee emp) {
        emp.setUpdateTime(LocalDateTime.now());

        empMapper.update(emp);
    }

    @Override
    public employee login(employee emp) {

        return empMapper.getByUsernameAndPassword(emp);
    }
}
