package com.zkteco.dbs.receiver.model;

import com.zkteco.dbs.common.base.model.BaseDTO;
import java.util.List;
import lombok.Data;

/**
 * PunchRecords
 * 考勤记录集合-Model
 * @author sheldon.wu
 * @date 2020/11/30 14:07
 * @since 1.0.0
 */
@Data
public class PunchRecords extends BaseDTO {

    /**
     * 考勤记录集合
     */
    private List<PunchRecord> punchRecords;
}
