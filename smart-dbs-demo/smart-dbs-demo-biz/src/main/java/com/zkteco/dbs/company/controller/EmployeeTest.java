package com.zkteco.dbs.company.controller;

import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.common.profile.Language;
import com.zkcloud.api.dbs.model.EmployeeUpdateRequest;
import com.zkcloud.api.dbs.model.User;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.model.Employee;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.company.service.EmployeeService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class EmployeeTest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CompanyService companyService;

    @Autowired
    private DbsConfig dbsConfig;

    @PostMapping("testAddEmployee")
    public void testAddEmployee(){
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        // 保存员工基本信息
        for(int i = 1; i<=1000; i++){
            String dstNum = RandomStringUtils.random(3, "abcdefghijklmnopqrstuvwxyz1234567890");
            Employee employee = new Employee();
            employee.setCompanyId(companyId);
            employee.setEmployeeNo("emp"+i);
            employee.setFirstName(dstNum+i);
            employee.setLastName(dstNum);
            employee.setFormattedName(dstNum+i + "." + dstNum);

            employeeService.save(employee);


            EmployeeUpdateRequest employeeUpdateRequest = new EmployeeUpdateRequest();
            employeeUpdateRequest.setEmployeeNo(employee.getEmployeeNo());
            employeeUpdateRequest.setFirstName(employee.getFirstName());
            employeeUpdateRequest.setLastName(employee.getLastName());
            employeeUpdateRequest.setFormattedName(employee.getFirstName() + "." + employee.getLastName());
            employeeUpdateRequest.setActive(Short.valueOf("1"));
            employeeUpdateRequest.setCardNo(null);
            employeeUpdateRequest.setLastUpdateTimeStamp(System.currentTimeMillis() / 1000);
            employeeUpdateRequest.setDevicePermission(null);
            employeeUpdateRequest.setDevicePassword(null);
            employeeUpdateRequest.setDevicePasswordEncryption(null);
            employeeUpdateRequest.setDevicePasswordSalt(null);
            User user = new User(company.getUserName(), company.getPassword());
            employeeUpdateRequest.setApiUser(user);
            DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang("zh-CN"));
            Message message = DBSApi.dbsClient.updateEmployee(employeeUpdateRequest);
        }

    }
}
