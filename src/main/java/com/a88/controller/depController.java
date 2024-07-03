package com.a88.controller;

import com.a88.Pojo.dept;
import com.a88.Pojo.result;
import com.a88.service.inter.deptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@Slf4j
@RequestMapping("/depts")
public class depController {

    @Autowired
    private deptService deptService;

    // @RequestMapping(value = "/depts", method = RequestMethod.GET)
    // 指定了請求方式是GET
    @GetMapping
    public result list() {
        log.info("check all department data");

        // user Service assess department data
        ArrayList<dept> deptList = deptService.list();

        return result.success(deptList);
    }


    @DeleteMapping("/{id}")
    public result delete(@PathVariable Integer id) {
        log.info("delete department according to id: {}", id);
        deptService.delete(id);
        return result.success();
    }

    @PostMapping
    public result add(@RequestBody dept d) {
        log.info("add department: {}", d);
        deptService.add(d);

        return result.success();
    }


}
