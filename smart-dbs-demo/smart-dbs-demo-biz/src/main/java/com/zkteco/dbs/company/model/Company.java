package com.zkteco.dbs.company.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import java.io.Serializable;

import com.zkteco.dbs.common.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ClassName Company
 * @Description: 企业
 * @Author able.lee
 * @Date 2020/11/30 11:41
 * @Since v1.0.0
 */
@Data
@TableName("company")
public class Company extends BaseModel<Company> {

    private static final long serialVersionUID=1L;

    @TableId
    private String id;

    /**
     * 状态，1代表正常，0代表软删除, 默认1
     */
    @TableField
    @ColumnComment("状态，1代表正常，0代表软删除, 默认1")
    private Boolean active;

    /**
     * 代理商编号
     */
    @TableField
    @ColumnComment("代理商编号")
    private String agentCode;

    /**
     * app_key
     */
    @TableField
    @ColumnComment("app_key")
    private String appKey;

    /**
     * 企业编号
     */
    @TableField
    @ColumnComment("企业编号")
    private String companyCode;

    /**
     * 企业名称
     */
    @TableField
    @ColumnComment("企业名称")
    private String companyName;

    /**
     * 邮箱
     */
    @TableField
    @ColumnComment("邮箱")
    private String email;

    /**
     * 姓
     */
    @TableField
    @ColumnComment("姓")
    private String firstName;

    /**
     * 名
     */
    @TableField
    @ColumnComment("名")
    private String lastName;

    /**
     * 格式化姓名
     */
    @TableField
    @ColumnComment("格式化姓名")
    private String formattedName;

    /**
     * 联系电话
     */
    @TableField
    @ColumnComment("联系电话")
    private String phone;

    /**
     * 网站
     */
    @TableField
    @ColumnComment("网站")
    private String website;

    /**
     * 集成账号
     */
    @TableField
    @ColumnComment("集成账号")
    private String userName;

    /**
     * 密码
     */
    @TableField
    @ColumnComment("集成账号密码")
    private String password;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
