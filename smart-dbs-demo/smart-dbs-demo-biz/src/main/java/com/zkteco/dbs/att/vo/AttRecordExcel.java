/*
 * File Name: DeviceVO
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.att.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * AttRecordExcel
 * 考勤记录-Excel
 * @author sheldon.wu
 * @date 2020/11/24 15:10
 * @since 1.0.0
 */
@Data
public class AttRecordExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工工号
     */
    @ExcelProperty(value = "${att.excel.employeeNo}", index = 0)
    private String employeeNo;

    /**
     * 员工姓名
     */
    @ExcelProperty(value = "${att.excel.formattedName}", index = 1)
    private String formattedName;

    /**
     * 设备序列号
     */
    @ExcelProperty(value = "${att.excel.sn}", index = 2)
    private String sn;

    /**
     * 设备别名
     */
    @ExcelProperty(value = "${att.excel.alias}", index = 3)
    private String alias;

    /**
     * 打卡时间(iso8601标准格式)
     */
    @ExcelProperty(value = "${att.excel.punchTime}", index = 4)
    private String isoCheckInTime;
}
