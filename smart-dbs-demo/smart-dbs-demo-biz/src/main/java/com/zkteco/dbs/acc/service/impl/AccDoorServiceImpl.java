package com.zkteco.dbs.acc.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.common.profile.Language;
import com.zkcloud.api.dbs.model.DoorPasswordDeleteRequest;
import com.zkcloud.api.dbs.model.DoorPasswordOpenRequest;
import com.zkcloud.api.dbs.model.DoorPasswordUpdateRequest;
import com.zkcloud.api.dbs.model.DoorQueryRequest;
import com.zkcloud.api.dbs.model.DoorQueryResponse;
import com.zkcloud.api.dbs.model.DoorUpdateRequest;
import com.zkcloud.api.dbs.model.OpenDoorRequest;
import com.zkcloud.api.dbs.model.User;
import com.zkteco.dbs.acc.dao.AccDoorMapper;
import com.zkteco.dbs.acc.dto.AccDoorDTO;
import com.zkteco.dbs.acc.model.AccAuthorityDoor;
import com.zkteco.dbs.acc.model.AccDoor;
import com.zkteco.dbs.acc.service.AccAuthorityDoorService;
import com.zkteco.dbs.acc.service.AccDoorService;
import com.zkteco.dbs.acc.vo.AccDoorVO;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.common.utils.PBKDF2Utils;
import com.zkteco.dbs.common.utils.PagingUtil;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.device.dto.DeviceDTO;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AccDoorServiceImpl extends ServiceImpl<AccDoorMapper, AccDoor> implements AccDoorService {

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AccAuthorityDoorService accAuthorityDoorService;

    @Override
    public void saveFromSdk(String sn) {

        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        //调用sdk 查询门信息
        DoorQueryRequest doorQueryRequest = new DoorQueryRequest(sn, null);
        User user = new User(company.getUserName(), company.getPassword());
        doorQueryRequest.setApiUser(user);
        Message<List<DoorQueryResponse>> response = DBSApi.dbsClient.queryDoor(doorQueryRequest);
        // 请求失败，抛出异常
        ResultUtil.handleDbsResultError(response);

        String companyId = company.getId();

        // 门信息写入本地表
        List<DoorQueryResponse> payload = response.getPayload().getResults();
        payload.forEach(door -> {
            AccDoor accDoor = this.getOne(new LambdaQueryWrapper<AccDoor>()
                    .eq(AccDoor::getSn,sn)
                    .eq(AccDoor::getDoorNum,door.getDoorNum()),false);
            if(ObjectUtil.isNull(accDoor)){
                AccDoor entity = new AccDoor();
                entity.setSn(sn);
                entity.setDoorName(door.getDoorName());
                entity.setDoorNum(door.getDoorNum());
                entity.setCompanyId(companyId);
                this.save(entity);
            }

        });
    }

    @Override
    public IPage<AccDoorVO> pageList(BaseDTO<DeviceDTO> dto) {
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        String companyId = company.getId();

        DeviceDTO deviceDTO = dto.getPayload();
        Page page = PagingUtil.initPage(deviceDTO);
        // 分页查询
        IPage<AccDoorVO> res = this.baseMapper.pageList(page, deviceDTO, companyId);

        return res;
    }

    @Override
    public void update(BaseDTO<DeviceDTO> dto) {

        DeviceDTO deviceDTO = dto.getPayload();
        AccDoor accDoor = this.getById(deviceDTO.getId());

        ResultUtil.handldNullError(accDoor, "E24", dto.getLang());

        accDoor.setDoorName(deviceDTO.getDoorName());

        this.updateById(accDoor);

        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        // 调用SDK 修改门信息
        User user = new User(company.getUserName(), company.getPassword());
        DoorUpdateRequest doorUpdateRequest = new DoorUpdateRequest(accDoor.getSn(), accDoor.getDoorNum(),
                accDoor.getDoorName());
        doorUpdateRequest.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message response = DBSApi.dbsClient.updateDoor(doorUpdateRequest);
        ResultUtil.handleDbsResultError(response);

    }

    @Override
    public void openDoor(BaseDTO<AccDoorDTO> dto) {
        AccDoorDTO accDoorDTO = dto.getPayload();
        AccDoor accDoor = this.getById(accDoorDTO.getId());

        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        ResultUtil.handldNullError(accDoor, "E24", dto.getLang());
        // 调用SDK 远程开门
        User apiUser = new User(company.getUserName(), company.getPassword());
        OpenDoorRequest openDoorRequest = new OpenDoorRequest(accDoor.getSn());
        openDoorRequest.setDoorNum(accDoor.getDoorNum().toString());
        openDoorRequest.setEmployeeNo(SysConstants.DEMO_ADMIN);
        openDoorRequest.setApiUser(apiUser);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message response = DBSApi.dbsClient.openDoor(openDoorRequest);
        ResultUtil.handleDbsResultError(response);
    }

    @Override
    public void openDoorByPassword(BaseDTO<AccDoorDTO> dto) {
        AccDoorDTO accDoorDTO = dto.getPayload();
        //参数校验
        ResultUtil.handldNullError(accDoorDTO.getPassword(), "E31", dto.getLang());

        //判断门是否存在
        AccDoor accDoor = this.getById(accDoorDTO.getId());
        ResultUtil.handldNullError(accDoor, "E24", dto.getLang());

        //判断密码是否正确
        if (!accDoor.getPassword().equals(accDoorDTO.getPassword())) {
            ResultUtil.handldErrorInfo("E32", dto.getLang());
        }
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        // 调用SDK 远程密码开门
        User apiUser = new User(company.getUserName(), company.getPassword());
        DoorPasswordOpenRequest doorPasswordOpenRequest = new DoorPasswordOpenRequest(accDoor.getSn(), accDoor.getDoorNum(),
                SysConstants.DEMO_ADMIN,
                accDoorDTO.getPassword());
        doorPasswordOpenRequest.setApiUser(apiUser);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message response = DBSApi.dbsClient.openDoorPassword(doorPasswordOpenRequest);
        ResultUtil.handleDbsResultError(response);
    }

    @Override
    public void saveDoorPassword(BaseDTO<AccDoorDTO> dto) {

        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        AccDoorDTO accDoorDTO = dto.getPayload();
        //参数校验
        ResultUtil.handldNullError(accDoorDTO.getPassword(), "E31", dto.getLang());
        ResultUtil.handldNullError(accDoorDTO.getPasswordType(), "E30", dto.getLang());

        //判断门是否存在
        AccDoor accDoor = this.getById(accDoorDTO.getId());
        ResultUtil.handldNullError(accDoor, "E24", dto.getLang());

        // 密码加密
        String password = PBKDF2Utils.getEncryptedPassword(accDoorDTO.getPassword(), StringUtils.isNotBlank(accDoorDTO.getPasswordSalt())
                ? accDoorDTO.getPasswordSalt() : "");
        // 调用SDK 下发开门密码
        User apiUser = new User(company.getUserName(), company.getPassword());
        DoorPasswordUpdateRequest doorPasswordUpdateRequest = new DoorPasswordUpdateRequest(accDoor.getSn(),
                SysConstants.DEMO_ADMIN, password);

        if (SysConstants.DOOR_TEMPORARY_PASSWORD.equals(accDoorDTO.getPasswordType())) {
            ResultUtil.handldNullError(accDoorDTO.getMinutes(), "E28", dto.getLang());
            // 填充临时密码所需数据
            Long startTime = System.currentTimeMillis() / 1000;
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.MINUTE, accDoorDTO.getMinutes());
            Long endTime = nowTime.getTimeInMillis() / 1000;
            doorPasswordUpdateRequest.setStartTime(startTime);
            doorPasswordUpdateRequest.setEndTime(endTime);
            doorPasswordUpdateRequest.setPasswordType(SysConstants.DOOR_TEMPORARY_PASSWORD);
        } else {
            // demo端保存密码
            accDoor.setPassword(accDoorDTO.getPassword());
            this.updateById(accDoor);
        }
        doorPasswordUpdateRequest.setApiUser(apiUser);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message response = DBSApi.dbsClient.updateDoorPassword(doorPasswordUpdateRequest);
        ResultUtil.handleDbsResultError(response);
    }

    @Override
    public void removeDoorPassword(BaseDTO<AccDoorDTO> dto) {
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        AccDoorDTO accDoorDTO = dto.getPayload();
        AccDoor accDoor = this.getById(accDoorDTO.getId());
        ResultUtil.handldNullError(accDoor, "E24", dto.getLang());
        // 将demo端密码置空
        accDoor.setPassword(null);
        this.updateById(accDoor);
        // 调用SDK 删除开门密码
        User apiUser = new User(company.getUserName(), company.getPassword());
        DoorPasswordDeleteRequest doorPasswordDeleteRequest = new DoorPasswordDeleteRequest(accDoor.getSn(), accDoor.getDoorNum(),
                SysConstants.DEMO_ADMIN, null);
        doorPasswordDeleteRequest.setApiUser(apiUser);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message response = DBSApi.dbsClient.deleteDoorPassword(doorPasswordDeleteRequest);
        ResultUtil.handleDbsResultError(response);
    }

    @Override
    public void removeBySn(String companyId, String sn) {
        List<AccDoor> list =
                this.baseMapper.selectList(new LambdaQueryWrapper<AccDoor>().eq(AccDoor::getCompanyId, companyId).eq(AccDoor::getSn, sn));
        if (ObjectUtil.isNotEmpty(list)) {
            list.stream().forEach(accDoor ->
                    accAuthorityDoorService.getBaseMapper()
                            .delete(new LambdaQueryWrapper<AccAuthorityDoor>().eq(AccAuthorityDoor::getDoorId,
                                    accDoor.getId()))
            );
            List<String> idList = list.stream().map(AccDoor::getId).collect(Collectors.toList());
            this.baseMapper.deleteBatchIds(idList);
        }

    }

}
