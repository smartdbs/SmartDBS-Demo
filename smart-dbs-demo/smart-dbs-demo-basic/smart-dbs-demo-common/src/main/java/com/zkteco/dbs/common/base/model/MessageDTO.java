package com.zkteco.dbs.common.base.model;


import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName BaseDTO
 * @Description: DTO基类
 * @Author able.lee
 * @Date 2020/11/25 15:29
 * @Since v1.0.0
 */
@Data
public class MessageDTO implements Serializable {


    /**
     * 结果编码
     */
    private String code;

    /**
     * 参数负荷
     */
    private Map<String, Object> payload;


}
