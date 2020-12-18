package com.zkteco.dbs.common.tool.exception;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.zkcloud.api.dbs.exception.ZKCloudSDKException;
import com.zkteco.dbs.common.base.vo.Result;
import com.zkteco.dbs.common.tool.wrapper.RequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName DefaultGlobalExceptionHandlerAdvice
 * @Description:
 * @Author able.lee
 * @Date 2020/11/17 18:12
 * @Since v1.0.0
 */
@Slf4j
@RestControllerAdvice
public class DefaultGlobalExceptionHandlerAdvice {


    /**
     * 接口有参数未传
     */
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    @ResponseBody
    public Result missingServletRequestParameterException(HttpServletRequest req, MissingServletRequestParameterException ex) {
        String lang = getLangFromReqBody(req);
        log.error("missing servlet request parameter exception:{}"+ex.getMessage(),ex);
        return Result.getFailure("E07",lang);
    }

    /**
     * 上传文件大小超限制
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {MultipartException.class})
    @ResponseBody
    public Result uploadFileLimitException(HttpServletRequest req, MultipartException ex) {
        String lang = getLangFromReqBody(req);
        log.error("upload file exception:{}"+ex.getMessage(),ex);
        return Result.getFailure("E08",lang);
    }

    /**
     * 数字格式错误
     */
    @ExceptionHandler(value = NumberFormatException.class)
    @ResponseBody
    public Result numberFormatError(HttpServletRequest req, NumberFormatException e) throws Exception {
        log.error("service exception:{}"+e.getMessage(),e );
        String lang = getLangFromReqBody(req);
        return Result.getFailure("E06",lang);
    }

    /**
     * JSON格式解析错误
     */
    @ExceptionHandler(value = JSONException.class)
    @ResponseBody
    public Result jsonError(HttpServletRequest req, JSONException e) throws Exception {
        log.error("service exception:{}"+e.getMessage(),e );
        String lang = getLangFromReqBody(req);
        return Result.getFailure("E03",lang);
    }

   /**
     * JSON格式解析错误
     */
    @ExceptionHandler(value = JsonMappingException.class)
    @ResponseBody
    public Result jsonMappingError(HttpServletRequest req, JsonMappingException e) throws Exception {
        log.error("service exception:{}"+e.getMessage(),e );
        String lang = getLangFromReqBody(req);
        return Result.getFailure("E03",lang);
    }
   /**
     * JSON格式解析错误
     */
    @ExceptionHandler(value = JsonParseException.class)
    @ResponseBody
    public Result jsonParseError(HttpServletRequest req, JsonParseException e) throws Exception {
        log.error("service exception:{}"+e.getMessage(),e );
        String lang = getLangFromReqBody(req);
        return Result.getFailure("E03",lang);
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public Result serviceException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        log.error("service exception:{}"+ex.getMessage(),ex );
        String lang = getLangFromReqBody(req);
        return Result.getFailure("E05",lang);
    }



    /**
     * 服务器内部错误
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public Result nullError(HttpServletRequest req, NullPointerException e) throws Exception {
        String lang = getLangFromReqBody(req);
        return Result.getFailure("E02",lang);
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Result baseException(HttpServletRequest req, RuntimeException ex, BusinessException bex) {
        String lang = getLangFromReqBody(req);
        String code = bex.getCode();
        log.error("base exception:{}"+ex.getMessage(),ex);
        return Result.getFailure(code,ex.getMessage(),true);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public Result illegalArgumentException(HttpServletRequest req,IllegalArgumentException ie) {
        String lang = getLangFromReqBody(req);
        log.error("base exception:{}"+ie.getMessage(),ie);
        return Result.getFailure(ie.getMessage(),lang);
    }

    @ExceptionHandler(value = {Exception.class,HttpMessageNotReadableException.class,IllegalStateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result exception(HttpServletRequest req, Exception ex) {
        String lang = getLangFromReqBody(req);
        log.error("base exception:{}"+ex.getMessage(),ex);
        return Result.getFailure("E01",lang);
    }

    @ExceptionHandler(value = ZKCloudSDKException.class)
    @ResponseBody
    public Result ZKCloudSDKException(HttpServletRequest req, ZKCloudSDKException ex){
        String lang = getLangFromReqBody(req);
        log.error("base exception:{}"+ex.getMessage(),ex);
        return Result.getFailure("E23",lang);
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result throwable(HttpServletRequest req) {
        String lang = getLangFromReqBody(req);
        return Result.getFailure(lang);
    }

    /**
     * 从request body中获取语言参数
     * @param req
     * @return
     */
    private String getLangFromReqBody(HttpServletRequest req){
        RequestWrapper requestWrapper = new RequestWrapper(req);
        String body = requestWrapper.getBody();
        JSONObject object = null;
        if (JSON.isValid(body)) {
            object = JSONObject.parseObject(body);
            return object.getString("lang");
        } else {
            object = new JSONObject();
            return null;
        }
    }
}