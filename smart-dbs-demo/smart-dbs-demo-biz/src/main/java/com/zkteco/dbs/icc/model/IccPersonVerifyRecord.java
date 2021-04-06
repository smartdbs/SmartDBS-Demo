package com.zkteco.dbs.icc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.zkteco.dbs.common.base.model.BaseModel;
import java.io.Serializable;
import lombok.Data;

/**
 * IccPersonVerifyRecord
 * 人证记录-Model
 * @author sheldon.wu
 * @date 2021/02/25 15:42
 * @since 1.0.0
 */
@Data
@TableName("icc_person_verify_record")
public class IccPersonVerifyRecord extends BaseModel<IccPersonVerifyRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 人证记录id
     */
    @TableId(type = IdType.AUTO)
    @IsAutoIncrement
    @ColumnComment("人证记录id")
    private Integer id;

    /**
     * 企业id
     */
    @TableField(value = "company_id")
    @ColumnComment("企业id")
    private String companyId;

    /**
     * 设备序列号
     */
    @TableField
    @ColumnComment("设备序列号")
    private String sn;

    /**
     * 	身份证号码或者身份证物理卡号
     */
    @TableField
    @ColumnComment("身份证号码或者身份证物理卡号")
    private String idCard;

    /**
     * 证件类型. (1 - 身份证(默认)，2 - 外国人永居证，3 - 港澳台居民居住证 )
     */
    @TableField
    @ColumnComment("证件类型. (1 - 身份证(默认)，2 - 外国人永居证，3 - 港澳台居民居住证")
    private String cardType;

    /**
     * 名
     */
    @TableField
    @ColumnComment("名")
    private String firstName;

    /**
     * 姓
     */
    @TableField
    @ColumnComment("姓")
    private String lastName;

    /**
     * 姓名
     */
    @TableField
    @ColumnComment("姓名")
    private String formattedName;

    /**
     * 民族
     */
    @TableField
    @ColumnComment("民族")
    private String nation;

    /**
     * 性别 1代表男性，2代表女性，3代表其他
     */
    @TableField
    @ColumnComment("性别 1代表男性，2代表女性，3代表其他")
    private String gender;

    /**
     * 出生日期
     */
    @TableField
    @ColumnComment("出生日期")
    private String birth;

    /**
     * 身份证住址
     */
    @TableField
    @ColumnComment("身份证住址")
    private String address;


    /**
     * 身份证签发机关
     */
    @TableField
    @ColumnComment("身份证签发机关")
    private String depart;

    /**
     * 身份证有效期限
     */
    @TableField
    @ColumnComment("身份证有效期限")
    private String validityTime;

    /**
     * 核验类型. (1 - 指纹核验, 2 – 1:1人脸比对, 3 – 1:N人脸比对)
     */
    @TableField
    @ColumnComment("核验类型. (1 - 指纹核验, 2 – 1:1人脸比对, 3 – 1:N人脸比对)")
    private String verifyMode;

    /**
     * 核验时间，ISO8601时间格式， 如2020-12-30T00:00:00+08:00
     */
    @TableField
    @ColumnComment("核验时间，ISO8601时间格式， 如2020-12-30T00:00:00+08:00")
    private String time;

    /**
     * 时间戳
     */
    @TableField(value = "time_stamp")
    @ColumnComment("时间戳")
    private long timeStamp;


    /**
     * 核验结果. 0：核验失败；1：核验成功；2：禁止
     */
    @TableField
    @ColumnComment("核验结果. 0：核验失败；1：核验成功；2：禁止")
    private String status;

    /**
     * 体温。此属性配合设备使用，当设备带体温检测功能才有返回值
     */
    @TableField
    @ColumnComment("体温。此属性配合设备使用，当设备带体温检测功能才有返回值")
    private String temperature;


    /**
     * 是否佩戴口罩，“0” 未佩戴口罩，“1” 已佩戴口罩。此属性配合设备使用，当设备带口罩检测功能才有返回值
     */
    @TableField
    @ColumnComment("是否佩戴口罩，“0” 未佩戴口罩，“1” 已佩戴口罩。此属性配合设备使用，当设备带口罩检测功能才有返回值")
    private String maskStatus;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
