package com.zkteco.dbs.icc.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkteco.dbs.icc.dto.IccVerifyRecordDTO;
import com.zkteco.dbs.icc.model.IccVerifyRecord;
import com.zkteco.dbs.icc.vo.IccVerifyRecordExcel;
import com.zkteco.dbs.icc.vo.IccVerifyRecordVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName IccVerifyRecordMapper
 * @Description: 核验记录 Mapper 接口
 * @author sheldon.wu
 * @date 2021/02/25 15:42
 * @since 1.0.0
 */
public interface IccVerifyRecordMapper extends BaseMapper<IccVerifyRecord> {

    /**
     * pageListSql
     *
     * @param page
     * @param dto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zkteco.dbs.icc.vo.IccVerifyRecordVO>
     * @throws
     * @author sheldon.wu
     * @date 2021/2/25 15:35
     * @since 1.0.0
     */
    IPage<IccVerifyRecordVO> pageListSql(Page page, @Param("params") IccVerifyRecordDTO dto);

    /**
     * listSql
     *
     * @param dto
     * @return java.util.List<IccVerifyRecordExcel>
     * @throws
     * @author sheldon.wu
     * @date 2021/2/25 15:36
     * @since 1.0.0
     */
    List<IccVerifyRecordExcel> listSql(@Param("params") IccVerifyRecordDTO dto);
}
