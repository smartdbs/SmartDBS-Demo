package com.zkteco.dbs.receiver.model;

import lombok.Data;

/**
 * DoorTransactionRecord
 * 门禁记录-Model
 * @author sheldon.wu
 * @date 2020/11/25 14:26
 * @since 1.0.0
 */
@Data
public class DoorTransactionRecord {

    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 门编号
     */
    private String doorNum;

    /**
     * 用户工号或者其他操作者
     */
    private String operator;

    /**
     * 时间, ISO标准时间格式:yyyy-MM-ddTHH:mm:ss±HH:mm, (yyyy-MM-ddTHH:mm:ss):设备的本地时间, (HH:mm):设备的时区
     */
    private String time;

    /**
     * 出入状态， 0:入， 1:出
     */
    private String inOutState;

    /**
     * 事件码
     */
    private String eventCode;

    /**
     * 验证方式
     */
    private String verified;
}
