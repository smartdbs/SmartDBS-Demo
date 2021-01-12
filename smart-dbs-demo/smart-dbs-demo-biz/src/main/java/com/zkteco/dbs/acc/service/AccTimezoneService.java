package com.zkteco.dbs.acc.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkteco.dbs.acc.model.AccTimezone;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import com.zkteco.dbs.acc.vo.AccTimezoneVO;

import java.util.List;

/**
 * AccTimezoneService
 * 服务类
 * @author able.lee
 * @date 2020/11/30 15:12
 * @since v1.0.0
 */
public interface AccTimezoneService extends IService<AccTimezone> {

    /**
     * pageList
     * 门禁时间段-分页列表
     * @param dto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.acc.vo.AccTimezoneVO>
     * @throws 
     * @author able.lee
     * @date 2020/11/30 14:46
     * @since v1.0.0
     */
    IPage<AccTimezoneVO> pageList(BaseDTO<BaseQueryVO> dto);

    /**
     * timezoneSetUp
     * 时间段设置
     * @param dto
     * @return void
     * @throws 
     * @author able.lee
     * @date 2020/11/30 14:46
     * @since v1.0.0
     */
    void timezoneSetUp(BaseDTO<AccTimezoneVO> dto);

    /**
     * update
     * 更新门禁时间段基本信息
     * @param dto
     * @return void
     * @throws 
     * @author able.lee
     * @date 2020/11/30 14:47
     * @since v1.0.0
     */
    @Deprecated
    void update(BaseDTO<AccTimezone> dto);

    /**
     * remove
     * 删除门禁时间段
     * @param dto
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:47
     * @since v1.0.0
     */
    void remove(BaseDTO<AccTimezone> dto);

    /**
     * add
     * 新建门禁时间段
     * @param vo
     * @return void
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:47
     * @since v1.0.0
     */
    void add(BaseDTO<AccTimezoneVO> vo);

    /**
     * selectList
     * 门禁时间普通列表
     * @param dto
     * @return java.util.List<com.zkteco.dbs.acc.model.AccTimezone>
     * @throws
     * @author able.lee
     * @date 2020/11/30 14:47
     * @since v1.0.0
     */
    List<AccTimezone> selectList(BaseDTO<BaseQueryVO> dto);

}
