package com.learn.yzh.utils;

import lombok.Data;

/**
 * @program: yj->JsonResult
 * @description: JsonResult
 * @author: yangzhanghui
 * @create: 2019-07-27 20:16
 **/
@Data
public class JsonResult {

    private String status = null;

    private Object result = null;

}
