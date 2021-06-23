package com.zkteco.dbs.receiver.processor.impl;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
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
import com.zkteco.dbs.device.dto.DeviceDTO;
import com.zkteco.dbs.device.model.Device;
import com.zkteco.dbs.device.service.DeviceService;
import com.zkteco.dbs.receiver.model.DeviceStatusChange;
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
        //判断设备是否存在
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        Device device = deviceService.getOne(new LambdaQueryWrapper<Device>()
                .eq(Device::getCompanyId, company.getId())
                .eq(Device::getSn, deviceStatusChange.getSn()));
        if (device == null) {
            ResultUtil.handleErrorInfo("E17", null);
        }
        //判断设备状态推送时间是否为最新
        if (ObjectUtil.isEmpty(device.getChangeTimeStamp()) || (device.getChangeTimeStamp() <= deviceStatusChange.getChangeTimeStamp())) {
            //若设备为重置状态，则删除demo端相关设备信息
            if (SysConstants.RESET == deviceStatusChange.getStatus().intValue()) {
                DeviceDTO deviceDTO = new DeviceDTO();
                deviceDTO.setCompanyId(company.getId());
                deviceDTO.setSn(deviceStatusChange.getSn());
                deviceService.removeOnDemo(deviceDTO);
            } else {
                //变更设备状态
                device.setStatus(deviceStatusChange.getStatus().intValue());
                device.setChangeTimeStamp(deviceStatusChange.getChangeTimeStamp());
                //调用SDk获取设备详细信息(防止设备信息变更demo端未同步)
                User apiUser = new User(company.getUserName(), company.getPassword());
                DeviceQueryRequest deviceQueryRequest = new DeviceQueryRequest(deviceStatusChange.getSn(), SysConstants.NEED_DETAILS);
                deviceQueryRequest.setApiUser(apiUser);
                Message<List<DeviceQueryResponse>> res = DBSApi.dbsClient.queryDevice(deviceQueryRequest);
                // 请求失败，抛出异常
                ResultUtil.handleDbsResultError(res);
                DeviceQueryResponse deviceQueryResponse = res.getPayload().getResults().get(0);
                DeviceQueryDetails deviceQueryDetails = deviceQueryResponse.getDetails();
                device.setFwVersion(deviceQueryResponse.getFwVersion());
                device.setType(Integer.valueOf(deviceQueryResponse.getType()));
                device.setIpAddress(deviceQueryResponse.getRemoteIp());
                device.setLocalIp(deviceQueryResponse.getLocalIp());
                device.setModel(deviceQueryDetails.getModel());
                device.setProtocol(Integer.valueOf(deviceQueryDetails.getProtocol()));
                device.setMac(deviceQueryDetails.getMacAddress());
                device.setSupportRemoteFacePhoto(Integer.valueOf(deviceQueryDetails.getSupportRemoteFacePhoto()));
                device.setSupportRemoteNearInfraredFace(Integer.valueOf(deviceQueryDetails.getSupportRemoteFace()));
                device.setSupportRemoteFinger(Integer.valueOf(deviceQueryDetails.getSupportRemoteFinger()));
                device.setSupportRemotePalmPrint(Integer.valueOf(deviceQueryDetails.getSupportRemotePalmPrint()));
                deviceService.updateById(device);
            }
        }
        logger.debug("DeviceStatusChangeProcessor：" + JSONObject.toJSON(deviceStatusChange));
        return new Message(ErrorCode.CODE_SUCCESS.getCode(), ErrorCode.CODE_SUCCESS.getMessage());
    }
}
