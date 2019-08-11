package com.learn.yzh.common.data;

import com.alibaba.fastjson.JSON;
import com.learn.yzh.common.utils.DateParseUtil;
import com.learn.yzh.common.utils.IdUtils;

import org.apache.http.annotation.NotThreadSafe;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:LogMessage
 *
 * @author licho
 * @create 2018-08-10 11:34
 */
@NotThreadSafe
public class LogMessage {
    private String logId;//记录id
    private String logTime;//记录时间
    private String logType;//日志消息类型一般记录形式,业务类型:操作(CRUD)
    private String message;//记录提示信息
    private  Map<String,Object> exceptionData=new HashMap<>();//记录当时的业务中间数据
    public LogMessage(String logType,String message){
        this.logId=IdUtils.uuid2();
        this.logTime=DateParseUtil.parseDateToStr(new Date(),DateParseUtil.DATETIME_PATTERN);
        this.logType=logType;
        this.message=message;
    }
    /**
     * 记录业务中间数据
     * @param key
     * @param object
     * @return
     */
    public LogMessage appendDate(String key,Object object){
        if(key==null || object==null)
            return this;
        exceptionData.put(key,object);
        return this;
    }

    /**
     * 获取json结果
     * @return
     */
    public String getJsonResult(){
        return JSON.toJSONString(this);
    }
    public static LogMessage createLogMessage(String logType,String message){
        return new LogMessage(logType,message);
    }

    public String getLogId() {
        return logId;
    }


    public String getLogTime() {
        return logTime;
    }



    public String getLogType() {
        return logType;
    }

    public LogMessage setLogType(String logType) {
        this.logType = logType;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public LogMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public Map<String, Object> getExceptionData() {
        return exceptionData;
    }

    public LogMessage setExceptionData(Map<String, Object> exceptionData) {
        this.exceptionData = exceptionData;
        return this;
    }



}
