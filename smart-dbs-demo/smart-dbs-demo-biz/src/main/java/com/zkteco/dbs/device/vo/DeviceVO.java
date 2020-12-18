/*
 * File Name: DeviceVO
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.device.vo;

import java.io.Serializable;
import lombok.Data;

/**
 * DeviceVO
 * 设备管理-VO
 * @author sheldon.wu
 * @date 2020/11/20 15:02
 * @since 1.0.0
 */
@Data
public class DeviceVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备id
     */
    private Integer id;

    /**
     * 企业id
     */
    private String companyId;

    /**
     * 设备别名
     */
    private String alias;

    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 设备在线状态(0:离线 1:在线)
     */
    private Integer status;

    /**
     * 设备激活状态(0:未激活 1:已激活)
     */
    private Integer active;

    /**
     * 设备类型(0:考勤设备 1:门禁设备)
     */
    private Integer type;

    /**
     * 设备启用状态(0:禁用 1:启用)
     */
    private Integer enable;

    /**
     * 固件版本
     */
    private String fwVersion;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 公网ip
     */
    private String ipAddress;

    /**
     * 内网ip
     */
    private String localIp;

    /**
     * 设备协议版本(0 pull，1 push，2 best，3 ufo，4 best-w，5 best-t)
     */
    private Integer protocol;

    /**
     * 物理地址
     */
    private String mac;

    /**
     * 是否支持远程登记可见光人脸，0 不支持，1支持，2未知
     */
    private Integer supportRemoteFacePhoto;

    /**
     * 是否支持远程登记指纹，0 不支持，1支持，2未知
     */
    private Integer supportRemoteFinger;
}
