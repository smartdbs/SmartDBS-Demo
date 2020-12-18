package com.zkteco.dbs.receiver.processor.impl;


import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.zkcloud.api.dbs.common.Message;
import com.zkteco.dbs.acc.service.AccDoorService;
import com.zkteco.dbs.common.tool.constants.ErrorCode;
import com.zkteco.dbs.common.tool.constants.Sid;
import com.zkteco.dbs.receiver.model.DoorInit;
import com.zkteco.dbs.receiver.processor.DataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * DoorInitProcessor
 * 门初始化事件处理器
 * @author sheldon.wu
 * @date 2020/11/30 14:06
 * @since 1.0.0
 */
@Component
@Order(1)
@ConditionalOnExpression("${dbs.push.switch.doorInit:true}")
public class DoorInitProcessor implements DataProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccDoorService accDoorService;

    @Override
    public boolean match(Message request) {
        return Sid.DOOR_INIT.check(request.getSid());
    }

    @Override
    public Message handle(Message request) {
        DoorInit doorInit = Convert.convert(DoorInit.class, request.getPayload().getParams());
        logger.debug("DoorInitProcessor：" + JSONObject.toJSON(doorInit));

        // 写入本地库表
        String sn = doorInit.getSn();
        accDoorService.saveFromSdk(sn);

        return new Message(ErrorCode.CODE_SUCCESS.getCode(), ErrorCode.CODE_SUCCESS.getMessage());
    }
}
