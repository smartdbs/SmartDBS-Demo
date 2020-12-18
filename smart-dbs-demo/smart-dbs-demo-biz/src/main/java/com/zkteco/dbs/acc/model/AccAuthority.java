package com.zkteco.dbs.acc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import java.io.Serializable;
import lombok.Data;

/**
 * @ClassName AccAuthority
 * @Description: 门禁权限
 * @Author able.lee
 * @Date 2020/11/25 13:43
 * @Since v1.0.0
 */
@Data
@TableName("acc_authority")
public class AccAuthority extends Model<AccAuthority> {

    private static final long serialVersionUID = 1L;

    /**
     * 门禁权限组编号
     */
    @TableId(type = IdType.AUTO)
    @IsAutoIncrement
    @ColumnComment("门禁权限组编号,企业内唯一")
    private Integer groupNum;

    /**
     * 权限组名称
     */
    @TableField
    @IsNotNull
    @ColumnComment("权限组名称")
    private String name;

    /**
     * 门禁时间段编号,企业内唯一
     */
    @TableField
    @IsNotNull
    @ColumnComment("门禁时间段编号,企业内唯一")
    private Integer timezoneNum;


    /**
     * 企业id
     */
    @TableField
    @IsNotNull
    @ColumnComment("企业id")
    private String companyId;

    /**
     * 权限组有效时间，默认及时生效，即当前时间，时间格式为ISO8601
     */
    @TableField
    @ColumnComment("权限组有效时间，默认及时生效，即当前时间，时间格式为ISO8601")
    private String startTime;

    /**
     * 权限组有效时间，默认永久，时间格式为ISO8601
     */
    @TableField
    @ColumnComment("权限组有效时间，默认永久，时间格式为ISO8601")
    private String endTime;


    @Override
    protected Serializable pkVal() {
        return this.groupNum;
    }

}
