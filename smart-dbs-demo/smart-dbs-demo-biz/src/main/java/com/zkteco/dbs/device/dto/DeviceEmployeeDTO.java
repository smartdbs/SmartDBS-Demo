/*
 * File Name: DeviceDTO
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.device.dto;

import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * DeviceDTO
 * 设备员工关系-DTO
 * @author sheldon.wu
 * @date 2020/11/20 15:01
 * @since 1.0.0
 */
@Data
public class DeviceEmployeeDTO extends BaseQueryVO implements Serializable {

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
     * 员工编号集合
     */
    private List<String> employeeNos;
}
