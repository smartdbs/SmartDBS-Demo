/*
 * File Name: IccVerifyRecordDTO
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.icc.dto;

import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import java.io.Serializable;
import lombok.Data;

/**
 * IccVerifyRecordDTO
 *
 * @author sheldon.wu
 * @date 2021/2/25 15:28
 * @since 1.0.0
 */
@Data
public class IccVerifyRecordDTO extends BaseQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 设备序列号
     */
    private String sn;


    /**
     * 员工工号
     */
    private String employeeNo;

    /**
     * 核验结果. 0：核验失败；1：核验成功；2：黑名单
     */
    private String status;

    /**
     * 企业id
     */
    private String companyId;
}
