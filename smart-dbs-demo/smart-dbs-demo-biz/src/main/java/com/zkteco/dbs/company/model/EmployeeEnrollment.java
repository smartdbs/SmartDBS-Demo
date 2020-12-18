package com.zkteco.dbs.company.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.annotation.DefaultValue;
import java.io.Serializable;

import com.zkteco.dbs.common.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ClassName EmployeeEnrollment
 * @Description: 员工注册信息
 * @Author able.lee
 * @Date 2020/11/30 11:42
 * @Since v1.0.0
 */
@Data
@TableName("employee_enrollment")
public class EmployeeEnrollment extends BaseModel<EmployeeEnrollment> {

    private static final long serialVersionUID=1L;

    @TableId
    private String id;

    /**
     * 卡号
     */
    @TableField
    @ColumnComment("卡号")
    private String cardNo;

    /**
     * 企业id
     */
    @TableField
    @ColumnComment("企业id")
    private String companyId;
    /**
     * 核验密码
     */
    @TableField
    @ColumnComment("核验密码")
    private String devicePassword;

    /**
     * 加密过后的核验密码
     */
    @TableField
    @ColumnComment("加密过后的核验密码")
    private String devicePasswordEncryption;

    /**
     * 加密的盐值，devicePasswordEncryption不为空时必填
     */
    @TableField
    @ColumnComment("加密的盐值，devicePasswordEncryption不为空时必填")
    private String devicePasswordSalt;

    /**
     * 设备用户权限
     */
    @TableField
    @ColumnComment("设备用户权限")
    @DefaultValue("0")
    @ColumnType(length = 2)
    private Integer devicePermission;

    /**
     * 员工支持的核验方式，与设备核验方式编码一致
     */
    @TableField
    @ColumnComment("员工支持的核验方式，与设备核验方式编码一致")
    private Integer deviceVerifyMode;

    /**
     * 员工ID
     */
    @TableField
    @ColumnComment("员工ID")
    private String employeeId;

    /**
     * 所属时间段编号
     */
    @TableField
    @ColumnComment("所属时间段编号")
    private String timezoneId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
