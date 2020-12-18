package com.zkteco.dbs.receiver.processor.impl;


import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zkcloud.api.dbs.common.Message;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.ErrorCode;
import com.zkteco.dbs.common.tool.constants.Sid;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.device.model.Device;
import com.zkteco.dbs.device.service.DeviceService;
import com.zkteco.dbs.receiver.model.DeviceStatusChange;
import com.zkteco.dbs.receiver.processor.DataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * DeviceStatusChangeProcessor
 * 设备在线事件处理器
 * @author sheldon.wu
 * @date 2020/11/30 14:06
 * @since 1.0.0
 */
@Component
@Order(1)
@ConditionalOnExpression("${dbs.push.switch.deviceStatus:true}")
public class DeviceStatusChangeProcessor implements DataProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DbsConfig dbsConfig;

    @Override
    public boolean match(Message request) {
        return Sid.DEVICE_STATUS_CHANGE.check(request.getSid());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message handle(Message request) {
        DeviceStatusChange deviceStatusChange = Convert.convert(DeviceStatusChange.class, request.getPayload().getParams());
        // 设备在线离线事件处理
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        Device device = deviceService.getOne(new LambdaQueryWrapper<Device>()
                .eq(Device::getCompanyId, company.getId())
                .eq(Device::getSn, deviceStatusChange.getSn()));
        if (device == null) {
            ResultUtil.handldErrorInfo("E17", null);
        }
        device.setStatus(deviceStatusChange.getStatus().intValue());
        deviceService.updateById(device);
        logger.debug("DeviceStatusChangeProcessor：" + JSONObject.toJSON(deviceStatusChange));
        return new Message(ErrorCode.CODE_SUCCESS.getCode(), ErrorCode.CODE_SUCCESS.getMessage());
    }
}
