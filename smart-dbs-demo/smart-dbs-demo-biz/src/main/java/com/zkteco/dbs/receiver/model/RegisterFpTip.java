package com.zkteco.dbs.receiver.model;

import com.zkteco.dbs.common.base.model.BaseModel;
import lombok.Data;

/**
 * RegisterFpTip
 *
 * @author sheldon.wu
 * @date 2020/12/2 11:55
 * @since 1.0.0
 */
@Data
public class RegisterFpTip extends BaseModel {

    /**
     * 设备序列号
     */
    private String sn;
    /**
     * 会话唯一标识，与登记生物特征匹配
     */
    private String sessionId;
    /**
     * 登记编号
     */
    private String num;
    /**
     * 返回值错误码
     */
    private String code;
    /**
     * 返回值对应的消息内容
     */
    private String message;
    /**
     * 结束标识，1:结束，0:否(默认)
     */
    private String end;
}
