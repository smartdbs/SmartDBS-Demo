package com.zkteco.dbs.acc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkteco.dbs.acc.dto.AccTransactionLogDTO;
import com.zkteco.dbs.acc.model.AccTransactionLog;
import com.zkteco.dbs.common.base.model.BaseDTO;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName AccTransactionLogService
 * @Description: 门禁事件记录 服务类
 * @Author able.lee
 * @Date 2020/11/25 17:35
 * @Since v1.0.0
 */
public interface AccTransactionLogService extends IService<AccTransactionLog> {


    /**
     * pageList
     * 门禁事件分页列表
     * @param dto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.acc.model.AccTransactionLog>
     * @throws 
     * @author able.lee
     * @date 2020/11/30 14:48
     * @since v1.0.0
     */
    IPage<AccTransactionLog> pageList(BaseDTO<AccTransactionLogDTO> dto);

    /**
     * exportExcel
     * 门禁事件-导出excel
     * @param dto
     * @param response
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:48
     * @since v1.0.0
     */
    void exportExcel(BaseDTO<AccTransactionLogDTO> dto, HttpServletResponse response);

}
