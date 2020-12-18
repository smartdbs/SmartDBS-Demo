/*
 * File Name: AccTransactionLogExcel
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.acc.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * AccTransactionLogExcel
 *
 * @author sheldon.wu
 * @date 2020/11/27 11:26
 * @since 1.0.0
 */
@Data
public class AccTransactionLogExcel implements Serializable {

    @ExcelProperty(value = "${acc.excel.sn}", index = 0)
    private String sn;

    @ExcelProperty(value = "${acc.excel.verified}", index = 1)
    private String verified;

    @ExcelProperty(value = "${acc.excel.eventCode}", index = 2)
    private String eventCode;

    @ExcelProperty(value = "${acc.excel.inOutType}", index = 3)
    private String inOutType;

    @ExcelProperty(value = "${acc.excel.time}", index = 4)
    private String time;
}
