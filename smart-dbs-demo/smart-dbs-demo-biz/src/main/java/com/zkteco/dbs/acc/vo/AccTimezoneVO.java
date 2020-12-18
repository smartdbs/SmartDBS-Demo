package com.zkteco.dbs.acc.vo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName AccDoorVO
 * @Description: 门列表vo
 * @Author able.lee
 * @Date 2020/11/25 16:08
 * @Since v1.0.0
 */
@Data
public class AccTimezoneVO {
    /**
     * 时间段编号，企业内唯一
     */
    private String timezoneNum;

    /**
     * 时间段名称
     */
    private String timezoneName;

    /**
     * 门禁时间段详情
     */
    private List<AccTimezoneDetailVO> detail;

}
