/*
 * File Name: UpgradeServiceImpl
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.common.profile.Language;
import com.zkcloud.api.dbs.model.DeviceUpgradeHistoryRequest;
import com.zkcloud.api.dbs.model.DeviceUpgradeHistoryResponse;
import com.zkcloud.api.dbs.model.DeviceUpgradeRequest;
import com.zkcloud.api.dbs.model.DeviceUpgradeResponse;
import com.zkcloud.api.dbs.model.NewFwVersion;
import com.zkcloud.api.dbs.model.NewFwVersionsRequest;
import com.zkcloud.api.dbs.model.NewFwVersionsResponse;
import com.zkcloud.api.dbs.model.User;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.device.dto.DeviceUpgradeDTO;
import com.zkteco.dbs.device.model.Device;
import com.zkteco.dbs.device.service.DeviceService;
import com.zkteco.dbs.device.service.DeviceUpgradeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UpgradeServiceImpl
 * 固件升级-业务实现层
 * @author sheldon.wu
 * @date 2021/1/4 16:21
 * @since 1.0.0
 */
@Service
public class DeviceUpgradeServiceImpl implements DeviceUpgradeService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DbsConfig dbsConfig;

    @Override
    public List<NewFwVersion> newestList(BaseDTO<DeviceUpgradeDTO> dto) {
        DeviceUpgradeDTO deviceUpgradeDTO = dto.getPayload();
        //检查参数是否合法
        ResultUtil.handleBlankError(deviceUpgradeDTO.getSn(), "E11", dto.getLang());
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        //调用SDK获取设备的升级版本号列表
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        User user = new User(company.getUserName(), company.getPassword());
        NewFwVersionsRequest request = new NewFwVersionsRequest(deviceUpgradeDTO.getSn());
        request.setApiUser(user);
        Message<NewFwVersionsResponse> response = DBSApi.dbsClient.getNewVersions(request);
        ResultUtil.handleDbsResultError(response);
        NewFwVersionsResponse newFwVersionsResponse = response.getPayload().getResults();
        return newFwVersionsResponse.getVersions();
    }

    @Override
    public Map<String, String> upgrade(BaseDTO<DeviceUpgradeDTO> dto) {
        DeviceUpgradeDTO deviceUpgradeDTO = dto.getPayload();
        //检查参数是否合法
        ResultUtil.handleBlankError(deviceUpgradeDTO.getSn(), "E11", dto.getLang());
        ResultUtil.handleBlankError(deviceUpgradeDTO.getVersion(), "E34", dto.getLang());
        //判断设备是否已经存在
        Device device = deviceService.getOne(new LambdaQueryWrapper<Device>().eq(Device::getSn, deviceUpgradeDTO.getSn()));
        if (device == null) {
            ResultUtil.handleErrorInfo("E16", null);
        }
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        //调用SDK升级设备固件
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        User user = new User(company.getUserName(), company.getPassword());
        DeviceUpgradeRequest request = new DeviceUpgradeRequest(deviceUpgradeDTO.getSn(), deviceUpgradeDTO.getVersion());
        request.setApiUser(user);
        Message<DeviceUpgradeResponse> res = DBSApi.dbsClient.deviceUpgrade(request);
        ResultUtil.handleDbsResultError(res);
        DeviceUpgradeResponse deviceUpgradeResponse = res.getPayload().getResults();
        Map<String, String> map = new HashMap<>(SysConstants.INIT_SIZE);
        map.put(SysConstants.TASK_ID, deviceUpgradeResponse.getTaskId());
        return map;
    }

    @Override
    public List<DeviceUpgradeHistoryResponse> historyList(BaseDTO<DeviceUpgradeDTO> dto) {
        DeviceUpgradeDTO deviceUpgradeDTO = dto.getPayload();
        //检查参数是否合法
        ResultUtil.handleBlankError(deviceUpgradeDTO.getSn(), "E11", dto.getLang());
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        //调用SDK查询设备升级历史
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        User user = new User(company.getUserName(), company.getPassword());
        DeviceUpgradeHistoryRequest request = new DeviceUpgradeHistoryRequest(deviceUpgradeDTO.getSn());
        if (StringUtils.isNotBlank(deviceUpgradeDTO.getTaskId())) {
            request.setTaskId(deviceUpgradeDTO.getTaskId());
        }
        request.setApiUser(user);
        Message<List<DeviceUpgradeHistoryResponse>> response = DBSApi.dbsClient.getUpgradeHistory(request);
        ResultUtil.handleDbsResultError(response);
        List<DeviceUpgradeHistoryResponse> list = response.getPayload().getResults();
        return list;
    }
}
