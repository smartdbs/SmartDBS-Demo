package com.zkteco.dbs.acc.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkteco.dbs.acc.model.AccTimezone;
import com.zkteco.dbs.acc.vo.AccTimezoneVO;
import org.apache.ibatis.annotations.Param;

/**
 * AccTimezoneMapper
 * Mapper 接口
 * @author able.lee
 * @date 2020/11/30 15:44
 * @since v1.0.0
 */
public interface AccTimezoneMapper extends BaseMapper<AccTimezone> {

    /**
     * pageList
     * 门禁时间段分页列表
     * @param page
     * @param companyId
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.acc.vo.AccTimezoneVO>
     * @throws 
     * @author able.lee
     * @date 2020/11/30 15:44
     * @since v1.0.0
     */
    IPage<AccTimezoneVO> pageList(Page page, @Param("companyId") String companyId);
}
