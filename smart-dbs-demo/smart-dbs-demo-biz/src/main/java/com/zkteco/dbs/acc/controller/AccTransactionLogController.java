package com.zkteco.dbs.acc.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zkteco.dbs.acc.dto.AccTransactionLogDTO;
import com.zkteco.dbs.acc.model.AccTransactionLog;
import com.zkteco.dbs.acc.service.AccTransactionLogService;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.Result;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AccTransactionLogController
 * 前端控制器
 * @author able.lee
 * @date 2020/11/30 15:17
 * @since v1.0.0
 */
@RestController
@RequestMapping("/accTransactionLog")
public class AccTransactionLogController {

    @Autowired
    private AccTransactionLogService accTransactionLogService;


    @PostMapping("pageList")
    public Result pageList(@RequestBody BaseDTO<AccTransactionLogDTO> dto){

        IPage<AccTransactionLog> res = accTransactionLogService.pageList(dto);

        return Result.getSuccess(res,dto.getLang());
    }

    @PostMapping("exportExcel")
    public void exportExcel(@RequestBody BaseDTO<AccTransactionLogDTO> dto, HttpServletResponse response) {
        accTransactionLogService.exportExcel(dto, response);
    }

}

