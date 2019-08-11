package com.learn.yzh.common.model.movie.cache;

import com.learn.yzh.common.constants.RedisConstants;
import com.learn.yzh.common.redis.IRedis;
import com.learn.yzh.common.redis.Redis;
import com.learn.yzh.common.utils.SpringContextUtils;
import com.learn.yzh.common.utils.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by wl on 2018/5/7.
 */
public abstract class CacheObject {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public String _cacheName;

    private Redis redis;

    protected boolean isList;

    private RedisConstants redisConstants;


    public CacheObject() {
    }

    public abstract String getCacheName();

    public boolean isList() {
        return this.isList;
    }

    public Redis getRedis(){
        if (redis == null) {
            redis = SpringContextUtils.getBean(Redis.class);
        }
        return redis;
    }

    public String getRedisData(String key){
        String result = getRedis().hget(getCacheName(),key,String.class);
        return result;
    }


    public Object getObject(Class<?> clz, String secondKey) {

        Object obj = null;
        try {
            String cacheContent = getRedis().hget(this.getCacheName(),secondKey,String.class);
            if(StringUtils.isNotBlank(cacheContent)) {
                obj = objectMapper.readValue(cacheContent, clz);
            }
        } catch (JsonParseException var11) {
            var11.printStackTrace();
        } catch (JsonMappingException var12) {
            var12.printStackTrace();
        } catch (IOException var13) {
            var13.printStackTrace();
        }
        return obj;
    }

    public long setObject(String secondKey, Object obj) {

        long result = 0L;
        try {
            if(obj instanceof List) {
                result = getRedis().hset(this.getCacheName(), secondKey, objectMapper.writeValueAsString(obj));
            }
            if(obj instanceof Map) {
                String s = getRedis().hmset(this.getCacheName(), (Map)obj);
                if(s.equalsIgnoreCase("ok")) {
                    result = 1L;
                }
            }
        } catch (JsonGenerationException var12) {
            var12.printStackTrace();
        } catch (JsonMappingException var13) {
            var13.printStackTrace();
        } catch (IOException var14) {
            var14.printStackTrace();
        }

        return result;
    }

    public long removeObject(String[] keys) {
        long result = getRedis().hdel(this.getCacheName(), keys).longValue();
        return result;
    }
    public RedisConstants getRedisConstants(){
        if (redisConstants == null) {
            redisConstants = SpringContextUtils.getBean(RedisConstants.class);
        }
        return redisConstants;
    }
}
