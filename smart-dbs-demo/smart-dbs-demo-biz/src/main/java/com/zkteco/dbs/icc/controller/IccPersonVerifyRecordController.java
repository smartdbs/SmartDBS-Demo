package com.zkteco.dbs.icc.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.Result;
import com.zkteco.dbs.icc.dto.IccPersonVerifyRecordDTO;
import com.zkteco.dbs.icc.service.IccPersonVerifyRecordService;
import com.zkteco.dbs.icc.vo.IccPersonVerifyRecordVO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IccPersonVerifyRecordController
 * 前端控制器
 * @author sheldon.wu
 * @date 2021/02/25 15:42
 * @since 1.0.0
 */
@RestController
@RequestMapping("/iccPersonVerifyRecord")
public class IccPersonVerifyRecordController {

    @Autowired
    private IccPersonVerifyRecordService iccPersonVerifyRecordService;


    @PostMapping("pageList")
    public Result pageList(@RequestBody BaseDTO<IccPersonVerifyRecordDTO> dto) {

        IPage<IccPersonVerifyRecordVO> res = iccPersonVerifyRecordService.pageList(dto);

        return Result.getSuccess(res, dto.getLang());
    }

    @PostMapping("exportExcel")
    public void exportExcel(@RequestBody BaseDTO<IccPersonVerifyRecordDTO> dto, HttpServletResponse response) {
        iccPersonVerifyRecordService.exportExcel(dto, response);
    }

}

