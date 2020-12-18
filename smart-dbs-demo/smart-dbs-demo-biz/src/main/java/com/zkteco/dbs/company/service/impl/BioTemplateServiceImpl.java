package com.zkteco.dbs.company.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.common.profile.Language;
import com.zkcloud.api.dbs.model.BiotemplateRequest;
import com.zkcloud.api.dbs.model.CancelRegisterBiometricRequest;
import com.zkcloud.api.dbs.model.RegisterBiometricRequest;
import com.zkcloud.api.dbs.model.RegisterBiometricResponse;
import com.zkcloud.api.dbs.model.User;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.company.dao.BioTemplateMapper;
import com.zkteco.dbs.company.dto.RegisterBiometricDTO;
import com.zkteco.dbs.company.model.BioTemplate;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.BioTemplateService;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.company.service.EmployeeEnrollmentService;
import com.zkteco.dbs.company.service.EmployeeService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName BioTemplateServiceImpl
 * @Description: 服务实现类
 * @Author able.lee
 * @Date 2020/11/25 10:42
 * @Since v1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BioTemplateServiceImpl extends ServiceImpl<BioTemplateMapper, BioTemplate> implements BioTemplateService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeEnrollmentService employeeEnrollmentService;

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private CompanyService companyService;

    @Override
    public void removeTemplate(String type, String companyId, String employeeNo, String lang) {

        BioTemplate template = this.listByCompanyIdAndEmployeeNo(companyId, employeeNo);
        if (null != template && type.equals(SysConstants.FINGER_PRINT)) {
            template.setFingerCount(0);
            this.updateById(template);
        }
        if (null != template && type.equals(SysConstants.FACE)) {
            template.setFaceCount(0);
            this.updateById(template);
        }
        if (null != template && type.equals(SysConstants.VISIBLE_FACE)) {
            template.setFacePhotoCount(0);
            this.updateById(template);
        }
        if (null != template && type.equals(SysConstants.PALM_PRINT)) {
            template.setPalmPrintCount(0);
            this.updateById(template);
        }

        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        // 调用sdk 删除模板信息
        User user = new User(company.getUserName(), company.getPassword());
        BiotemplateRequest biotemplateRequest = new BiotemplateRequest(employeeNo, type);
        biotemplateRequest.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(lang));
        Message message = DBSApi.dbsClient.deleteBiotemplate(biotemplateRequest);
        ResultUtil.handleDbsResultError(message);
    }

    @Override
    public BioTemplate listByCompanyIdAndEmployeeNo(String companyId, String employeeNo) {

        BioTemplate bioTemplate =
                this.getOne(new LambdaQueryWrapper<BioTemplate>()
                        .eq(BioTemplate::getCompanyId, companyId)
                        .eq(BioTemplate::getEmployeeNo, employeeNo), false);

        return bioTemplate;
    }

    @Override
    public void cancelRegisterBiometric(BaseDTO<RegisterBiometricDTO> dto) {
        RegisterBiometricDTO registerBiometricDTO = dto.getPayload();
        //检查参数是否合法
        ResultUtil.handldBlankError(registerBiometricDTO.getSn(), "E11", dto.getLang());

        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        //调用SDK 取消生物特征登记
        User apiUser = new User(company.getUserName(), company.getPassword());
        CancelRegisterBiometricRequest cancelRegisterBiometricRequest = new CancelRegisterBiometricRequest(registerBiometricDTO.getSn());
        cancelRegisterBiometricRequest.setApiUser(apiUser);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message response = DBSApi.dbsClient.cancelRegisterBiometric(cancelRegisterBiometricRequest);
        ResultUtil.handleDbsResultError(response);
    }

    @Override
    public Map<String, String> registerBiometric(BaseDTO<RegisterBiometricDTO> dto) {
        RegisterBiometricDTO registerBiometricDTO = dto.getPayload();
        //检查参数是否合法
        ResultUtil.handldBlankError(registerBiometricDTO.getSn(), "E11", dto.getLang());
        ResultUtil.handldBlankError(registerBiometricDTO.getEmployeeNo(), "E12", dto.getLang());
        ResultUtil.handldBlankError(registerBiometricDTO.getType(), "E29", dto.getLang());

        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        //调用SDK 进行生物特征登记
        User apiUser = new User(company.getUserName(), company.getPassword());
        RegisterBiometricRequest registerBiometricRequest = new RegisterBiometricRequest(registerBiometricDTO.getSn(),
                registerBiometricDTO.getEmployeeNo(), registerBiometricDTO.getType());
        registerBiometricRequest.setIsCover("0");
        registerBiometricRequest.setApiUser(apiUser);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message<RegisterBiometricResponse> response = DBSApi.dbsClient.registerBiometric(registerBiometricRequest);
        ResultUtil.handleDbsResultError(response);
        //获取登记操作sid
        RegisterBiometricResponse registerBiometricResponse = response.getPayload().getResults();
        Map<String, String> map = new HashMap<>(SysConstants.INIT_SIZE);
        map.put(SysConstants.SID, registerBiometricResponse.getSessionId());
        return map;
    }
}
