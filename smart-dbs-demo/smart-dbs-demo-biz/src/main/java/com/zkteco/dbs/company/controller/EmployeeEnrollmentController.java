package com.zkteco.dbs.company.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import com.zkteco.dbs.common.base.vo.Result;
import com.zkteco.dbs.company.dto.EmployeeEnrollmentDTO;
import com.zkteco.dbs.company.service.EmployeeEnrollmentService;
import com.zkteco.dbs.company.vo.EmployeeEnrollmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * EmployeeEnrollmentController
 * 前端控制器
 * @author able.lee
 * @date 2020/11/30 15:16
 * @since v1.0.0
 */
@RestController
@RequestMapping("/employeeEnrollment")
public class EmployeeEnrollmentController {

    @Autowired
    private EmployeeEnrollmentService employeeEnrollmentService;

    @PostMapping("pageList")
    public Result pageList(@RequestBody BaseDTO<BaseQueryVO> vo){

        IPage<EmployeeEnrollmentVO> res =  employeeEnrollmentService.pageList(vo);

        return Result.getSuccess(res,vo.getLang());

    }

    @PostMapping("unassignedList")
    public Result unassignedList(@RequestBody BaseDTO<BaseQueryVO> dto){

        List<EmployeeEnrollmentVO> list =  employeeEnrollmentService.unassignedList(dto);

        return Result.getSuccess(list,dto.getLang());
    }

    @PostMapping("save")
    public Result save(@RequestBody BaseDTO<EmployeeEnrollmentDTO> dto){

        employeeEnrollmentService.save(dto);

        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("update")
    public Result update(@RequestBody BaseDTO<EmployeeEnrollmentDTO> dto){

        employeeEnrollmentService.update(dto);

        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("remove")
    public Result remove(@RequestBody BaseDTO<BaseQueryVO> dto){

        employeeEnrollmentService.remove(dto);

        return Result.getSuccess(dto.getLang());
    }

}

