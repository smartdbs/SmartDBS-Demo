package com.zkteco.dbs.company.dto;

import lombok.Data;

/**
 * EmployeeEnrollmentDTO
 * 员工注册信息DTO
 * @author able.lee
 * @date 2020/11/30 15:25
 * @since v1.0.0
 */
@Data
public class EmployeeEnrollmentDTO {

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

    /**
     * 个人身份证号或者身份证物理卡号
     */
    private String idCard;

    /**
     * 证件类型. (1 - 身份证(默认)，2 - 外国人永居证，3 - 港澳台居民居住证)
     */
    private String cardType;

    /**
     * 人员临时状态.(0 - 否，1 - 是)，默认为空
     */
    private String temporaryStatus;

    /**
     * 人员允许状态.(1 - 允许名单，0 - 禁止名单)，默认为空
     */
    private String allowStatus;

    /**
     * 人员白名单有效期开始时间, ISO标准时间格式:yyyy-MM-ddTHH:mm:ss±HH:mm, (yyyy-MM-ddTHH:mm:ss):服务端的本地时间, (±HH:mm):服务端的时区
     */
    private String startTime;

    /**
     * 人员白名单有效期结束时间, ISO标准时间格式:yyyy-MM-ddTHH:mm:ss±HH:mm, (yyyy-MM-ddTHH:mm:ss):服务端的本地时间, (±HH:mm):服务端的时区
     */
    private String endTime;

    /**
     * 白名单人员在有效时间区间内，允许核验成功总次数，每核验成功一次，总次数-1
     */
    private String validCount;

}
