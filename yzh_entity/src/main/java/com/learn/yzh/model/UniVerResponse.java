package com.learn.yzh.model;

import lombok.Data;

/**
 * @program: yj->UniVerResponse
 * @description:
 * @author: yangzhanghui
 * @create: 2019-07-29 12:45
 **/
@Data
public class UniVerResponse {

    public static final int ERROR_BUSINESS = 500;

    private int code;

    private String desc;

    public void beFalse3(String desc, int errorCode){
        this.code = errorCode;
        this.desc = desc;
    }
}
