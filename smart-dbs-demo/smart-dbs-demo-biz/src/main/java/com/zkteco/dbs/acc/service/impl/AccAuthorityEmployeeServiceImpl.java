package com.zkteco.dbs.acc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.common.profile.Language;
import com.zkcloud.api.dbs.model.DoorPermissionGroupAddEmployeeRequest;
import com.zkcloud.api.dbs.model.DoorPermissionGroupRemoveEmployeeRequest;
import com.zkcloud.api.dbs.model.User;
import com.zkteco.dbs.acc.dao.AccAuthorityEmployeeMapper;
import com.zkteco.dbs.acc.dto.AccAuthorityEmployeeDTO;
import com.zkteco.dbs.acc.dto.SaveAccAuthorityEmployeeDTO;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zkteco.dbs.device.model.DeviceEmployee;
import com.zkteco.dbs.device.service.DeviceEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author able.lee
 * @since 2020-11-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccAuthorityEmployeeServiceImpl extends ServiceImpl<AccAuthorityEmployeeMapper, AccAuthorityEmployee> implements
        AccAuthorityEmployeeService {

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AccAuthorityDoorService accAuthorityDoorService;

    @Autowired
    private DeviceEmployeeService deviceEmployeeService;

    @Autowired
    private AccAuthorityService accAuthorityService;


    @Override
    public void saveEmployeeAuthority(BaseDTO<SaveAccAuthorityEmployeeDTO> dto) {

        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        SaveAccAuthorityEmployeeDTO saveAccAuthorityEmployeeDTO = dto.getPayload();

        Integer groupNum = saveAccAuthorityEmployeeDTO.getGroupNum();

        List<AccAuthorityEmployee> employees = saveAccAuthorityEmployeeDTO.getEmployees();

        List<String> employeeNos = new ArrayList<>();
        for (AccAuthorityEmployee accAuthorityEmployee : employees) {

            accAuthorityEmployee.setGroupNum(groupNum);

            this.save(accAuthorityEmployee);

            employeeNos.add(accAuthorityEmployee.getEmployeeNo());

        }

        // 绑定员工，查看此权限组绑定的门 设备
        List<AccAuthorityDoor> authorityDoors =
                accAuthorityDoorService.list(new LambdaQueryWrapper<AccAuthorityDoor>().eq(AccAuthorityDoor::getGroupNum,
                        groupNum));
        Set<String> sns = new HashSet<>();
        for (AccAuthorityDoor authorityDoor : authorityDoors) {
            sns.add(authorityDoor.getSn());
        }

        for (String sn : sns) {
            for (String employeeNo : employeeNos) {
                // 若设备与人员关系已经存在，则不再保存
                List<DeviceEmployee> list =
                        deviceEmployeeService.list(new LambdaQueryWrapper<DeviceEmployee>().eq(DeviceEmployee::getSn,sn).eq(DeviceEmployee::getEmployeeNo,employeeNo));
                if(CollectionUtil.isEmpty(list)){
                    DeviceEmployee deviceEmployee = new DeviceEmployee();
                    deviceEmployee.setSn(sn);
                    deviceEmployee.setCompanyId(companyId);
                    deviceEmployee.setEmployeeNo(employeeNo);
                    deviceEmployeeService.save(deviceEmployee);
                }

            }
        }

        //调用SDK分配人员
        User user = new User(company.getUserName(), company.getPassword());
        DoorPermissionGroupAddEmployeeRequest doorPermissionGroupAddEmployeeRequest =
                new DoorPermissionGroupAddEmployeeRequest(groupNum, employeeNos);
        doorPermissionGroupAddEmployeeRequest.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message message = DBSApi.dbsClient.doorPermissionGroupAddEmployee(doorPermissionGroupAddEmployeeRequest);
        ResultUtil.handleDbsResultError(message);

    }


    @Override
    public void removeEmployeeAuthority(BaseDTO<AccAuthorityEmployeeDTO> dto) {
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        AccAuthorityEmployeeDTO employeeDTO = dto.getPayload();

        Integer groupNum = employeeDTO.getGroupNum();
        String employeeNo = employeeDTO.getEmployeeNo();

        this.remove(new LambdaQueryWrapper<AccAuthorityEmployee>()
                .eq(AccAuthorityEmployee::getGroupNum, groupNum)
                .eq(AccAuthorityEmployee::getEmployeeNo, employeeNo)
        );

        // 解绑人员和设备关系  查看此权限组绑定的门 设备
        List<AccAuthorityDoor> authorityDoors =
                accAuthorityDoorService.list(new LambdaQueryWrapper<AccAuthorityDoor>().eq(AccAuthorityDoor::getGroupNum,
                        groupNum));

        Set<String> sns = new HashSet<>();
        for (AccAuthorityDoor authorityDoor : authorityDoors) {
            sns.add(authorityDoor.getSn());
        }

        for (String sn : sns) {
            AccAuthorityEmployeeDTO accAuthorityEmployeeDTO = new AccAuthorityEmployeeDTO();
            accAuthorityEmployeeDTO.setEmployeeNo(employeeNo);
            accAuthorityEmployeeDTO.setSn(sn);
            accAuthorityEmployeeDTO.setGroupNum(groupNum);
            long count = accAuthorityService.getCountBySnAndEmployeeNoAndGroupNum(accAuthorityEmployeeDTO);
            if (count == 0) {
                //该设备上 所有权限组 都没有该人员的信息，从该设备上移除该人员
                deviceEmployeeService.removeBySnAndEmployeeNo(sn, employeeNo);
            }
        }

        // 调用SDK 移除人
        List<String> employeeNos = new ArrayList<>();
        employeeNos.add(employeeNo);
        User user = new User(company.getUserName(), company.getPassword());
        DoorPermissionGroupRemoveEmployeeRequest request = new DoorPermissionGroupRemoveEmployeeRequest(groupNum, employeeNos);
        request.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message message = DBSApi.dbsClient.doorPermissionGroupRemoveEmployee(request);
        ResultUtil.handleDbsResultError(message);


    }

    @Override
    public void removeByEmployeeNo(String employeeNo) {
        this.remove(new LambdaQueryWrapper<AccAuthorityEmployee>()
                .eq(AccAuthorityEmployee::getEmployeeNo,employeeNo));
    }
}
