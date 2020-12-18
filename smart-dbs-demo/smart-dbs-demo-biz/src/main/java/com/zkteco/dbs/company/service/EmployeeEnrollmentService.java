package com.zkteco.dbs.company.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import com.zkteco.dbs.company.dto.EmployeeEnrollmentDTO;
import com.zkteco.dbs.company.model.Employee;
import com.zkteco.dbs.company.model.EmployeeEnrollment;
import com.zkteco.dbs.company.vo.EmployeeEnrollmentVO;
import java.util.List;

/**
 * @ClassName EmployeeEnrollmentService
 * @Description: 服务类
 * @Author able.lee
 * @Date 2020/11/20 10:08
 * @Since v1.0.0
 */
public interface EmployeeEnrollmentService extends IService<EmployeeEnrollment> {

    /**
     * pageList
     * 员工分页列表
     * @param vo
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.company.vo.EmployeeEnrollmentVO>
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:51
     * @since v1.0.0
     */
    IPage<EmployeeEnrollmentVO> pageList(BaseDTO<BaseQueryVO> vo);

    /**
     * unassignedList
     * 未分配到设备的员工列表
     * @param vo
     * @return java.util.List<com.zkteco.dbs.company.vo.EmployeeEnrollmentVO>
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:51
     * @since v1.0.0
     */
    List<EmployeeEnrollmentVO> unassignedList(BaseDTO<BaseQueryVO> vo);

    /**
     * save
     * 保存员工信息
     * @param vo
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/11/30 15:46
     * @since v1.0.0
     */
    void save(BaseDTO<EmployeeEnrollmentDTO> vo);

    /**
     * update
     * 修改员工信息
     * @param vo
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/11/30 15:51
     * @since v1.0.0
     */
    void update(BaseDTO<EmployeeEnrollmentDTO> vo);

    /**
     * remove
     * 删除员工及相关信息
     * @param vo
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/11/30 15:50
     * @since v1.0.0
     */
    void remove(BaseDTO<BaseQueryVO> vo);

    /**
     * getByCompanyIdAndEmployeeId
     * 获员工注册信息
     * @param companyId 企业id
     * @param employeeId 员工id
     * @return com.zkteco.dbs.company.model.EmployeeEnrollment
     * @throws
     * @author able.lee
     * @date 2020/11/30 15:50
     * @since v1.0.0
     */
    EmployeeEnrollment getByCompanyIdAndEmployeeId(String companyId, String employeeId);

    /**
     * fillUpRequest
     *
     * @param lang
     * @param employee
     * @param employeeEnrollment
     * @return void
     * @throws
     * @author sheldon.wu
     * @date 2020/12/9 15:37
     * @since 1.0.0
     */
    void fillUpRequest(String lang, Employee employee, EmployeeEnrollment employeeEnrollment);

}
