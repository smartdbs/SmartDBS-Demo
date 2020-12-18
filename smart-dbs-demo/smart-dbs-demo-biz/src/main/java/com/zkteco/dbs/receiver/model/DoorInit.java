package com.zkteco.dbs.receiver.model;

import com.zkteco.dbs.common.base.model.BaseDTO;
import lombok.Data;

/**
 * DoorInit
 * 门初始化-Model
 * @author sheldon.wu
 * @date 2020/11/25 14:26
 * @since 1.0.0
 */
@Data
public class DoorInit extends BaseDTO {

    /**
     * 设备序列号
     */
    private String sn;
}
