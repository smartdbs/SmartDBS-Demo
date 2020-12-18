package com.zkteco.dbs.receiver.processor.impl;


import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.model.DeviceQueryDetails;
import com.zkcloud.api.dbs.model.DeviceQueryRequest;
import com.zkcloud.api.dbs.model.DeviceQueryResponse;
import com.zkcloud.api.dbs.model.User;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.ErrorCode;
import com.zkteco.dbs.common.tool.constants.Sid;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.device.model.Device;
import com.zkteco.dbs.device.service.DeviceService;
import com.zkteco.dbs.receiver.model.DeviceInit;
import com.zkteco.dbs.receiver.processor.DataProcessor;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * DeviceInitProcessor
 * 设备初始化事件处理器
 * @author sheldon.wu
 * @date 2020/11/30 14:06
 * @since 1.0.0
 */
@Component
@Order(1)
@ConditionalOnExpression("${dbs.push.switch.deviceInit:true}")
public class DeviceInitProcessor implements DataProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DbsConfig dbsConfig;

    @Override
    public boolean match(Message request) {
        return Sid.DEVICE_INIT.check(request.getSid());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message handle(Message request) {
        DeviceInit deviceInit = Convert.convert(DeviceInit.class, request.getPayload().getParams());
        // 设备初始化记录
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        Device device = deviceService.getOne(new LambdaQueryWrapper<Device>()
                .eq(Device::getCompanyId, company.getId())
                .eq(Device::getSn, deviceInit.getSn()));
        if (device == null) {
            ResultUtil.handldErrorInfo("E17", null);
        }
        device.setAlias(deviceInit.getAlais());
        device.setStatus(deviceInit.getStatus().intValue());
        device.setEnable(deviceInit.getEnable().intValue());
        device.setModel(deviceInit.getModel());
        device.setIpAddress(deviceInit.getRemoteIp());
        device.setType(Integer.valueOf(deviceInit.getType()).intValue());
        User apiUser = new User(company.getUserName(), company.getPassword());
        DeviceQueryRequest deviceQueryRequest = new DeviceQueryRequest(deviceInit.getSn(), SysConstants.NEED_DETAILS);
        deviceQueryRequest.setApiUser(apiUser);
        Message<List<DeviceQueryResponse>> res = DBSApi.dbsClient.queryDevice(deviceQueryRequest);
        // 请求失败，抛出异常
        ResultUtil.handleDbsResultError(res);
        DeviceQueryResponse deviceQueryResponse = res.getPayload().getResults().get(0);
        DeviceQueryDetails deviceQueryDetails = deviceQueryResponse.getDetails();
        device.setFwVersion(deviceQueryResponse.getFwVersion());
        device.setLocalIp(deviceQueryResponse.getLocalIp());
        device.setMac(deviceQueryDetails.getMacAddress());
        device.setProtocol(Integer.valueOf(deviceQueryDetails.getProtocol()).intValue());
        device.setSupportRemoteFacePhoto(Integer.valueOf(deviceQueryDetails.getSupportRemoteFacePhoto()).intValue());
        device.setSupportRemoteFinger(Integer.valueOf(deviceQueryDetails.getSupportFinger()).intValue());
        device.setSupportRemotePalmPrint(Integer.valueOf(deviceQueryDetails.getSupportRemotePalmPrint()).intValue());
        deviceService.updateById(device);

        logger.debug("DeviceInitProcessor：" + JSONObject.toJSON(deviceInit));
        logger.debug("DeviceQueryResponse：" + JSONObject.toJSON(deviceQueryResponse));
        return new Message(ErrorCode.CODE_SUCCESS.getCode(), ErrorCode.CODE_SUCCESS.getMessage());
    }
}
