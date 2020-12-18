package com.zkteco.dbs.acc.dto;

import com.zkteco.dbs.acc.model.AccAuthorityDoor;
import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * SaveAccAuthorityDoorDTO
 * 门禁权限组与门保存DTO
 * @author able.lee
 * @date 2020/11/30 15:26
 * @since v1.0.0
 */
@Data
public class SaveAccAuthorityDoorDTO implements Serializable {


    /**
     * 门禁权限组编号
     */
    private Integer groupNum;

    /**
     * 权限组与门关系
     */
    private List<AccAuthorityDoor> doors;


}
