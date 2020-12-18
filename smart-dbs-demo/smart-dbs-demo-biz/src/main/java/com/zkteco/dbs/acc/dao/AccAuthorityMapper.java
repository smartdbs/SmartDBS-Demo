package com.zkteco.dbs.acc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkteco.dbs.acc.dto.AccAuthorityDoorDTO;
import com.zkteco.dbs.acc.dto.AccAuthorityEmployeeDTO;
import com.zkteco.dbs.acc.model.AccAuthority;
import com.zkteco.dbs.acc.vo.AccAuthorityDoorVO;
import com.zkteco.dbs.acc.vo.AccAuthorityEmployeeVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author able.lee
 * @since 2020-11-25
 */
public interface AccAuthorityMapper extends BaseMapper<AccAuthority> {

    /**
     * 已分配权限组的门
     * @param groupNum
     * @param companyId
     * @return
     */
    List<AccAuthorityDoorVO> doorAllocatedList(@Param("groupNum") int groupNum, @Param("companyId") String companyId);

    /**
     * 未分配权限组的门
     * @param dto
     * @param companyId
     * @return
     */
    List<AccAuthorityDoorVO> doorUnassignedList(@Param("params") AccAuthorityDoorDTO dto, @Param("companyId") String companyId);

    /**
     * 已分配权限组的员工
     * @param dto
     * @param companyId
     * @return
     */
    List<AccAuthorityEmployeeVO> employeeAllocatedList(@Param("params") AccAuthorityEmployeeDTO dto, @Param("companyId") String companyId);

    /**
     * 未分配权限组的员工
     * @param dto
     * @param companyId
     * @return
     */
    List<AccAuthorityEmployeeVO> employeeUnassignedList(@Param("params") AccAuthorityEmployeeDTO dto, @Param("companyId") String companyId);

    /**
     * getCountBySnAndEmployeeIdAndGroupNum
     *
     * @param dto
     * @return long
     * @throws
     * @author sheldon.wu
     * @date 2020/12/15 20:23
     * @since 1.0.0
     */
    long getCountBySnAndEmployeeNoAndGroupNum(@Param("params") AccAuthorityEmployeeDTO dto);
}
