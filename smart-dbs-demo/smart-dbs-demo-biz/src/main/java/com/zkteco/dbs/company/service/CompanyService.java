package com.zkteco.dbs.company.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.company.model.Company;

/**
 * CompanyService
 * 服务类
 * @author able.lee
 * @date 2020/11/30 15:11
 * @since v1.0.0
 */
public interface CompanyService extends IService<Company> {

    /**
     * getByAppKeyAndCompanyCode
     * 根据appKey，和企业编号查询企业
     * @param appKey
     * @param code
     * @param lang
     * @return com.zkteco.dbs.company.model.Company
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:50
     * @since v1.0.0
     */
    Company getByAppKeyAndCompanyCode(String appKey,String code,String lang);

    /**
     * getByAppKey
     * 根据appKey查找企业
     * @param appKey
     * @return com.zkteco.dbs.company.model.Company
     * @throws 
     * @author able.lee
     * @date 2020/12/7 16:17
     * @since v1.0.0
     */
    Company getByAppKey(String appKey);

    /**
     * update
     * 更新企业基本信息
     * @param dto
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:51
     * @since v1.0.0
     */
    void update(BaseDTO<Company> dto);


}
