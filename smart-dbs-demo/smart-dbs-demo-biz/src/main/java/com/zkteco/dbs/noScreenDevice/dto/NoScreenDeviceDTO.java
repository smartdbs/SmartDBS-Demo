/*
 * File Name: RegisterBiometricDTO
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.noScreenDevice.dto;

import com.zkteco.dbs.device.dto.DeviceDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * DeviceDTO
 * 
 * @author able.lee
 * @date 2021/4/6 14:43
 * @since v1.0.0
 */
@Data
public class NoScreenDeviceDTO extends DeviceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 企业集成账号
     */
    private String account;

    /**
     * 集成账号密码
     */
    private String password;




}
