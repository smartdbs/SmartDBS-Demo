package com.zkteco.dbs.att.model;

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
 * AttRecord
 * 考勤记录-Model
 * @author sheldon.wu
 * @date 2020/11/30 11:42
 * @since 1.0.0
 */
@Data
@TableName("att_record")
public class AttRecord extends BaseModel<AttRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 考勤记录id
     */
    @TableId(type = IdType.AUTO)
    @IsAutoIncrement
    @ColumnComment("考勤记录id")
    private Integer id;

    /**
     * 打卡时间
     */
    @TableField
    @ColumnComment("打卡时间")
    private Long checkInTime;

    /**
     * 企业id
     */
    @TableField
    @ColumnComment("企业id")
    private String companyId;

    /**
     * 打卡时间(iso8601标准格式)
     */
    @TableField
    @ColumnComment("打卡时间(iso8601标准格式)")
    private String isoCheckInTime;

    /**
     * 员工工号
     */
    @TableField
    @ColumnComment("员工工号")
    private String employeeNo;

    /**
     * 设备序列号
     */
    @TableField
    @ColumnComment("设备序列号")
    private String sn;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
