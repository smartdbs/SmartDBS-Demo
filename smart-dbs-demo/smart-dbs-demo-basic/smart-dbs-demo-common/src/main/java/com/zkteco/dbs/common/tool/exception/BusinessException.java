package com.zkteco.dbs.common.tool.exception;


import com.zkteco.dbs.common.utils.I18nUtils;

/**
 * @ClassName BusinessException
 * @Description: 异常封装
 * @Author able.lee
 * @Date 2020/11/17 13:41
 * @Since v1.0.0
 */
public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private String code = "-9999";
	private String message = "";

	/**
	 * 根据异常编号创建实例<br/>
	 * ("00", "操作成功");<br/>
	 * ("01", "操作失败");<br/>
	 */
	public BusinessException(String code,String lang) {
		super();
		this.code = code;
		this.message = I18nUtils.getValue(code,lang);
	}

	public BusinessException(String code,String message,String lang) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}
	@Override
	public String getMessage() {
		return message;
	}
}
