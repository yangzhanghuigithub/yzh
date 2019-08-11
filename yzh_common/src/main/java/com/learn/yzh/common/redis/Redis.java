/**
 * Json版本 Shared
 */
package com.learn.yzh.common.redis;

import com.google.common.base.Function;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.*;


/**
 * redis工具类
 */
@Service
public class Redis implements IRedis {
    private static final Logger logger = LoggerFactory.getLogger(Redis.class);

    @Autowired
    protected JedisPool jedisPool;
    //使用默认的类型转换
    protected TypeConvert typeConvert = TypeConvert.DEFAULT;

    public Redis(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public long del(final String key) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.del(key);
            }
        }, key);
    }

    @Override
    public long incr(final String key) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.incr(key);
            }
        }, key);
    }

    @Override
    public long decr(final String key) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.decr(key);
            }
        }, key);
    }

    @Override
    public boolean exists(final String key) {
        if(StringUtils.isEmpty(key)){
            return false;
        }
        return execute(new IExecutor<Boolean>() {
            @Override
            public Boolean run(Jedis jedis) {
                return jedis.exists(key);
            }
        }, key);
    }

    @Override
    public long expire(final String key, final int seconds) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.expire(key, seconds);
            }
        }, key);
    }

    @Override
    public long ttl(final String key) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.ttl(key);
            }
        }, key);
    }

    @Override
    public String rename(final String oldkey, final String newkey) {
        return execute(new IExecutor<String>() {
            @Override
            public String run(Jedis jedis) {
                return jedis.rename(oldkey, newkey);
            }
        }, oldkey, newkey);
    }

    @Override
    public <T> boolean setNX(String key, T value) {
          return execute(new IExecutor<Boolean>() {
            @Override
            public Boolean run(Jedis jedis) {
                return jedis.setnx(key,typeConvert.toString(value))==1;
            }
        }, key);
    }

    @Override
    public String type(final String key) {
        return execute(new IExecutor<String>() {
            @Override
            public String run(Jedis jedis) {
                return jedis.type(key);
            }
        }, key);
    }


    @Override
    public <T, F> long hset(final String key, final F field, final T value) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.hset(key, typeConvert.toString(field), typeConvert.toString(value));
            }
        }, key);
    }

    @Override
    public String hmset(final String key, final Map<String, String> hashMap) {
        return execute(new IExecutor<String>() {
            @Override
            public String run(Jedis jedis) {
//                Map<String, String> map = new HashMap<>();

                //LJK 2015-12-16 暂不开启.  当使用 net.sf.json.JSONObject 时, value = null 会被转成 "null",此时 typeConvert.toString 会报错.
                //LJK 2015-12-16 对数据进行处理,特别是 Date 数据
//                for (Map.Entry<String, T> stringStringEntry : hashMap.entrySet()) {
//                    Object value = stringStringEntry.getValue();
//                    String valueStr = typeConvert.toString(value);
//                    map.put(stringStringEntry.getKey(), valueStr);
//                }

                return jedis.hmset(key, hashMap);
            }
        }, key);
    }

    @Override
    public <F> boolean hexists(final String key, final F field){
        return execute(new IExecutor<Boolean>() {
            @Override
            public Boolean run(Jedis jedis) {
                return jedis.hexists(key, typeConvert.toString(field));
            }
        }, key);
    }

    @Override
    public <T, F> T hget(final String key, final F field, final Class<T> clazz) {
        return execute(new IExecutor<T>() {
            @Override
            public T run(Jedis jedis) {
                return typeConvert.toType(jedis.hget(key, typeConvert.toString(field)), clazz);
            }
        }, key);
    }

    @Override
    public <T, F> Map<F, T> hgetAll(final String key, final Class<F> keyClazz, final Class<T> valueClazz) {
        Map<String, String> stringStringMap = hgetAll(key);
        Map<F, T> ftMap = typeConvert.transformMap(stringStringMap, new Function<String, F>() {
            @Override
            public F apply(String input) {
                return typeConvert.toType(input, keyClazz);
            }
        }, new Function<String, T>() {
            @Override
            public T apply(String input) {
                return typeConvert.toType(input, valueClazz);
            }
        });
        return ftMap;
    }

    /**
     * 增加 hgetall 操作
     *
     * @param key key
     * @return map
     */
    public Map<String, String> hgetAll(final String key) {
        return execute(new IExecutor<Map<String, String>>() {
            @Override
            public Map<String, String> run(Jedis jedis) {
                return jedis.hgetAll(key);
            }
        }, key);
    }

    @Override
    public <F> Long hdel(final String key, final F field) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.hdel(key, typeConvert.toString(field));
            }
        }, key);
    }

    @Override
    public Long hdel(final String key, final String... fields) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.hdel(key, fields);
            }
        }, key);
    }

    @Override
    public long hincrBy(final String key, final String field, final long value) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.hincrBy(key, field, value);
            }
        }, key);
    }

    @Override
    public long incrBy(final String key, final long value) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.incrBy(key, value);
            }
        }, key);
    }
    @Override
    public long decrBy(final String key, final long value) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.decrBy(key, value);
            }
        }, key);
    }




    @Override
    public <T> long srem(final String key, final T member) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.srem(key, typeConvert.toString(member));
            }
        }, key);
    }

    @Override
    public <T> long sadd(final String key, final T member) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.sadd(key, typeConvert.toString(member));
            }
        }, key);
    }

    @Override
    public long sadds(final String key, final String[] members) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.sadd(key, members);
            }
        }, key);
    }

    @Override
    public <T> Set<T> smembers(final String key, final Class<T> clazz) {
        return execute(new IExecutor<Set<T>>() {
            @Override
            public Set<T> run(Jedis jedis) {
                ;
                return typeConvert.toSet(jedis.smembers(key), clazz);
            }
        }, key);
    }

    @Override
    public <T> boolean sisMember(final String key, final T member) {
        return execute(new IExecutor<Boolean>() {
            @Override
            public Boolean run(Jedis jedis) {
                return jedis.sismember(key, typeConvert.toString(member));
            }
        }, key);
    }

    @Override
    public long scard(final String key) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.scard(key);
            }
        }, key);
    }

    @Override
    public <T> T spop(final String key, final Class<T> clazz) {
        return execute(new IExecutor<T>() {
            @Override
            public T run(Jedis jedis) {
                return typeConvert.toType(jedis.spop(key), clazz);
            }
        }, key);
    }

    @Override
    public <T> T srandMember(final String key, final Class<T> clazz) {
        return execute(new IExecutor<T>() {
            @Override
            public T run(Jedis jedis) {
                return typeConvert.toType(jedis.srandmember(key), clazz);
            }
        }, key);
    }

    @Override
    public <T> String set(String key, T value) {
        return set(key, value, -1);
    }

    @Override
    public <T> String set(final String key, final T value, final int seconds) {
        if (value == null) return null;
        return execute(new IExecutor<String>() {
            @Override
            public String run(Jedis jedis) {
                if (seconds <= 0) {
                    return jedis.set(key, typeConvert.toString(value));
                } else {
                    return jedis.setex(key, seconds, typeConvert.toString(value));
                }
            }
        }, key);
    }

    @Override
    public String set(final byte[] key, final byte[] value, final int seconds) {
        if (value == null) return null;
        return execute(new IExecutor<String>() {
            @Override
            public String run(Jedis jedis) {
                if (seconds <= 0) {
                    return jedis.set(key, value);
                } else {
                    return jedis.setex(key, seconds, value);
                }
            }
        });
    }

    @Override
    public <T> T get(final String key, final Class<T> clazz) {
        return execute(new IExecutor<T>() {
            @Override
            public T run(Jedis jedis) {
                return typeConvert.toType(jedis.get(key), clazz);
            }
        }, key);
    }

    @Override
    public Set<String> keys(final String key) {
        return execute(new IExecutor<Set<String>>() {
            @Override
            public Set<String> run(Jedis jedis) {
                Set<String> keySet = jedis.keys(key);
                return keySet;
            }
        }, key);
    }

    @Override
    public byte[] get(final byte[] key) {
        return execute(new IExecutor<byte[]>() {
            @Override
            public  byte[] run(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    @Override
    public <T> List<T> mget(final String[] keys, final Class<T> clazz) {
        return execute(new IExecutor<List<T>>() {
            @Override
            public List<T> run(Jedis jedis) {
                return typeConvert.toList(jedis.mget(keys), clazz);
            }
        }, keys);
    }


    @Override
    public <T> List<T> mget(final List<String> keys, final Class<T> clazz) {
        final String[] keysStr = keys.toArray(new String[keys.size()]);
        return execute(new IExecutor<List<T>>() {
            @Override
            public List<T> run(Jedis jedis) {
                return typeConvert.toList(jedis.mget(keysStr), clazz);
            }
        }, keysStr);
    }

    //===== list =====

    @Override
    public Long llength(final String key) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.llen(key);
            }
        }, key);
    }

    @Override
    public String ltrim(final String key, final long startIndex, final long endIndex) {
        return execute(new IExecutor<String>() {
            @Override
            public String run(Jedis jedis) {
                return jedis.ltrim(key, startIndex, endIndex);
            }
        }, key);
    }


    @Override
    public <T> List<T> lrange(final String key, final long start, final long size, final Class<T> clazz) {
        return execute(new IExecutor<List<T>>() {
            @Override
            public List<T> run(Jedis jedis) {
                long end = getEnd(start, size);
                List<T> list = typeConvert.toList(jedis.lrange(key, start, end), clazz);
                return list;
            }
        }, key);
    }

    @Override
    public List<String> lrange(final String key, final long start, final long end) {
        return execute(new IExecutor<List<String>>() {
            @Override
            public List<String> run(Jedis jedis) {
                return jedis.lrange(key, start, end);
            }
        }, key);
    }

    public <T> List<T> lrangeStartEnd(final String key, final long start, final long end, final Class<T> clazz) {
        return execute(new IExecutor<List<T>>() {
            @Override
            public List<T> run(Jedis jedis) {
                List<T> list = typeConvert.toList(jedis.lrange(key, start, end), clazz);
                return list;
            }
        }, key);
    }

    @Override
    public <T> T lindex(final String key, final long index, final Class<T> clazz) {
        return execute(new IExecutor<T>() {
            @Override
            public T run(Jedis jedis) {
                return typeConvert.toType(jedis.lindex(key, index), clazz);
            }
        }, key);
    }

    @Override
    public <T> Long lpush(final String key, final T value) {
        if (value == null) return null;
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.lpush(key, typeConvert.toString(value));
            }
        }, key);
    }

    @Override
    public <T> Long lpushList(final String key, final List<T> valueList) {
        if (CollectionUtils.isEmpty(valueList)) {
            return null;
        }
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                int length = valueList.size();
                String[] values = new String[length];
                for (int i = 0; i < length; i++) {
                    values[i] = typeConvert.toString(valueList.get(i));
                }
                return jedis.lpush(key, values);
            }
        }, key);
    }

    @Override
    public <T> T lpop(final String key, final Class<T> clazz) {
        return execute(new IExecutor<T>() {
            @Override
            public T run(Jedis jedis) {
                return typeConvert.toType(jedis.lpop(key), clazz);
            }
        }, key);
    }

    @Override
    public <T> Long rpush(final String key, final T value) {
        if (value == null) return null;
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.rpush(key, typeConvert.toString(value));
            }
        }, key);
    }

    @Override
    public <T> T rpop(final String key, final Class<T> clazz) {
        return execute(new IExecutor<T>() {
            @Override
            public T run(Jedis jedis) {
                return typeConvert.toType(jedis.rpop(key), clazz);
            }
        }, key);
    }

    @Override
    public <T> Long lrem(final String key, T value) {
        return lrem(key, 0, value);
    }

    @Override
    public <T> Long lrem(final String key, final long count, final T value) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.lrem(key, count, typeConvert.toString(value));
            }
        }, key);
    }


    //===== zset =====


    @Override
    public <T> Long zadd(final String key, final Number score, final T member) {
        if (score == null || member == null) return null;
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.zadd(key, score.doubleValue(), typeConvert.toString(member));
            }
        }, key);
    }

    @Override
    public <T> Long zrem(final String key, final T member) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.zrem(key, typeConvert.toString(member));
            }
        }, key);
    }

    @Override
    public Long zremrangeByRank(final String key, final long start, final long size) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                long end = getEnd(start, size);
                return jedis.zremrangeByRank(key, start, end);
            }
        }, key);
    }

    @Override
    public Long zremrangeByScore(final String key, final Number min, final Number max) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.zremrangeByScore(key, min.doubleValue(), max.doubleValue());
            }
        }, key);
    }


    @Override
    public long zcard(final String key) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.zcard(key);
            }
        }, key);
    }

    @Override
    public <T> Number zincrby(final String key, final Number increment, final T member) {
        return execute(new IExecutor<Number>() {
            @Override
            public Number run(Jedis jedis) {
                return jedis.zincrby(key, increment.doubleValue(), typeConvert.toString(member));
            }
        }, key);
    }


    @Override
    public <T> long zrank(final String key, final T member) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                Long rank = jedis.zrank(key, typeConvert.toString(member));
                return rank == null ? -1 : rank;
            }
        }, key);
    }

    @Override
    public <T> long zrevrank(final String key, final T member) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                Long rank = jedis.zrevrank(key, typeConvert.toString(member));
                return rank == null ? -1 : rank;
            }
        }, key);
    }

    @Override
    public <T> Number zscore(final String key, final T member) {
        return execute(new IExecutor<Number>() {
            @Override
            public Number run(Jedis jedis) {
                return jedis.zscore(key, typeConvert.toString(member));
            }
        }, key);
    }

    @Override
    public long zcount(final String key, final Number min, final Number max) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.zcount(key, min.doubleValue(), max.doubleValue());
            }
        }, key);
    }

    @Override
    public <T> Set<T> zrange(final String key, final long start, final long size, final Class<T> clazz) {
        return execute(new IExecutor<Set<T>>() {
            @Override
            public Set<T> run(Jedis jedis) {
                long end = getEnd(start, size);
                return typeConvert.toSet(jedis.zrange(key, start, end), clazz);
            }
        }, key);
    }

    @Override
    public <T> Set<T> zrange(final String key, final int start, final int end, final Class<T> clazz) {
        return execute(new IExecutor<Set<T>>() {
            @Override
            public Set<T> run(Jedis jedis) {
                return typeConvert.toSet(jedis.zrange(key, start, end), clazz);
            }
        }, key);

    }

    @Override
    public <T> Set<Zuple<T>> zrangeWithScores(final String key, final long start,
                                              final long size, final Class<T> clazz) {
        return execute(new IExecutor<Set<Zuple<T>>>() {
            @Override
            public Set<Zuple<T>> run(Jedis jedis) {
                long end = getEnd(start, size);
                return toTuples(jedis.zrangeWithScores(key, start, end), clazz);
            }
        }, key);
    }

    @Override
    public <T> Set<T> zrevrange(final String key, final long start, final long size,
                                final Class<T> clazz) {
        return execute(new IExecutor<Set<T>>() {
            @Override
            public Set<T> run(Jedis jedis) {
                long end = getEnd(start, size);
                return typeConvert.toSet(jedis.zrevrange(key, start, end), clazz);
            }
        }, key);
    }

    @Override
    public <T> Set<Zuple<T>> zrevrangeWithScores(final String key, final long start,
                                                 final long size, final Class<T> clazz) {
        return execute(new IExecutor<Set<Zuple<T>>>() {
            @Override
            public Set<Zuple<T>> run(Jedis jedis) {
                long end = getEnd(start, size);
                return toTuples(jedis.zrevrangeWithScores(key, start, end), clazz);
            }
        }, key);
    }

    @Override
    public <T> Set<T> zrangeByScore(final String key, final Number min, final Number max,
                                    final Class<T> clazz) {
        return execute(new IExecutor<Set<T>>() {
            @Override
            public Set<T> run(Jedis jedis) {
                return typeConvert.toSet(jedis.zrangeByScore(key, min.doubleValue(), max.doubleValue()), clazz);
            }
        }, key);
    }

    @Override
    public <T> Set<Zuple<T>> zrangeByScoreWithScores(final String key, final Number min,
                                                     final Number max, final Class<T> clazz) {
        return execute(new IExecutor<Set<Zuple<T>>>() {
            @Override
            public Set<Zuple<T>> run(Jedis jedis) {
                return toTuples(jedis.zrangeByScoreWithScores(key, min.doubleValue(), max.doubleValue()), clazz);
            }
        }, key);
    }

    @Override
    public <T> Set<T> zrevrangeByScore(final String key, final Number min, final Number max,
                                       final Class<T> clazz) {
        return execute(new IExecutor<Set<T>>() {
            @Override
            public Set<T> run(Jedis jedis) {
                return typeConvert.toSet(jedis.zrevrangeByScore(key, min.doubleValue(), max.doubleValue()), clazz);
            }
        }, key);
    }

    @Override
    public <T> Set<Zuple<T>> zrevrangeByScoreWithScores(final String key,
                                                        final Number min, final Number max, final Class<T> clazz) {
        return execute(new IExecutor<Set<Zuple<T>>>() {
            @Override
            public Set<Zuple<T>> run(Jedis jedis) {
                return toTuples(jedis.zrevrangeByScoreWithScores(key, min.doubleValue(), max.doubleValue()), clazz);
            }
        }, key);
    }


    @Override
    public long zunionStore(final String newKey, final String... keys) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.zunionstore(newKey, keys);
            }
        }, ArrayUtils.addAll(new String[]{newKey}, keys));
    }

    @Override
    public long zinterStore(final String newKey, final String... keys) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.zinterstore(newKey, keys);
            }
        }, ArrayUtils.addAll(new String[]{newKey}, keys));
    }

    @Override
    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    @Override
    public Set<String> hkeys(final String key) {
        return execute(new IExecutor<Set<String>>() {
            @Override
            public Set<String> run(Jedis jedis) {
                Set<String> keySet = jedis.hkeys(key);
                return keySet;
            }
        }, key);
    }

    protected <T> Set<Zuple<T>> toTuples(Set<Tuple> tuples, Class<T> clazz) {
        if (tuples == null) return Collections.emptySet();
        Set<Zuple<T>> zuples = new LinkedHashSet<Zuple<T>>(tuples.size());
        for (Tuple tuple : tuples) {
            T element = typeConvert.toType(tuple.getElement(), clazz);
            Zuple<T> zuple = new Zuple<T>(element, tuple.getScore());
            zuples.add(zuple);
        }
        return zuples;
    }

    private long getEnd(long start, long size) {
        return size < 0 ? -1 : start + size - 1;
    }

    protected interface IExecutor<T> {
        T run(Jedis jedis);
    }

    protected <T> T execute(IExecutor<T> executor, String... keys) {
//        //可以再支持不仅是Key，value，用泛型G，识别是String，进行trim()
        if (keys == null || keys.length == 0) return null;
        for (final String key : keys) {
            //有一个Key有问题，就返回null
            if (key == null || key.trim().length() == 0) return null;
        }
        Jedis jedis = null;
        T result = null;
        try {
            jedis = jedisPool.getResource();
            result = executor.run(jedis);
        } catch (Exception e) {
            logger.error("keys: " + keys, e);
            closeResource(jedis,false);
        } finally {
            closeResource(jedis,true);
        }
        return result;

    }

    public void closeResource(Jedis jedis, boolean isOK) {
        if (null != jedis) {
            if(!isOK){
                jedisPool.returnBrokenResource(jedis);
            }else{
                jedisPool.returnResource(jedis);
            }
        }
    }

    @Override
    public long hsetnx(String key,String filed, String value) {
        return execute(new IExecutor<Long>() {
            @Override
            public Long run(Jedis jedis) {
                return jedis.hsetnx(key,filed,value);
            }
        },key);
    }
}











