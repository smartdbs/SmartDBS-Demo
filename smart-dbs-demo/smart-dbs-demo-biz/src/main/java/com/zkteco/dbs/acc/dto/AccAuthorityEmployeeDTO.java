package com.zkteco.dbs.acc.dto;

import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import lombok.Data;

import java.io.Serializable;
/**
 * AccAuthorityEmployeeDTO
 * 门禁权限与员工DTO
 * @author able.lee
 * @date 2020/11/30 15:21
 * @since v1.0.0
 */
@Data
public class AccAuthorityEmployeeDTO extends BaseQueryVO implements Serializable {

    /**
     * 公司id
     */
    private String companyId;

    /**
     * 员工id
     */
    private String employeeId;
    /**
     * 权限组编号
     */
    private Integer groupNum;

    /**
     * 员工姓名
     */
    private String formattedName;

    /**
     * 员工工号
     */
    private String employeeNo;

    /**
     * 设备序列号
     */
    private String sn;





}
