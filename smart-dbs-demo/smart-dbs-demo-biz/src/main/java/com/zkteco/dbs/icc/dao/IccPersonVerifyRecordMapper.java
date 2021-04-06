package com.zkteco.dbs.icc.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkteco.dbs.icc.dto.IccPersonVerifyRecordDTO;
import com.zkteco.dbs.icc.model.IccPersonVerifyRecord;
import com.zkteco.dbs.icc.vo.IccPersonVerifyRecordExcel;
import com.zkteco.dbs.icc.vo.IccPersonVerifyRecordVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName IccPersonVerifyRecordMapper
 * @Description: 人证记录 Mapper 接口
 * @author sheldon.wu
 * @date 2021/02/25 15:42
 * @since 1.0.0
 */
public interface IccPersonVerifyRecordMapper extends BaseMapper<IccPersonVerifyRecord> {

    /**
     * pageListSql
     *
     * @param page
     * @param dto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.icc.vo.IccPersonVerifyRecordVO>
     * @throws
     * @author sheldon.wu
     * @date 2021/2/25 15:35
     * @since 1.0.0
     */
    IPage<IccPersonVerifyRecordVO> pageListSql(Page page, @Param("params") IccPersonVerifyRecordDTO dto);

    /**
     * listSql
     *
     * @param dto
     * @return java.util.List<IccPersonVerifyRecordExcel>
     * @throws
     * @author sheldon.wu
     * @date 2021/2/25 15:36
     * @since 1.0.0
     */
    List<IccPersonVerifyRecordExcel> listSql(@Param("params") IccPersonVerifyRecordDTO dto);

}
