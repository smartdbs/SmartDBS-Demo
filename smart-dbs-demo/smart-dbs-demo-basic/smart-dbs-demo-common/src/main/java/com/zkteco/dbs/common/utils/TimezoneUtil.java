package com.zkteco.dbs.common.utils;

import com.zkteco.dbs.common.base.model.MessageDTO;
import java.time.Instant;
import java.time.ZoneId;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * TimezoneUtil
 * 时区工具类
 * @author able.lee
 * @date 2020/12/28 11:24
 * @since v1.0.0
 */
@Component
public class TimezoneUtil {


    private static String timezoneServiceBaseUrl;


    /**
     * @return com.zkteco.message.utils.Message
     * @Title: getTimezoneInfo
     * @Description: 获取timezone信息
     * @Author: able.lee
     */
    public static MessageDTO getTimezoneInfo() {

        String timezoneServiceUrl = timezoneServiceBaseUrl + "/timezone/getTimezoneInfo";
        RestTemplate restTemplate = new RestTemplate();
        MessageDTO response = restTemplate.getForEntity(timezoneServiceUrl, MessageDTO.class).getBody();
        return response;
    }

    @Value("${databus.service.timezone.url:http://timezone.service.zkclouds.com:18702}")
    public void setPathValue(String pathValue){
        timezoneServiceBaseUrl = pathValue;
    }


    /**
     * 获取当前时区
     * @return
     */
    public static String getStandardOffset() {
        String timezoneId = TimeZone.getDefault().getID();
        ZoneId zone = ZoneId.of(timezoneId);
        String standardOffset = zone.getRules().getStandardOffset(Instant.now()).getId();
        return standardOffset;
    }
}
