package com.zkteco.dbs.company.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkteco.dbs.company.model.EmployeeEnrollment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkteco.dbs.company.vo.EmployeeEnrollmentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * EmployeeEnrollmentMapper
 * Mapper接口
 * @author able.lee
 * @date 2020/11/30 15:45
 * @since v1.0.0
 */
public interface EmployeeEnrollmentMapper extends BaseMapper<EmployeeEnrollment> {

    /**
     * pageList
     * 员工信息分页列表
     * @param page
     * @param companyId
     * @param employeeNo
     * @param formattedName
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.company.vo.EmployeeEnrollmentVO>
     * @throws
     * @author able.lee
     * @date 2020/11/30 15:44
     * @since v1.0.0
     */
    IPage<EmployeeEnrollmentVO> pageList(Page page, @Param("companyId") String companyId,
                                         @Param("employeeNo") String employeeNo,
                                         @Param("formattedName") String formattedName);

    /**
     * unassignedList
     * 设备未分配员工列表
     * @param companyId
     * @param employeeNo
     * @param formattedName
     * @param sn
     * @return java.util.List<com.zkteco.dbs.company.vo.EmployeeEnrollmentVO>
     * @throws 
     * @author able.lee
     * @date 2020/11/30 15:45
     * @since v1.0.0
     */
    List<EmployeeEnrollmentVO> unassignedList(@Param("companyId") String companyId,
                                        @Param("employeeNo") String employeeNo,
                                              @Param("formattedName") String formattedName,@Param("sn") String sn);
}
