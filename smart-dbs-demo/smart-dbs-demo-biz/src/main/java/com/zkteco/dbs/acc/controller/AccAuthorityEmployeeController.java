package com.zkteco.dbs.acc.controller;


import com.zkteco.dbs.acc.dto.AccAuthorityEmployeeDTO;
import com.zkteco.dbs.acc.dto.SaveAccAuthorityEmployeeDTO;
import com.zkteco.dbs.acc.service.AccAuthorityEmployeeService;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * AccAuthorityEmployeeController
 * 前端控制器
 * @author able.lee
 * @date 2020/11/30 15:16
 * @since v1.0.0
 */
@RestController
@RequestMapping("/accAuthorityEmployee")
public class AccAuthorityEmployeeController {

    @Autowired
    private AccAuthorityEmployeeService accAuthorityEmployeeService;

    @PostMapping("saveEmployeeAuthority")
    public Result saveEmployeeAuthority(@RequestBody BaseDTO<SaveAccAuthorityEmployeeDTO> dto){

        accAuthorityEmployeeService.saveEmployeeAuthority(dto);

        return Result.getSuccess(dto.getLang());

    }


    @PostMapping("removeEmployeeAuthority")
    public Result removeEmployeeAuthority(@RequestBody BaseDTO<AccAuthorityEmployeeDTO> dto){

        accAuthorityEmployeeService.removeEmployeeAuthority(dto);

        return Result.getSuccess(dto.getLang());

    }
}

