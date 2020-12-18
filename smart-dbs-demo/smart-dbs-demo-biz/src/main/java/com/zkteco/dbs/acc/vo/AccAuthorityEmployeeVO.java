package com.zkteco.dbs.acc.vo;

import lombok.Data;

/**
 * @ClassName AccDoorVO
 * @Description: 权限组与员工
 * @Author able.lee
 * @Date 2020/11/25 16:08
 * @Since v1.0.0
 */
@Data
public class AccAuthorityEmployeeVO {
    /**
     * 员工id
     */
    private String employeeId;

    /**
     * 员工姓名
     */
    private String formattedName;

    /**
     * 员工工号
     */
    private String employeeNo;

    /**
     * 权限组编号
     */
    private String groupNum;

    /**
     * 员工头像
     */
    private String avatar;


}
