/*
 * File Name: I18nTool
 * Created by zhaoyong.luo on 17-3-10 下午2:37.
 * Copyright:Copyright © 1985-2017 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.common.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

/**
 * @ClassName I18nUtils
 * @Description: 国际化工具类
 * @Author able.lee
 * @Date 2020/11/17 14:07
 * @Since v1.0.0
 */
public class I18nUtils {

    private static Logger logger = LoggerFactory.getLogger(I18nUtils.class);

    private static final String RESOURCE_PATH = "zk-i18n/";

    private static final String PROPERTIES_SUFFIX = ".properties";

    private static final String ASTERISK = "*";

    private static ReloadableResourceBundleMessageSource i18nMessageSource = new ReloadableResourceBundleMessageSource();

    static {
        Set<String> set = new HashSet<>();
        try {
            for (Resource resource : ResourceUtils.getResources(RESOURCE_PATH + ASTERISK + PROPERTIES_SUFFIX)) {
                String fileName = resource.getFilename();
                String defaultFileName;
                if (fileName.contains("_")) {
                    defaultFileName = fileName.substring(0, fileName.indexOf("_")).replace(PROPERTIES_SUFFIX, "");
                } else {
                    defaultFileName = fileName.replace(PROPERTIES_SUFFIX, "");
                }
                set.add(resource.getURI().toString().replace(fileName, defaultFileName));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        i18nMessageSource.setBasenames(set.toArray(new String[set.size()]));
        i18nMessageSource.setDefaultEncoding(StandardCharsets.UTF_8.toString());
    }

    /**
     * 获取本地化的值
     *
     * @param key     the key
     * @param lang    the lang
     * @param objects the objects
     * @return the value
     */
    public static String getValue(String key, String lang, Object[] objects) {
        try {
            if (StringUtils.hasLength(lang)) {
                lang = lang.replace("_", "-");
                Locale locale = Locale.forLanguageTag(lang);
                return i18nMessageSource.getMessage(key, objects, locale);
            } else {
                return i18nMessageSource.getMessage(key, objects, Locale.getDefault());
            }
        } catch (Exception e) {
            logger.error("can't find key:" + key);
            return null;
        }
    }

    /**
     * 获取本地化的值
     *
     * @param key  the key
     * @param lang the lang
     * @return the value
     */
    public static String getValue(String key, String lang) {
        return getValue(key, lang, null);
    }
}
