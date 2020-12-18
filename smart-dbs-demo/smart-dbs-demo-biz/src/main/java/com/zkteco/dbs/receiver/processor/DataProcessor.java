package com.zkteco.dbs.receiver.processor;

import com.zkcloud.api.dbs.common.Message;

/**
 * DataProcessor
 * 数据处理器
 * @author sheldon.wu
 * @date 2020/11/25 13:59
 * @since 1.0.0
 */
public interface DataProcessor {

    /**
     * match
     *
     * @param request
     * @return boolean
     * @throws
     * @author sheldon.wu
     * @date 2020/11/25 13:38
     * @since 1.0.0
     */
    boolean match(Message request);

    /**
     * handle
     *
     * @param request
     * @return com.zkcloud.api.dbs.common.Message
     * @throws
     * @author sheldon.wu
     * @date 2020/11/25 13:39
     * @since 1.0.0
     */
    Message handle(Message request);
}
