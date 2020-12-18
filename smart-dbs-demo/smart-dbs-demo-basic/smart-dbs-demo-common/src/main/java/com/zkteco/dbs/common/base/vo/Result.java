package com.zkteco.dbs.common.base.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zkteco.dbs.common.utils.I18nUtils;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * @ClassName Result
 * @Description: 接口返回的统一包装对象，已含分页信息
 * @Author able.lee
 * @Date 2020/11/17 13:38
 * @Since v1.0.0
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.CUSTOM)
public class Result<T> implements Serializable {

    /**
     * 请求结果编码
     */
    private String code;
    /**
     * 请求结果信息
     */
    private String message;
    /**
     * 请求结果数据
     */
    private T data;

    /**
     * 分页信息-总页数
     */
    private Integer totalPages;
    /**
     * 分页信息-总记录数
     */
    private Long total;
    /**
     * 分页信息-每页记录数
     */

    private Integer pageSize;

    /**
     * 分页信息-当前页数
     */
    private Integer curPage;

    private Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 生成结果对象
     * @param code
     * @param message
     * @param data
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> Result<T> newInstance(String code, String message, T data) {
        Result result = new Result(code, message);
        result.setData(data);
        return result;
    }

    /**
     * 生成成功结果
     * @return
     */
    public static <T> Result<T> getSuccess(String language) {
        return newInstance("00", I18nUtils.getValue("00", language), null);
    }

    /**
     * 生成成功结果
     * @param data
     * @return
     */
    public static <T> Result<T> getSuccess(String language, T data) {
        return newInstance("00", I18nUtils.getValue("00", language), data);
    }

    /**
     * 生成成功结果-分页列表
     * @param page
     * @return
     */
    public static <T> Result<T> getSuccess(Page<T> page, String language) {
        Result result = newInstance("00", I18nUtils.getValue("00", language), page.getContent());
        result.totalPages = page.getTotalPages();
        result.total = page.getTotalElements();
        //当前页数首页为0，与前端一致需+1表示第一页
        result.curPage = page.getNumber() + 1;
        result.pageSize = page.getSize();
        return result;
    }

    /**
     * 生成成功结果-分页列表
     * @param page
     * @return
     */
    public static <T> Result<T> getSuccess(IPage<T> page, String language) {
        Result result = newInstance("00", I18nUtils.getValue("00", language), page.getRecords());
        result.totalPages = ((int) page.getPages());
        result.total = page.getTotal();
        //当前页数首页为0，与前端一致需+1表示第一页
        result.curPage = (int) page.getCurrent();
        result.pageSize = (int) page.getSize();
        return result;
    }


    /**
     * 生成成功结果-列表
     * @param data
     * @return
     */
    public static <T> Result<T> getSuccess(List<T> data, String language) {
        Result result = newInstance("00", I18nUtils.getValue("00", language), data);
        return result;
    }

    /**
     * 生成失败结果
     *
     * @return
     */
    public static <T> Result<T> getFailure(String lang) {
        return getFailure("01", lang);
    }

    /**
     * @Title getFailure
     * @Description 生成失败结果
     * @param code : 错误编号
     * @param lang : 语言
     * @return com.zkteco.dbs.common.base.vo.Result<T>
     * @throws
     * @author able.lee
     * @date 2020/11/18 10:50
     * @since v1.0.0
     */
    public static <T> Result<T> getFailure(String code, String lang) {
        return newInstance(code, I18nUtils.getValue(code, lang), null);
    }

    /**
     * 生成带code 和 异常信息的失败结果
     * @param code
     * @param message
     * @param isBusiness
     * @param <T>
     * @return
     */
    public static <T> Result<T> getFailure(String code, String message, boolean isBusiness) {
        return newInstance(code, message, null);
    }

    /**
     * 生成失败结果-含数据信息
     * @param message 失败消息
     * @param data
     * @return
     */
    public static <T> Result<T> getFailure(String message, T data) {
        return newInstance("01", message, data);
    }

}