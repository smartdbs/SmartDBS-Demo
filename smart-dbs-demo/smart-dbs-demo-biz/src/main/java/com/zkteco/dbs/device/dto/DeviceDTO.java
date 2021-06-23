/*
 * File Name: DeviceDTO
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.device.dto;

import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import java.io.Serializable;
import lombok.Data;

/**
 * DeviceDTO
 * 设备管理-DTO
 * @author sheldon.wu
 * @date 2020/11/20 15:01
 * @since 1.0.0
 */
@Data
public class DeviceDTO extends BaseQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备别名
     */
    private String alias;

    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 设备类型(0:考勤设备 1:门禁设备)
     */
    private Integer type;

    /**
     * 设备在线状态(0:离线 1:在线)
     */
    private Integer status;

    /**
     * 门名称
     */
    private String doorName;

    /**
     * 企业id
     */
    private String companyId;

    /**
     * 员工工号
     */
    private String employeeNo;

    /**
     *
     * 生物特征类型 1-指纹 2-面部 8-掌静脉 9-可见光人脸
     */
    private String biometricType;

    /**
     * 是否支持远程登记可见光人脸，0 不支持，1支持，2未知
     */
    private Integer supportRemoteFacePhoto;

    /**
     * 是否支持远程登记近红外人脸，0 不支持，1支持，2未知
     */
    private Integer supportRemoteNearInfraredFace;

    /**
     * 是否支持远程登记指纹，0 不支持，1支持，2未知
     */
    private Integer supportRemoteFinger;

    /**
     * 是否支持远程登记掌纹，0 不支持，1支持，2未知
     */
    private Integer supportRemotePalmPrint;

    /**
     * 企业集成账号
     */
    private String account;

    /**
     * 集成账号密码
     */
    private String password;
}
