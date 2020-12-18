package com.zkteco.dbs.receiver.processor.impl;


import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.receiver.model.ReceiverDTO;
import com.zkteco.dbs.receiver.processor.PreReceiverProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * DataDecryptProcessor
 * 数据解密，当推送配置开启了数据加密后使用，默认不加密
 * @author sheldon.wu
 * @date 2020/11/30 14:06
 * @since 1.0.0
 */
@Component
@Order(2)
@ConditionalOnExpression("${dbs.push.switch.encrypt:false}")
public class DataDecryptProcessor implements PreReceiverProcessor {

    @Autowired
    private DbsConfig dbsConfig;

    @Override
    public ReceiverDTO handle(ReceiverDTO receiver) {
        String json = new SymmetricCrypto(SymmetricAlgorithm.AES, dbsConfig.getPushSecret().getBytes())
                .decryptStr(receiver.getRequestStr());
        receiver.setRequestStr(json);
        return receiver;
    }
}
