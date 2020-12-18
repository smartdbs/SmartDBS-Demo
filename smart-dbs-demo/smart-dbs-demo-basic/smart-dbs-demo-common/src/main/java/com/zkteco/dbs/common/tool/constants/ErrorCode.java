package com.zkteco.dbs.common.tool.constants;

/**
 * ErrorCode
 *
 * @author able.lee
 * @date 2020/11/30 14:16
 * @since 1.0.0
 */
public enum ErrorCode {
    /**
     * 成功
     */
    CODE_SUCCESS("00000000", "success"),
    /**
     * 服务不存在
     */
    CODE_NOT_SUPPORT("E0000001", "not exist service"),
    /**
     * 无效请求
     */
    CODE_INVALID("E0000002", "invalid request"),
    /**
     * 签名错误
     */
    CODE_ERROR_SIGN("E0000003", "error sign"),
    /**
     * 参数类型错误
     */
    CODE_ERROR_PARAM_TYPE("E0000004", "param type error"),
    /**
     * 参数转换异常
     */
    CODE_ERROR_PARAM_NOT_READ("E0000005", "param convert exception"),
    /**
     * 其他异常
     */
    CODE_ERROR_GLOBAL_EXCEPTION("E0000006", "other exception");

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误内容
     */
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
