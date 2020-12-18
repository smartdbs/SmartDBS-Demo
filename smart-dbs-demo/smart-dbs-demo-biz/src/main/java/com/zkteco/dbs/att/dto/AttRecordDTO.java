/*
 * File Name: DeviceDTO
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.att.dto;

import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import java.io.Serializable;
import lombok.Data;

/**
 * AttRecordDTO
 * 考勤记录-DTO
 * @author sheldon.wu
 * @date 2020/11/23 14:24
 * @since 1.0.0
 */
@Data
public class AttRecordDTO extends BaseQueryVO implements Serializable {

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
     * 设备别名
     */
    private String alias;

    /**
     * 员工工号
     */
    private String employeeNo;

    /**
     * 员工姓名
     */
    private String formattedName;

    /**
     * 企业id
     */
    private String companyId;
}
