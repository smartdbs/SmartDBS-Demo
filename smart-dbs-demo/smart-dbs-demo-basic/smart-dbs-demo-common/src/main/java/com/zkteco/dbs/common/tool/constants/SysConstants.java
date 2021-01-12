package com.zkteco.dbs.common.tool.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SysConstants
 * @Description: 全应用程序的常量
 * @Author able.lee
 * @Date 2020/11/18 10:55
 * @Since v1.0.0
 */
public class SysConstants {

    /**
     * 操作成功
     */
    public static final String MESSAGE_00 = "00";

    /**
     * 操作失败
     */
    public static final String MESSAGE_01 = "01";

    /**
     * DBS成功消息
     */
    public static final String DBS_MESSAGE_SUCCESS = "00000000";

    // ************业务常量*******************
    /**
     * 指纹类型
     */
    public static final String FINGER_PRINT = "1";
    public static final String FACE = "2";
    public static final String PALM_PRINT = "8";
    public static final String VISIBLE_FACE = "9";
    /**
     * 设备相关
     */
    public static final Integer ENABLE = 1;
    public static final Integer DISABLE = 0;
    public static final Integer RESET = 2;
    public static final Integer ONLINE = 1;
    public static final Integer OFFLINE = 0;
    public static final Integer ACTIVATED = 1;
    public static final Integer UNACTIVATED = 0;
    public static final Integer NEED_DETAILS = 1;
    public static final String DOOR_TEMPORARY_PASSWORD = "1";
    public static final String DOOR_FIXED_PASSWORD = "0";
    public static final String REGISTER_BIOMETRIC_END = "1";
    public static final Integer ACC = 1;
    public static final Integer ATT = 0;
    /**
     * DBS推送sid
     */
    public static final String PUSH_TEST = "dse.push.test";
    public static final String PUSH_ATT_RECORD = "dse.push.punchRecord";

    /**
     * 消息MAP集合<br/>
     * ("00", "操作成功");<br/>
     * ("01", "操作失败");<br/>
     */
    public static final Map<String, String> MESSAGE_MAP = new HashMap<String, String>();

      static {
        MESSAGE_MAP.put("00", "操作成功");
        MESSAGE_MAP.put("01", "操作失败");
    }

    /**
     * 获取消息MAP集合，单个值<br/>
     * ("00", "操作成功");<br/>
     * ("01", "操作失败");<br/>
     */
    public static Map<String, String> getMessageUniq(String code) {
        Map<String, String> result = new HashMap<>(SysConstants.INIT_SIZE);
        if (SysConstants.MESSAGE_MAP.containsKey(code)) {
            result.put("code", code);
            result.put("message", MESSAGE_MAP.get(code));
        }
        return result;
    }

    /**
     * 初始化大小
     */
    public static final Integer INIT_SIZE = 16;

    /**
     * demoAdmin工号
     */
    public static final String DEMO_ADMIN = "demoAdmin";

    /**
     * 会话Id
     */
    public static final String SID = "sid";

    /**
     * taskId
     */
    public static final String TASK_ID = "taskId";

    /**
     * 未知
     */
    public static final String UNKNOWN = "unknown";
}
