package com.zkteco.dbs.company.vo;

import lombok.Data;

/**
 * EmployeeEnrollmentVO
 * 员工注册信息VO
 * @author able.lee
 * @date 2020/11/30 15:25
 * @since v1.0.0
 */
@Data
public class EmployeeEnrollmentVO {

    private String id;

    /**
     * 员工工号
     */
    private String employeeNo;

    /**
     * 名
     */
    private String firstName;

    /**
     * 姓
     */
    private String lastName;

     /**
     * 格式化名称
     */
    private String formattedName;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 公司id
     */
    private String companyId;

    /**
     * 核验密码
     */
    private String devicePassword;

    /**
     * 设备用户权限 0:普通员工(默认), 14:管理员
     */
    private Integer devicePermission;

    /**
     * 指纹  1
     */
    private Integer fingerCount;

    /**
     * 面部（人脸） 2
     */
    private Integer faceCount;
    /**
     *  可见光人脸 9
     */
    private Integer visibleFaceCount;
    /**
     * 掌纹  6
     */
    private Integer palmPrintCount;

    /**
     * 头像
     */
    private String avatar;
}
