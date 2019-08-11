package com.learn.yzh.common.utils.file;

import java.io.Serializable;

/**
 * 文件上传返回对象
 * Created by tongy on 2016/1/4.
 */
public class FileUploadResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int resultCode;      //返回代码
    private String resultDesc;   //返回的描述
    private String resultData;   //返回的数据  

    /**
     * 默认保存失败
     */
    public FileUploadResult() {
        this.resultCode = 1;
        this.resultData = "";
        this.resultDesc = "failed";
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }
}
