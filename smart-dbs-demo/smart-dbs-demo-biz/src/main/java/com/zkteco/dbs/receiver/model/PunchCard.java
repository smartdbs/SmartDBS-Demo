/*
 * File Name: PunchCard
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.receiver.model;

import lombok.Data;

/**
 * PunchCard
 *
 * @author sheldon.wu
 * @date 2020/12/28 15:36
 * @since 1.0.0
 */
@Data
public class PunchCard {

    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 员工工号
     */
    private String employeeNo;

    /**
     * 员工卡号
     */
    private String card;

    /**
     * 卡号信息变更时间
     */
    private Long pushTimeStamp;
}
