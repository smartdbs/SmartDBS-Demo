package com.zkteco.dbs.acc.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkteco.dbs.acc.model.AccDoor;
import com.zkteco.dbs.acc.vo.AccDoorVO;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.device.dto.DeviceDTO;

/**
 * @ClassName AccDoorService
 * @Description: 服务类
 * @Author able.lee
 * @Date 2020/11/25 14:06
 * @Since v1.0.0
 */
public interface AccDoorService extends IService<AccDoor> {

    /**
     * saveFromSdk
     *  从门初始化信息
     * @param sn
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:39
     * @since v1.0.0
     */
    void saveFromSdk(String sn);

    /**
     * pageList
     * 门 分页列表
     * @param dto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.acc.vo.AccDoorVO>
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:39
     * @since v1.0.0
     */
    IPage<AccDoorVO> pageList(BaseDTO<DeviceDTO> dto);

    /**
     * update
     * 更新门基本信息
     * @param dto
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:41
     * @since v1.0.0
     */
    void update(BaseDTO<DeviceDTO> dto);

    /**
     * openDoor
     * 远程开门
     * @param dto
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/12/1 17:29
     * @since 1.0.0
     */
    void openDoor(BaseDTO<DeviceDTO> dto);

    /**
     * removeBySn
     *
     * @param companyId
     * @param sn
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/12/4 16:40
     * @since 1.0.0
     */
    void removeBySn(String companyId, String sn);

}
