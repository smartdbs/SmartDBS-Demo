package com.zkteco.dbs.acc.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zkteco.dbs.acc.service.AccDoorService;
import com.zkteco.dbs.acc.vo.AccDoorVO;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.Result;
import com.zkteco.dbs.device.dto.DeviceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AccDoorController
 * 前端控制器
 * @author able.lee
 * @date 2020/11/30 15:16
 * @since v1.0.0
 */
@RestController
@RequestMapping("/accDoor")
public class AccDoorController {

    @Autowired
    private AccDoorService accDoorService;

    @PostMapping("pageList")
    public Result pageList(@RequestBody BaseDTO<DeviceDTO> dto) {

        IPage<AccDoorVO> res = accDoorService.pageList(dto);

        return Result.getSuccess(res, dto.getLang());
    }

    @PostMapping("update")
    public Result update(@RequestBody BaseDTO<DeviceDTO> dto) {

        accDoorService.update(dto);

        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("openDoor")
    public Result openDoor(@RequestBody BaseDTO<DeviceDTO> dto) {

        accDoorService.openDoor(dto);

        return Result.getSuccess(dto.getLang());
    }
}

