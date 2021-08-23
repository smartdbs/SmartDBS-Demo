package com.zkteco.dbs.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.common.profile.Language;
import com.zkcloud.api.dbs.model.CreateDeviceRequest;
import com.zkcloud.api.dbs.model.DeviceDeleteRequest;
import com.zkcloud.api.dbs.model.DeviceDisableRequest;
import com.zkcloud.api.dbs.model.DeviceEnableRequest;
import com.zkcloud.api.dbs.model.DeviceRebootRequest;
import com.zkcloud.api.dbs.model.DeviceUpdateRequest;
import com.zkcloud.api.dbs.model.User;
import com.zkteco.dbs.acc.service.AccDoorService;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.common.utils.ModelUtils;
import com.zkteco.dbs.common.utils.PagingUtil;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.device.dao.DeviceMapper;
import com.zkteco.dbs.device.dto.DeviceDTO;
import com.zkteco.dbs.device.model.Device;
import com.zkteco.dbs.device.service.DeviceEmployeeService;
import com.zkteco.dbs.device.service.DeviceService;
import com.zkteco.dbs.device.vo.DeviceVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * DeviceServiceImpl
 * 设备管理-业务实现层
 * @author sheldon.wu
 * @date 2020/11/20 14:25
 * @since 1.0.0
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private DeviceEmployeeService deviceEmployeeService;

    @Autowired
    private AccDoorService accDoorService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<DeviceVO> pageList(BaseDTO<DeviceDTO> dto) {
        DeviceDTO deviceDTO = dto.getPayload();
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        deviceDTO.setCompanyId(company.getId());
        Page<DeviceVO> page = PagingUtil.initPage(deviceDTO);
        return this.baseMapper.pageListSql(page, deviceDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(BaseDTO<DeviceDTO> dto) {
        DeviceDTO deviceDTO = dto.getPayload();
        //检查参数是否合法
        ResultUtil.handleBlankError(deviceDTO.getSn(), "E11", dto.getLang());
        ResultUtil.handleBlankError(deviceDTO.getAlias(), "E13", dto.getLang());
        //判断设备是否已经存在
        Device device = baseMapper.selectOne(new LambdaQueryWrapper<Device>().eq(Device::getSn, deviceDTO.getSn()));
        ResultUtil.handleExistsError(device, "E15", dto.getLang());
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        Device entity = new Device();
        ModelUtils.copyPropertiesIgnoreNull(deviceDTO, entity);
        entity.setCompanyId(company.getId());
        entity.setStatus(SysConstants.OFFLINE);
        entity.setEnable(SysConstants.ENABLE);
        entity.setActive(SysConstants.ACTIVATED);
        //保存demo端设备信息
        this.save(entity);
        //保存dbs端设备信息
        User apiUser = new User(company.getUserName(), company.getPassword());
        CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
        createDeviceRequest.setSn(entity.getSn());
        createDeviceRequest.setAlais(entity.getAlias());
        createDeviceRequest.setApiUser(apiUser);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message res = DBSApi.dbsClient.addDevice(createDeviceRequest);

        ResultUtil.handleDbsResultError(res);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(BaseDTO<DeviceDTO> dto) {
        String sn = dto.getPayload().getSn();
        //检查参数是否合法
        ResultUtil.handleBlankError(sn, "E11", dto.getLang());
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();
        //判断设备是否已经存在
        Device device = baseMapper.selectOne(new LambdaQueryWrapper<Device>().eq(Device::getCompanyId, companyId).eq(Device::getSn, sn));
        ResultUtil.handleNullError(device, "E16", dto.getLang());
        //删除demo端设备信息
        this.removeById(device.getId());
        //删除demo端设备员工关系信息
        deviceEmployeeService.removeBySn(companyId, sn);
        //删除门信息和门权限关系
        accDoorService.removeBySn(companyId, sn);

        //删除dbs端设备信息
        User apiUser = new User(company.getUserName(), company.getPassword());
        DeviceDeleteRequest deviceDeleteRequest = new DeviceDeleteRequest(sn);
        deviceDeleteRequest.setApiUser(apiUser);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message res = DBSApi.dbsClient.deleteDevice(deviceDeleteRequest);

        ResultUtil.handleDbsResultError(res);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeOnDemo(DeviceDTO dto) {
        //删除demo端设备信息
        this.remove(new LambdaQueryWrapper<Device>()
                .eq(Device::getCompanyId, dto.getCompanyId())
                .eq(Device::getSn, dto.getSn()));
        //删除demo端设备员工关系信息
        deviceEmployeeService.removeBySn(dto.getCompanyId(), dto.getSn());
        //删除门信息和门权限关系
        accDoorService.removeBySn(dto.getCompanyId(), dto.getSn());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BaseDTO<DeviceDTO> dto) {
        DeviceDTO deviceDTO = dto.getPayload();
        //检查参数是否合法
        ResultUtil.handleBlankError(deviceDTO.getSn(), "E11", dto.getLang());
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        //判断设备是否已经存在
        Device device = baseMapper.selectOne(new LambdaQueryWrapper<Device>()
                .eq(Device::getCompanyId, company.getId())
                .eq(Device::getSn, deviceDTO.getSn()));
        ResultUtil.handleNullError(device, "E16", dto.getLang());
        //更新demo端设备信息
        device.setAlias(deviceDTO.getAlias());
        this.updateById(device);
        //更新dbs端设备信息
        User apiUser = new User(company.getUserName(), company.getPassword());
        DeviceUpdateRequest deviceUpdateRequest = new DeviceUpdateRequest(deviceDTO.getSn());
        deviceUpdateRequest.setAlais(deviceDTO.getAlias());
        deviceUpdateRequest.setApiUser(apiUser);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message res = DBSApi.dbsClient.updateDevice(deviceUpdateRequest);

        ResultUtil.handleDbsResultError(res);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disable(BaseDTO<DeviceDTO> dto) {
        String sn = dto.getPayload().getSn();
        //检查参数是否合法
        ResultUtil.handleBlankError(sn, "E11", dto.getLang());
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        //判断设备是否已经存在
        Device device = baseMapper.selectOne(new LambdaQueryWrapper<Device>()
                .eq(Device::getCompanyId, company.getId())
                .eq(Device::getSn, sn));
        //更新demo端设备信息
        device.setEnable(SysConstants.DISABLE);
        this.updateById(device);
        //更新dbs端设备信息
        User apiUser = new User(company.getUserName(), company.getPassword());
        DeviceDisableRequest deviceDisableRequest = new DeviceDisableRequest(sn);
        deviceDisableRequest.setApiUser(apiUser);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message res = DBSApi.dbsClient.disableDevice(deviceDisableRequest);

        ResultUtil.handleDbsResultError(res);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enable(BaseDTO<DeviceDTO> dto) {
        String sn = dto.getPayload().getSn();
        //检查参数是否合法
        ResultUtil.handleBlankError(sn, "E11", dto.getLang());
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        //判断设备是否已经存在
        Device device = baseMapper.selectOne(new LambdaQueryWrapper<Device>()
                .eq(Device::getCompanyId, company.getId())
                .eq(Device::getSn, sn));
        ResultUtil.handleNullError(device, "E16", dto.getLang());
        //更新demo端设备信息
        device.setEnable(SysConstants.ENABLE);
        this.updateById(device);
        //更新dbs端设备信息
        User apiUser = new User(company.getUserName(), company.getPassword());
        DeviceEnableRequest deviceEnableRequest = new DeviceEnableRequest(sn);
        deviceEnableRequest.setApiUser(apiUser);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message res = DBSApi.dbsClient.enableDevice(deviceEnableRequest);

        ResultUtil.handleDbsResultError(res);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reboot(BaseDTO<DeviceDTO> dto) {
        //获取sn
        String sn = dto.getPayload().getSn();
        //检查参数是否合法
        ResultUtil.handleBlankError(sn, "E11", dto.getLang());
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        //判断设备是否已经存在
        Device device = baseMapper.selectOne(new LambdaQueryWrapper<Device>()
                .eq(Device::getCompanyId, company.getId())
                .eq(Device::getSn, sn));
        ResultUtil.handleNullError(device, "E16", dto.getLang());
        //更新dbs端设备信息
        User apiUser = new User(company.getUserName(), company.getPassword());
        DeviceRebootRequest deviceRebootRequest = new DeviceRebootRequest(sn);
        deviceRebootRequest.setApiUser(apiUser);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message res = DBSApi.dbsClient.rebootDevice(deviceRebootRequest);

        ResultUtil.handleDbsResultError(res);
    }

    @Override
    public List<DeviceVO> listByEmployee(BaseDTO<DeviceDTO> dto) {
        DeviceDTO deviceDTO = dto.getPayload();
        //检查参数是否合法
        ResultUtil.handleBlankError(deviceDTO.getEmployeeNo(), "E12", dto.getLang());
        ResultUtil.handleBlankError(deviceDTO.getBiometricType(), "E29", dto.getLang());
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        deviceDTO.setCompanyId(company.getId());
        if (SysConstants.FINGER_PRINT.equals(deviceDTO.getBiometricType())) {
            deviceDTO.setSupportRemoteFinger(1);
        }
        if (SysConstants.VISIBLE_FACE.equals(deviceDTO.getBiometricType())) {
            deviceDTO.setSupportRemoteFacePhoto(1);
        }
        if (SysConstants.FACE.equals(deviceDTO.getBiometricType())) {
            deviceDTO.setSupportRemoteNearInfraredFace(1);
        }
        if (SysConstants.PALM_PRINT.equals(deviceDTO.getBiometricType())) {
            deviceDTO.setSupportRemotePalmPrint(1);
        }
        return this.baseMapper.listByEmployee(deviceDTO);
    }
}
