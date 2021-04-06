package com.zkteco.dbs.company.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.zkteco.dbs.common.base.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Employee
 * @Description: 员工
 * @Author able.lee
 * @Date 2020/11/30 11:42
 * @Since v1.0.0
 */
@Data
@TableName("employee")
public class Employee extends BaseModel<Employee> {

    private static final long serialVersionUID=1L;

    @TableId
    private String id;

    /**
     * 状态（假删除使用）
     */
    @TableField
    @ColumnComment("状态（假删除使用）")
    private Boolean active;

    /**
     * 员工工号
     */
    @TableField
    @ColumnComment("员工工号")
    private String employeeNo;

    /**
     * 名
     */
    @TableField
    @ColumnComment("名")
    private String firstName;

    /**
     * 格式化名称
     */
    @TableField
    @ColumnComment("格式化名称")
    private String formattedName;

    /**
     * 性别。3表示其他，1表示男性，2表示女性
     */
    @TableField
    @ColumnComment("性别。3表示其他，1表示男性，2表示女性")
    private String gender;

    /**
     * 姓
     */
    @TableField
    @ColumnComment("姓")
    private String lastName;

    /**
     * 手机
     */
    @TableField
    @ColumnComment("手机")
    private String phone;

    /**
     * 档案照（头像），存url
     */
    @TableField
    @ColumnComment("档案照（头像），存url")
    private String avatar;

    /**
     * 企业id
     */
    @TableField
    @ColumnComment("企业id")
    private String companyId;

    /**
     * 个人身份证号或者身份证物理卡号
     */
    @TableField
    @ColumnComment("个人身份证号或者身份证物理卡号")
    private String idCard;

    /**
     * 证件类型. (1 - 身份证(默认)，2 - 外国人永居证，3 - 港澳台居民居住证)
     */
    @TableField
    @ColumnComment("证件类型. (1 - 身份证(默认)，2 - 外国人永居证，3 - 港澳台居民居住证)")
    private String cardType;

    /**
     * 人员临时状态.(0 - 否，1 - 是)，默认为空
     */
    @TableField
    @ColumnComment("人员临时状态.(0 - 否，1 - 是)，默认为空")
    private String temporaryStatus;

    /**
     * 人员允许状态.(1 - 允许名单，0 - 禁止名单)，默认为空
     */
    @TableField
    @ColumnComment("人员允许状态.(1 - 允许名单，0 - 禁止名单)，默认为空")
    private String allowStatus;

    /**
     * 人员白名单有效期开始时间, ISO标准时间格式:yyyy-MM-ddTHH:mm:ss±HH:mm, (yyyy-MM-ddTHH:mm:ss):服务端的本地时间, (±HH:mm):服务端的时区
     */
    @TableField
    @ColumnComment("人员白名单有效期开始时间, ISO标准时间格式:yyyy-MM-ddTHH:mm:ss±HH:mm, (yyyy-MM-ddTHH:mm:ss):服务端的本地时间, (±HH:mm):服务端的时区")
    private String startTime;

    /**
     * 人员白名单有效期结束时间, ISO标准时间格式:yyyy-MM-ddTHH:mm:ss±HH:mm, (yyyy-MM-ddTHH:mm:ss):服务端的本地时间, (±HH:mm):服务端的时区
     */
    @TableField
    @ColumnComment("人员白名单有效期结束时间, ISO标准时间格式:yyyy-MM-ddTHH:mm:ss±HH:mm, (yyyy-MM-ddTHH:mm:ss):服务端的本地时间, (±HH:mm):服务端的时区")
    private String endTime;

    /**
     * 白名单人员在有效时间区间内，允许核验成功总次数，每核验成功一次，总次数-1
     */
    @TableField
    @ColumnComment("白名单人员在有效时间区间内，允许核验成功总次数，每核验成功一次，总次数-1")
    private String validCount;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
