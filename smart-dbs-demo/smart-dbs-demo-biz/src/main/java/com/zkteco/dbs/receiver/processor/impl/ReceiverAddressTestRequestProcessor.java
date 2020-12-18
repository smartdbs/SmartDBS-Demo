package com.zkteco.dbs.receiver.processor.impl;

import com.zkcloud.api.dbs.common.Message;
import com.zkteco.dbs.common.tool.constants.Sid;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.receiver.processor.DataProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * ReceiverAddressTestRequestProcessor
 * DBS测试推送地址的请求处理器
 * @author sheldon.wu
 * @date 2020/11/30 14:08
 * @since 1.0.0
 */
@Component
@Order
public class ReceiverAddressTestRequestProcessor implements DataProcessor {

    @Override
    public boolean match(Message request) {
        return Sid.PUSH_TEST.check(request.getSid());
    }

    @Override
    public Message handle(Message request) {
        return new Message(SysConstants.DBS_MESSAGE_SUCCESS, "success");
    }
}
