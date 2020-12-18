/*
 * File Name: DeviceVO
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.att.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * AttRecordVO
 * 考勤记录-VO
 * @author sheldon.wu
 * @date 2020/11/23 14:22
 * @since 1.0.0
 */
@Data
public class AttRecordVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 打卡时间
     */
    private Long checkInTime;

    /**
     * 企业id
     */
    private String companyId;

    /**
     * 打卡时间(iso8601标准格式)
     */
    private String isoCheckInTime;

    /**
     * 员工工号
     */
    private String employeeNo;

    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 设备别名
     */
    private String alias;

    /**
     * 员工姓名
     */
    private String formattedName;
}
