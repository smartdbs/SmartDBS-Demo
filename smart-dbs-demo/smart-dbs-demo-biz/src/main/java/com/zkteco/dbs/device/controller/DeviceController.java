package com.zkteco.dbs.device.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.Result;
import com.zkteco.dbs.device.dto.DeviceDTO;
import com.zkteco.dbs.device.service.DeviceService;
import com.zkteco.dbs.device.vo.DeviceVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DeviceController
 * 设备管理-前端控制器
 * @author sheldon.wu
 * @date 2020/11/20 16:07
 * @since 1.0.0
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping("pageList")
    public Result pageList(@RequestBody BaseDTO<DeviceDTO> dto) {
        IPage<DeviceVO> result = deviceService.pageList(dto);
        return Result.getSuccess(result, dto.getLang());
    }

    @PostMapping("save")
    public Result save(@RequestBody BaseDTO<DeviceDTO> dto) {
        deviceService.save(dto);
        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("remove")
    public Result remove(@RequestBody BaseDTO<DeviceDTO> dto) {
        deviceService.remove(dto);
        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("update")
    public Result update(@RequestBody BaseDTO<DeviceDTO> dto) {
        deviceService.update(dto);
        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("enable")
    public Result enable(@RequestBody BaseDTO<DeviceDTO> dto) {
        deviceService.enable(dto);
        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("disable")
    public Result disable(@RequestBody BaseDTO<DeviceDTO> dto) {
        deviceService.disable(dto);
        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("reboot")
    public Result reboot(@RequestBody BaseDTO<DeviceDTO> dto) {
        deviceService.reboot(dto);
        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("listByEmployee")
    public Result listByEmployee(@RequestBody BaseDTO<DeviceDTO> dto) {
        List<DeviceVO> result = deviceService.listByEmployee(dto);
        return Result.getSuccess(result, dto.getLang());
    }
}

