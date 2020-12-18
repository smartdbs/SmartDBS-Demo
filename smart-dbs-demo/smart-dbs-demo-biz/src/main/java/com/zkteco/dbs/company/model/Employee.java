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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
