package com.zkteco.dbs.acc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.common.profile.Language;
import com.zkcloud.api.dbs.model.DoorPermissionGroupDeleteRequest;
import com.zkcloud.api.dbs.model.DoorPermissionGroupUpdateRequest;
import com.zkcloud.api.dbs.model.User;
import com.zkteco.dbs.acc.dao.AccAuthorityMapper;
import com.zkteco.dbs.acc.dto.AccAuthorityDoorDTO;
import com.zkteco.dbs.acc.dto.AccAuthorityEmployeeDTO;
import com.zkteco.dbs.acc.model.AccAuthority;
import com.zkteco.dbs.acc.model.AccAuthorityDoor;
import com.zkteco.dbs.acc.model.AccAuthorityEmployee;
import com.zkteco.dbs.acc.service.AccAuthorityDoorService;
import com.zkteco.dbs.acc.service.AccAuthorityEmployeeService;
import com.zkteco.dbs.acc.service.AccAuthorityService;
import com.zkteco.dbs.acc.vo.AccAuthorityDoorVO;
import com.zkteco.dbs.acc.vo.AccAuthorityEmployeeVO;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.utils.PagingUtil;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.device.service.DeviceEmployeeService;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
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
public class AccAuthorityServiceImpl extends ServiceImpl<AccAuthorityMapper, AccAuthority> implements AccAuthorityService {

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AccAuthorityDoorService accAuthorityDoorService;

    @Autowired
    private AccAuthorityEmployeeService accAuthorityEmployeeService;

    @Autowired
    private DeviceEmployeeService deviceEmployeeService;

    @Autowired
    private AccAuthorityService accAuthorityService;

    @Override
    public IPage<AccAuthority> pageList(BaseDTO<BaseQueryVO> dto) {
        BaseQueryVO queryVO = dto.getPayload();
        Page page = PagingUtil.initPage(queryVO);

        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        IPage<AccAuthority> pageList = this.page(page, new LambdaQueryWrapper<AccAuthority>().eq(AccAuthority::getCompanyId, companyId));
        pageList.getRecords().forEach(entity -> {
            String startTime = entity.getStartTime();
            String endTime = entity.getEndTime();
            if(StringUtils.isNotBlank(startTime)){
                int idx = startTime.indexOf("+");
                entity.setStartTime(startTime.substring(0,idx));
            }else{
                entity.setStartTime(null);
            }
            if(StringUtils.isNotBlank(endTime)){
                int idx = endTime.indexOf("+");
                entity.setEndTime(endTime.substring(0,idx));
            }else{
                entity.setEndTime(null);
            }

        });
        return pageList;
    }

    @Override
    public void save(BaseDTO<AccAuthority> dto) {
        // 获取本地时区
        String offset = getStandardOffset();

        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        AccAuthority entity = dto.getPayload();
        entity.setStartTime(StringUtils.isNotBlank(entity.getStartTime())?entity.getStartTime() + offset : null);
        entity.setEndTime(StringUtils.isNotBlank(entity.getEndTime())?entity.getEndTime() + offset : null);
        entity.setCompanyId(companyId);

        this.save(entity);

        // 调用SDK 保存门禁权限组
        User user = new User(company.getUserName(), company.getPassword());
        DoorPermissionGroupUpdateRequest doorPermissionGroupUpdateRequest =
                new DoorPermissionGroupUpdateRequest(entity.getGroupNum(), entity.getTimezoneNum(), entity.getStartTime(),
                        entity.getEndTime());
        doorPermissionGroupUpdateRequest.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message message = DBSApi.dbsClient.updateDoorPermissionGroup(doorPermissionGroupUpdateRequest);
        ResultUtil.handleDbsResultError(message);


    }

    @Override
    public void update(BaseDTO<AccAuthority> dto) {

        AccAuthority entity = dto.getPayload();

        // 获取本地时区
        String offset = getStandardOffset();

        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        entity.setCompanyId(companyId);
        entity.setStartTime(StringUtils.isNotBlank(entity.getStartTime())?entity.getStartTime() + offset : null);
        entity.setEndTime(StringUtils.isNotBlank(entity.getEndTime())?entity.getEndTime() + offset : null);

        this.updateById(entity);

        // 调用SDK 保存门禁权限组
        User user = new User(company.getUserName(), company.getPassword());
        DoorPermissionGroupUpdateRequest doorPermissionGroupUpdateRequest =
                new DoorPermissionGroupUpdateRequest(entity.getGroupNum(), entity.getTimezoneNum(), entity.getStartTime(),
                        entity.getEndTime());
        doorPermissionGroupUpdateRequest.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message message = DBSApi.dbsClient.updateDoorPermissionGroup(doorPermissionGroupUpdateRequest);
        ResultUtil.handleDbsResultError(message);

    }

    @Override
    public void remove(BaseDTO<AccAuthority> dto) {
        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        AccAuthority entity = dto.getPayload();
        Integer groupNum = entity.getGroupNum();
        this.removeById(groupNum);

        // 移除权限组和门关系，以及权限组和人员关系，  人和设备关系
        // 移除人和设备关系
        // 解绑人员和设备关系  查看此权限组绑定的门 设备
        List<AccAuthorityDoor> authorityDoors =
                accAuthorityDoorService.list(new LambdaQueryWrapper<AccAuthorityDoor>().eq(AccAuthorityDoor::getGroupNum,
                        groupNum));
        Set<String> sns = new HashSet<>();
        for (AccAuthorityDoor authorityDoor : authorityDoors) {
            sns.add(authorityDoor.getSn());
        }
        // 查找与权限组关联的员工
        List<AccAuthorityEmployee> accAuthorityEmployees =
                accAuthorityEmployeeService.list(new LambdaQueryWrapper<AccAuthorityEmployee>()
                        .eq(AccAuthorityEmployee::getGroupNum, groupNum));

        Set<String> employeeNos = new HashSet<>();
        for (AccAuthorityEmployee accAuthorityEmployee : accAuthorityEmployees) {
            employeeNos.add(accAuthorityEmployee.getEmployeeNo());
        }
        // 遍历删除员工与设备关系
        for (String sn : sns) {
            for (String employeeNo : employeeNos) {
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
        }

        // 移除权限组和门关系
        accAuthorityDoorService.remove(new LambdaQueryWrapper<AccAuthorityDoor>().eq(AccAuthorityDoor::getGroupNum,
                groupNum));

        // 移除权限组和人员关系
        accAuthorityEmployeeService.remove(new LambdaQueryWrapper<AccAuthorityEmployee>().eq(AccAuthorityEmployee::getGroupNum, groupNum));

        // 调用SDK 删除门禁权限组
        User user = new User(company.getUserName(), company.getPassword());
        DoorPermissionGroupDeleteRequest request = new DoorPermissionGroupDeleteRequest(groupNum);
        request.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message message = DBSApi.dbsClient.deleteDoorPermissionGroup(request);
        ResultUtil.handleDbsResultError(message);
    }

    @Override
    public List<AccAuthorityDoorVO> doorAllocatedList(BaseDTO<AccAuthority> dto) {
        // 权限组编号
        Integer groupNum = dto.getPayload().getGroupNum();

        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        List<AccAuthorityDoorVO> list = this.baseMapper.doorAllocatedList(groupNum, companyId);

        return list;
    }

    @Override
    public List<AccAuthorityDoorVO> doorUnassignedList(BaseDTO<AccAuthorityDoorDTO> dto) {

        AccAuthorityDoorDTO doorDTO = dto.getPayload();
        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        List<AccAuthorityDoorVO> list = this.baseMapper.doorUnassignedList(doorDTO, companyId);

        return list;
    }

    @Override
    public List<AccAuthorityEmployeeVO> employeeAllocatedList(BaseDTO<AccAuthorityEmployeeDTO> dto) {

        AccAuthorityEmployeeDTO employeeDTO = dto.getPayload();

        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        List<AccAuthorityEmployeeVO> list = this.baseMapper.employeeAllocatedList(employeeDTO, companyId);

        return list;
    }

    @Override
    public List<AccAuthorityEmployeeVO> employeeUnassignedList(BaseDTO<AccAuthorityEmployeeDTO> dto) {
        AccAuthorityEmployeeDTO employeeDTO = dto.getPayload();

        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        List<AccAuthorityEmployeeVO> list = this.baseMapper.employeeUnassignedList(employeeDTO, companyId);

        return list;
    }

    @Override
    public long getCountBySnAndEmployeeNoAndGroupNum(AccAuthorityEmployeeDTO dto) {

        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        dto.setCompanyId(company.getId());
        return this.baseMapper.getCountBySnAndEmployeeNoAndGroupNum(dto);
    }

    /**
     * 获取当前时区
     * @return
     */
    private String getStandardOffset() {
        String timezoneId = TimeZone.getDefault().getID();
        ZoneId zone = ZoneId.of(timezoneId);
        String standardOffset = zone.getRules().getStandardOffset(Instant.now()).getId();
        return standardOffset;
    }
}
