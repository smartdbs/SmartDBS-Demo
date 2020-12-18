package com.zkteco.dbs.company.init;

import com.alibaba.fastjson.JSONObject;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.common.MessagePayload;
import com.zkcloud.api.dbs.model.CompanyRequest;
import com.zkcloud.api.dbs.model.CreateCompanyResponse;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * CompanyInfoInit
 * 项目启动初始化写入企业信息
 * @author able.lee
 * @date 2020/11/30 18:11
 * @since v1.0.0
 */
@Component
public class CompanyInfoInit {
    Logger logger = LoggerFactory.getLogger(CompanyInfoInit.class);
    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private CompanyService companyService;

    @PostConstruct
    public void init() {
        // 项目启动马上进行DBSDemo初始化
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        logger.info("company info：{}",company);
        if(null == company){
            //调用SDK创建公司
            CompanyRequest request = new CompanyRequest("demo","demo","demo@xx.com");
            MessagePayload<CreateCompanyResponse> responseMessage = DBSApi.dbsClient.createCompany(request).getPayload();
            logger.info("response info：{}", JSONObject.toJSON(responseMessage));
            CreateCompanyResponse companyResponse = responseMessage.getResults();

            Company newCompany = new Company();
            newCompany.setId(companyResponse.getCompanyId());
            newCompany.setActive(true);
            newCompany.setAppKey(dbsConfig.getAppKey());
            newCompany.setCompanyCode("demo");
            newCompany.setEmail("demo@xx.com");
            newCompany.setUserName(companyResponse.getApiUser().getUserName());
            newCompany.setPassword(companyResponse.getApiUser().getPassword());
            companyService.save(newCompany);
        }

    }
}
