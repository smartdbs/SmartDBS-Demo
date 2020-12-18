package com.zkteco.dbs.common.tool.exception;

import lombok.Getter;

/**
 * SystemErrorType
 *
 * @author able.lee
 * @date 2020/11/30 14:17
 * @since 1.0.0
 */
@Getter
public enum SystemErrorType implements ErrorType {

    /**
     * 系统异常
     */
    SYSTEM_ERROR("-1", "系统异常"),
    /**
     * 系统繁忙,请稍候再试
     */
    SYSTEM_BUSY("000001", "系统繁忙,请稍候再试"),
    /**
     * 服务未找到
     */
    GATEWAY_NOT_FOUND_SERVICE("010404", "服务未找到"),
    /**
     * 网关异常
     */
    GATEWAY_ERROR("010500", "网关异常"),
    /**
     * 网关超时
     */
    GATEWAY_CONNECT_TIME_OUT("010002", "网关超时"),
    /**
     * 请求参数校验不通过
     */
    ARGUMENT_NOT_VALID("020000", "请求参数校验不通过"),
    /**
     * 上传文件大小超过限制
     */
    UPLOAD_FILE_SIZE_LIMIT("020001", "上传文件大小超过限制"),
    /**
     * 唯一键冲突
     */
    DUPLICATE_PRIMARY_KEY("030000","唯一键冲突");

    /**
     * 错误类型码
     */
    private String code;
    /**
     * 错误类型描述信息
     */
    private String mesg;

    SystemErrorType(String code, String mesg) {
        this.code = code;
        this.mesg = mesg;
    }
}
