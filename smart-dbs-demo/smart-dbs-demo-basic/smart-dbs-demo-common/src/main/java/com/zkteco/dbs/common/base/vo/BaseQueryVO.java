package com.zkteco.dbs.common.base.vo;

import java.util.Optional;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * @ClassName BaseQueryVo
 * @Description:
 * @Author able.lee
 * @Date 2020/11/16 16:05
 * @Since v1.0.0
 */
@Data
public class BaseQueryVO {

    /**
     * 当前页
     */
    protected Integer curPage;
    /**
     * 每页记录数
     */
    protected Integer pageSize;
    /**
     * 关键字
     */
    protected String keyWord;
    /**
     * 导出的excel文件名称
     */
    protected String excelName;
    private String id;

    /**
     * 员工工号
     */
    private String employeeNo;

    /**
     * 员工完整姓名
     */
    private String formattedName;

    /**
     * 设备序列号
     */
    private String sn;

    protected String lang;

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * 默认带%，用于SQL的like
     *
     * @return
     */
    public String getKeyWord() {
        String tmpKeyWord = handleLikeSpecialChar(this.keyWord);
        if (StringUtils.isNotBlank(tmpKeyWord)) {
            return "%" + tmpKeyWord + "%";
        } else {
            return tmpKeyWord;
        }
    }

    /**
     * 不带%， 用于QueryWrapper
     *
     * @return
     */
    public String getKeyWordNotLike() {
        return handleLikeSpecialChar(this.keyWord);
    }


    /**
     * 处理mysql中模糊查询的特殊字符——【\、_、%】
     *
     * @param words 待处理字符串
     * @return 处理后的字符串
     */
    static String handleLikeSpecialChar(String words) {
        if (StringUtils.isNotBlank(words)) {
            words = words.replace("\\", "\\\\")
                    .replace("%", "\\%")
                    .replace("_", "\\_");
        }
        return words;
    }


    public void setExcelName(String excelName) {
        this.excelName = Optional.ofNullable(excelName)
                .filter(str -> StringUtils.lastIndexOf(str, ".") != -1)
                .map(str -> StringUtils.substring(str, 0, StringUtils.lastIndexOf(str, ".")))
                .orElse(excelName);
    }

}
