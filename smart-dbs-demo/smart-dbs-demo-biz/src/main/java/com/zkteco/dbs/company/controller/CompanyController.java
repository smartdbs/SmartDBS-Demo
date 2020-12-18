package com.zkteco.dbs.company.controller;


import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import com.zkteco.dbs.common.base.vo.Result;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * CompanyController
 * 前端控制器
 * @author able.lee
 * @date 2020/11/30 15:16
 * @since v1.0.0
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DbsConfig dbsConfig;

    @PostMapping("currentCompanyInfo")
    public Result currentCompanyInfo(@RequestBody BaseDTO<BaseQueryVO> dto){
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        return Result.getSuccess(dto.getLang(),company);
    }

    @PostMapping("update")
    public Result update(@RequestBody BaseDTO<Company> dto){
        companyService.update(dto);
        return Result.getSuccess(dto.getLang());
    }

}

