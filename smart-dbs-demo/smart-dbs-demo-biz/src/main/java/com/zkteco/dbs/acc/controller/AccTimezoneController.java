package com.zkteco.dbs.acc.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zkteco.dbs.acc.model.AccTimezone;
import com.zkteco.dbs.acc.service.AccTimezoneService;
import com.zkteco.dbs.acc.vo.AccTimezoneVO;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import com.zkteco.dbs.common.base.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AccTimezoneController
 * 前端控制器
 * @author able.lee
 * @date 2020/11/30 15:16
 * @since v1.0.0
 */
@RestController
@RequestMapping("/accTimezone")
public class AccTimezoneController {

    @Autowired
    private AccTimezoneService accTimezoneService;

    @PostMapping("pageList")
    public Result pageList(@RequestBody BaseDTO<BaseQueryVO> dto){

        IPage<AccTimezoneVO> res = accTimezoneService.pageList(dto);

        return Result.getSuccess(res,dto.getLang());
    }

    @PostMapping("timezoneSetUp")
    public Result timezoneSetUp(@RequestBody BaseDTO<AccTimezoneVO> dto){

        accTimezoneService.timezoneSetUp(dto);

        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("update")
    public Result update(@RequestBody BaseDTO<AccTimezone> dto){

        accTimezoneService.update(dto);

        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("remove")
    public Result remove(@RequestBody BaseDTO<AccTimezone> dto){

        accTimezoneService.remove(dto);

        return Result.getSuccess(dto.getLang());
    }

    @PostMapping("add")
    public Result add(@RequestBody BaseDTO<AccTimezoneVO> dto){

        accTimezoneService.add(dto);

        return Result.getSuccess(dto.getLang());
    }


    @PostMapping("selectList")
    public Result selectList(@RequestBody BaseDTO<BaseQueryVO> dto){

        List<AccTimezone> list = accTimezoneService.selectList(dto);

        return Result.getSuccess(list,dto.getLang());
    }




}

