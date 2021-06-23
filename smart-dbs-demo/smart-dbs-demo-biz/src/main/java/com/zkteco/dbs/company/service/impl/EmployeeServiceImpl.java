package com.zkteco.dbs.company.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.common.profile.Language;
import com.zkcloud.api.dbs.model.BiotemplateRequest;
import com.zkcloud.api.dbs.model.EmployeeDeleteRequest;
import com.zkcloud.api.dbs.model.User;
import com.zkteco.dbs.acc.service.AccAuthorityEmployeeService;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.FileTypeEnum;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.common.utils.FileUploadUtil;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.company.dao.EmployeeMapper;
import com.zkteco.dbs.company.model.BioTemplate;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.model.Employee;
import com.zkteco.dbs.company.service.BioTemplateService;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.company.service.EmployeeService;
import com.zkteco.dbs.device.service.DeviceEmployeeService;
import java.io.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName EmployeeServiceImpl
 * @Description: 实现类
 * @Author able.lee
 * @Date 2020/11/24 10:38
 * @Since v1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private DeviceEmployeeService deviceEmployeeService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private BioTemplateService bioTemplateService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private AccAuthorityEmployeeService accAuthorityEmployeeService;

    @Override
    public String uploadAvatar(MultipartFile file, String lang) throws FileNotFoundException {

        return fileUploadUtil.upload(file, FileTypeEnum.IMAGE.getType(), lang);
    }

    @Override
    public Employee getByCompanyIdAndEmployeeNo(String companyId, String employeeNo, String lang) {

        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getCompanyId, companyId)
                .eq(Employee::getEmployeeNo, employeeNo);

        Employee employee = this.getOne(wrapper, false);

        return employee;
    }

    @Override
    public void remove(BaseDTO<BaseQueryVO> vo) {
        BaseQueryVO queryVO = vo.getPayload();
        String id = queryVO.getId();
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(vo.getLang()));
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        Employee employee = this.getById(id);
        ResultUtil.handleNullError(employee, "E19", vo.getLang());

        // 删除人员与设备关系
        deviceEmployeeService.removeByEmployeeNo(companyId, employee.getEmployeeNo());

        // 解绑人员与权限组的关系
        accAuthorityEmployeeService.removeByEmployeeNo(employee.getEmployeeNo());


        // 删除bio_template
        BioTemplate template = bioTemplateService.listByCompanyIdAndEmployeeNo(companyId,employee.getEmployeeNo());
        if(template != null){
            User user = new User(company.getUserName(), company.getPassword());
            if(template.getFingerCount() != 0){
                template.setFingerCount(0);
                bioTemplateService.updateById(template);

                // 调用sdk 删除模板信息
                BiotemplateRequest biotemplateRequest = new BiotemplateRequest(employee.getEmployeeNo(), SysConstants.FINGER_PRINT);
                biotemplateRequest.setApiUser(user);
                Message message = DBSApi.dbsClient.deleteBiotemplate(biotemplateRequest);
                ResultUtil.handleDbsResultError(message);
            }
            if(template.getFaceCount() != 0){
                template.setFaceCount(0);
                bioTemplateService.updateById(template);

                // 调用sdk 删除模板信息
                BiotemplateRequest biotemplateRequest = new BiotemplateRequest(employee.getEmployeeNo(), SysConstants.FACE);
                biotemplateRequest.setApiUser(user);
                Message message = DBSApi.dbsClient.deleteBiotemplate(biotemplateRequest);
                ResultUtil.handleDbsResultError(message);
            }
            if(template.getFacePhotoCount() != 0){
                template.setFacePhotoCount(0);
                bioTemplateService.updateById(template);

                // 调用sdk 删除模板信息
                BiotemplateRequest biotemplateRequest = new BiotemplateRequest(employee.getEmployeeNo(), SysConstants.VISIBLE_FACE);
                biotemplateRequest.setApiUser(user);
                Message message = DBSApi.dbsClient.deleteBiotemplate(biotemplateRequest);
                ResultUtil.handleDbsResultError(message);
            }
            if( template.getPalmPrintCount() != 0){
                template.setPalmPrintCount(0);
                bioTemplateService.updateById(template);

                // 调用sdk 删除模板信息
                BiotemplateRequest biotemplateRequest = new BiotemplateRequest(employee.getEmployeeNo(), SysConstants.PALM_PRINT);
                biotemplateRequest.setApiUser(user);
                Message message = DBSApi.dbsClient.deleteBiotemplate(biotemplateRequest);
                ResultUtil.handleDbsResultError(message);
            }
        }

        long lastUpdateTimeStamp = System.currentTimeMillis() / 1000;
        // 调用sdk删除员工信息
        EmployeeDeleteRequest employeeDeleteRequest = new EmployeeDeleteRequest(employee.getEmployeeNo(), lastUpdateTimeStamp);
        User user = new User(company.getUserName(), company.getPassword());
        employeeDeleteRequest.setApiUser(user);
        Message message = DBSApi.dbsClient.deleteEmployee(employeeDeleteRequest);

        ResultUtil.handleDbsResultError(message);
        this.removeById(id);


    }
}
