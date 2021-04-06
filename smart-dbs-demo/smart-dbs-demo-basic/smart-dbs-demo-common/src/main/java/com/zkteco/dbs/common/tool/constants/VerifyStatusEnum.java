/*
 * File Name: InOutTypeEnum
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.common.tool.constants;

import cn.hutool.core.util.ObjectUtil;

/**
 * VerifyStatusEnum
 *
 * @author sheldon.wu
 * @date 2021/3/11 11:12
 * @since 1.0.0
 */
public enum VerifyStatusEnum {
    /**
     * 核验失败
     */
    fail(0, "verifyStatus.fail"),
    /**
     * 核验成功
     */
    success(1, "verifyStatus.success"),
    /**
     * 禁止人员
     */
    ban(2, "verifyStatus.ban");

    /**
     * 值
     */
    private int val;

    /**
     * 国际化key
     */
    private String i18nKey;

    VerifyStatusEnum(int val, String i18nKey) {
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

    public static VerifyStatusEnum getByVal(Integer val) {
        if (ObjectUtil.isEmpty(val)) {
            return null;
        }
        for (VerifyStatusEnum verifyStatusEnum : values()) {
            if (verifyStatusEnum.getVal() == val) {
                return verifyStatusEnum;
            }
        }

        return null;
    }

    public static String getI18nName(Integer val) {
        VerifyStatusEnum verifyStatusEnum = getByVal(val);
        return verifyStatusEnum == null ? null : verifyStatusEnum.getI18nKey();
    }
}
