package com.zkteco.dbs.icc.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.Result;
import com.zkteco.dbs.icc.dto.IccVerifyRecordDTO;
import com.zkteco.dbs.icc.service.IccVerifyRecordService;
import com.zkteco.dbs.icc.vo.IccVerifyRecordVO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IccVerifyRecordController
 * 前端控制器
 * @author sheldon.wu
 * @date 2021/02/25 15:42
 * @since 1.0.0
 */
@RestController
@RequestMapping("/iccVerifyRecord")
public class IccVerifyRecordController {

    @Autowired
    private IccVerifyRecordService iccVerifyRecordService;


    @PostMapping("pageList")
    public Result pageList(@RequestBody BaseDTO<IccVerifyRecordDTO> dto) {

        IPage<IccVerifyRecordVO> res = iccVerifyRecordService.pageList(dto);

        return Result.getSuccess(res, dto.getLang());
    }

    @PostMapping("exportExcel")
    public void exportExcel(@RequestBody BaseDTO<IccVerifyRecordDTO> dto, HttpServletResponse response) {
        iccVerifyRecordService.exportExcel(dto, response);
    }

}

