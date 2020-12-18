package com.zkteco.dbs.receiver.processor;

import com.zkteco.dbs.receiver.model.ReceiverDTO;

/**
 * PreReceiverProcessor
 * 预接收处理器
 * @author sheldon.wu
 * @date 2020/11/25 14:01
 * @since 1.0.0
 */
public interface PreReceiverProcessor {

    /**
     * handle
     *
     * @param receiver
     * @return com.zkteco.dbs.receiver.model.ReceiverDTO
     * @throws
     * @author sheldon.wu
     * @date 2020/11/25 13:42
     * @since 1.0.0
     */
    ReceiverDTO handle(ReceiverDTO receiver);
}
