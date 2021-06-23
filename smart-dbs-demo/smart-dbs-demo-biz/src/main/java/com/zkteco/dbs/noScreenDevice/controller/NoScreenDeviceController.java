package com.zkteco.dbs.noScreenDevice.controller;


import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.Result;
import com.zkteco.dbs.device.dto.DeviceDTO;
import com.zkteco.dbs.device.service.DeviceService;
import com.zkteco.dbs.noScreenDevice.service.NoScreenDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * NoScreenDeviceController
 * 无屏设备前端控制器
 * @author able.lee
 * @date 2021/4/6 14:23
 * @since v1.0.0
 */
@RestController
@RequestMapping("/noScreenDevice")
public class NoScreenDeviceController {

    @Autowired
    private NoScreenDeviceService noScreenDeviceService;

    @Autowired
    private DeviceService deviceService;

    @PostMapping("testConnect")
    public Result testConnect(@RequestBody BaseDTO dto) {

        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("addDevice")
    public Result addDevice(@RequestBody BaseDTO<DeviceDTO> dto) {

        noScreenDeviceService.addNoScreenDevice(dto);

        return Result.getSuccess(dto.getLang());
    }


    @PostMapping("deleteDevice")
    public Result deleteDevice(@RequestBody BaseDTO<DeviceDTO> dto) {
        deviceService.remove(dto);
        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("generateQRCode")
    public Result generateQRCode(@RequestBody BaseDTO dto){
        String res = noScreenDeviceService.generateQRCode();
        return Result.getSuccess(dto.getLang(),res);
    }
}

