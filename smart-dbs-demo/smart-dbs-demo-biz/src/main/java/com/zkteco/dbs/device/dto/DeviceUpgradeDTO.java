/*
 * File Name: UpgradeDTO
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.device.dto;

import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import java.io.Serializable;
import lombok.Data;

/**
 * UpgradeDTO
 * 固件升级-DTO
 * @author sheldon.wu
 * @date 2021/1/4 15:59
 * @since 1.0.0
 */
@Data
public class DeviceUpgradeDTO extends BaseQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 目标版本号
     */
    private String version;

    /**
     * 开始时间
     */
    private Long beginTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 任务Id
     */
    private String taskId;
}
