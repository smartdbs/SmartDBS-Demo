package com.zkteco.dbs.acc.vo;

import lombok.Data;

/**
 * @ClassName AccDoorVO
 * @Description: 权限组与门
 * @Author able.lee
 * @Date 2020/11/25 16:08
 * @Since v1.0.0
 */
@Data
public class AccAuthorityDoorVO {
    /**
     * 门id
     */
    private String doorId;

    /**
     * 门名称
     */
    private String doorName;

    /**
     * 门编号
     */
    private String doorNum;

    /**
     * 设备编号
     */
    private String devId;
    /**
     * 设备名称
     */
    private String alias;

    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 设备状态
     */
    private String status;



}
