package com.zkteco.dbs.acc.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName AccAuthorityDoor
 * @Description: 门禁权限组与门关系
 * @Author able.lee
 * @Date 2020/11/25 13:43
 * @Since v1.0.0
 */
@Data
@TableName("acc_authority_door")
public class AccAuthorityDoor extends Model<AccAuthorityDoor> {

    private static final long serialVersionUID=1L;

    /**
     * 权限组-门中间表id
     */
    @TableId(type = IdType.AUTO)
    @IsAutoIncrement
    @ColumnComment("权限组-门中间表id")
    private Integer id;

    /**
     * 权限组编号
     */
    @TableField
    @IsNotNull
    @ColumnComment("权限组id")
    private Integer groupNum;

    /**
     * 门id
     */
    @TableField
    @IsNotNull
    @ColumnComment("门id")
    private String doorId;

    /**
     * 设备id
     */
    @TableField(value = "dev_id")
    @IsNotNull
    @ColumnComment("设备id")
    private Integer devId;

    /**
     * 门编号
     */
    @TableField
    @IsNotNull
    @ColumnComment("门编号")
    private Integer doorNum;

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
