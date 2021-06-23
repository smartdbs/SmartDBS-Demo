package com.zkteco.dbs.device.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.annotation.DefaultValue;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.zkteco.dbs.common.base.model.BaseModel;
import java.io.Serializable;
import lombok.Data;

/**
 * Device
 * 设备管理-Model
 * @author sheldon.wu
 * @date 2020/11/30 11:47
 * @since 1.0.0
 */
@Data
@TableName("device")
public class Device extends BaseModel<Device> {

    private static final long serialVersionUID = 1L;

    /**
     * 设备id
     */
    @TableId(type = IdType.AUTO)
    @IsAutoIncrement
    @ColumnComment("设备id")
    private Integer id;

    /**
     * 企业id
     */
    @TableField
    @ColumnComment("企业id")
    private String companyId;

    /**
     * 设备别名
     */
    @TableField
    @ColumnComment("设备别名")
    private String alias;

    /**
     * 设备序列号
     */
    @TableField
    @ColumnComment("设备序列号")
    private String sn;

    /**
     * 设备在线状态(0:离线 1:在线)
     */
    @TableField
    @ColumnComment("设备在线状态(0:离线 1:在线)")
    @ColumnType(length = 1)
    private Integer status;

    /**
     * 设备类型(0:考勤设备 1:门禁设备 2:人证设备)
     */
    @TableField
    @ColumnComment("设备类型(0:考勤设备 1:门禁设备 2:人证设备)")
    @ColumnType(length = 1)
    private Integer type;

    /**
     * 设备激活状态(0:未激活 1:已激活)
     */
    @TableField
    @ColumnComment("设备激活状态(0:未激活 1:已激活)")
    @ColumnType(length = 1)
    private Integer active;

    /**
     * 设备启用状态(0:禁用 1:启用)
     */
    @TableField
    @ColumnComment("设备启用状态(0:禁用 1:启用)")
    @ColumnType(length = 1)
    private Integer enable;

    /**
     * 固件版本
     */
    @TableField
    @ColumnComment("固件版本")
    private String fwVersion;


    /**
     * 设备型号
     */
    @TableField
    @ColumnComment("设备型号")
    private String model;

    /**
     * 公网ip
     */
    @TableField
    @ColumnComment("公网ip")
    private String ipAddress;

    /**
     * 内网ip
     */
    @TableField
    @ColumnComment("内网ip")
    private String localIp;

    /**
     * 设备协议版本(0 pull，1 push，2 best，3 ufo，4 best-w，5 best-t)
     */
    @TableField
    @ColumnComment("设备协议版本(0 pull，1 push，2 best，3 ufo，4 best-w，5 best-t)")
    @ColumnType(length = 1)
    private Integer protocol;

    /**
     * 物理地址
     */
    @TableField
    @ColumnComment("物理地址")
    private String mac;

    /**
     * 是否支持远程登记可见光人脸，0 不支持，1支持，2未知
     */
    @TableField
    @ColumnComment("是否支持远程登记可见光人脸，0 不支持，1支持，2未知")
    @ColumnType(length = 1)
    @DefaultValue("0")
    private Integer supportRemoteFacePhoto;

    /**
     * 是否支持远程登记可见光人脸，0 不支持，1支持，2未知
     */
    @TableField
    @ColumnComment("是否支持远程登记近红外人脸，0 不支持，1支持，2未知")
    @ColumnType(length = 1)
    @DefaultValue("0")
    private Integer supportRemoteNearInfraredFace;

    /**
     * 是否支持远程登记指纹，0 不支持，1支持，2未知
     */
    @TableField
    @ColumnComment("是否支持远程登记指纹，0 不支持，1支持，2未知")
    @ColumnType(length = 1)
    @DefaultValue("0")
    private Integer supportRemoteFinger;

    /**
     * 是否支持远程登记掌纹，0 不支持，1支持，2未知
     */
    @TableField
    @ColumnComment("是否支持远程登记掌纹，0 不支持，1支持，2未知")
    @ColumnType(length = 1)
    @DefaultValue("0")
    private Integer supportRemotePalmPrint;

    /**
     * 设备状态变更时间
     */
    @TableField
    @ColumnComment("DBS推送数据中设备状态变化时间戳")
    private Long changeTimeStamp;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
