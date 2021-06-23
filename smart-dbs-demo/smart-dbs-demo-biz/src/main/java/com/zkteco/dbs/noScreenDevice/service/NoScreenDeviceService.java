package com.zkteco.dbs.noScreenDevice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.device.dto.DeviceDTO;
import com.zkteco.dbs.device.model.Device;

/**
 * NoScreenDeviceService
 * 无屏设备接口
 * @author able.lee
 * @date 2021/4/6 15:37
 * @since v1.0.0
 */
public interface NoScreenDeviceService extends IService<Device> {


    /**
     * addNoScreenDevice
     * 添加无屏设备
     * @param dto
     * @return void
     * @throws
     * @author able.lee
     * @date 2021/4/7 15:35
     * @since v1.0.0
     */
    void addNoScreenDevice(BaseDTO<DeviceDTO> dto);

    /**
     * pingIP
     * 模拟CMD ping IP地址
     * @param ipAddress
     * @param pingTimes
     * @param timeOut
     * @return boolean true网络ping通，false 网络ping失败
     * @throws
     * @author able.lee
     * @date 2021/4/7 15:35
     * @since v1.0.0
     */
    boolean pingIP(String ipAddress, int pingTimes, int timeOut);

    /**
     * generateQRCode
     * 生成二维码
     * @return void
     * @throws
     * @author able.lee
     * @date 2021/4/23 15:22
     * @since v1.0.0
     */
    String generateQRCode();


}
