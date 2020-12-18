package com.zkteco.dbs.acc.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName AccAuthorityEmployee
 * @Description: 门禁权限组与人员关系
 * @Author able.lee
 * @Date 2020/11/25 13:44
 * @Since v1.0.0
 */
@Data
@TableName("acc_authority_employee")
public class AccAuthorityEmployee extends Model<AccAuthorityEmployee> {

    private static final long serialVersionUID=1L;

    /**
     * 权限组-人员中间表id
     */
    @TableId
    @ColumnComment("权限组-人员中间表id")
    private String id;

    /**
     * 权限组编号
     */
    @TableField
    @IsNotNull
    @ColumnComment("权限组id")
    private Integer groupNum;

    /**
     * 员工id
     */
    @TableField
    @IsNotNull
    @ColumnComment("员工id")
    private String employeeId;

    /**
     * 员工工号
     */
    @TableField
    @ColumnComment("员工工号")
    private String employeeNo;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
