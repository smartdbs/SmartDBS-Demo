package com.zkteco.dbs.common.tool.constants;

/**
 * Sid
 *
 * @author sheldon.wu
 * @date 2020/11/25 14:28
 * @since 1.0.0
 */
public enum Sid {

    /**
     * 推送服务测试
     */
    PUSH_TEST("dse.push.test"),

    /**
     * 打卡记录
     */
    PUNCH_RECORD("dse.push.punchRecord"),

    /**
     * 打卡照片
     */
    PUNCH_PHOTO("dse.push.punchPhoto"),

    /**
     * 设备初始化(激活)
     */
    DEVICE_INIT("dse.push.deviceInit"),

    /**
     * 设备(在线)状态变化
     */
    DEVICE_STATUS_CHANGE("dse.push.deviceStatus"),

    /**
     * 远程登记指纹进度提示
     */
    REGISTER_FP_TIP("dse.push.registerFpTip"),

    /**
     * 远程生物特征登记
     */
    REGISTER_BIO_TIP("dse.push.registerTipBio"),

    /**
     * 门初始化
     */
    DOOR_INIT("dse.push.doorInit"),

    /**
     * 门禁事件
     */
    DOOR_TRANSACTION_RECORD("dse.push.acc.transaction"),

    /**
     * 门实时状态
     */
    DOOR_REAL_STATUS("dse.push.acc.realDoorStatus"),

    /**
     * 卡信息
     */
    PUNCH_CARD("dse.push.employee.card");

    /**
     * 服务id
     */
    private final String sid;

    Sid(String sid) {
        this.sid = sid;
    }

    @Override
    public String toString() {
        return this.sid;
    }

    public String getValue() {
        return this.sid;
    }

    public boolean check(String sid) {
        return getValue().equals(sid);
    }
}
