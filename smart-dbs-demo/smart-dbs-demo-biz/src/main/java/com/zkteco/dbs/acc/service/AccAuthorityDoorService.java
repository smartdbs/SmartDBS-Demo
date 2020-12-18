package com.zkteco.dbs.acc.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zkteco.dbs.acc.dto.AccAuthorityDoorDTO;
import com.zkteco.dbs.acc.dto.SaveAccAuthorityDoorDTO;
import com.zkteco.dbs.acc.model.AccAuthorityDoor;
import com.zkteco.dbs.acc.vo.AccAuthorityDoorVO;
import com.zkteco.dbs.common.base.model.BaseDTO;

import java.util.List;

/**
 * @ClassName AccAuthorityDoorService
 * @Description: 服务类
 * @Author able.lee
 * @Date 2020/11/25 13:49
 * @Since v1.0.0
 */
public interface AccAuthorityDoorService extends IService<AccAuthorityDoor> {

    /**
     * removeDoorAuthority
     * 门禁权限组-移除门
     * @param dto
     * @return void
     * @throws 
     * @author able.lee
     * @date 2020/11/30 14:42
     * @since v1.0.0
     */
    void removeDoorAuthority(BaseDTO<AccAuthorityDoor> dto);

    /**
     * saveDoorAuthority
     * 门禁权限组-分配门
     * @param dto
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:42
     * @since v1.0.0
     */
    void saveDoorAuthority(BaseDTO<SaveAccAuthorityDoorDTO> dto);

}
