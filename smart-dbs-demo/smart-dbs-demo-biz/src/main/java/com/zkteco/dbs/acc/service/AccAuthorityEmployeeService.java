package com.zkteco.dbs.acc.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zkteco.dbs.acc.dto.AccAuthorityEmployeeDTO;
import com.zkteco.dbs.acc.dto.SaveAccAuthorityEmployeeDTO;
import com.zkteco.dbs.acc.model.AccAuthorityEmployee;
import com.zkteco.dbs.common.base.model.BaseDTO;

/**
 * AccAuthorityEmployeeService
 * 服务类
 * @author able.lee
 * @date 2020/11/30 15:12
 * @since v1.0.0
 */
public interface AccAuthorityEmployeeService extends IService<AccAuthorityEmployee> {

    /**
     * removeEmployeeAuthority
     * 门禁权限组-移除员工
     * @param dto
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:43
     * @since v1.0.0
     */
    void removeEmployeeAuthority(BaseDTO<AccAuthorityEmployeeDTO> dto);

    /**
     * saveEmployeeAuthority
     * 门禁权限组-分配门
     * @param dto
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:43
     * @since v1.0.0
     */
    void saveEmployeeAuthority(BaseDTO<SaveAccAuthorityEmployeeDTO> dto);

    /**
     * removeByEmployeeNo
     * 根据员工工号移除与权限组的关系
     * @param employeeNo
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/12/16 14:26
     * @since v1.0.0
     */
    void removeByEmployeeNo(String employeeNo);
}
