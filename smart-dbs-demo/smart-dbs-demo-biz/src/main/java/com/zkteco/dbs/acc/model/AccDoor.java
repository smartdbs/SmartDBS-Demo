package com.zkteco.dbs.acc.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import java.io.Serializable;
import lombok.Data;

/**
 * @ClassName AccDoor
 * @Description: 门
 * @Author able.lee
 * @Date 2020/11/25 13:44
 * @Since v1.0.0
 */
@Data
@TableName("acc_door")
public class AccDoor extends Model<AccDoor> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    /**
     * 门编号
     */
    @TableField
    @IsNotNull
    @ColumnComment("门编号")
    private Integer doorNum;

    /**
     * 门名称
     */
    @TableField
    @IsNotNull
    @ColumnComment("门名称")
    private String doorName;

    /**
     * 门禁设备序列号
     */
    @TableField
    @IsNotNull
    @ColumnComment("设备序列号")
    private String sn;


    /**
     * 企业id
     */
    @TableField
    @IsNotNull
    @ColumnComment("企业id")
    private String companyId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
