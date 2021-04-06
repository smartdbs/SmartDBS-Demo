package com.zkteco.dbs.icc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.zkteco.dbs.common.base.model.BaseModel;
import java.io.Serializable;
import lombok.Data;

/**
 * IccVerifyRecord
 * 核验记录-Model
 * @author sheldon.wu
 * @date 2021/02/25 15:42
 * @since 1.0.0
 */
@Data
@TableName("icc_verify_record")
public class IccVerifyRecord extends BaseModel<IccVerifyRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 核验记录id
     */
    @TableId(type = IdType.AUTO)
    @IsAutoIncrement
    @ColumnComment("核验记录id")
    private Integer id;

    /**
     * 企业id
     */
    @TableField(value = "company_id")
    @ColumnComment("企业id")
    private String companyId;

    /**
     * 设备序列号
     */
    @TableField
    @ColumnComment("设备序列号")
    private String sn;

    /**
     * 记录id，每条记录生成一个记录id，与核验照片推送的 logId 关联一一对应
     */
    @TableField
    @ColumnComment("记录id，每条记录生成一个记录id，与核验照片推送的 logId 关联一一对应")
    private String logId;

    /**
     * 员工工号
     */
    @TableField
    @ColumnComment("员工工号")
    private String employeeNo;

    /**
     * 核验时间，ISO8601时间格式， 如2020-12-30T00:00:00+08:00
     */
    @TableField
    @ColumnComment("核验时间，ISO8601时间格式， 如2020-12-30T00:00:00+08:00")
    private String time;

    /**
     * 时间戳
     */
    @TableField(value = "time_stamp")
    @ColumnComment("时间戳")
    private long timeStamp;


    /**
     * 核验结果. 0：核验失败；1：核验成功；2：禁止
     */
    @TableField
    @ColumnComment("核验结果. 0：核验失败；1：核验成功；2：禁止")
    private String status;

    /**
     * 验证方式，默认=0，详见验证方式说明
     */
    @TableField
    @ColumnComment("验证方式，默认=0，详见验证方式说明")
    private String deviceVerifyMode;

    /**
     * 体温。此属性配合设备使用，当设备带体温检测功能才有返回值
     */
    @TableField
    @ColumnComment("体温。此属性配合设备使用，当设备带体温检测功能才有返回值")
    private String temperature;


    /**
     * 是否佩戴口罩，“0” 未佩戴口罩，“1” 已佩戴口罩。此属性配合设备使用，当设备带口罩检测功能才有返回值
     */
    @TableField
    @ColumnComment("是否佩戴口罩，“0” 未佩戴口罩，“1” 已佩戴口罩。此属性配合设备使用，当设备带口罩检测功能才有返回值")
    private String maskStatus;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
