package com.zkteco.dbs.acc.dto;

import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import lombok.Data;

import java.io.Serializable;

/**
 * AccAuthorityDoorDTO
 * 门禁权限与门关系DTO
 * @author able.lee
 * @date 2020/11/30 15:21
 * @since v1.0.0
 */
@Data
public class AccAuthorityDoorDTO extends BaseQueryVO implements Serializable {

    /**
     * 公司id
     */
    private String companyId;

    /**
     * 权限组编号
     */
    private Integer groupNum;

    /**
     * 门名称
     */
    private String doorName;

    /**
     * 设备名称
     */
    private String alias;

    /**
     * 设备序列号
     */
    private String sn;




}
