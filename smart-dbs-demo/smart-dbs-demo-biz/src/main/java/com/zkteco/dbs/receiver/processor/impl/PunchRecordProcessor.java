package com.zkteco.dbs.receiver.processor.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.zkcloud.api.dbs.common.Message;
import com.zkteco.dbs.att.model.AttRecord;
import com.zkteco.dbs.att.service.AttRecordService;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.ErrorCode;
import com.zkteco.dbs.common.tool.constants.Sid;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.receiver.model.PunchRecords;
import com.zkteco.dbs.receiver.processor.DataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * PunchRecordProcessor
 * 考勤记录事件处理器
 * @author sheldon.wu
 * @date 2020/11/30 14:07
 * @since 1.0.0
 */
@Component
@Order(1)
@ConditionalOnExpression("${dbs.push.switch.punchRecord:true}")
public class PunchRecordProcessor implements DataProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AttRecordService attRecordService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DbsConfig dbsConfig;

    @Override
    public boolean match(Message request) {
        return Sid.PUNCH_RECORD.check(request.getSid());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message handle(Message request) {
        PunchRecords punchRecords = Convert.convert(PunchRecords.class, request.getPayload().getParams());
        // 处理打卡记录
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        punchRecords.getPunchRecords().stream().forEach(punchRecord -> {
            AttRecord attRecord = new AttRecord();
            attRecord.setCheckInTime(punchRecord.getPunchTime());
            attRecord.setCompanyId(company.getId());
            attRecord.setIsoCheckInTime(punchRecord.getIso8601PunchTime());
            attRecord.setEmployeeNo(punchRecord.getEmployeeNo());
            attRecord.setSn(punchRecord.getSn());
            attRecordService.save(attRecord);
        });
        logger.debug("PunchRecordProcessor：" + JSONObject.toJSON(punchRecords));
        return new Message(ErrorCode.CODE_SUCCESS.getCode(), ErrorCode.CODE_SUCCESS.getMessage());
    }
}
