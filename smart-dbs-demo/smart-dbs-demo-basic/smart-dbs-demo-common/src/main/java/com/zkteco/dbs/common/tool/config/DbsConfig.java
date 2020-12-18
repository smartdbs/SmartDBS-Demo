package com.zkteco.dbs.common.tool.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * DbsConfig
 *
 * @author sheldon.wu
 * @date 2020/11/25 18:18
 * @since 1.0.0
 */
@Component
@Getter
public class DbsConfig {

    /**
     * 接收DBS推送的token，作为签名认证使用
     */
    @Value("${dbs.push.token:false}")
    private String pushToken;

    /**
     * 接收DBS推送的数据密钥，当开启数据加密时使用
     */
    @Value("${dbs.push.secret:false}")
    private String pushSecret;

    /**
     * 数据推送加密开关
     */
    @Value("${dbs.push.switch.encrypt:false}")
    private boolean encrypt;

    /**
     * 数据推送请求有效性验证之 时间戳有效性范围，单位：秒
     */
    @Value("${dbs.push.valid.rangTime:300}")
    private int validRangTime;

    /**
     * appKey
     */
    @Value("${dbs.api.appKey}")
    private String appKey;

    /**
     * appSecret
     */
    @Value("${dbs.api.appSecret}")
    private String appSecret;

    /**
     * DBS API节点服务器地址
     */
    @Value("${dbs.api.endpoint}")
    private String endpoint;

    /**
     * DBS API节点服务器是否支持ssl，即https
     */
    @Value("${dbs.api.ssl:true}")
    private boolean ssl;

    @Value("${dbs.attRecord.cron:0 0/10 * * * ? }")
    private String attRecordCron;
}
