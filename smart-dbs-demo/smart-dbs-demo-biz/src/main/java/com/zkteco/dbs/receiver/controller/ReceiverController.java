/*
 * File Name: ReceiverController
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.receiver.controller;

import com.zkcloud.api.dbs.common.Message;
import com.zkteco.dbs.common.tool.constants.ErrorCode;
import com.zkteco.dbs.receiver.model.ReceiverDTO;
import com.zkteco.dbs.receiver.processor.DataProcessor;
import com.zkteco.dbs.receiver.processor.PreReceiverProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ReceiverController
 * 推送接收-控制器
 * @author sheldon.wu
 * @date 2020/11/25 13:54
 * @since 1.0.0
 */
@RestController
public class ReceiverController {

    @Autowired(required = false)
    private DataProcessor[] dataProcessors;

    @Autowired(required = false)
    private PreReceiverProcessor[] preReceiverProcessors;

    @RequestMapping(value = "/receiver", method = RequestMethod.POST)
    @ResponseBody
    public Message receiver(@RequestParam Long timestamp, @RequestParam String nonce, @RequestParam String sign,
            @RequestBody String request) {

        ReceiverDTO receiverDTO = new ReceiverDTO(timestamp, nonce, sign, request);
        if (preReceiverProcessors != null) {
            for (PreReceiverProcessor preReceiverProcessor : preReceiverProcessors) {
                preReceiverProcessor.handle(receiverDTO);
            }
        }

        if (dataProcessors != null) {
            for (DataProcessor processor : dataProcessors) {
                if (processor.match(receiverDTO.getRequest())) {
                    return processor.handle(receiverDTO.getRequest());
                }
            }
        }

        return new Message(ErrorCode.CODE_NOT_SUPPORT.getCode(), ErrorCode.CODE_NOT_SUPPORT.getMessage());
    }
}
