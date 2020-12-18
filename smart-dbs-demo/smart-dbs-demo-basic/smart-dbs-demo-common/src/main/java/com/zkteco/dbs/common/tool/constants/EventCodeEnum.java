/*
 * File Name: InOutTypeEnum
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.common.tool.constants;

import cn.hutool.core.util.ObjectUtil;

/**
 * EventCodeEnum
 *
 * @author sheldon.wu
 * @date 2020/12/7 16:19
 * @since 1.0.0
 */
public enum EventCodeEnum {
    /**
     * 正常验证开门
     */
    EVENT_CODE0(0, "eventCode0"),
    /**
     * 常开时间段内验证
     */
    EVENT_CODE1(1, "eventCode1"),
    /**
     * 首人开门
     */
    EVENT_CODE2(2, "eventCode2"),
    /**
     * 多人开门
     */
    EVENT_CODE3(3, "eventCode3"),
    /**
     * 紧急状态密码开门
     */
    EVENT_CODE4(4, "eventCode4"),
    /**
     * 常开时间段开门
     */
    EVENT_CODE5(5, "eventCode5"),
    /**
     * 触发联动
     */
    EVENT_CODE6(6, "eventCode6"),
    /**
     * 取消报警
     */
    EVENT_CODE7(7, "eventCode7"),
    /**
     * 远程开门
     */
    EVENT_CODE8(8, "eventCode8"),
    /**
     * 远程关门
     */
    EVENT_CODE9(9, "eventCode9"),
    /**
     * 禁用当天常开时间段
     */
    EVENT_CODE10(10, "eventCode10"),
    /**
     * 启用当天常开时间段
     */
    EVENT_CODE11(11, "eventCode11"),
    /**
     * 辅助输出远程打开
     */
    EVENT_CODE12(12, "eventCode12"),
    /**
     * 辅助输出远程关闭
     */
    EVENT_CODE13(13, "eventCode13"),
    /**
     * 操作间隔太短
     */
    EVENT_CODE20(20, "eventCode20"),
    /**
     * 门非有效时间段验证开门
     */
    EVENT_CODE21(21, "eventCode21"),
    /**
     * 非法时间段
     */
    EVENT_CODE22(22, "eventCode22"),
    /**
     * 非法访问
     */
    EVENT_CODE23(23, "eventCode23"),
    /**
     * 反潜
     */
    EVENT_CODE24(24, "eventCode24"),
    /**
     * 互锁
     */
    EVENT_CODE25(25, "eventCode25"),
    /**
     * 多人验证等待
     */
    EVENT_CODE26(26, "eventCode26"),
    /**
     * 人未登记
     */
    EVENT_CODE27(27, "eventCode27"),
    /**
     * 门开超时
     */
    EVENT_CODE28(28, "eventCode28"),
    /**
     * 人已过有效期
     */
    EVENT_CODE29(29, "eventCode29"),
    /**
     * 门非有效时间段(按出门按钮)
     */
    EVENT_CODE36(36, "eventCode36"),
    /**
     * 常开时间段无法关门
     */
    EVENT_CODE37(37, "eventCode37"),
    /**
     * 卡已挂失
     */
    EVENT_CODE38(38, "eventCode38"),
    /**
     * 禁止名单
     */
    EVENT_CODE39(39, "eventCode39"),
    /**
     * 验证方式错误
     */
    EVENT_CODE41(41, "eventCode41"),
    /**
     * 韦根格式错误
     */
    EVENT_CODE42(42, "eventCode42"),
    /**
     * 后台验证失败
     */
    EVENT_CODE44(44, "eventCode44"),
    /**
     * 后台验证超时
     */
    EVENT_CODE45(45, "eventCode45"),
    /**
     * 多人验证失败
     */
    EVENT_CODE48(48, "eventCode48"),
    /**
     * 门已锁定
     */
    EVENT_CODE63(63, "eventCode63"),
    /**
     * 出门按钮不在时间段内时操作
     */
    EVENT_CODE64(64, "eventCode64"),
    /**
     * 辅助输入不在时间段内时操作
     */
    EVENT_CODE65(65, "eventCode65"),
    /**
     * 防拆报警
     */
    EVENT_CODE100(100, "eventCode100"),
    /**
     * 胁迫开门报警
     */
    EVENT_CODE101(101, "eventCode101"),
    /**
     * 门被意外打开
     */
    EVENT_CODE102(102, "eventCode102"),
    /**
     * 无法连接服务端
     */
    EVENT_CODE105(105, "eventCode105"),
    /**
     * 电池掉电
     */
    EVENT_CODE106(106, "eventCode106"),
    /**
     * 市电掉电
     */
    EVENT_CODE107(107, "eventCode107"),
    /**
     * 无法连接主控
     */
    EVENT_CODE108(108, "eventCode108"),
    /**
     * 门已打开
     */
    EVENT_CODE200(200, "eventCode200"),
    /**
     * 门已关闭
     */
    EVENT_CODE201(201, "eventCode201"),
    /**
     * 出门按钮开门
     */
    EVENT_CODE202(202, "eventCode202"),
    /**
     * 门常开时间段结束
     */
    EVENT_CODE204(204, "eventCode204"),
    /**
     * 远程开门常开
     */
    EVENT_CODE205(205, "eventCode205"),
    /**
     * 设备启动
     */
    EVENT_CODE206(206, "eventCode206"),
    /**
     * 密码开门
     */
    EVENT_CODE207(207, "eventCode207"),
    /**
     * 超级用户开门
     */
    EVENT_CODE208(208, "eventCode208"),
    /**
     * 触发出门按钮(被锁定)
     */
    EVENT_CODE209(209, "eventCode209"),
    /**
     * 超级用户关门
     */
    EVENT_CODE211(211, "eventCode211"),
    /**
     * 成功连接服务端
     */
    EVENT_CODE214(214, "eventCode214"),
    /**
     * 成功连接主控
     */
    EVENT_CODE217(217, "eventCode217"),
    /**
     * 身份证通行
     */
    EVENT_CODE218(218, "eventCode218"),
    /**
     * 辅助输入点断开
     */
    EVENT_CODE220(220, "eventCode220"),
    /**
     * 辅助输入点短路
     */
    EVENT_CODE221(221, "eventCode221"),
    /**
     * 后台验证成功
     */
    EVENT_CODE222(222, "eventCode222"),
    /**
     * 后台验证
     */
    EVENT_CODE223(223, "eventCode223"),
    /**
     * 辅助输出定时常开
     */
    EVENT_CODE229(229, "eventCode229"),
    /**
     * 辅助输出定时关闭常开
     */
    EVENT_CODE230(230, "eventCode230"),
    /**
     * 验证通过
     */
    EVENT_CODE232(232, "eventCode232"),
    /**
     * 远程锁定
     */
    EVENT_CODE233(233, "eventCode233"),
    /**
     * 远程解锁
     */
    EVENT_CODE234(234, "eventCode234"),
    /**
     * 门状态
     */
    EVENT_CODE255(255, "eventCode255");

    /**
     * 值
     */
    private int val;

    /**
     * 国际化key
     */
    private String i18nKey;

    EventCodeEnum(int val, String i18nKey) {
        this.val = val;
        this.i18nKey = i18nKey;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    public static EventCodeEnum getByVal(Integer val) {
        if (ObjectUtil.isEmpty(val)) {
            return null;
        }
        for (EventCodeEnum eventCodeEnum : values()) {
            if (eventCodeEnum.getVal() == val) {
                return eventCodeEnum;
            }
        }

        return null;
    }

    public static String getI18nName(Integer val) {
        EventCodeEnum eventCodeEnum = getByVal(val);
        return eventCodeEnum == null ? null : eventCodeEnum.getI18nKey();
    }
    }
