package com.zkteco.dbs.receiver.model;

import com.zkteco.dbs.common.base.model.BaseDTO;
import lombok.Data;

/**
 * DeviceStatusChange
 * 设备状态变更-Model
 * @author sheldon.wu
 * @date 2020/11/30 13:49
 * @since 1.0.0
 */
@Data
public class DeviceStatusChange extends BaseDTO {

    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 设备在线状态(0:离线 1:在线)
     */
    private Short status;

    /**
     * 设备状态变更时间
     */
    private Long changeTimeStamp;
}
