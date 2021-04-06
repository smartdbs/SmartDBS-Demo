package com.zkteco.dbs.icc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.icc.dto.IccPersonVerifyRecordDTO;
import com.zkteco.dbs.icc.model.IccPersonVerifyRecord;
import com.zkteco.dbs.icc.vo.IccPersonVerifyRecordVO;
import javax.servlet.http.HttpServletResponse;

/**
 * IccPersonVerifyRecordService
 * 人证事件记录 服务类
 * @author sheldon.wu
 * @date 2021/2/25 15:27
 * @since 1.0.0
 */
public interface IccPersonVerifyRecordService extends IService<IccPersonVerifyRecord> {


    /**
     * pageList
     * 人证事件分页列表
     * @param dto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.icc.model.IccPersonVerifyRecordVO>
     * @throws
     * @author sheldon.wu
     * @date 2021/2/25 15:29
     * @since 1.0.0
     */
    IPage<IccPersonVerifyRecordVO> pageList(BaseDTO<IccPersonVerifyRecordDTO> dto);

    /**
     * exportExcel
     * 人证事件事件-导出excel
     * @param dto
     * @param response
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2021/2/25 15:29
     * @since 1.0.0
     */
    void exportExcel(BaseDTO<IccPersonVerifyRecordDTO> dto, HttpServletResponse response);

}
