package com.zkteco.dbs.acc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.common.profile.Language;
import com.zkcloud.api.dbs.model.DoorPermissionGroupAddDoorDevice;
import com.zkcloud.api.dbs.model.DoorPermissionGroupAddDoorRequest;
import com.zkcloud.api.dbs.model.DoorPermissionGroupRemoveDoorRequest;
import com.zkcloud.api.dbs.model.User;
import com.zkteco.dbs.acc.dao.AccAuthorityDoorMapper;
import com.zkteco.dbs.acc.dto.AccAuthorityEmployeeDTO;
import com.zkteco.dbs.acc.dto.SaveAccAuthorityDoorDTO;
import com.zkteco.dbs.acc.model.AccAuthorityDoor;
import com.zkteco.dbs.acc.model.AccAuthorityEmployee;
import com.zkteco.dbs.acc.service.AccAuthorityDoorService;
import com.zkteco.dbs.acc.service.AccAuthorityEmployeeService;
import com.zkteco.dbs.acc.service.AccAuthorityService;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.device.model.DeviceEmployee;
import com.zkteco.dbs.device.service.DeviceEmployeeService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName AccAuthorityDoorServiceImpl
 * @Description: 服务实现类
 * @Author able.lee
 * @Date 2020/11/27 15:50
 * @Since v1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccAuthorityDoorServiceImpl extends ServiceImpl<AccAuthorityDoorMapper, AccAuthorityDoor> implements AccAuthorityDoorService {

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private AccAuthorityEmployeeService accAuthorityEmployeeService;

    @Autowired
    private DeviceEmployeeService deviceEmployeeService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AccAuthorityService accAuthorityService;

    @Override
    public void removeDoorAuthority(BaseDTO<AccAuthorityDoor> dto) {
        AccAuthorityDoor entity = dto.getPayload();

        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        this.remove(new LambdaQueryWrapper<AccAuthorityDoor>()
                .eq(AccAuthorityDoor::getDoorNum, entity.getDoorNum())
                .eq(AccAuthorityDoor::getGroupNum, entity.getGroupNum())
                .eq(AccAuthorityDoor::getSn, entity.getSn()));

        // 若此门未在其他权限组绑定相同的人员，则解绑人员和此门对应的设备关系
        // 获取权限组关联了哪些人
        List<AccAuthorityEmployee> list = accAuthorityEmployeeService
                .list(new LambdaQueryWrapper<AccAuthorityEmployee>().eq(AccAuthorityEmployee::getGroupNum, entity.getGroupNum()));
        //该设备上 所有权限组 都没有该人员的信息，从该设备上移除该人员
        list.stream().forEach(accAuthorityEmployee -> {
            AccAuthorityEmployeeDTO accAuthorityEmployeeDTO = new AccAuthorityEmployeeDTO();
            accAuthorityEmployeeDTO.setEmployeeNo(accAuthorityEmployee.getEmployeeNo());
            accAuthorityEmployeeDTO.setSn(entity.getSn());
            accAuthorityEmployeeDTO.setGroupNum(entity.getGroupNum());
            long count = accAuthorityService.getCountBySnAndEmployeeNoAndGroupNum(accAuthorityEmployeeDTO);
            if (count == 0) {
                //该设备上 所有权限组 都没有该人员的信息，从该设备上移除该人员
                deviceEmployeeService.removeBySnAndEmployeeNo(entity.getSn(), accAuthorityEmployee.getEmployeeNo());
            }
        });
        // 调用sdk移除门
        List<Integer> doorNums = new ArrayList<>();
        doorNums.add(entity.getDoorNum());
        User user = new User(company.getUserName(), company.getPassword());
        DoorPermissionGroupRemoveDoorRequest doorPermissionGroupRemoveDoorRequest =
                new DoorPermissionGroupRemoveDoorRequest(entity.getGroupNum(), entity.getSn(), doorNums);
        doorPermissionGroupRemoveDoorRequest.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message message =  DBSApi.dbsClient.doorPermissionGroupRemoveDoor(doorPermissionGroupRemoveDoorRequest);
        ResultUtil.handleDbsResultError(message);
    }

    @Override
    public void saveDoorAuthority(BaseDTO<SaveAccAuthorityDoorDTO> dto) {
        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        SaveAccAuthorityDoorDTO saveDto = dto.getPayload();

        Integer groupNum = saveDto.getGroupNum();

        List<AccAuthorityDoor> doors = saveDto.getDoors();

        //去重sn
        Set<String> sns = new HashSet<>();
        for (AccAuthorityDoor door : doors) {
            door.setGroupNum(groupNum);
            this.save(door);

            sns.add(door.getSn());
        }

        // 维护员工与设备的关系-权限组与员工关系
        List<AccAuthorityEmployee> authorityEmployees =
                accAuthorityEmployeeService
                        .list(new LambdaQueryWrapper<AccAuthorityEmployee>().eq(AccAuthorityEmployee::getGroupNum, groupNum));

        // 调用SDK 分配门
        User user = new User(company.getUserName(), company.getPassword());
        // 构建sdk请求数据结构
        List<DoorPermissionGroupAddDoorDevice> devices = new ArrayList<>();
        for (String sn : sns) {
            List<Integer> doorNums = new ArrayList<>();
            for (AccAuthorityDoor door : doors) {
                if (sn.equals(door.getSn())) {
                    doorNums.add(door.getDoorNum());
                }
            }
            DoorPermissionGroupAddDoorDevice doorDevice = new DoorPermissionGroupAddDoorDevice(sn, doorNums);
            devices.add(doorDevice);

            for (AccAuthorityEmployee authorityEmployee : authorityEmployees) {
                // 绑定设备与员工关系
                // 若设备与人员关系已经存在，则不再保存
                List<DeviceEmployee> list =
                        deviceEmployeeService.list(new LambdaQueryWrapper<DeviceEmployee>().eq(DeviceEmployee::getSn, sn)
                                .eq(DeviceEmployee::getEmployeeNo, authorityEmployee.getEmployeeNo()));
                if (CollectionUtil.isEmpty(list)) {
                    DeviceEmployee deviceEmployee = new DeviceEmployee();
                    deviceEmployee.setCompanyId(companyId);
                    deviceEmployee.setEmployeeNo(authorityEmployee.getEmployeeNo());
                    deviceEmployee.setSn(sn);
                    deviceEmployeeService.save(deviceEmployee);
                }

            }

        }

        DoorPermissionGroupAddDoorRequest doorPermissionGroupAddDoorRequest =
                new DoorPermissionGroupAddDoorRequest(groupNum, devices);
        doorPermissionGroupAddDoorRequest.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message message = DBSApi.dbsClient.doorPermissionGroupAddDoor(doorPermissionGroupAddDoorRequest);
        ResultUtil.handleDbsResultError(message);

    }
}
