package com.zkteco.dbs.device.controller;


import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.Result;
import com.zkteco.dbs.device.dto.DeviceEmployeeDTO;
import com.zkteco.dbs.device.service.DeviceEmployeeService;
import com.zkteco.dbs.device.vo.DeviceEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DeviceEmployeeController
 * 设备员工关系-前端控制器
 * @author sheldon.wu
 * @date 2020/11/20 18:16
 * @since 1.0.0
 */
@RestController
@RequestMapping("/deviceEmployee")
public class DeviceEmployeeController {

    @Autowired
    private DeviceEmployeeService deviceEmployeeService;

    @PostMapping("list")
    public Result list(@RequestBody BaseDTO<DeviceEmployeeDTO> dto) {
        List<DeviceEmployeeVO> result = deviceEmployeeService.list(dto);
        return Result.getSuccess(result, dto.getLang());
    }

    @PostMapping("save")
    public Result save(@RequestBody BaseDTO<DeviceEmployeeDTO> dto) {
        deviceEmployeeService.save(dto);
        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("remove")
    public Result remove(@RequestBody BaseDTO<DeviceEmployeeDTO> dto) {
        deviceEmployeeService.remove(dto);
        return Result.getSuccess(dto.getLang());
    }
}

