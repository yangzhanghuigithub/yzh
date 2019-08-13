package com.learn.yzh.util;


import java.io.Serializable;

/**
 * 接口返回结构
 * @author Lomis
 *
 */
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String resultCode = ResultConstants.RESULT_CODE_SUCCESS;	// 结果编码 0:成功 1:失败
	private String resultDesc = ResultConstants.RESULT_DESC_SUCCESS;	// 返回的结果描述
	private T resultData;	// 返回的结果数据

	public Result(){}

	public Result(String resultCode, String resultDesc) {
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public T getResultData() {
		return resultData;
	}

	public void setResultData(T resultData) {
		this.resultData = resultData;
	}

	public boolean isSuccess(){
		return this.resultCode.equals(ResultConstants.RESULT_CODE_SUCCESS);
	}

	public  static <T> Result<T> getDefaultFailureResult(T resultData){
		Result<T> result=new Result<>();
		result.setResultCode(ResultConstants.RESULT_CODE_FAILED);
		result.setResultDesc("未知原因，处理失败");
		result.setResultData(resultData);
		return result;
	}
}
