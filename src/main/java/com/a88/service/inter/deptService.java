package com.a88.service.inter;

import com.a88.Pojo.dept;

import java.util.ArrayList;

public interface deptService {
    // use to check all department data
    ArrayList<dept> list();

    void delete(Integer id);

    void add(dept d);
}
