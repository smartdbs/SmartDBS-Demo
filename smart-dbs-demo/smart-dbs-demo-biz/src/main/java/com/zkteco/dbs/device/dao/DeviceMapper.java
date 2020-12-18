package com.zkteco.dbs.device.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkteco.dbs.device.dto.DeviceDTO;
import com.zkteco.dbs.device.model.Device;
import com.zkteco.dbs.device.vo.DeviceVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * DeviceMapper
 * 设备管理-业务层
 * @author sheldon.wu
 * @date 2020/11/20 18:34
 * @since 1.0.0
 */
public interface DeviceMapper extends BaseMapper<Device> {

    /**
     * pageListSql
     *
     * @param page
     * @param dto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.device.vo.DeviceVO>
     * @throws
     * @author sheldon.wu
     * @date 2020/11/20 14:55
     * @since 1.0.0
     */
    IPage<DeviceVO> pageListSql(Page page, @Param("params") DeviceDTO dto);

    /**
     * listByEmployee
     *
     * @param dto
     * @return java.util.List<com.zkteco.dbs.device.vo.DeviceVO>
     * @throws
     * @author sheldon.wu
     * @date 2020/12/2 15:28
     * @since 1.0.0
     */
    List<DeviceVO> listByEmployee(@Param("params") DeviceDTO dto);

}
