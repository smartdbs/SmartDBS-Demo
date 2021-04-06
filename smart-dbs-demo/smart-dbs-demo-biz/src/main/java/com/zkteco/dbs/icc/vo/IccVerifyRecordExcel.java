/*
 * File Name: IccVerifyRecordExcel
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.icc.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import java.io.Serializable;
import lombok.Data;

/**
 * IccVerifyRecordExcel
 *
 * @author sheldon.wu
 * @date 2021/2/25 15:36
 * @since 1.0.0
 */
@Data
public class IccVerifyRecordExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备序列号
     */
    @ExcelProperty(value = "${icc.excel.sn}", index = 0)
    private String sn;

    /**
     * 员工工号
     */
    @ExcelProperty(value = "${icc.excel.employeeNo}", index = 1)
    private String employeeNo;

    /**
     * 核验时间(iso8601标准格式)
     */
    @ExcelProperty(value = "${icc.excel.time}", index = 2)
    private String time;

    /**
     * 核验结果. 0：核验失败；1：核验成功；2：禁止
     */
    @ExcelProperty(value = "${icc.excel.status}", index = 3)
    private String status;

    /**
     * 体温。此属性配合设备使用，当设备带体温检测功能才有返回值
     */
    @ExcelProperty(value = "${icc.excel.temperature}", index = 4)
    private String temperature;


    /**
     * 是否佩戴口罩，“0” 未佩戴口罩，“1” 已佩戴口罩。此属性配合设备使用，当设备带口罩检测功能才有返回值
     */
    @ExcelProperty(value = "${icc.excel.maskStatus}", index = 5)
    private String maskStatus;

    /**
     * 验证方式，默认=0，详见验证方式说明
     */
    @ExcelProperty(value = "${icc.excel.deviceVerifyMode}", index = 6)
    private String deviceVerifyMode;
}
