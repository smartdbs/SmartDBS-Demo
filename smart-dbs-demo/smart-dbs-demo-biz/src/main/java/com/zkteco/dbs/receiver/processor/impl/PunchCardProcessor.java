package com.zkteco.dbs.receiver.processor.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.zkcloud.api.dbs.common.Message;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.ErrorCode;
import com.zkteco.dbs.common.tool.constants.Sid;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.model.Employee;
import com.zkteco.dbs.company.model.EmployeeEnrollment;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.company.service.EmployeeEnrollmentService;
import com.zkteco.dbs.company.service.EmployeeService;
import com.zkteco.dbs.receiver.model.PunchCards;
import com.zkteco.dbs.receiver.processor.DataProcessor;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * PunchCardProcessor
 * 员工卡信息事件处理器
 * @author sheldon.wu
 * @date 2020/12/28 16:07
 * @since 1.0.0
 */
@Component
@Order(1)
@ConditionalOnExpression("${dbs.push.switch.punchCard:true}")
public class PunchCardProcessor implements DataProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeEnrollmentService employeeEnrollmentService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DbsConfig dbsConfig;

    @Override
    public boolean match(Message request) {
        return Sid.PUNCH_CARD.check(request.getSid());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message handle(Message request) {
        PunchCards punchCards = Convert.convert(PunchCards.class, request.getPayload().getParams());
        // 处理打卡记录
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        punchCards.getPunchCards().stream().forEach(punchCard -> {
            String employeeId = Optional.ofNullable(employeeService.getByCompanyIdAndEmployeeNo(company.getId(),
                    punchCard.getEmployeeNo(), null)).map(Employee::getId).orElse(null);
            if (employeeId == null) {
                ResultUtil.handldErrorInfo("E19", null);
            }
            EmployeeEnrollment employeeEnrollment = employeeEnrollmentService.getByCompanyIdAndEmployeeId(company.getId(), employeeId);
            if (ObjectUtil.isEmpty(employeeEnrollment.getPushTimeStamp()) || (employeeEnrollment.getPushTimeStamp() < punchCard
                    .getPushTimeStamp())) {
                employeeEnrollment.setCardNo(punchCard.getCard());
                employeeEnrollment.setPushTimeStamp(punchCard.getPushTimeStamp());
                employeeEnrollmentService.updateById(employeeEnrollment);
            }
        });
        logger.debug("PunchCardProcessor：" + JSONObject.toJSON(punchCards));
        return new Message(ErrorCode.CODE_SUCCESS.getCode(), ErrorCode.CODE_SUCCESS.getMessage());
    }
}
