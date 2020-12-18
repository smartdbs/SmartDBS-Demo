package com.zkteco.dbs.acc.dto;

import com.zkteco.dbs.acc.model.AccAuthorityEmployee;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * SaveAccAuthorityEmployeeDTO
 * 门禁权限组与员工保存DTO
 * @author able.lee
 * @date 2020/11/30 15:27
 * @since v1.0.0
 */
@Data
public class SaveAccAuthorityEmployeeDTO implements Serializable {


    /**
     * 权限组编号
     */
    private Integer groupNum;

    /**
     *
     */
    private List<AccAuthorityEmployee> employees;


}
