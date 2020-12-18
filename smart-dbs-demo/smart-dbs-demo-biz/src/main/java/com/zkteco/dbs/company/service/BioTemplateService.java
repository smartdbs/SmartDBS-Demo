package com.zkteco.dbs.company.service;

import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.company.dto.RegisterBiometricDTO;
import com.zkteco.dbs.company.model.BioTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

import com.zkteco.dbs.device.dto.DeviceDTO;
import java.util.List;
import java.util.Map;

/**
 * BioTemplateService
 * 服务类
 * @author able.lee
 * @date 2020/11/30 15:11
 * @since v1.0.0
 */
public interface BioTemplateService extends IService<BioTemplate> {

    /**
     * removeTemplate
     * 删除员工生物模板
     * @param type
     * @param companyId
     * @param employeeNo
     * @param lang
     * @return void
     * @throws 
     * @author able.lee
     * @date 2020/11/30 14:50
     * @since v1.0.0
     */
    void removeTemplate(String type,String companyId,String employeeNo,String lang);

    /**
     * listByCompanyIdAndEmployeeNo
     *
     * @param companyId
     * @param employeeNo
     * @return com.zkteco.dbs.company.model.BioTemplate
     * @throws
     * @author sheldon.wu
     * @date 2020/12/2 13:59
     * @since 1.0.0
     */
    BioTemplate listByCompanyIdAndEmployeeNo(String companyId,String employeeNo);

    /**
     * cancelRegisterBiometric
     *
     * @param dto
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/12/2 14:00
     * @since 1.0.0
     */
    void cancelRegisterBiometric(BaseDTO<RegisterBiometricDTO> dto);

    /**
     * registerBiometric
     *
     * @param dto
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @throws
     * @author sheldon.wu
     * @date 2020/12/2 14:34
     * @since 1.0.0
     */
    Map<String,String> registerBiometric(BaseDTO<RegisterBiometricDTO> dto);
}
