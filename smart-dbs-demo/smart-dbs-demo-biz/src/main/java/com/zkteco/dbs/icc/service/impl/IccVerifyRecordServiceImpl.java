/*
 * File Name: IccVerifyRecordServiceImpl
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
import com.zkteco.dbs.icc.dao.IccVerifyRecordMapper;
import com.zkteco.dbs.icc.dto.IccVerifyRecordDTO;
import com.zkteco.dbs.icc.model.IccVerifyRecord;
import com.zkteco.dbs.icc.service.IccVerifyRecordService;
import com.zkteco.dbs.icc.vo.IccVerifyRecordExcel;
import com.zkteco.dbs.icc.vo.IccVerifyRecordVO;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * IccVerifyRecordServiceImpl
 *
 * @author sheldon.wu
 * @date 2021/2/25 15:38
 * @since 1.0.0
 */
@Service
public class IccVerifyRecordServiceImpl extends ServiceImpl<IccVerifyRecordMapper, IccVerifyRecord> implements IccVerifyRecordService {

    @Autowired
    private DbsConfig dbsConfig;
    @Autowired
    private CompanyService companyService;

    @Override
    public IPage<IccVerifyRecordVO> pageList(BaseDTO<IccVerifyRecordDTO> dto) {
        IccVerifyRecordDTO iccVerifyRecordDTO = dto.getPayload();
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        iccVerifyRecordDTO.setCompanyId(company.getId());
        Page<IccVerifyRecordVO> page = PagingUtil.initPage(iccVerifyRecordDTO);
        return this.baseMapper.pageListSql(page, iccVerifyRecordDTO);
    }

    @Override
    public void exportExcel(BaseDTO<IccVerifyRecordDTO> dto, HttpServletResponse response) {
        IccVerifyRecordDTO iccVerifyRecordDTO = dto.getPayload();
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        iccVerifyRecordDTO.setCompanyId(company.getId());
        List<IccVerifyRecordExcel> list = this.baseMapper.listSql(iccVerifyRecordDTO);
        list.stream().forEach(icVerifyRecordExcel -> {
            icVerifyRecordExcel.setDeviceVerifyMode(I18nUtils.getValue(
                    Optional.ofNullable(VerifiedTypeEnum.getByVal(Integer.valueOf(icVerifyRecordExcel.getDeviceVerifyMode())))
                            .map(VerifiedTypeEnum::getI18nKey).orElse(
                            SysConstants.UNKNOWN), dto.getLang()));
            icVerifyRecordExcel.setStatus(I18nUtils.getValue(
                    Optional.ofNullable(VerifyStatusEnum.getByVal(Integer.valueOf(icVerifyRecordExcel.getStatus())))
                            .map(VerifyStatusEnum::getI18nKey).orElse(
                            SysConstants.UNKNOWN), dto.getLang()));
            if (StrUtil.isNotBlank(icVerifyRecordExcel.getMaskStatus())) {
                icVerifyRecordExcel.setMaskStatus(I18nUtils.getValue(
                        Optional.ofNullable(MaskStatusEnum.getByVal(Integer.valueOf(icVerifyRecordExcel.getMaskStatus())))
                                .map(MaskStatusEnum::getI18nKey).orElse(
                                SysConstants.UNKNOWN), dto.getLang()));
            }
        });
        EasyExcelUtils.exportExcel(response, IccVerifyRecordExcel.class, list, dto.getLang());
    }
}
