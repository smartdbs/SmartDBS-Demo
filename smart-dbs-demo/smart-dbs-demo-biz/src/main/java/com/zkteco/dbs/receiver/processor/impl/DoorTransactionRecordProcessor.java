package com.zkteco.dbs.receiver.processor.impl;


import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.zkcloud.api.dbs.common.Message;
import com.zkteco.dbs.acc.model.AccTransactionLog;
import com.zkteco.dbs.acc.service.AccTransactionLogService;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.ErrorCode;
import com.zkteco.dbs.common.tool.constants.Sid;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.receiver.model.DoorTransactionRecords;
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
 * DoorTransactionRecordProcessor
 * 门禁记录事件处理器
 * @author sheldon.wu
 * @date 2020/11/30 14:07
 * @since 1.0.0
 */
@Component
@Order(1)
@ConditionalOnExpression("${dbs.push.switch.doorTransactionRecord:true}")
public class DoorTransactionRecordProcessor implements DataProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private AccTransactionLogService accTransactionLogService;

    @Override
    public boolean match(Message request) {
        return Sid.DOOR_TRANSACTION_RECORD.check(request.getSid());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message handle(Message request) {
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        String companyId = company.getId();
        DoorTransactionRecords doorTransactionRecords = Convert.convert(DoorTransactionRecords.class, request.getPayload().getParams());
        // 门禁事件记录存储到本地
        doorTransactionRecords.getTransactions().forEach(transaction -> {
            // 保存对象
            AccTransactionLog log = new AccTransactionLog();
            log.setSn(transaction.getSn());
            log.setDoorNum(Integer.parseInt(transaction.getDoorNum()));
            log.setOperator(transaction.getOperator());
            log.setTime(transaction.getTime());
            LocalDateTime time = LocalDateTime.parse(transaction.getTime(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            log.setTimeStamp(Timestamp.valueOf(time).getTime() / 1000);
            log.setInOutType(Integer.parseInt(transaction.getInOutState()));
            log.setVerified(Integer.parseInt(transaction.getVerified()));
            log.setEventCode(Integer.parseInt(transaction.getEventCode()));
            log.setCompanyId(companyId);
            accTransactionLogService.save(log);
        });

        logger.debug("DoorTransactionRecordProcessor：" + JSONObject.toJSON(doorTransactionRecords));
        return new Message(ErrorCode.CODE_SUCCESS.getCode(), ErrorCode.CODE_SUCCESS.getMessage());
    }

}
