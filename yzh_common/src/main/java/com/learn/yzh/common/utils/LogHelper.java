package com.learn.yzh.common.utils;

import org.apache.http.annotation.NotThreadSafe;

/**
 * 方便进行日志记录 ClassName:LogHelper
 *这个是非线程安全的，为了方便进行日志记录
 * @author licho
 * @create 2018-07-19 15:38
 */
@NotThreadSafe
public class LogHelper {
    private StringBuilder builder=new StringBuilder();

    public LogHelper appendOneLine(String info,Object obj){
        return this;
    }
}
