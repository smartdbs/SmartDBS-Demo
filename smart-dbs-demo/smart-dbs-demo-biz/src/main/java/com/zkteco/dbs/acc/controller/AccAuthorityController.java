package com.zkteco.dbs.acc.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zkteco.dbs.acc.dto.AccAuthorityDoorDTO;
import com.zkteco.dbs.acc.dto.AccAuthorityEmployeeDTO;
import com.zkteco.dbs.acc.model.AccAuthority;
import com.zkteco.dbs.acc.service.AccAuthorityService;
import com.zkteco.dbs.acc.vo.AccAuthorityDoorVO;
import com.zkteco.dbs.acc.vo.AccAuthorityEmployeeVO;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import com.zkteco.dbs.common.base.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AccAuthorityController
 * 前端控制器
 * @author able.lee
 * @date 2020/11/30 14:58
 * @since v1.0.0
 */
@RestController
@RequestMapping("/accAuthority")
public class AccAuthorityController {

    @Autowired
    private AccAuthorityService accAuthorityService;


    @PostMapping("pageList")
    public Result pageList(@RequestBody BaseDTO<BaseQueryVO> dto){

        IPage<AccAuthority> res = accAuthorityService.pageList(dto);

        return Result.getSuccess(res,dto.getLang());
    }

    @PostMapping("save")
    public Result save(@RequestBody BaseDTO<AccAuthority> dto){

        accAuthorityService.save(dto);

        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("update")
    public Result update(@RequestBody BaseDTO<AccAuthority> dto){

        accAuthorityService.update(dto);

        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("remove")
    public Result remove(@RequestBody BaseDTO<AccAuthority> dto){

        accAuthorityService.remove(dto);

        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("doorAllocatedList")
    public Result doorAllocatedList(@RequestBody BaseDTO<AccAuthority> dto){

        List<AccAuthorityDoorVO> list = accAuthorityService.doorAllocatedList(dto);

        return Result.getSuccess(list,dto.getLang());
    }

    @PostMapping("doorUnassignedList")
    public Result doorUnassignedList(@RequestBody BaseDTO<AccAuthorityDoorDTO> dto){

        List<AccAuthorityDoorVO> list = accAuthorityService.doorUnassignedList(dto);

        return Result.getSuccess(list,dto.getLang());
    }

    @PostMapping("employeeAllocatedList")
    public Result employeeAllocatedList(@RequestBody BaseDTO<AccAuthorityEmployeeDTO> dto){

        List<AccAuthorityEmployeeVO> list = accAuthorityService.employeeAllocatedList(dto);

        return Result.getSuccess(list,dto.getLang());
    }

    @PostMapping("employeeUnassignedList")
    public Result employeeUnassignedList(@RequestBody BaseDTO<AccAuthorityEmployeeDTO> dto){

        List<AccAuthorityEmployeeVO> list = accAuthorityService.employeeUnassignedList(dto);

        return Result.getSuccess(list,dto.getLang());
    }



}

