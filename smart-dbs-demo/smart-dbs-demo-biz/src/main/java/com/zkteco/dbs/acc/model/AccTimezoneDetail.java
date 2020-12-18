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
 * @ClassName AccTimezone
 * @Description: 门禁时间段
 * @Author able.lee
 * @Date 2020/11/25 13:44
 * @Since v1.0.0
 */
@Data
@TableName("acc_timezone_detail")
public class AccTimezoneDetail extends Model<AccTimezoneDetail> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    /**
     * 时间段类型，0～6表示周日～周六，7～9表示节假日类型，即7=节假日类型一，8=节假日类型二，9=节假日类型三
     */
    @TableField
    @ColumnComment("时间段类型，0～6表示周日～周六，7～9表示节假日类型，即7=节假日类型一，8=节假日类型二，9=节假日类型三")
    private Integer week;

    /**
     * 开始时间，格式HH:mm
     */
    @TableField(value = "start_time")
    @ColumnComment("开始时间，格式HH:mm")
    private String startTime;

    /**
     * 截止时间，格式HH:mm
     */
    @TableField(value = "end_time")
    @ColumnComment("截止时间，格式HH:mm")
    private String endTime;

    /**
     * 门禁时间段编号
     */
    @TableField(value = "timezone_num")
    @IsNotNull
    @ColumnComment("时间段编号")
    private Integer timezoneNum;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
