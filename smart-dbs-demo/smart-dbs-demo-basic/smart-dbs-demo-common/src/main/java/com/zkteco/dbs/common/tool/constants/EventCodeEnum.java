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
    EVENT_CODE0("N00001", "eventCode0"),
    /**
     * 常开时间段内验证
     */
    EVENT_CODE1("N00002", "eventCode1"),
    /**
     * 首人开门
     */
    EVENT_CODE2("N00003", "eventCode2"),
    /**
     * 多人开门
     */
    EVENT_CODE3("N00004", "eventCode3"),
    /**
     * 紧急状态密码开门
     */
    EVENT_CODE4("N00005", "eventCode4"),
    /**
     * 常开时间段开门
     */
    EVENT_CODE5("N00006", "eventCode5"),
    /**
     * 触发联动
     */
    EVENT_CODE6("N00007", "eventCode6"),
    /**
     * 取消报警
     */
    EVENT_CODE7("N00008", "eventCode7"),
    /**
     * 远程开门
     */
    EVENT_CODE8("N00009", "eventCode8"),
    /**
     * 远程关门
     */
    EVENT_CODE9("N00010", "eventCode9"),
    /**
     * 禁用当天常开时间段
     */
    EVENT_CODE10("N00011", "eventCode10"),
    /**
     * 启用当天常开时间段
     */
    EVENT_CODE11("N00012", "eventCode11"),
    /**
     * 辅助输出远程打开
     */
    EVENT_CODE12("N00013", "eventCode12"),
    /**
     * 辅助输出远程关闭
     */
    EVENT_CODE13("N00014", "eventCode13"),
    /**
     * 操作间隔太短
     */
    EVENT_CODE20("E00001", "eventCode20"),
    /**
     * 门非有效时间段验证开门
     */
    EVENT_CODE21("E00002", "eventCode21"),
    /**
     * 非法时间段
     */
    EVENT_CODE22("E00003", "eventCode22"),
    /**
     * 非法访问
     */
    EVENT_CODE23("E00004", "eventCode23"),
    /**
     * 反潜
     */
    EVENT_CODE24("E00005", "eventCode24"),
    /**
     * 互锁
     */
    EVENT_CODE25("E00006", "eventCode25"),
    /**
     * 多人验证等待
     */
    EVENT_CODE26("N00015", "eventCode26"),
    /**
     * 人未登记
     */
    EVENT_CODE27("E00007", "eventCode27"),
    /**
     * 门开超时
     */
    EVENT_CODE28("W00001", "eventCode28"),
    /**
     * 人已过有效期
     */
    EVENT_CODE29("E00008", "eventCode29"),
    /**
     * 门非有效时间段(按出门按钮)
     */
    EVENT_CODE36("E00009", "eventCode36"),
    /**
     * 常开时间段无法关门
     */
    EVENT_CODE37("E00010", "eventCode37"),
    /**
     * 卡已挂失
     */
    EVENT_CODE38("E00011", "eventCode38"),
    /**
     * 禁止名单
     */
    EVENT_CODE39("E00012", "eventCode39"),
    /**
     * 验证方式错误
     */
    EVENT_CODE41("E00013", "eventCode41"),
    /**
     * 韦根格式错误
     */
    EVENT_CODE42("E00014", "eventCode42"),
    /**
     * 后台验证失败
     */
    EVENT_CODE44("E00015", "eventCode44"),
    /**
     * 后台验证超时
     */
    EVENT_CODE45("E00016", "eventCode45"),
    /**
     * 多人验证失败
     */
    EVENT_CODE48("E00018", "eventCode48"),
    /**
     * 门已锁定
     */
    EVENT_CODE63("E00019", "eventCode63"),
    /**
     * 出门按钮不在时间段内时操作
     */
    EVENT_CODE64("E00020", "eventCode64"),
    /**
     * 辅助输入不在时间段内时操作
     */
    EVENT_CODE65("E00021", "eventCode65"),
    /**
     * 防拆报警
     */
    EVENT_CODE100("W00002", "eventCode100"),
    /**
     * 胁迫开门报警
     */
    EVENT_CODE101("W00003", "eventCode101"),
    /**
     * 门被意外打开
     */
    EVENT_CODE102("W00004", "eventCode102"),
    /**
     * 无法连接服务端
     */
    EVENT_CODE105("W00005", "eventCode105"),
    /**
     * 电池掉电
     */
    EVENT_CODE106("W00006", "eventCode106"),
    /**
     * 市电掉电
     */
    EVENT_CODE107("W00007", "eventCode107"),
    /**
     * 无法连接主控
     */
    EVENT_CODE108("W00008", "eventCode108"),
    /**
     * 门已打开
     */
    EVENT_CODE200("N00016", "eventCode200"),
    /**
     * 门已关闭
     */
    EVENT_CODE201("N00017", "eventCode201"),
    /**
     * 出门按钮开门
     */
    EVENT_CODE202("N00018", "eventCode202"),
    /**
     * 门常开时间段结束
     */
    EVENT_CODE204("N00019", "eventCode204"),
    /**
     * 远程开门常开
     */
    EVENT_CODE205("N00020", "eventCode205"),
    /**
     * 设备启动
     */
    EVENT_CODE206("N00021", "eventCode206"),
    /**
     * 密码开门
     */
    EVENT_CODE207("N00022", "eventCode207"),
    /**
     * 超级用户开门
     */
    EVENT_CODE208("N00023", "eventCode208"),
    /**
     * 触发出门按钮(被锁定)
     */
    EVENT_CODE209("N00024", "eventCode209"),
    /**
     * 超级用户关门
     */
    EVENT_CODE211("N00025", "eventCode211"),
    /**
     * 成功连接服务端
     */
    EVENT_CODE214("N00026", "eventCode214"),
    /**
     * 成功连接主控
     */
    EVENT_CODE217("N00027", "eventCode217"),
    /**
     * 身份证通行
     */
    EVENT_CODE218("N00028", "eventCode218"),
    /**
     * 辅助输入点断开
     */
    EVENT_CODE220("W00020", "eventCode220"),
    /**
     * 辅助输入点短路
     */
    EVENT_CODE221("W00021", "eventCode221"),
    /**
     * 后台验证成功
     */
    EVENT_CODE222("N00030", "eventCode222"),
    /**
     * 后台验证
     */
    EVENT_CODE223("N00031", "eventCode223"),
    /**
     * 辅助输出定时常开
     */
    EVENT_CODE229("N00033", "eventCode229"),
    /**
     * 辅助输出定时关闭常开
     */
    EVENT_CODE230("N00034", "eventCode230"),
    /**
     * 验证通过
     */
    EVENT_CODE232("N00035", "eventCode232"),
    /**
     * 远程锁定
     */
    EVENT_CODE233("N00036", "eventCode233"),
    /**
     * 远程解锁
     */
    EVENT_CODE234("N00037", "eventCode234"),
    /**
     * 门状态
     */
    EVENT_CODE255("N00042", "eventCode255");

    /**
     * 值
     */
    private String val;

    /**
     * 国际化key
     */
    private String i18nKey;

    EventCodeEnum(String val, String i18nKey) {
        this.val = val;
        this.i18nKey = i18nKey;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    public static EventCodeEnum getByVal(String val) {
        if (ObjectUtil.isEmpty(val)) {
            return null;
        }
        for (EventCodeEnum eventCodeEnum : values()) {
            if (eventCodeEnum.getVal().equals(val)) {
                return eventCodeEnum;
            }
        }

        return null;
    }

    public static String getI18nName(String val) {
        EventCodeEnum eventCodeEnum = getByVal(val);
        return eventCodeEnum == null ? null : eventCodeEnum.getI18nKey();
    }
    }
