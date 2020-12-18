package com.zkteco.dbs.common.tool.intercept;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyMetaObjectHandler
 * @Description: mybatis统一的公共属性赋值
 * @Author able.lee
 * @Date 2020/11/25 15:01
 * @Since v1.0.0
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    private static final String FIELD_CREATED_DATE = "createdDate";
    private static final String FIELD_UPDATED_DATE = "modifiedDate";

    @Override
    public void insertFill(MetaObject metaObject) {
        //避免使用metaObject.setValue()
        long time = System.currentTimeMillis() / 1000;
        this.setFieldValByName(FIELD_CREATED_DATE, time, metaObject);
        this.setFieldValByName(FIELD_UPDATED_DATE, time, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        long time = System.currentTimeMillis() / 1000;
        this.setFieldValByName(FIELD_UPDATED_DATE, time, metaObject);
    }

}