package com.zkteco.dbs.common.base.model;


import java.io.Serializable;
import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * @ClassName BaseDTO
 * @Description: DTO基类
 * @Author able.lee
 * @Date 2020/11/25 15:29
 * @Since v1.0.0
 */
@Data
public class BaseDTO<T> implements Serializable {

    /**
     * 语言
     */
    @Transient
    private String lang;

    /**
     * 参数负载
     */
    private T payload;

    /**
     * 企业id
     */
    private String companyId;

    /**
     * 企业code
     */
    private String companyCode;


}
