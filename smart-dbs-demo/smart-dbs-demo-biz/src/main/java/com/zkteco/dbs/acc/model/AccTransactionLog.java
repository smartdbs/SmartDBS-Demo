package com.zkteco.dbs.acc.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.zkteco.dbs.common.base.model.BaseModel;
import java.io.Serializable;
import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * @ClassName AccTransactionLog
 * @Description: 门禁事件记录
 * @Author able.lee
 * @Date 2020/11/25 17:34
 * @Since v1.0.0
 */
@Data
@TableName("acc_transaction_log")
public class AccTransactionLog extends BaseModel<AccTransactionLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 门禁上传记录表
     */
    @TableId
    @ColumnComment("门禁记录id")
    private String id;

    /**
     * 用户工号或者其他操作者
     */
    @TableField
    @ColumnComment("用户工号或者其他操作者")
    private String operator;

    /**
     * 验证方式
     */
    @TableField
    @ColumnComment("验证方式")
    private Integer verified;

    /**
     * 验证方式
     */
    @TableField(exist = false)
    private String verifiedI18n;

    /**
     * 事件码
     */
    @TableField(value = "event_code")
    @ColumnComment("事件码")
    private String eventCode;

    /**
     * 事件码
     */
    @TableField(exist = false)
    private String eventCodeI18n;

    /**
     * 门编号
     */
    @TableField
    @ColumnComment("门编号")
    private Integer doorNum;

    /**
     * 出入状态， 0:入， 1:出
     */
    @TableField
    @ColumnComment("出入状态， 0:入， 1:出")
    private Integer inOutType;

    /**
     * 时间, ISO标准时间格式:yyyy-MM-ddTHH:mm:ss±HH:mm, (yyyy-MM-ddTHH:mm:ss):设备的本地时间, (HH:mm):设备的时区
     */
    @TableField
    @ColumnComment("时间, ISO标准时间格式:yyyy-MM-ddTHH:mm:ss±HH:mm, (yyyy-MM-ddTHH:mm:ss):设备的本地时间, (HH:mm):设备的时区")
    private String time;

    /**
     * 时间戳
     */
    @TableField(value = "time_stamp")
    @ColumnComment("时间戳")
    private long timeStamp;

    /**
     * 企业id
     */
    @TableField(value = "company_id")
    @ColumnComment("企业id")
    private String companyId;

    /**
     * 门禁设备SN号
     */
    @TableField
    @ColumnComment("设备序列号")
    private String sn;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
