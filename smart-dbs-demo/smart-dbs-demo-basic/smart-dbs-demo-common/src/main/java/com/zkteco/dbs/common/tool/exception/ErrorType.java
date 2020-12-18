package com.zkteco.dbs.common.tool.exception;

/**
 * ErrorType
 *
 * @author able.lee
 * @date 2020/11/30 14:17
 * @since 1.0.0
 */
public interface ErrorType {
    /**
     * 返回code
     *
     * @return
     */
    String getCode();

    /**
     * 返回mesg
     *
     * @return
     */
    String getMesg();
}
