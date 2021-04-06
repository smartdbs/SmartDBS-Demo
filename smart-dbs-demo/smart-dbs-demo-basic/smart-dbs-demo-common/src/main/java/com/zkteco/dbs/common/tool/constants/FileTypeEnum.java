/*
 * File Name: FileTypeEnum
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.common.tool.constants;

import org.apache.commons.lang.StringUtils;

/**
 * FileTypeEnum
 *
 * @author able.lee
 * @date 2020/11/26 17:20
 * @since 1.0.0
 */
public enum FileTypeEnum {

    /**
     * 文件类型-图片
     */
    IMAGE(0, "image", "/image/");

    /**
     * 文件类型值
     */
    private int val;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件类型路径
     */
    private String path;

    FileTypeEnum(int val, String type, String path) {
        this.val = val;
        this.type = type;
        this.path = path;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static FileTypeEnum getByType(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        for (FileTypeEnum fileTypeEnum : values()) {
            if (fileTypeEnum.getType().equals(type)) {
                return fileTypeEnum;
            }
        }

        return null;
    }

    public static String getPathByType(String type) {
        FileTypeEnum fileTypeEnum = getByType(type);
        return fileTypeEnum == null ? null : fileTypeEnum.getPath();
    }
}
