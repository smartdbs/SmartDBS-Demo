package com.zkteco.dbs.common.utils;


import com.zkcloud.api.dbs.common.Message;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.common.tool.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.Nullable;

/**
 * ResultUtil
 *
 * @author able.lee
 * @date 2020/11/30 14:18
 * @since 1.0.0
 */
public class ResultUtil {

    public ResultUtil() {
    }

    ;

    /**
     * 抛出异常信息
     * @param code
     * @param lang
     */
    public static void handldErrorInfo(String code, String lang) {
        throw new BusinessException(code, lang);
    }

    /**
     * 抛出异常信息 - 断言非null
     * @param object
     * @param code
     * @param lang
     */
    public static void handldNullError(@Nullable Object object, String code, String lang) {
        if (object == null) {
            throw new BusinessException(code, lang);
        }
    }

    /**
     * 抛出异常信息 - 断言非null
     * @param object
     * @param code
     * @param lang
     */
    public static void handldNoNullError(@Nullable Object object, String code, String lang) {
        if (object != null) {
            throw new BusinessException(code, lang);
        }
    }

    /**
     * 抛出异常信息 - 断言字符串 非空
     * @param object
     * @param code
     * @param lang
     */
    public static void handldBlankError(@Nullable String object, String code, String lang) {
        if (StringUtils.isBlank(object)) {
            throw new BusinessException(code, lang);
        }
    }

    /**
     * 抛出异常信息 - 断言对象存在
     * @param object
     * @param code
     * @param lang
     */
    public static void handleExistsError(@Nullable Object object, String code, String lang) {
        if (object != null) {
            throw new BusinessException(code, lang);
        }
    }

    public static void handleDbsResultError(@Nullable Message respMsg) {
        if (!SysConstants.DBS_MESSAGE_SUCCESS.equals(respMsg.getCode())) {
            throw new BusinessException(respMsg.getCode(), respMsg.getMessage() ,respMsg.getLang());
        }
    }
}
