package com.zkteco.dbs.receiver.processor.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.zkcloud.api.dbs.common.Message;
import com.zkteco.dbs.common.tool.constants.ErrorCode;
import com.zkteco.dbs.common.tool.constants.Sid;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.common.tool.ws.WebSocketServer;
import com.zkteco.dbs.receiver.model.RegisterFpTip;
import com.zkteco.dbs.receiver.processor.DataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * RegisterFpTipProcessor
 *
 * @author sheldon.wu
 * @date 2020/12/2 13:32
 * @since 1.0.0
 */
@Component
@Order(1)
@ConditionalOnExpression("${dbs.push.switch.registerBioTip:true}")
public class RegisterBioTipProcessor implements DataProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean match(Message request) {
        return Sid.REGISTER_BIO_TIP.check(request.getSid());
    }

    @Override
    public Message handle(Message request) {
        RegisterFpTip registerFpTip = Convert.convert(RegisterFpTip.class, request.getPayload().getParams());
        if (registerFpTip.getEnd().equalsIgnoreCase(SysConstants.REGISTER_BIOMETRIC_END)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", registerFpTip.getCode());
            jsonObject.put("message", registerFpTip.getMessage());
            WebSocketServer.sendInfo(jsonObject.toJSONString(), registerFpTip.getSessionId());
        }
        logger.debug("RegisterBioTipProcessor：" + JSONObject.toJSON(registerFpTip));
        return new Message(ErrorCode.CODE_SUCCESS.getCode(), ErrorCode.CODE_SUCCESS.getMessage());
    }
}
