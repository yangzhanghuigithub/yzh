package com.learn.yzh.common.utils;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * 美化输出日志
 * @author sunguoliang
 * @create 2019-01-04 10:49 AM
 */
public class LogKit {

    public static String append(Object ...params){
        return StringUtils.join(Arrays.asList(params).toArray(), "===>");

//        String result = "";
//        for(Object str:params){
//            result+=str+"===>";
//        }
//        if(result.endsWith("===>")){
//            result = result.substring(0, result.length()-4);
//        }
//        return result;
    }

}