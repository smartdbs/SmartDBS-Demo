package com.zkteco.dbs.device.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.zkteco.dbs.common.base.model.BaseModel;
import java.io.Serializable;
import lombok.Data;

/**
 * DeviceEmployee
 * 设备员工关系-Model
 * @author sheldon.wu
 * @date 2020/11/30 15:10
 * @since 1.0.0
 */
@Data
@TableName("device_employee")
public class DeviceEmployee extends BaseModel<DeviceEmployee> {

    private static final long serialVersionUID = 1L;

    /**
     * 设备员工关系id
     */
    @TableId
    @ColumnComment("设备员工关系id")
    private String id;

    /**
     * 企业id
     */
    @TableField
    @ColumnComment("企业id")
    private String companyId;

    /**
     * 员工工号
     */
    @TableField
    @ColumnComment("员工工号")
    private String employeeNo;

    /**
     * 设备序列号
     */
    @TableField
    @ColumnComment("设备序列号")
    private String sn;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
