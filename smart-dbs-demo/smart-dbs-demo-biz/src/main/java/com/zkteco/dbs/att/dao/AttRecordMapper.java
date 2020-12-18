package com.zkteco.dbs.att.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkteco.dbs.att.dto.AttRecordDTO;
import com.zkteco.dbs.att.model.AttRecord;
import com.zkteco.dbs.att.vo.AttRecordExcel;
import com.zkteco.dbs.att.vo.AttRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AttRecordMapper
 * 考勤记录-业务层
 * @author sheldon.wu
 * @date 2020/11/24 9:13
 * @since 1.0.0
 */
public interface AttRecordMapper extends BaseMapper<AttRecord> {

    /**
     * pageListSql
     *
     * @param page
     * @param dto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.att.vo.AttRecordVO>
     * @throws
     * @author sheldon.wu
     * @date 2020/11/23 14:28
     * @since 1.0.0
     */
    IPage<AttRecordVO> pageListSql(Page page, @Param("params") AttRecordDTO dto);

    /**
     * listSql
     *
     * @param dto
     * @return java.util.List<com.zkteco.dbs.att.vo.AttRecordExcel>
     * @throws
     * @author sheldon.wu
     * @date 2020/11/24 8:53
     * @since 1.0.0
     */
    List<AttRecordExcel> listSql(@Param("params") AttRecordDTO dto);
}
