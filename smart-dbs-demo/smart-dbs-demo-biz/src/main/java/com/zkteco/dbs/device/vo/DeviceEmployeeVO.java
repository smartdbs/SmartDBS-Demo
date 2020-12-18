/*
 * File Name: DeviceEmployeeVO
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.device.vo;

import java.io.Serializable;
import lombok.Data;

/**
 * DeviceEmployeeVO
 * 设备员工关系-VO
 * @author sheldon.wu
 * @date 2020/11/20 17:46
 * @since 1.0.0
 */
@Data
public class DeviceEmployeeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 员工工号
     */
    private String employeeNo;

    /**
     * 企业id
     */
    private String companyId;

    /**
     * 员工头像
     */
    private String avatar;

    /**
     * 员工姓名
     */
    private String formattedName;
}
