package com.zkteco.dbs.acc.vo;

import lombok.Data;

/**
 * @ClassName AccDoorVO
 * @Description: 门列表vo
 * @Author able.lee
 * @Date 2020/11/25 16:08
 * @Since v1.0.0
 */
@Data
public class AccTimezoneDetailVO {
    /**
     * 时间段类型，0～6表示周日～周六，7～9表示节假日类型，即7=节假日类型一，8=节假日类型二，9=节假日类型三
     */
    private Integer week;

    /**
     * 开始时间，格式HH:mm
     */
    private String startTime;

    /**
     * 截止时间，格式HH:mm
     */
    private String endTime;

}
