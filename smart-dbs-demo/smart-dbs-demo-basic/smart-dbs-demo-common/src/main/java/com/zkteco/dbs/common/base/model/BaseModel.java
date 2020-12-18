package com.zkteco.dbs.common.base.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;

/**
 * @ClassName BaseModel
 * @Description: 基类
 * @Author able.lee
 * @Date 2020/11/25 15:28
 * @Since v1.0.0
 */
@Data
public class BaseModel<T> extends Model<BaseModel<T>> implements Serializable {


    /**
     * 创建时间
     */
    @TableField(value = "created_date", fill = FieldFill.INSERT)
    private Long createdDate;

    /**
     * 修改时间
     */
    @TableField(value = "modified_date", fill = FieldFill.INSERT_UPDATE)
    private Long modifiedDate;


}
