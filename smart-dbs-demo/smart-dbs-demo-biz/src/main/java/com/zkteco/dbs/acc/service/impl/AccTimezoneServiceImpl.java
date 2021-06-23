package com.zkteco.dbs.acc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.common.profile.Language;
import com.zkcloud.api.dbs.model.DoorTimeZoneDeleteRequest;
import com.zkcloud.api.dbs.model.DoorTimeZoneDetail;
import com.zkcloud.api.dbs.model.DoorTimeZoneUpdateRequest;
import com.zkcloud.api.dbs.model.User;
import com.zkteco.dbs.acc.dao.AccTimezoneMapper;
import com.zkteco.dbs.acc.model.AccTimezone;
import com.zkteco.dbs.acc.model.AccTimezoneDetail;
import com.zkteco.dbs.acc.service.AccTimezoneDetailService;
import com.zkteco.dbs.acc.service.AccTimezoneService;
import com.zkteco.dbs.acc.vo.AccTimezoneDetailVO;
import com.zkteco.dbs.acc.vo.AccTimezoneVO;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.utils.PagingUtil;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import java.util.ArrayList;
import java.util.List;

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
public class AccTimezoneServiceImpl extends ServiceImpl<AccTimezoneMapper, AccTimezone> implements AccTimezoneService {

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AccTimezoneDetailService accTimezoneDetailService;

    @Override
    public IPage<AccTimezoneVO> pageList(BaseDTO<BaseQueryVO> dto) {
        BaseQueryVO queryVO = dto.getPayload();
        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        Page page = PagingUtil.initPage(queryVO);

        IPage<AccTimezoneVO> pageList = this.baseMapper.pageList(page, companyId);

        pageList.getRecords().forEach(record -> {
            List<AccTimezoneDetailVO> details = new ArrayList<>();
            LambdaQueryWrapper<AccTimezoneDetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(AccTimezoneDetail::getTimezoneNum, record.getTimezoneNum());
            List<AccTimezoneDetail> list = accTimezoneDetailService.list(wrapper);
            for (AccTimezoneDetail detail : list) {
                AccTimezoneDetailVO entity = new AccTimezoneDetailVO();
                entity.setWeek(detail.getWeek());
                entity.setStartTime(detail.getStartTime());
                entity.setEndTime(detail.getEndTime());
                details.add(entity);
            }
            record.setDetail(details);
        });

        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void timezoneSetUp(BaseDTO<AccTimezoneVO> dto) {
        AccTimezoneVO accTimezoneVO = dto.getPayload();

        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        //更新时间段前，先删除已存在的
        LambdaQueryWrapper<AccTimezoneDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccTimezoneDetail::getTimezoneNum, accTimezoneVO.getTimezoneNum());
        accTimezoneDetailService.remove(wrapper);

        Integer timezoneNum = Integer.parseInt(accTimezoneVO.getTimezoneNum());
        String timezoneName = accTimezoneVO.getTimezoneName();
        AccTimezone accTimezone = new AccTimezone();
        accTimezone.setTimezoneNum(timezoneNum);
        accTimezone.setTimezoneName(timezoneName);
        this.updateById(accTimezone);
        // 保存
        accTimezoneVO.getDetail().forEach(detail -> {
            if(StringUtils.isNotBlank(detail.getStartTime()) || StringUtils.isNotBlank(detail.getEndTime())){
                AccTimezoneDetail newEntity = new AccTimezoneDetail();
                newEntity.setTimezoneNum(timezoneNum);
                newEntity.setWeek(detail.getWeek());
                newEntity.setStartTime(detail.getStartTime());
                newEntity.setEndTime(detail.getEndTime());

                accTimezoneDetailService.save(newEntity);
            }

        });


        //  调用sdk 更新门禁时间段
        User user = new User(company.getUserName(), company.getPassword());
        List<DoorTimeZoneDetail> details = new ArrayList<>();
        accTimezoneVO.getDetail().forEach(detail -> {
            if(StringUtils.isNotBlank(detail.getStartTime()) || StringUtils.isNotBlank(detail.getEndTime())){
                DoorTimeZoneDetail o = new DoorTimeZoneDetail(detail.getWeek(), detail.getStartTime(), detail.getEndTime());
                details.add(o);
            }

        });
        DoorTimeZoneUpdateRequest doorTimeZoneUpdateRequest = new DoorTimeZoneUpdateRequest(timezoneNum, timezoneName, details);
        doorTimeZoneUpdateRequest.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message message = DBSApi.dbsClient.updateDoorTimeZone(doorTimeZoneUpdateRequest);
        ResultUtil.handleDbsResultError(message);
    }

    @Override
    public void update(BaseDTO<AccTimezone> dto) {

        AccTimezone accTimezone = dto.getPayload();

        AccTimezone oldAccTimezone = this.getById(accTimezone.getTimezoneNum());
        ResultUtil.handleNullError(oldAccTimezone, "E26", dto.getLang());

        oldAccTimezone.setTimezoneName(accTimezone.getTimezoneName());
        this.updateById(oldAccTimezone);

        // 调用sdk更新门信息
        LambdaQueryWrapper<AccTimezoneDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AccTimezoneDetail::getTimezoneNum, accTimezone.getTimezoneNum());
        List<AccTimezoneDetail> list = accTimezoneDetailService.list(queryWrapper);
        List<DoorTimeZoneDetail> details = new ArrayList<>();
        for (AccTimezoneDetail detail : list) {
            DoorTimeZoneDetail entity = new DoorTimeZoneDetail(detail.getWeek(), detail.getStartTime(), detail.getEndTime());
            details.add(entity);
        }
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        DoorTimeZoneUpdateRequest doorTimeZoneUpdateRequest = new DoorTimeZoneUpdateRequest(accTimezone.getTimezoneNum(),
                accTimezone.getTimezoneName(), details);
        User user = new User(company.getUserName(), company.getPassword());
        doorTimeZoneUpdateRequest.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message message = DBSApi.dbsClient.updateDoorTimeZone(doorTimeZoneUpdateRequest);

        ResultUtil.handleDbsResultError(message);

    }

    @Override
    public void remove(BaseDTO<AccTimezone> dto) {
        AccTimezone accTimezone = dto.getPayload();

        Integer timezoneNum = accTimezone.getTimezoneNum();

        this.baseMapper.deleteById(timezoneNum);

        // 删除时间段详情
        accTimezoneDetailService.remove(new LambdaQueryWrapper<AccTimezoneDetail>().eq(AccTimezoneDetail::getTimezoneNum,
                timezoneNum));

        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        // 调用SDK 删除门禁时间段
        User user = new User(company.getUserName(), company.getPassword());
        DoorTimeZoneDeleteRequest deleteRequest = new DoorTimeZoneDeleteRequest(timezoneNum);
        deleteRequest.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message message = DBSApi.dbsClient.deleteDoorTimeZone(deleteRequest);

        ResultUtil.handleDbsResultError(message);

    }

    @Override
    public void add(BaseDTO<AccTimezoneVO> vo) {
        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        // 保存门禁时间段
        AccTimezoneVO accTimezoneVO = vo.getPayload();
        AccTimezone accTimezone = new AccTimezone();
        accTimezone.setTimezoneName(accTimezoneVO.getTimezoneName());
        accTimezone.setCompanyId(companyId);
        this.save(accTimezone);

        // 保存门禁时间段详情
        List<AccTimezoneDetailVO> list = accTimezoneVO.getDetail();
        Integer timezoneNum = accTimezone.getTimezoneNum();

        // SDK用
        List<DoorTimeZoneDetail> details = new ArrayList<>();

        for (AccTimezoneDetailVO detailVO : list) {
            if(StringUtils.isNotBlank(detailVO.getStartTime()) || StringUtils.isNotBlank(detailVO.getEndTime())){
                AccTimezoneDetail detail = new AccTimezoneDetail();
                detail.setWeek(detailVO.getWeek());
                detail.setStartTime(detailVO.getStartTime());
                detail.setEndTime(detailVO.getEndTime());
                detail.setTimezoneNum(timezoneNum);

                accTimezoneDetailService.save(detail);

                DoorTimeZoneDetail entity = new DoorTimeZoneDetail(detail.getWeek(), detail.getStartTime(), detail.getEndTime());
                details.add(entity);
            }

        }

        // 调用SDK 增加门禁时间段
        User user = new User(company.getUserName(), company.getPassword());
        DoorTimeZoneUpdateRequest doorTimeZoneUpdateRequest = new DoorTimeZoneUpdateRequest(accTimezone.getTimezoneNum(),
                accTimezone.getTimezoneName(),details);
        doorTimeZoneUpdateRequest.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(vo.getLang()));
        Message message = DBSApi.dbsClient.updateDoorTimeZone(doorTimeZoneUpdateRequest);

        ResultUtil.handleDbsResultError(message);


    }

    @Override
    public List<AccTimezone> selectList(BaseDTO<BaseQueryVO> dto) {

        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        List<AccTimezone> list = this.list(new QueryWrapper<AccTimezone>().eq("company_id", companyId));

        return list;
    }
}
