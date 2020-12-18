package com.zkteco.dbs.device.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkteco.dbs.device.dto.DeviceEmployeeDTO;
import com.zkteco.dbs.device.model.DeviceEmployee;
import com.zkteco.dbs.device.vo.DeviceEmployeeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DeviceEmployeeMapper
 * 设备员工关系-业务层
 * @author sheldon.wu
 * @date 2020/11/20 18:18
 * @since 1.0.0
 */
public interface DeviceEmployeeMapper extends BaseMapper<DeviceEmployee> {

    /**
     * listSql
     *
     * @param dto
     * @return java.util.List<com.zkteco.dbs.device.vo.DeviceEmployeeVO>
     * @throws
     * @author sheldon.wu
     * @date 2020/11/20 18:16
     * @since 1.0.0
     */
    List<DeviceEmployeeVO> listSql(@Param("params") DeviceEmployeeDTO dto);
}
