package com.zkteco.dbs.acc.dto;

import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import lombok.Data;

import java.io.Serializable;

/**
 * AccTransactionLogDTO
 * 门禁事件记录DTO
 * @author able.lee
 * @date 2020/11/30 15:22
 * @since v1.0.0
 */
@Data
public class AccTransactionLogDTO extends BaseQueryVO implements Serializable {

    /**
     * 操作者
     */
    private String operator;

    /**
     * 门编号
     */
    private String doorNum;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;




}
