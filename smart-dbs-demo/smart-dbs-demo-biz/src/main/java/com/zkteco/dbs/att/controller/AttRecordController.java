package com.zkteco.dbs.att.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zkteco.dbs.att.dto.AttRecordDTO;
import com.zkteco.dbs.att.service.AttRecordService;
import com.zkteco.dbs.att.vo.AttRecordVO;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.Result;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AttRecordController
 * 考勤记录-前端控制器
 * @author sheldon.wu
 * @date 2020/11/24 8:59
 * @since 1.0.0
 */
@RestController
@RequestMapping("/attRecord")
public class AttRecordController {

    @Autowired
    private AttRecordService attRecordService;

    @PostMapping("pageList")
    public Result pageList(@RequestBody BaseDTO<AttRecordDTO> dto) {
        IPage<AttRecordVO> result = attRecordService.pageList(dto);
        return Result.getSuccess(result, dto.getLang());
    }

    @PostMapping("exportExcel")
    public void exportExcel(@RequestBody BaseDTO<AttRecordDTO> dto, HttpServletResponse response) {
        attRecordService.exportExcel(dto, response);
    }

}

