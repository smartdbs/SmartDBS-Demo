package com.zkteco.dbs.receiver.model;

import com.zkcloud.api.dbs.common.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ReceiverDTO
 * 数据接收-DTO
 * @author sheldon.wu
 * @date 2020/11/30 14:08
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class ReceiverDTO {

    /**
     * unix时间戳，与nonce一起，防止重放攻击
     */
    private Long timestamp;

    /**
     * 随机数，与timestamp一起，防止重放攻击
     */
    private String nonce;

    /**
     * 数字签名，md5-32位-小写(timestamp+nonce+token)
     */
    private String sign;

    /**
     * 请求参数(String)
     */
    private String requestStr;

    /**
     * 请求参数(Message)
     */
    private Message request;

    /**
     * 服务id，代表消息事件类型
     */
    private String sid;

    public ReceiverDTO(Long timestamp, String nonce, String sign, String requestStr) {
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.sign = sign;
        this.requestStr = requestStr;
    }

    @Override
    public String toString() {
        return "ReceiverDTO{" +
                "timestamp=" + timestamp +
                ", nonce='" + nonce + '\'' +
                ", sign='" + sign + '\'' +
                ", requestStr='" + requestStr + '\'' +
                '}';
    }
}
