package com.zkteco.dbs.receiver.model;

import lombok.Data;

/**
 * PunchRecord
 * 考勤记录-Model
 * @author sheldon.wu
 * @date 2020/11/30 14:07
 * @since 1.0.0
 */
@Data
public class PunchRecord {

    /**
     * 设备序列号
     */
    private String sn;
    /**
     * 员工工号
     */
    private String employeeNo;

    /**
     * 打卡时间
     */
    private Long punchTime;

    /**
     * 打卡时间(iso8601标准格式)
     */
    private String iso8601PunchTime;

    /**
     * 工作代码
     */
    private String workCode;

    /**
     * 打卡状态
     */
    private String status;
}
