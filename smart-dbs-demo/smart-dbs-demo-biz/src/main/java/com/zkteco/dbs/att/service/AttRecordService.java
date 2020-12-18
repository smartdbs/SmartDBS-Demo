package com.zkteco.dbs.att.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkteco.dbs.att.dto.AttRecordDTO;
import com.zkteco.dbs.att.model.AttRecord;
import com.zkteco.dbs.att.vo.AttRecordVO;
import com.zkteco.dbs.common.base.model.BaseDTO;
import javax.servlet.http.HttpServletResponse;

/**
 * AttRecordService
 * 考勤记录-业务层
 * @author sheldon.wu
 * @date 2020/11/23 14:25
 * @since 1.0.0
 */
public interface AttRecordService extends IService<AttRecord> {

    /**
     * pageList
     * 考勤记录-分页列表
     * @param dto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.att.vo.AttRecordVO>
     * @throws
     * @author sheldon.wu
     * @date 2020/11/30 11:47
     * @since 1.0.0
     */
    IPage<AttRecordVO> pageList(BaseDTO<AttRecordDTO> dto);

    /**
     * exportExcel
     * 考勤记录-导出excel
     * @param dto
     * @param response
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/11/24 10:09
     * @since 1.0.0
     */
    void exportExcel(BaseDTO<AttRecordDTO> dto, HttpServletResponse response);
}
