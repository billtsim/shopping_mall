package com.a88.service.inter;

import com.a88.Pojo.PageBean;
import com.a88.Pojo.employee;

import java.time.LocalDate;
import java.util.ArrayList;

public interface empService {
    PageBean page(Integer page,
                  Integer pageSize,
                  String name,
                  Short gender,
                  LocalDate begin,
                  LocalDate end);


    void delete(ArrayList<Integer> ids);

    employee getById(Integer id);

    void update(employee emp);

    employee login(employee emp);
}
