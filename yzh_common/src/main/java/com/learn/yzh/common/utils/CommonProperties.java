package com.learn.yzh.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by wl on 2017/11/20.
 */
@Component
@PropertySource(value = {"classpath:cache-key.properties"},encoding="utf-8")
@PropertySource(value={
        "classpath:cache-key.properties",
        "classpath:redis-key.properties"
},encoding="utf-8")
public class CommonProperties {

    @Value("${cache.dict.key}")public String cacheDictKey;

    

}
