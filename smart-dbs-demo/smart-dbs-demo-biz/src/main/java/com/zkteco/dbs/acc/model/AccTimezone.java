package com.zkteco.dbs.acc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName AccTimezone
 * @Description: 门禁时间段
 * @Author able.lee
 * @Date 2020/11/25 13:44
 * @Since v1.0.0
 */
@Data
@TableName("acc_timezone")
public class AccTimezone extends Model<AccTimezone> {

    private static final long serialVersionUID=1L;

    /**
     * 门禁时间段编号,企业内唯一
     */
    @TableId(type = IdType.AUTO)
    @IsAutoIncrement
    @ColumnComment("门禁时间段编号,企业内唯一")
    private Integer timezoneNum;

    /**
     * 门禁时间段名称
     */
    @TableField
    @ColumnComment("门禁时间段名称")
    private String timezoneName;

    /**
     * 企业id
     */
    @TableField
    @ColumnComment("企业id")
    private String companyId;


    @Override
    protected Serializable pkVal() {
        return this.timezoneNum;
    }

}
