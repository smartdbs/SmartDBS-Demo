package com.zkteco.dbs.company.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.common.profile.Language;
import com.zkcloud.api.dbs.model.EmployeeUpdateRequest;
import com.zkcloud.api.dbs.model.User;
import com.zkcloud.api.dbs.model.VerifyInfoRequest;
import com.zkcloud.api.dbs.model.VerifyInfoResponse;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.common.utils.PBKDF2Utils;
import com.zkteco.dbs.common.utils.PagingUtil;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.common.utils.TimezoneUtil;
import com.zkteco.dbs.company.dao.EmployeeEnrollmentMapper;
import com.zkteco.dbs.company.dto.EmployeeEnrollmentDTO;
import com.zkteco.dbs.company.model.BioTemplate;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.model.Employee;
import com.zkteco.dbs.company.model.EmployeeEnrollment;
import com.zkteco.dbs.company.service.BioTemplateService;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.company.service.EmployeeEnrollmentService;
import com.zkteco.dbs.company.service.EmployeeService;
import com.zkteco.dbs.company.vo.EmployeeEnrollmentVO;
import com.zkteco.dbs.device.service.DeviceEmployeeService;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName EmployeeEnrollmentServiceImpl
 * @Description: 服务实现类
 * @Author able.lee
 * @Date 2020/11/20 10:33
 * @Since v1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeEnrollmentServiceImpl extends ServiceImpl<EmployeeEnrollmentMapper, EmployeeEnrollment> implements
        EmployeeEnrollmentService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private BioTemplateService bioTemplateService;

    @Autowired
    private DeviceEmployeeService deviceEmployeeService;


    @Override
    public IPage<EmployeeEnrollmentVO> pageList(BaseDTO<BaseQueryVO> vo) {
        BaseQueryVO queryVo = vo.getPayload();

        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        // 查询员工基本信息
        Page<EmployeeEnrollmentVO> page = PagingUtil.initPage(queryVo);

        IPage<EmployeeEnrollmentVO> res = this.baseMapper.pageList(page, companyId, queryVo.getEmployeeNo(), queryVo.getFormattedName());
        // 拼接模板信息
        res.getRecords().stream().peek(entity -> {
            DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(vo.getLang()));
            //调用SDK查询员工核验信息 同步dbs的生物模板信息
            User user = new User(company.getUserName(), company.getPassword());
            VerifyInfoRequest request = new VerifyInfoRequest(entity.getEmployeeNo());
            request.setApiUser(user);
            Message<VerifyInfoResponse> response = DBSApi.dbsClient.queryVerifyInfo(request);
            VerifyInfoResponse verifyInfoResponse = response.getPayload().getResults();

            BioTemplate template = bioTemplateService.listByCompanyIdAndEmployeeNo(companyId, entity.getEmployeeNo());
            if (null == template) {
                BioTemplate bioTemplate = new BioTemplate();
                bioTemplate.setCompanyId(companyId);
                bioTemplate.setEmployeeNo(entity.getEmployeeNo());
                bioTemplate.setFingerCount(verifyInfoResponse.getFingerCount());
                bioTemplate.setFaceCount(verifyInfoResponse.getFaceCount());
                bioTemplate.setFacePhotoCount(verifyInfoResponse.getFacePhotoCount());
                bioTemplate.setPalmPrintCount(verifyInfoResponse.getPalmPrintCount());

                bioTemplateService.save(bioTemplate);

                entity.setFingerCount(verifyInfoResponse.getFingerCount());
                entity.setFaceCount(verifyInfoResponse.getFaceCount());
                entity.setVisibleFaceCount(verifyInfoResponse.getFacePhotoCount());
                entity.setPalmPrintCount(verifyInfoResponse.getPalmPrintCount());
            } else {
                entity.setFingerCount(verifyInfoResponse.getFingerCount());
                entity.setFaceCount(verifyInfoResponse.getFaceCount());
                entity.setVisibleFaceCount(verifyInfoResponse.getFacePhotoCount());
                entity.setPalmPrintCount(verifyInfoResponse.getPalmPrintCount());

                template.setFingerCount(verifyInfoResponse.getFingerCount());
                template.setFaceCount(verifyInfoResponse.getFaceCount());
                template.setFacePhotoCount(verifyInfoResponse.getFacePhotoCount());
                template.setPalmPrintCount(verifyInfoResponse.getPalmPrintCount());
                bioTemplateService.updateById(template);
            }

        }).collect(Collectors.toList());

        return res;
    }

    @Override
    public List<EmployeeEnrollmentVO> unassignedList(BaseDTO<BaseQueryVO> vo) {
        BaseQueryVO queryVO = vo.getPayload();

        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        // 查询已经授权的员工
        List<EmployeeEnrollmentVO> list = this.baseMapper.unassignedList(companyId, queryVO.getEmployeeNo(),
                queryVO.getFormattedName(), queryVO.getSn());

        return list;
    }

    @Override
    public void save(BaseDTO<EmployeeEnrollmentDTO> vo) {
        EmployeeEnrollmentDTO dto = vo.getPayload();

        // 校验employeeNo 是否已存在
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();
        Employee ext = employeeService.getByCompanyIdAndEmployeeNo(companyId, dto.getEmployeeNo(), vo.getLang());
        // 员工工号已存在
        ResultUtil.handleNoNullError(ext, "E18", vo.getLang());

        // 保存员工基本信息
        Employee employee = new Employee();
        employee.setIdCard(dto.getIdCard());
        employee.setCardType(dto.getCardType());
        employee.setTemporaryStatus(dto.getTemporaryStatus());
        employee.setAllowStatus(dto.getAllowStatus());
        employee.setStartTime(StringUtils.isNotBlank(dto.getStartTime()) ? dto.getStartTime() + TimezoneUtil.getStandardOffset() : null);
        employee.setEndTime(StringUtils.isNotBlank(dto.getEndTime()) ? dto.getEndTime() + TimezoneUtil.getStandardOffset() : null);
        employee.setCompanyId(companyId);
        employee.setEmployeeNo(dto.getEmployeeNo());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setFormattedName(dto.getLastName() + "." + dto.getFirstName());
        employee.setAvatar(dto.getAvatar());

        employeeService.save(employee);

        // 保存核验信息表
        EmployeeEnrollment employeeEnrollment = new EmployeeEnrollment();
        employeeEnrollment.setCompanyId(companyId);
        employeeEnrollment.setEmployeeId(employee.getId());
        employeeEnrollment.setCardNo(dto.getCardNo());
        if (StringUtils.isNotBlank(dto.getDevicePassword())) {
            String devicePasswordSalt = UUID.randomUUID().toString(true);
            employeeEnrollment.setDevicePassword(dto.getDevicePassword());
            employeeEnrollment.setDevicePasswordSalt(devicePasswordSalt);
            employeeEnrollment.setDevicePasswordEncryption(PBKDF2Utils.getEncryptedPassword(dto.getDevicePassword(), devicePasswordSalt));
        }
        employeeEnrollment.setDevicePermission(dto.getDevicePermission());
        this.save(employeeEnrollment);

        // 填充请求参数，并调用SDK
        fillUpRequest(vo.getLang(), employee, employeeEnrollment);

    }

    @Override
    public void update(BaseDTO<EmployeeEnrollmentDTO> vo) {
        EmployeeEnrollmentDTO dto = vo.getPayload();

        // 校验employeeNo 是否已存在
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        // 更新员工信息
        Employee ext = employeeService.getById(dto.getId());
        ext.setIdCard(dto.getIdCard());
        ext.setCardType(dto.getCardType());
        ext.setTemporaryStatus(dto.getTemporaryStatus());
        ext.setAllowStatus(dto.getAllowStatus());
        ext.setStartTime(StringUtils.isNotBlank(dto.getStartTime()) ? dto.getStartTime() + TimezoneUtil.getStandardOffset() : null);
        ext.setEndTime(StringUtils.isNotBlank(dto.getEndTime()) ? dto.getEndTime() + TimezoneUtil.getStandardOffset() : null);
        ext.setFirstName(dto.getFirstName());
        ext.setLastName(dto.getLastName());
        ext.setFormattedName(dto.getLastName() + "." + dto.getFirstName());
        ext.setAvatar(dto.getAvatar());
        employeeService.updateById(ext);

        // 更新注册信息
        EmployeeEnrollment employeeEnrollment = this.getByCompanyIdAndEmployeeId(companyId, ext.getId());
        employeeEnrollment.setCardNo(dto.getCardNo());
        if (employeeEnrollment.getDevicePassword() != dto.getDevicePassword()) {
            if (StringUtils.isBlank(dto.getDevicePassword())) {
                employeeEnrollment.setDevicePassword("");
                employeeEnrollment.setDevicePasswordSalt("");
                employeeEnrollment.setDevicePasswordEncryption("");
            } else {
                String devicePasswordSalt = UUID.randomUUID().toString(true);
                employeeEnrollment.setDevicePasswordSalt(devicePasswordSalt);
                employeeEnrollment
                        .setDevicePasswordEncryption(PBKDF2Utils.getEncryptedPassword(dto.getDevicePassword(), devicePasswordSalt));
                employeeEnrollment.setDevicePassword(dto.getDevicePassword());
            }
        }
        employeeEnrollment.setDevicePermission(dto.getDevicePermission());
        this.updateById(employeeEnrollment);
        // 填充请求参数，并调用SDK
        fillUpRequest(vo.getLang(), ext, employeeEnrollment);

        // 删除模板信息
        if (dto.getFingerCount() == -1) {
            bioTemplateService.removeTemplate(SysConstants.FINGER_PRINT, companyId, ext.getEmployeeNo(), vo.getLang());
        }
        if (dto.getFaceCount() == -1) {
            bioTemplateService.removeTemplate(SysConstants.FACE, companyId, ext.getEmployeeNo(), vo.getLang());
        }
        if (dto.getVisibleFaceCount() == -1) {
            bioTemplateService.removeTemplate(SysConstants.VISIBLE_FACE, companyId, ext.getEmployeeNo(), vo.getLang());
        }
        if (dto.getPalmPrintCount() == -1) {
            bioTemplateService.removeTemplate(SysConstants.PALM_PRINT, companyId, ext.getEmployeeNo(), vo.getLang());
        }


    }

    @Override
    public void remove(BaseDTO<BaseQueryVO> vo) {

        BaseQueryVO queryVO = vo.getPayload();

        employeeService.remove(vo);

        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        // 删除员工注册信息
        LambdaQueryWrapper<EmployeeEnrollment> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(EmployeeEnrollment::getEmployeeId, queryVO.getId())
                .eq(EmployeeEnrollment::getCompanyId, companyId);
        this.remove(deleteWrapper);

    }

    @Override
    public EmployeeEnrollment getByCompanyIdAndEmployeeId(String companyId, String employeeId) {
        LambdaQueryWrapper<EmployeeEnrollment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmployeeEnrollment::getCompanyId, companyId)
                .eq(EmployeeEnrollment::getEmployeeId, employeeId);
        EmployeeEnrollment enrollment = this.getOne(wrapper, false);
        return enrollment;
    }


    /**
     * fillUpRequest
     * 填充请求参数，并调用SDK
     * @param employee
     * @param employeeEnrollment
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/11/30 15:43
     * @since v1.0.0
     */
    @Override
    public void fillUpRequest(String lang, Employee employee, EmployeeEnrollment employeeEnrollment) {
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        EmployeeUpdateRequest employeeUpdateRequest = new EmployeeUpdateRequest();
        employeeUpdateRequest.setEmployeeNo(employee.getEmployeeNo());
        employeeUpdateRequest.setFirstName(employee.getFirstName());
        employeeUpdateRequest.setLastName(employee.getLastName());
        employeeUpdateRequest.setFormattedName(employee.getFirstName() + "." + employee.getLastName());
        employeeUpdateRequest.setActive(Short.valueOf("1"));
        employeeUpdateRequest.setCardNo(employeeEnrollment.getCardNo());
        employeeUpdateRequest.setLastUpdateTimeStamp(System.currentTimeMillis() / 1000);
        employeeUpdateRequest.setDevicePermission(employeeEnrollment.getDevicePermission().toString());
        employeeUpdateRequest.setDevicePassword(employeeEnrollment.getDevicePassword());
        employeeUpdateRequest.setDevicePasswordEncryption(employeeEnrollment.getDevicePasswordEncryption());
        employeeUpdateRequest.setDevicePasswordSalt(employeeEnrollment.getDevicePasswordSalt());
        employeeUpdateRequest.setIdCard(employee.getIdCard());
        employeeUpdateRequest.setCardType(employee.getCardType());
        employeeUpdateRequest.setTemporaryStatus(employee.getTemporaryStatus());
        employeeUpdateRequest.setAllowStatus(employee.getAllowStatus());
        employeeUpdateRequest.setStartTime(employee.getStartTime());
        employeeUpdateRequest.setEndTime(employee.getEndTime());
        User user = new User(company.getUserName(), company.getPassword());
        employeeUpdateRequest.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(lang));
        Message message = DBSApi.dbsClient.updateEmployee(employeeUpdateRequest);

        ResultUtil.handleDbsResultError(message);
    }
}
