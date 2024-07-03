package com.a88.service.impl;

import com.a88.Pojo.dept;
import com.a88.service.inter.deptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.a88.mapper.*;

@Service
public class deptServiceImpl implements deptService {
    @Autowired
    private deptMapper dM;

    @Override
    public ArrayList<dept> list() {
        return dM.list();
    }

    @Override
    public void delete(Integer id) {
        dM.deleteById(id);
    }

    @Override
    public void add(dept d) {
        d.setCreateTime(LocalDateTime.now());
        d.setUpdateTime(LocalDateTime.now());
        dM.insert(d);
    }
}
