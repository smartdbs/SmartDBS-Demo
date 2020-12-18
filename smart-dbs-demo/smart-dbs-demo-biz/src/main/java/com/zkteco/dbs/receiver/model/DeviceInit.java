package com.zkteco.dbs.receiver.model;

import com.zkteco.dbs.common.base.model.BaseDTO;
import lombok.Data;

/**
 * DeviceInit
 * 设备初始化-Model
 * @author sheldon.wu
 * @date 2020/11/30 13:55
 * @since 1.0.0
 */
@Data
public class DeviceInit extends BaseDTO {

    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 设备时区
     */
    private String timezone;

    /**
     * 设备别名
     */
    private String alais;
    /**
     * 设备在线状态(0:离线 1:在线)
     */
    private Short status;
    /**
     * 设备类型
     */
    private String type;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 设备启用状态(0:禁用线 1:启用)
     */
    private Short enable;

    /**
     * 公网ip
     */
    private String remoteIp;

    /**
     * 设备初始化时间
     */
    private Long initTimeStamp;
}
