package com.zkteco.dbs.acc.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkteco.dbs.acc.dto.AccTransactionLogDTO;
import com.zkteco.dbs.acc.model.AccTransactionLog;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName AccTransactionLogMapper
 * @Description: 门禁事件记录 Mapper 接口
 * @Author able.lee
 * @Date 2020/11/25 17:35
 * @Since v1.0.0
 */
public interface AccTransactionLogMapper extends BaseMapper<AccTransactionLog> {

}
