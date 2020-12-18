package com.zkteco.dbs.acc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkteco.dbs.acc.dao.AccTransactionLogMapper;
import com.zkteco.dbs.acc.dto.AccTransactionLogDTO;
import com.zkteco.dbs.acc.model.AccTransactionLog;
import com.zkteco.dbs.acc.service.AccTransactionLogService;
import com.zkteco.dbs.acc.vo.AccTransactionLogExcel;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.EventCodeEnum;
import com.zkteco.dbs.common.tool.constants.InOutTypeEnum;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.common.tool.constants.VerifiedTypeEnum;
import com.zkteco.dbs.common.utils.EasyExcelUtils;
import com.zkteco.dbs.common.utils.I18nUtils;
import com.zkteco.dbs.common.utils.ModelUtils;
import com.zkteco.dbs.common.utils.PagingUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName AccTransactionLogServiceImpl
 * @Description: 门禁事件记录 服务实现类
 * @Author able.lee
 * @Date 2020/11/25 17:34
 * @Since v1.0.0
 */
@Service
public class AccTransactionLogServiceImpl extends ServiceImpl<AccTransactionLogMapper, AccTransactionLog> implements
        AccTransactionLogService {

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private CompanyService companyService;


    @Override
    public IPage<AccTransactionLog> pageList(BaseDTO<AccTransactionLogDTO> dto) {

        AccTransactionLogDTO logDTO = dto.getPayload();

        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();

        Page page = PagingUtil.initPage(logDTO);

        LambdaQueryWrapper<AccTransactionLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccTransactionLog::getCompanyId, companyId)
                .eq(StringUtils.isNotBlank(logDTO.getSn()), AccTransactionLog::getSn, logDTO.getSn())
                .eq(StringUtils.isNotBlank(logDTO.getDoorNum()), AccTransactionLog::getDoorNum, logDTO.getDoorNum())
                .gt(logDTO.getStartTime() != null, AccTransactionLog::getTimeStamp, logDTO.getStartTime())
                .lt(logDTO.getEndTime() != null, AccTransactionLog::getTimeStamp, logDTO.getEndTime())
                .orderByDesc(AccTransactionLog::getTime);

        IPage<AccTransactionLog> pageList = this.baseMapper.selectPage(page, wrapper);
        pageList.getRecords().stream().peek(accTransactionLog -> {
            accTransactionLog.setEventCodeI18n(I18nUtils.getValue(
                    Optional.ofNullable(EventCodeEnum.getByVal(accTransactionLog.getEventCode()))
                            .map(EventCodeEnum::getI18nKey).orElse(
                            SysConstants.UNKNOWN), dto.getLang()));
            accTransactionLog.setVerifiedI18n(I18nUtils.getValue(
                    Optional.ofNullable(VerifiedTypeEnum.getByVal(accTransactionLog.getVerified()))
                            .map(VerifiedTypeEnum::getI18nKey).orElse(
                            SysConstants.UNKNOWN), dto.getLang()));
        }).collect(Collectors.toList());

        return pageList;
    }

    @Override
    public void exportExcel(BaseDTO<AccTransactionLogDTO> dto, HttpServletResponse response) {
        AccTransactionLogDTO logDTO = dto.getPayload();

        // 获取公司id
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        LambdaQueryWrapper<AccTransactionLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccTransactionLog::getCompanyId, company.getId())
                .eq(StringUtils.isNotBlank(logDTO.getSn()), AccTransactionLog::getSn, logDTO.getSn())
                .eq(StringUtils.isNotBlank(logDTO.getDoorNum()), AccTransactionLog::getDoorNum, logDTO.getDoorNum())
                .gt(logDTO.getStartTime() != null, AccTransactionLog::getTimeStamp, logDTO.getStartTime())
                .lt(logDTO.getEndTime() != null, AccTransactionLog::getTimeStamp, logDTO.getEndTime())
                .orderByDesc(AccTransactionLog::getTime);
        List<AccTransactionLog> list = this.baseMapper.selectList(wrapper);
        List<AccTransactionLogExcel> excelList = new ArrayList<>();
        list.stream().forEach(accTransactionLog -> {
            AccTransactionLogExcel accTransactionLogExcel = new AccTransactionLogExcel();
            ModelUtils.copyPropertiesIgnoreNull(accTransactionLog, accTransactionLogExcel);
            accTransactionLogExcel.setEventCode(I18nUtils.getValue(
                    Optional.ofNullable(EventCodeEnum.getByVal(accTransactionLog.getEventCode()))
                            .map(EventCodeEnum::getI18nKey).orElse(
                            SysConstants.UNKNOWN), dto.getLang()));
            accTransactionLogExcel.setVerified(I18nUtils.getValue(
                    Optional.ofNullable(VerifiedTypeEnum.getByVal(accTransactionLog.getVerified()))
                            .map(VerifiedTypeEnum::getI18nKey).orElse(
                            SysConstants.UNKNOWN), dto.getLang()));
            accTransactionLogExcel.setInOutType(I18nUtils.getValue(
                    Optional.ofNullable(InOutTypeEnum.getByVal(accTransactionLog.getInOutType()))
                            .map(InOutTypeEnum::getI18nKey).orElse(
                            SysConstants.UNKNOWN), dto.getLang()));
            excelList.add(accTransactionLogExcel);
        });
        EasyExcelUtils.exportExcel(response, AccTransactionLogExcel.class, excelList, dto.getLang());
    }
}
