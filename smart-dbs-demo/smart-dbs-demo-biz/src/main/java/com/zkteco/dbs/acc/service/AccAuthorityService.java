package com.zkteco.dbs.acc.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkteco.dbs.acc.dto.AccAuthorityDoorDTO;
import com.zkteco.dbs.acc.dto.AccAuthorityEmployeeDTO;
import com.zkteco.dbs.acc.model.AccAuthority;
import com.zkteco.dbs.acc.vo.AccAuthorityDoorVO;
import com.zkteco.dbs.acc.vo.AccAuthorityEmployeeVO;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.BaseQueryVO;

import java.util.List;

/**
 * AccAuthorityService
 * 服务类
 * @author able.lee
 * @date 2020/11/30 15:12
 * @since v1.0.0
 */
public interface AccAuthorityService extends IService<AccAuthority> {


    /**
     * pageList
     * 门禁权限组 分页列表
     * @param dto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.acc.model.AccAuthority>
     * @throws 
     * @author able.lee
     * @date 2020/11/30 14:43
     * @since v1.0.0
     */
    IPage<AccAuthority> pageList(BaseDTO<BaseQueryVO> dto);

    /**
     * save
     * 新增门禁权限组
     * @param dto
     * @return void
     * @throws 
     * @author able.lee
     * @date 2020/11/30 14:44
     * @since v1.0.0
     */
    void save(BaseDTO<AccAuthority> dto);

    /**
     * update
     * 更新门禁权限组信息
     * @param dto
     * @return void
     * @throws 
     * @author able.lee
     * @date 2020/11/30 14:44
     * @since v1.0.0
     */
    void update(BaseDTO<AccAuthority> dto);

    /**
     * remove
     * 删除门禁权限组
     * @param dto
     * @return void
     * @throws 
     * @author able.lee
     * @date 2020/11/30 14:44
     * @since v1.0.0
     */
    void remove(BaseDTO<AccAuthority> dto);

    /**
     * doorAllocatedList
     * 已门禁权限的门 列表
     * @param dto
     * @return java.util.List<com.zkteco.dbs.acc.vo.AccAuthorityDoorVO>
     * @throws 
     * @author able.lee
     * @date 2020/11/30 14:44
     * @since v1.0.0
     */
    List<AccAuthorityDoorVO> doorAllocatedList(BaseDTO<AccAuthority> dto);

    /**
     * doorUnassignedList
     * 未门禁权限的门 列表
     * @param dto
     * @return java.util.List<com.zkteco.dbs.acc.vo.AccAuthorityDoorVO>
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:45
     * @since v1.0.0
     */
    List<AccAuthorityDoorVO> doorUnassignedList(BaseDTO<AccAuthorityDoorDTO> dto);

    /**
     * employeeAllocatedList
     * 已门禁权限的员工表
     * @param dto
     * @return java.util.List<com.zkteco.dbs.acc.vo.AccAuthorityEmployeeVO>
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:45
     * @since v1.0.0
     */
    List<AccAuthorityEmployeeVO> employeeAllocatedList(BaseDTO<AccAuthorityEmployeeDTO> dto);

    /**
     * employeeUnassignedList
     * 未门禁权限的员工表
     * @param dto
     * @return java.util.List<com.zkteco.dbs.acc.vo.AccAuthorityEmployeeVO>
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:45
     * @since v1.0.0
     */
    List<AccAuthorityEmployeeVO> employeeUnassignedList(BaseDTO<AccAuthorityEmployeeDTO> dto);

    /**
     * getCountBySnAndEmployeeIdAndGroupNum
     *
     * @param dto
     * @return long
     * @throws
     * @author sheldon.wu
     * @date 2020/12/15 20:32
     * @since 1.0.0
     */
    long getCountBySnAndEmployeeNoAndGroupNum(AccAuthorityEmployeeDTO dto);
}
