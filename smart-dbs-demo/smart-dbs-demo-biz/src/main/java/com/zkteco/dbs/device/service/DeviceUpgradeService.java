/*
 * File Name: UpgradeService
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.device.service;

import com.zkcloud.api.dbs.model.DeviceUpgradeHistoryResponse;
import com.zkcloud.api.dbs.model.NewFwVersion;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.device.dto.DeviceUpgradeDTO;
import java.util.List;
import java.util.Map;

/**
 * UpgradeService
 * 固件升级-业务层
 * @author sheldon.wu
 * @date 2021/1/4 15:57
 * @since 1.0.0
 */
public interface DeviceUpgradeService {

    /**
     * newestList
     * 查询升级版本列表
     * @param dto
     * @return java.util.List<com.zkcloud.api.dbs.model.NewFwVersion>
     * @throws
     * @author sheldon.wu
     * @date 2021/1/4 16:21
     * @since 1.0.0
     */
    List<NewFwVersion> newestList(BaseDTO<DeviceUpgradeDTO> dto);

    /**
     * upgrade
     * 升级固件
     * @param dto
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2021/1/4 16:27
     * @since 1.0.0
     */
    Map<String,String> upgrade(BaseDTO<DeviceUpgradeDTO> dto);

    /**
     * historyList
     *
     * @param dto
     * @return java.util.List<com.zkcloud.api.dbs.model.DeviceUpgradeHistoryResponse>
     * @throws
     * @author sheldon.wu
     * @date 2021/1/4 16:37
     * @since 1.0.0
     */
    List<DeviceUpgradeHistoryResponse> historyList(BaseDTO<DeviceUpgradeDTO> dto);
}
