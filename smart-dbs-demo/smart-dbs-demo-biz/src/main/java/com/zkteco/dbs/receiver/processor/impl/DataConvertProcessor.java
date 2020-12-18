package com.zkteco.dbs.receiver.processor.impl;


import com.alibaba.fastjson.JSONObject;
import com.zkcloud.api.dbs.common.Message;
import com.zkteco.dbs.receiver.model.ReceiverDTO;
import com.zkteco.dbs.receiver.processor.PreReceiverProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * DataConvertProcessor
 * 数据转换
 * @author sheldon.wu
 * @date 2020/11/25 13:59
 * @since 1.0.0
 */
@Component
@Order(3)
public class DataConvertProcessor implements PreReceiverProcessor {

    @Override
    public ReceiverDTO handle(ReceiverDTO receiver) {
        receiver.setRequest(JSONObject.parseObject(receiver.getRequestStr(), Message.class));
        return receiver;
    }
}
