/*
 * File Name: IccVerifyRecordDTO
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.icc.dto;

import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import java.io.Serializable;
import lombok.Data;

/**
 * IccPersonVerifyRecordDTO
 *
 * @author sheldon.wu
 * @date 2021/2/25 15:28
 * @since 1.0.0
 */
@Data
public class IccPersonVerifyRecordDTO extends BaseQueryVO implements Serializable {

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 设备序列号
     */
    private String sn;


    /**
     * 身份证号码或者身份证物理卡号
     */
    private String idCard;

    /**
     * 核验结果. 0：核验失败；1：核验成功；2：黑名单
     */
    private String status;

    /**
     * 企业id
     */
    private String companyId;

    /**
     * 姓名
     */
    private String name;
}
