package com.zkteco.dbs.company.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.common.profile.Language;
import com.zkcloud.api.dbs.model.CompanyRequest;
import com.zkcloud.api.dbs.model.User;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.company.dao.CompanyMapper;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName CompanyServiceImpl
 * @Description: 服务实现类
 * @Author able.lee
 * @Date 2020/11/25 10:38
 * @Since v1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Autowired
    private DbsConfig dbsConfig;

    @Override
    public Company getByAppKeyAndCompanyCode(String appKey, String code, String lang) {
        // 查询企业信息
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.eq("app_key", appKey)
                .eq("company_code", code);
        Company company = this.getOne(wrapper, false);

        return company;
    }

    @Override
    public Company getByAppKey(String appKey) {
        // 查询企业信息
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.eq("app_key", appKey);
        Company company = this.getOne(wrapper, false);

        return company;
    }


    @Override
    public void update(BaseDTO<Company> dto) {
        Company company = dto.getPayload();
        this.updateById(company);

        // 调用sdk 更新企业信息
        CompanyRequest companyRequest = new CompanyRequest();
        companyRequest.setCompanyCode(company.getCompanyCode());
        companyRequest.setCompanyName(company.getCompanyName());
        companyRequest.setWebsite(company.getWebsite());
        companyRequest.setPhone(company.getPhone());
        companyRequest.setEmail(company.getEmail());
        companyRequest.setLastUpdateTimeStamp(System.currentTimeMillis() / 1000);

        User user = new User(company.getUserName(), company.getPassword());
        companyRequest.setApiUser(user);
        DBSApi.dbsClient.getClientProfile().setLanguage(Language.getByLang(dto.getLang()));
        Message message = DBSApi.dbsClient.updateCompany(companyRequest);

        ResultUtil.handleDbsResultError(message);
    }
}
