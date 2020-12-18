/*
 * File Name: InOutTypeEnum
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.common.tool.constants;

import cn.hutool.core.util.ObjectUtil;

/**
 * InOutTypeEnum
 *
 * @author sheldon.wu
 * @date 2020/11/27 13:32
 * @since 1.0.0
 */
public enum InOutTypeEnum {
    /**
     * 出入状态-入
     */
    IN(0, "inOutType.in"),
    /**
     * 出入状态-出
     */
    OUT(1, "inOutType.out");

    /**
     * 值
     */
    private int val;

    /**
     * 国际化key
     */
    private String i18nKey;

    InOutTypeEnum(int val, String i18nKey) {
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

    public static InOutTypeEnum getByVal(Integer val) {
        if (ObjectUtil.isEmpty(val)) {
            return null;
        }
        for (InOutTypeEnum inOutTypeEnum : values()) {
            if (inOutTypeEnum.getVal() == val) {
                return inOutTypeEnum;
            }
        }

        return null;
    }

    public static String getI18nName(Integer val) {
        InOutTypeEnum inOutTypeEnum = getByVal(val);
        return inOutTypeEnum == null ? null : inOutTypeEnum.getI18nKey();
    }
}
