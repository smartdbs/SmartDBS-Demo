package com.zkteco.dbs.acc.vo;

import lombok.Data;

/**
 * @ClassName AccDoorVO
 * @Description: 门列表vo
 * @Author able.lee
 * @Date 2020/11/25 16:08
 * @Since v1.0.0
 */
@Data
public class AccDoorVO {

    /**
     * 门 记录id
     */
    private String id;
    /**
     * 门名称
     */
    private String doorName;

    /**
     * 设备别名
     */
    private String alias;

    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 设备状态 0-离线  1-在线
     */
    private Integer status;
}
