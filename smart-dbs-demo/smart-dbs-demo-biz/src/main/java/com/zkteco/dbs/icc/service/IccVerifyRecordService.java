package com.zkteco.dbs.icc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.icc.dto.IccVerifyRecordDTO;
import com.zkteco.dbs.icc.model.IccVerifyRecord;
import com.zkteco.dbs.icc.vo.IccVerifyRecordVO;
import javax.servlet.http.HttpServletResponse;

/**
 * IccVerifyRecordService
 * 核验事件记录 服务类
 * @author sheldon.wu
 * @date 2021/2/25 15:27
 * @since 1.0.0
 */
public interface IccVerifyRecordService extends IService<IccVerifyRecord> {


    /**
     * pageList
     * 核验事件分页列表
     * @param dto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.icc.model.IccVerifyRecord>
     * @throws
     * @author sheldon.wu
     * @date 2021/2/25 15:29
     * @since 1.0.0
     */
    IPage<IccVerifyRecordVO> pageList(BaseDTO<IccVerifyRecordDTO> dto);

    /**
     * exportExcel
     * 核验事件-导出excel
     * @param dto
     * @param response
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2021/2/25 15:29
     * @since 1.0.0
     */
    void exportExcel(BaseDTO<IccVerifyRecordDTO> dto, HttpServletResponse response);

}
