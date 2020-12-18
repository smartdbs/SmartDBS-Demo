package com.zkteco.dbs.company.service;

import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import com.zkteco.dbs.company.model.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 * EmployeeService
 * 服务类
 * @author able.lee
 * @date 2020/11/30 15:11
 * @since v1.0.0
 */
public interface EmployeeService extends IService<Employee> {


    /**
     * uploadAvatar
     * 上传用户头像
     * @param file
     * @param lang
     * @return java.lang.String
     * @throws FileNotFoundException
     * @author able.lee
     * @date 2020/11/30 15:51
     * @since v1.0.0
     */
    String uploadAvatar(MultipartFile file,String lang) throws FileNotFoundException;

    /**
     * getByComanyIdAndEmployeeNo
     * 根据公司id和员工工号查询
     * @param companyId
     * @param employeeNo
     * @param lang
     * @return com.zkteco.dbs.company.model.Employee
     * @throws
     * @author able.lee
     * @date 2020/11/30 15:52
     * @since v1.0.0
     */
    Employee getByCompanyIdAndEmployeeNo(String companyId, String employeeNo, String lang);

    /**
     * remove
     * 删除员工及相关信息
     * @param vo
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/11/30 15:52
     * @since v1.0.0
     */
    void remove(BaseDTO<BaseQueryVO> vo);


}
