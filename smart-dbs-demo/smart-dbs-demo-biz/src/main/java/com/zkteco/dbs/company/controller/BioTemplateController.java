package com.zkteco.dbs.company.controller;


import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.Result;
import com.zkteco.dbs.company.dto.RegisterBiometricDTO;
import com.zkteco.dbs.company.service.BioTemplateService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BioTemplateController
 * 前端控制器
 * @author able.lee
 * @date 2020/11/30 15:16
 * @since v1.0.0
 */
@RestController
@RequestMapping("/bioTemplate")
public class BioTemplateController {

    @Autowired
    private BioTemplateService bioTemplateService;

    @PostMapping("registerBiometric")
    public Result registerBiometric(@RequestBody BaseDTO<RegisterBiometricDTO> dto) {

        Map<String, String> sessionId = bioTemplateService.registerBiometric(dto);

        return Result.getSuccess(dto.getLang(), sessionId);
    }

    @PostMapping("cancelRegisterBiometric")
    public Result cancelRegisterBiometric(@RequestBody BaseDTO<RegisterBiometricDTO> dto) {

        bioTemplateService.cancelRegisterBiometric(dto);

        return Result.getSuccess(dto.getLang());
    }
}

