package com.zkteco.dbs.device.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.device.dto.DeviceDTO;
import com.zkteco.dbs.device.model.Device;
import com.zkteco.dbs.device.vo.DeviceVO;
import java.util.List;

/**
 * DeviceService
 * 设备管理-业务层
 * @author sheldon.wu
 * @date 2020/11/20 14:24
 * @since 1.0.0
 */
public interface DeviceService extends IService<Device> {

    /**
     * pageList
     * 设备管理-分页列表
     * @param dto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.device.vo.DeviceVO>
     * @throws
     * @author sheldon.wu
     * @date 2020/11/20 18:12
     * @since 1.0.0
     */
    IPage<DeviceVO> pageList(BaseDTO<DeviceDTO> dto);


    /**
     * add
     * 设备管理-新增设备
     * @param dto
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/11/20 14:24
     * @since 1.0.0
     */
    void save(BaseDTO<DeviceDTO> dto);

    /**
     * delete
     * 设备管理-删除设备
     * @param dto
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/11/20 14:24
     * @since 1.0.0
     */
    void remove(BaseDTO<DeviceDTO> dto);

    /**
     * update
     * 设备管理-修改设备
     * @param dto
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/11/20 14:25
     * @since 1.0.0
     */
    void update(BaseDTO<DeviceDTO> dto);

    /**
     * disable
     * 设备管理-禁用设备
     * @param dto
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/11/20 16:56
     * @since 1.0.0
     */
    void disable(BaseDTO<DeviceDTO> dto);

    /**
     * enable
     * 设备管理-启用设备
     * @param dto
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/11/20 16:57
     * @since 1.0.0
     */
    void enable(BaseDTO<DeviceDTO> dto);

    /**
     * reboot
     * 设备管理-重启设备
     * @param dto
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/11/20 16:58
     * @since 1.0.0
     */
    void reboot(BaseDTO<DeviceDTO> dto);

    /**
     * listByEmployee
     *
     * @param dto
     * @return java.util.List<com.zkteco.dbs.device.vo.DeviceVO>
     * @throws
     * @author sheldon.wu
     * @date 2020/12/2 15:23
     * @since 1.0.0
     */
    List<DeviceVO> listByEmployee(BaseDTO<DeviceDTO> dto);
}