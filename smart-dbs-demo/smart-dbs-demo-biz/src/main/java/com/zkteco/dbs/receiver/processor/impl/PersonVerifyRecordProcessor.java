package com.zkteco.dbs.receiver.processor.impl;


import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.zkcloud.api.dbs.common.Message;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.ErrorCode;
import com.zkteco.dbs.common.tool.constants.Sid;
import com.zkteco.dbs.common.utils.ModelUtils;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.icc.model.IccPersonVerifyRecord;
import com.zkteco.dbs.icc.service.IccPersonVerifyRecordService;
import com.zkteco.dbs.receiver.model.PersonVerifyRecords;
import com.zkteco.dbs.receiver.processor.DataProcessor;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * PersonVerifyRecordProcessor
 * 人证记录事件处理器
 * @author sheldon.wu
 * @date 2020/11/30 14:07
 * @since 1.0.0
 */
@Component
@Order(1)
@ConditionalOnExpression("${dbs.push.switch.personVerifyRecordProcessor:true}")
public class PersonVerifyRecordProcessor implements DataProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private IccPersonVerifyRecordService iccPersonVerifyRecordService;

    @Override
    public boolean match(Message request) {
        return Sid.PERSON_VERIFY_RECORD.check(request.getSid());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message handle(Message request) {
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();
        PersonVerifyRecords personVerifyRecords = Convert.convert(PersonVerifyRecords.class, request.getPayload().getParams());
        // 人证事件记录存储到本地
        personVerifyRecords.getPersonVerifyRecords().forEach(personVerifyRecord -> {
            // 保存对象
            IccPersonVerifyRecord log = new IccPersonVerifyRecord();
            ModelUtils.copyPropertiesIgnoreNull(personVerifyRecord, log);
            LocalDateTime time = LocalDateTime.parse(personVerifyRecord.getTime(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            log.setTimeStamp(Timestamp.valueOf(time).getTime() / 1000);
            log.setCompanyId(companyId);
            iccPersonVerifyRecordService.save(log);
        });

        logger.debug("VerifyRecordProcessor：" + JSONObject.toJSON(personVerifyRecords));
        return new Message(ErrorCode.CODE_SUCCESS.getCode(), ErrorCode.CODE_SUCCESS.getMessage());
    }

}
