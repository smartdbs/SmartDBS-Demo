package com.zkteco.dbs.receiver.model;

import com.zkteco.dbs.common.base.model.BaseDTO;
import java.util.List;
import lombok.Data;

/**
 * DoorTransactionRecords
 * 门禁记录集合-Model
 * @author sheldon.wu
 * @date 2020/11/25 14:26
 * @since 1.0.0
 */
@Data
public class DoorTransactionRecords extends BaseDTO {

    /**
     * 门禁事件记录集合
     */
    private List<DoorTransactionRecord> transactions;
}
