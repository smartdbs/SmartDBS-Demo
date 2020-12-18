package com.zkteco.dbs.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.common.profile.Language;
import com.zkcloud.api.dbs.model.EmployeeBindDeviceRequest;
import com.zkcloud.api.dbs.model.EmployeeUnbindDeviceRequest;
import com.zkcloud.api.dbs.model.User;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.device.dao.DeviceEmployeeMapper;
import com.zkteco.dbs.device.dto.DeviceEmployeeDTO;
import com.zkteco.dbs.device.model.DeviceEmployee;
import com.zkteco.dbs.device.service.DeviceEmployeeService;
import com.zkteco.dbs.device.vo.DeviceEmployeeVO;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * DeviceEmployeeServiceImpl
 * 设备员工关系-业务实现层
 * @author sheldon.wu
 * @date 2020/11/20 18:16
 * @since 1.0.0
 */
@Service
public class DeviceEmployeeServiceImpl extends ServiceImpl<DeviceEmployeeMapper, DeviceEmployee> implements DeviceEmployeeService {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private DbsConfig dbsConfig;

    @Override
    public List<DeviceEmployeeVO> list(BaseDTO<DeviceEmployeeDTO> dto) {
        DeviceEmployeeDTO deviceEmployeeDTO = dto.getPayload();
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        deviceEmployeeDTO.setCompanyId(company.getId());
        return this.baseMapper.listSql(deviceEmployeeDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(BaseDTO<DeviceEmployeeDTO> dto) {
        DeviceEmployeeDTO deviceEmployeeDTO = dto.getPayload();
        List<String> employeeNos = deviceEmployeeDTO.getEmployeeNos();
        String sn = deviceEmployeeDTO.getSn();
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        ResultUtil.handldBlankError(sn, "E11", dto.getLang());
        ResultUtil.handldNullError(employeeNos, "E12", dto.getLang());
        //demo端保存设备员工关系
        employeeNos.stream().forEach(employeeNo -> {
            DeviceEmployee entity = new DeviceEmployee();
            entity.setCompanyId(company.getId());
            entity.setEmployeeNo(employeeNo);
            entity.setSn(sn);
            this.save(entity);
        });
        //dbs端保存设备员工关系
        User apiUser = new User(company.getUserName(), company.getPassword());
        EmployeeBindDeviceRequest employeeBindDeviceRequest = new EmployeeBindDeviceRequest(Arrays.asList(sn),
                employeeNos);
        employeeBindDeviceRequest.setApiUser(apiUser);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message res = DBSApi.dbsClient.employeeBindDevice(employeeBindDeviceRequest);

        ResultUtil.handleDbsResultError(res);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(BaseDTO<DeviceEmployeeDTO> dto) {
        String sn = dto.getPayload().getSn();
        String employeeNo = dto.getPayload().getEmployeeNo();
        ResultUtil.handldBlankError(sn, "E11", dto.getLang());
        ResultUtil.handldBlankError(employeeNo, "E12", dto.getLang());
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        this.baseMapper.delete(new LambdaQueryWrapper<DeviceEmployee>()
                .eq(DeviceEmployee::getCompanyId, company.getId())
                .eq(DeviceEmployee::getSn, sn)
                .eq(DeviceEmployee::getEmployeeNo, employeeNo));
        User apiUser = new User(company.getUserName(), company.getPassword());
        EmployeeUnbindDeviceRequest employeeUnbindDeviceRequest = new EmployeeUnbindDeviceRequest(Arrays.asList(sn),
                Arrays.asList(employeeNo));
        employeeUnbindDeviceRequest.setApiUser(apiUser);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message res = DBSApi.dbsClient.employeeUnBindDevice(employeeUnbindDeviceRequest);

        ResultUtil.handleDbsResultError(res);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByEmployeeNo(String companyId, String employeeNo) {
        this.baseMapper.delete(new LambdaQueryWrapper<DeviceEmployee>()
                .eq(DeviceEmployee::getCompanyId, companyId)
                .eq(DeviceEmployee::getEmployeeNo, employeeNo));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBySn(String companyId, String sn) {
        this.baseMapper.delete(new LambdaQueryWrapper<DeviceEmployee>()
                .eq(DeviceEmployee::getCompanyId, companyId)
                .eq(DeviceEmployee::getSn, sn));
    }

    @Override
    public void removeBySnAndEmployeeNo(String sn, String employeeNo) {
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        this.baseMapper.delete(new LambdaQueryWrapper<DeviceEmployee>()
                .eq(DeviceEmployee::getSn,sn)
                .eq(DeviceEmployee::getCompanyId,company.getId())
                .eq(DeviceEmployee::getEmployeeNo,employeeNo));
    }
}
