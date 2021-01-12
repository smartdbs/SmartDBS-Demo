/*
 * File Name: UpgradeController
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.device.controller;

import com.zkcloud.api.dbs.model.DeviceUpgradeHistoryResponse;
import com.zkcloud.api.dbs.model.NewFwVersion;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.Result;
import com.zkteco.dbs.device.dto.DeviceUpgradeDTO;
import com.zkteco.dbs.device.service.DeviceUpgradeService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UpgradeController
 * 固件升级-前端控制器
 * @author sheldon.wu
 * @date 2021/1/4 15:54
 * @since 1.0.0
 */
@RestController
@RequestMapping("/deviceUpgrade")
public class DeviceUpgradeController {

    @Autowired
    private DeviceUpgradeService deviceUpgradeService;

    @PostMapping("newestList")
    public Result newestList(@RequestBody BaseDTO<DeviceUpgradeDTO> dto) {
        List<NewFwVersion> list = deviceUpgradeService.newestList(dto);
        return Result.getSuccess(list, dto.getLang());
    }

    @PostMapping("upgrade")
    public Result upgrade(@RequestBody BaseDTO<DeviceUpgradeDTO> dto) {
        Map<String, String> taskId = deviceUpgradeService.upgrade(dto);
        return Result.getSuccess(dto.getLang(), taskId);
    }

    @PostMapping("historyList")
    public Result historyList(@RequestBody BaseDTO<DeviceUpgradeDTO> dto) {
        List<DeviceUpgradeHistoryResponse> list = deviceUpgradeService.historyList(dto);
        return Result.getSuccess(list, dto.getLang());
    }
}
