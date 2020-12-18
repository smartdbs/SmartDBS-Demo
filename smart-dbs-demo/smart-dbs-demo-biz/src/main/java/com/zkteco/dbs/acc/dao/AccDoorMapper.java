package com.zkteco.dbs.acc.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkteco.dbs.acc.model.AccDoor;
import com.zkteco.dbs.acc.vo.AccDoorVO;
import com.zkteco.dbs.device.dto.DeviceDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AccDoorMapper
 * Mapper 接口
 * @author able.lee
 * @date 2020/11/30 15:44
 * @since v1.0.0
 */
public interface AccDoorMapper extends BaseMapper<AccDoor> {

    /**
     * pageList
     * 门-分页列表
     * @param page
     * @param dto
     * @param companyId
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.acc.vo.AccDoorVO>
     * @throws
     * @author able.lee
     * @date 2020/11/30 15:43
     * @since v1.0.0
     */
    IPage<AccDoorVO> pageList(Page page, @Param("params") DeviceDTO dto,@Param("companyId") String companyId);

}
