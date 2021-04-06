/*
 * File Name: IccPersonVerifyRecordServiceImpl
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.icc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.MaskStatusEnum;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.common.tool.constants.VerifiedTypeEnum;
import com.zkteco.dbs.common.tool.constants.VerifyStatusEnum;
import com.zkteco.dbs.common.utils.EasyExcelUtils;
import com.zkteco.dbs.common.utils.I18nUtils;
import com.zkteco.dbs.common.utils.PagingUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.icc.dao.IccPersonVerifyRecordMapper;
import com.zkteco.dbs.icc.dto.IccPersonVerifyRecordDTO;
import com.zkteco.dbs.icc.model.IccPersonVerifyRecord;
import com.zkteco.dbs.icc.service.IccPersonVerifyRecordService;
import com.zkteco.dbs.icc.vo.IccPersonVerifyRecordExcel;
import com.zkteco.dbs.icc.vo.IccPersonVerifyRecordVO;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * IccPersonVerifyRecordServiceImpl
 *
 * @author sheldon.wu
 * @date 2021/2/25 15:38
 * @since 1.0.0
 */
@Service
public class IccPersonVerifyRecordServiceImpl extends ServiceImpl<IccPersonVerifyRecordMapper, IccPersonVerifyRecord> implements
        IccPersonVerifyRecordService {

    @Autowired
    private DbsConfig dbsConfig;
    @Autowired
    private CompanyService companyService;

    @Override
    public IPage<IccPersonVerifyRecordVO> pageList(BaseDTO<IccPersonVerifyRecordDTO> dto) {
        IccPersonVerifyRecordDTO iccPersonVerifyRecordDTO = dto.getPayload();
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        iccPersonVerifyRecordDTO.setCompanyId(company.getId());
        Page<IccPersonVerifyRecordVO> page = PagingUtil.initPage(iccPersonVerifyRecordDTO);
        return this.baseMapper.pageListSql(page, iccPersonVerifyRecordDTO);
    }

    @Override
    public void exportExcel(BaseDTO<IccPersonVerifyRecordDTO> dto, HttpServletResponse response) {
        IccPersonVerifyRecordDTO iccPersonVerifyRecordDTO = dto.getPayload();
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        iccPersonVerifyRecordDTO.setCompanyId(company.getId());
        List<IccPersonVerifyRecordExcel> list = this.baseMapper.listSql(iccPersonVerifyRecordDTO);
        list.stream().forEach(iccPersonVerifyRecordExcel -> {
            iccPersonVerifyRecordExcel.setVerifyMode(I18nUtils.getValue(
                    Optional.ofNullable(VerifiedTypeEnum.getByVal(Integer.valueOf(iccPersonVerifyRecordExcel.getVerifyMode())))
                            .map(VerifiedTypeEnum::getI18nKey).orElse(
                            SysConstants.UNKNOWN), dto.getLang()));
            iccPersonVerifyRecordExcel.setStatus(I18nUtils.getValue(
                    Optional.ofNullable(VerifyStatusEnum.getByVal(Integer.valueOf(iccPersonVerifyRecordExcel.getStatus())))
                            .map(VerifyStatusEnum::getI18nKey).orElse(
                            SysConstants.UNKNOWN), dto.getLang()));
            if (StrUtil.isNotBlank(iccPersonVerifyRecordExcel.getMaskStatus())) {
                iccPersonVerifyRecordExcel.setMaskStatus(I18nUtils.getValue(
                        Optional.ofNullable(MaskStatusEnum.getByVal(Integer.valueOf(iccPersonVerifyRecordExcel.getMaskStatus())))
                                .map(MaskStatusEnum::getI18nKey).orElse(
                                SysConstants.UNKNOWN), dto.getLang()));
            }
        });
        EasyExcelUtils.exportExcel(response, IccPersonVerifyRecordExcel.class, list, dto.getLang());
    }
}
