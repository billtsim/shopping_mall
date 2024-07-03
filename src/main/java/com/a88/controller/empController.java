package com.a88.controller;

import com.a88.Pojo.PageBean;
import com.a88.Pojo.result;
import com.a88.Pojo.employee;
import com.a88.service.inter.empService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/emps")
public class empController {
    @Autowired
    private empService empService;

    @GetMapping
    public result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name,
                       Short gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end)
    {
         log.info("分頁查詢, 參數: {}, {}, {}, {}, {}, {} ", page, pageSize, name, gender, begin, end);

         PageBean pageBean = empService.page(page, pageSize, name, gender, begin, end);
         return result.success(pageBean);
    }

    @DeleteMapping("/{ids}")
    public result delete(@PathVariable ArrayList<Integer> ids) {
        log.info("delete operation, ids:{}", ids);
        empService.delete(ids);
        return result.success();
    }

    // data 回顯
    @GetMapping("/{id}")
    public result getById(@PathVariable Integer id) {
        log.info("check emp_data bsed on id, ID: {}", id);
        employee emp = empService.getById(id);

        return result.success(emp);
    }

    // update data
    @PutMapping
    public result update(@RequestBody employee emp) {
        log.info("update employee: {}", emp);
        empService.update(emp);
        return result.success();
    }





}
