package com.zkteco.dbs.receiver.model;

import com.zkteco.dbs.common.base.model.BaseDTO;
import java.util.List;
import lombok.Data;

/**
 * PersonVerifyRecords
 * 人证核验记录集合-Model
 * @author sheldon.wu
 * @date 2021/02/25 14:07
 * @since 1.0.0
 */
@Data
public class PersonVerifyRecords extends BaseDTO {

    /**
     * 人证核验记录集合
     */
    private List<PersonVerifyRecord> personVerifyRecords;
}
