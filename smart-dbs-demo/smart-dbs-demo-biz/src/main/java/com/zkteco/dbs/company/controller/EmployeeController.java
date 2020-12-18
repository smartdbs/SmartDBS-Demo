package com.zkteco.dbs.company.controller;


import com.zkteco.dbs.common.base.vo.Result;
import com.zkteco.dbs.company.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 * EmployeeController
 * 前端控制器
 * @author able.lee
 * @date 2020/11/30 15:16
 * @since v1.0.0
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("uploadAvatar")
    public Result uploadAvatar(@RequestParam(name = "file", required = false) MultipartFile file,
                               @RequestParam(value="lang") String lang) throws FileNotFoundException {
        String url = employeeService.uploadAvatar(file,lang);

        return Result.getSuccess(lang,url);

    }


}

