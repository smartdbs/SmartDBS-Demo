package com.zkteco.dbs.system.service;

import java.util.Map;

/**
 * DeviceService
 *
 * @author sheldon.wu
 * @date 2020/11/20 14:24
 * @since 1.0.0
 */
public interface SystemInforService {

    /**
     * systemInfor
     * 系统信息
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws 
     * @author able.lee
     * @date 2020/11/30 15:18
     * @since v1.0.0
     */
    Map<String,Object> systemInfo();
}