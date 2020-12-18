package com.zkteco.dbs.system.controller;


import com.zkteco.dbs.common.base.vo.Result;
import com.zkteco.dbs.system.service.SystemInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * SystemInforController
 * 系统信息
 * @author able.lee
 * @date 2020/11/30 15:18
 * @since v1.0.0
 */
@RestController
public class SystemInfoController {

    @Autowired
    private SystemInforService systemInforService;

    @PostMapping("systemInfo")
    public Result systemInfo(){

        Map<String, Object> list = systemInforService.systemInfo();

        return Result.getSuccess(null,list);

    }

}

