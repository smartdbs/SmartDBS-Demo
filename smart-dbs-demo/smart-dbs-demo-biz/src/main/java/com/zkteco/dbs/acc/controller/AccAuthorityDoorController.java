package com.zkteco.dbs.acc.controller;


import com.zkteco.dbs.acc.dto.SaveAccAuthorityDoorDTO;
import com.zkteco.dbs.acc.model.AccAuthorityDoor;
import com.zkteco.dbs.acc.service.AccAuthorityDoorService;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * AccAuthorityDoorController
 * 前端控制器
 * @author able.lee
 * @date 2020/11/30 15:16
 * @since v1.0.0
 */
@RestController
@RequestMapping("/accAuthorityDoor")
public class AccAuthorityDoorController {

    @Autowired
    private AccAuthorityDoorService accAuthorityDoorService;

    @PostMapping("removeDoorAuthority")
    public Result removeDoorAuthority(@RequestBody BaseDTO<AccAuthorityDoor> dto){

        accAuthorityDoorService.removeDoorAuthority(dto);

        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("saveDoorAuthority")
    public Result saveDoorAuthority(@RequestBody BaseDTO<SaveAccAuthorityDoorDTO> dto){

        accAuthorityDoorService.saveDoorAuthority(dto);

        return Result.getSuccess(dto.getLang());
    }
}

