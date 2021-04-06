/*
 * File Name: VerifyRecord
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.receiver.model;

import lombok.Data;

/**
 * VerifyRecord
 * 核验记录
 * @author sheldon.wu
 * @date 2021/2/25 14:55
 * @since 1.0.0
 */
@Data
public class VerifyRecord {
    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 记录id，每条记录生成一个记录id，与核验照片推送的 logId 关联一一对应
     */
    private String logId;

    /**
     * 员工工号
     */
    private String employeeNo;

    /**
     * 核验时间，ISO8601时间格式， 如2020-12-30T00:00:00+08:00
     */
    private String time;

    /**
     * 核验结果. 0：核验失败；1：核验成功；2：黑名单)
     */
    private String status;

    /**
     * 验证方式，默认=0，详见验证方式说明
     */
    private String deviceVerifyMode;

    /**
     * 体温。此属性配合设备使用，当设备带体温检测功能才有返回值
     */
    private String temperature;


    /**
     * 是否佩戴口罩，“0” 未佩戴口罩，“1” 已佩戴口罩。此属性配合设备使用，当设备带口罩检测功能才有返回值
     */
    private String maskStatus;
}
