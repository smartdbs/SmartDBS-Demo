package com.zkteco.dbs.att.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkteco.dbs.att.dao.AttRecordMapper;
import com.zkteco.dbs.att.dto.AttRecordDTO;
import com.zkteco.dbs.att.model.AttRecord;
import com.zkteco.dbs.att.service.AttRecordService;
import com.zkteco.dbs.att.vo.AttRecordExcel;
import com.zkteco.dbs.att.vo.AttRecordVO;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.utils.EasyExcelUtils;
import com.zkteco.dbs.common.utils.PagingUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AttRecordServiceImpl
 * 考勤记录-业务实现层
 * @author sheldon.wu
 * @date 2020/11/23 14:28
 * @since 1.0.0
 */
@Service
public class AttRecordServiceImpl extends ServiceImpl<AttRecordMapper, AttRecord> implements AttRecordService {

    @Autowired
    private DbsConfig dbsConfig;
    @Autowired
    private CompanyService companyService;

    @Override
    public IPage<AttRecordVO> pageList(BaseDTO<AttRecordDTO> dto) {
        AttRecordDTO attRecordDTO = dto.getPayload();
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        attRecordDTO.setCompanyId(company.getId());
        Page<AttRecordVO> page = PagingUtil.initPage(attRecordDTO);
        return this.baseMapper.pageListSql(page, attRecordDTO);
    }

    @Override
    public void exportExcel(BaseDTO<AttRecordDTO> dto, HttpServletResponse response) {
        AttRecordDTO attRecordDTO = dto.getPayload();
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        attRecordDTO.setCompanyId(company.getId());
        List<AttRecordExcel> list = this.baseMapper.listSql(attRecordDTO);
        EasyExcelUtils.exportExcel(response, AttRecordExcel.class, list, dto.getLang());
    }
}
