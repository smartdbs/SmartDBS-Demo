package com.zkteco.dbs.company.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import java.io.Serializable;

import com.zkteco.dbs.common.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ClassName BioTemplate
 * @Description: 员工生物模板
 * @Author able.lee
 * @Date 2020/11/30 11:42
 * @Since v1.0.0
 */
@Data
@TableName("bio_template")
public class BioTemplate extends BaseModel<BioTemplate> {

    private static final long serialVersionUID=1L;

    @TableId
    private String id;

    /**
     * 设备序列号
     */
    @TableField
    @ColumnComment("设备序列号")
    private String sn;

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
     * 指纹数量
     */
    @TableField
    @ColumnComment("指纹数量")
    private Integer fingerCount;

    /**
     * 人脸数量
     */
    @TableField
    @ColumnComment("人脸数量")
    private Integer faceCount;

    /**
     * 掌纹数量
     */
    @TableField
    @ColumnComment("掌纹数量")
    private Integer palmPrintCount;
    /**
     * 可见光数量
     */
    @TableField
    @ColumnComment("可见光人脸数量")
    private Integer facePhotoCount;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
