package com.learn.yzh.service.base.impl;

import com.learn.yzh.service.base.BaseService;
import com.learn.yzh.util.HttpClientUtils;
import com.learn.yzh.util.JavaBeanUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by wl on 2017/4/6.
 */
public  abstract class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {
    @Override
    public String excuteMapUrl(String url,Map<String, Object> maps) {

        String resultData = HttpClientUtils.sendPostRequest(url, maps);
        return resultData;
    }

    @Override
    public String excuteBeanUrl(String url,T bean) {

        Map<String,Object> params = JavaBeanUtil.beanToMap(bean);
        String resultData = HttpClientUtils.sendPostRequest(url, params);
        return resultData;
    }
    
   
}
