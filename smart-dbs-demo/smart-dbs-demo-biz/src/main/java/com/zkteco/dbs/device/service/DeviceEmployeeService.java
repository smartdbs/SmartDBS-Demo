package com.zkteco.dbs.device.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.device.dto.DeviceEmployeeDTO;
import com.zkteco.dbs.device.model.DeviceEmployee;
import com.zkteco.dbs.device.vo.DeviceEmployeeVO;
import java.util.List;


/**
 * DeviceEmployeeService
 * 设备员工关系-业务层
 * @author sheldon.wu
 * @date 2020/11/20 18:10
 * @since 1.0.0
 */
public interface DeviceEmployeeService extends IService<DeviceEmployee> {

    /**
     * list
     * 设备员工列表
     * @param dto
     * @return java.util.List<com.zkteco.dbs.device.vo.DeviceEmployeeVO>
     * @throws
     * @author sheldon.wu
     * @date 2020/11/20 18:13
     * @since 1.0.0
     */
    List<DeviceEmployeeVO> list(BaseDTO<DeviceEmployeeDTO> dto);

    /**
     * save
     * 保存设备员工关系
     * @param dto
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/11/20 18:34
     * @since 1.0.0
     */
    void save(BaseDTO<DeviceEmployeeDTO> dto);

    /**
     * remove
     * 删除设备员工关系
     * @param dto
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/11/20 18:33
     * @since 1.0.0
     */
    void remove(BaseDTO<DeviceEmployeeDTO> dto);

    /**
     * removeByEmployeeNo
     * 通过员工工号删除设备员工关系
     * @param companyId
     * @param employeeNo
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/11/24 14:30
     * @since 1.0.0
     */
    void removeByEmployeeNo(String companyId, String employeeNo);

    /**
     * removeBySn
     * 通过设备序列号删除设备员工关系
     * @param companyId
     * @param sn
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/11/24 17:26
     * @since 1.0.0
     */
    void removeBySn(String companyId, String sn);

    /**
     * removeBySnAndEmployeeNo
     * 根据设备sn和员工工号解绑关系
     * @param sn
     * @param employeeNo
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/12/15 17:17
     * @since v1.0.0
     */
    void removeBySnAndEmployeeNo(String sn, String employeeNo);



}
