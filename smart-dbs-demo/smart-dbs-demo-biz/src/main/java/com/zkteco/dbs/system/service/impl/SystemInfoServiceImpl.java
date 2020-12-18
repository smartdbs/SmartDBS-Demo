package com.zkteco.dbs.system.service.impl;

import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.system.service.SystemInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SystemInforServiceImpl
 * @Description: 方法实现类
 * @Author able.lee
 * @Date 2020/11/24 17:10
 * @Since v1.0.0
 */
@Service
public class SystemInfoServiceImpl implements SystemInforService {

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private CompanyService companyService;

    @Override
    public Map<String, Object> systemInfo() {

        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        Map<String, Object> resMap = new HashMap<>(SysConstants.INIT_SIZE);

        Map<String, Object> appInfo = new HashMap<>(SysConstants.INIT_SIZE);
        Map<String, Object> nodeInfo = new HashMap<>(SysConstants.INIT_SIZE);

        appInfo.put("AppKey",dbsConfig.getAppKey());
        appInfo.put("AppSecret",dbsConfig.getAppSecret());
        appInfo.put("Account",company.getUserName());
        appInfo.put("Password",company.getPassword());

        nodeInfo.put("node",dbsConfig.getEndpoint());
        nodeInfo.put("ssl",dbsConfig.isSsl());

        resMap.put("appInfo",appInfo);
        resMap.put("nodeInfo",nodeInfo);

        return resMap;
    }
}