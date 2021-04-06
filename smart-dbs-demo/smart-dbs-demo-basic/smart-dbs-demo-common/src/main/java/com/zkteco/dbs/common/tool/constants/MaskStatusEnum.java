/*
 * File Name: InOutTypeEnum
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.common.tool.constants;

import cn.hutool.core.util.ObjectUtil;

/**
 * MaskStatusEnum
 *
 * @author sheldon.wu
 * @date 2021/3/11 11:12
 * @since 1.0.0
 */
public enum MaskStatusEnum {

    /**
     * 佩戴口罩
     */
    TRUE(0, "maskStatus.ture"),
    /**
     * 未佩戴口罩
     */
    FALSE(1, "maskStatus.false");


    /**
     * 值
     */
    private int val;

    /**
     * 国际化key
     */
    private String i18nKey;

    MaskStatusEnum(int val, String i18nKey) {
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

    public static MaskStatusEnum getByVal(Integer val) {
        if (ObjectUtil.isEmpty(val)) {
            return null;
        }
        for (MaskStatusEnum maskStatusEnum : values()) {
            if (maskStatusEnum.getVal() == val) {
                return maskStatusEnum;
            }
        }

        return null;
    }

    public static String getI18nName(Integer val) {
        MaskStatusEnum maskStatusEnum = getByVal(val);
        return maskStatusEnum == null ? null : maskStatusEnum.getI18nKey();
    }
}
