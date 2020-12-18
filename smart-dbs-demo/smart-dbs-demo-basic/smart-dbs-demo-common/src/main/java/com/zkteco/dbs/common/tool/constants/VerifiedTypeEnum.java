/*
 * File Name: InOutTypeEnum
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.common.tool.constants;

import cn.hutool.core.util.ObjectUtil;

/**
 * VerifiedTypeEnum
 *
 * @author sheldon.wu
 * @date 2020/11/27 13:32
 * @since 1.0.0
 */
public enum VerifiedTypeEnum {
    /**
     * 指静脉或人脸或指纹或卡或密码 (自动识别)
     */
    VERIFIED_TYPE0(0, "verifiedType0"),
    /**
     * 仅指纹
     */
    VERIFIED_TYPE1(1, "verifiedType1"),
    /**
     * 工号验证
     */
    VERIFIED_TYPE2(2, "verifiedType2"),
    /**
     * 仅密码
     */
    VERIFIED_TYPE3(3, "verifiedType3"),
    /**
     * 仅卡
     */
    VERIFIED_TYPE4(4, "verifiedType4"),
    /**
     * 指纹或密码
     */
    VERIFIED_TYPE5(5, "verifiedType5"),
    /**
     * 指纹或卡
     */
    VERIFIED_TYPE6(6, "verifiedType6"),
    /**
     * 卡或密码
     */
    VERIFIED_TYPE7(7, "verifiedType7"),
    /**
     * 工号加指纹
     */
    VERIFIED_TYPE8(8, "verifiedType8"),
    /**
     * 指纹加密码
     */
    VERIFIED_TYPE9(9, "verifiedType9"),
    /**
     * 卡加指纹
     */
    VERIFIED_TYPE10(10, "verifiedType10"),
    /**
     * 卡加密码
     */
    VERIFIED_TYPE11(11, "verifiedType11"),
    /**
     * 指纹加密码加卡
     */
    VERIFIED_TYPE12(12, "verifiedType12"),
    /**
     * 工号加指纹加密码
     */
    VERIFIED_TYPE13(13, "verifiedType13"),
    /**
     * 工号加指纹或卡加指纹
     */
    VERIFIED_TYPE14(14, "verifiedType14"),
    /**
     * 人脸
     */
    VERIFIED_TYPE15(15, "verifiedType15"),
    /**
     * 人脸加指纹
     */
    VERIFIED_TYPE16(16, "verifiedType16"),
    /**
     * 人脸加密码
     */
    VERIFIED_TYPE17(17, "verifiedType17"),
    /**
     * 人脸加卡
     */
    VERIFIED_TYPE18(18, "verifiedType18"),
    /**
     * 人脸加指纹加卡
     */
    VERIFIED_TYPE19(19, "verifiedType19"),
    /**
     * 人脸加指纹加密码
     */
    VERIFIED_TYPE20(20, "verifiedType20"),
    /**
     * 指静脉
     */
    VERIFIED_TYPE21(21, "verifiedType21"),
    /**
     * 指静脉加密码
     */
    VERIFIED_TYPE22(22, "verifiedType22"),
    /**
     * 指静脉加卡
     */
    VERIFIED_TYPE23(23, "verifiedType23"),
    /**
     * 指静脉加密码加卡
     */
    VERIFIED_TYPE24(24, "verifiedType24"),
    /**
     * 掌纹
     */
    VERIFIED_TYPE25(25, "verifiedType25"),
    /**
     * 掌纹加卡
     */
    VERIFIED_TYPE26(26, "verifiedType26"),
    /**
     * 掌纹加面部
     */
    VERIFIED_TYPE27(27, "verifiedType27"),
    /**
     * 掌纹加指纹
     */
    VERIFIED_TYPE28(28, "verifiedType28"),
    /**
     * 掌纹加指纹加面部
     */
    VERIFIED_TYPE29(29, "verifiedType29"),
    /**
     * 其他
     */
    VERIFIED_TYPE200(200, "verifiedType200");

    /**
     * 值
     */
    private int val;

    /**
     * 国际化key
     */
    private String i18nKey;

    VerifiedTypeEnum(int val, String i18nKey) {
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

    public static VerifiedTypeEnum getByVal(Integer val) {
        if (ObjectUtil.isEmpty(val)) {
            return null;
        }
        for (VerifiedTypeEnum verifiedTypeEnum : values()) {
            if (verifiedTypeEnum.getVal() == val) {
                return verifiedTypeEnum;
            }
        }

        return null;
    }

    public static String getI18nName(Integer val) {
        VerifiedTypeEnum verifiedTypeEnum = getByVal(val);
        return verifiedTypeEnum == null ? null : verifiedTypeEnum.getI18nKey();
    }
}
