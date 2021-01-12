package com.zkteco.dbs.receiver.model;

import com.zkteco.dbs.common.base.model.BaseDTO;
import java.util.List;
import lombok.Data;

/**
 * PunchCards
 *
 * @author sheldon.wu
 * @date 2020/12/28 15:37
 * @since 1.0.0
 */
@Data
public class PunchCards extends BaseDTO {

    /**
     * 卡信息集合
     */
    private List<PunchCard> punchCards;
}
