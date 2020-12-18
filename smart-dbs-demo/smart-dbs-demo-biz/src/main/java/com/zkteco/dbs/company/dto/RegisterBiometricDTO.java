/*
 * File Name: RegisterBiometricDTO
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.company.dto;

import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import java.io.Serializable;
import lombok.Data;

/**
 * RegisterBiometricDTO
 *
 * @author sheldon.wu
 * @date 2020/12/2 13:50
 * @since 1.0.0
 */
@Data
public class RegisterBiometricDTO extends BaseQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 员工工号
     */
    private String employeeNo;

    /**
     * 生物特征类型 1-指纹 2-面部 8-掌静脉 9-可见光人脸
     */
    private String type;


    /**
     *  生物具体个体编号
     * 【指纹】编号是： 0-9。对应的手指是：左手：小拇指/无名指/中指/食指/拇指，右手：拇指/食指/中指/无名指/小拇指；
     * 【指静脉】和指纹相同；
     * 【面部】no=0；
     * 【掌静脉】no=0;
     * 如果no 没有明确定义，则默认值为 0。
     */
    private String no;

    /**
     * 是否覆盖设备该生物特征信息，0代表不覆盖（默认），1代表覆盖
     */
    private String isCover;

}
