package com.learn.yzh.service.base;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by wl on 2017/4/6.
 */
public interface BaseService<T,PK extends Serializable> {

     String excuteBeanUrl(String url, T bean);

     String excuteMapUrl(String url, Map<String, Object> map);

}
