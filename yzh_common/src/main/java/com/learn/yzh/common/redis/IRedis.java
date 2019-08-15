package com.learn.yzh.common.redis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRedis {

	public static final String TYPE_NONE = "none";
	public static final String TYPE_STRING = "string";
	public static final String TYPE_LIST = "list";
	public static final String TYPE_SET = "set";
	public static final String TYPE_ZSET = "zset";
	public static final String TYPE_HASH = "hash";

	boolean exists(String key);

	long expire(String key, int seconds);

	String type(String key);

	long ttl(String key);

	/**
	 * 将key换一个名字
	 */
	String rename(String oldkey, String newkey);

	/**
	 * 新版的set
	 * @param key
	 * @param value
	 * @return
	 */
	<T> String set(String key, T value);

	<T> boolean setNX(String key, T value);
	/**
	 * 新版的set
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	<T> String set(String key, T value, int seconds);

	/**
	 * 插入byte的set
	 * @param key
	 * @param value
	 * @param seconds 有效时间（秒）
	 * @return
	 */
	String set(final byte[] key, final byte[] value, int seconds);

	/**
	 * 新版的get
	 * @param key
	 * @param clazz
	 * @return
	 */
	<T> T get(String key, Class<T> clazz);

	/**
	 * byte参数及返回值
	 * @param key
	 * @return
	 */
	byte[] get(final byte[] key);

	Set<String> keys(String key);
	<T> List<T> mget(String[] keys, final Class<T> clazz);
	<T> List<T> mget(List<String> keys, final Class<T> clazz);

	long del(String pk);
	long incr(String key);
	long decr(String key);

	//------ Hash -------

	<T, F> long hset(String key, F field, T value);
	String hmset(final String key, final Map<String, String> hashMap);
	<F> boolean hexists(String key, F field);
	<T, F> T hget(String key, F field, Class<T> clazz);
	<T, F> Map<F, T> hgetAll(String key, Class<F> keyClazz, Class<T> valueClazz);
	<F> Long hdel(String key, F field);
	Long hdel(final String key, final String... fields);
	long hincrBy(String key, String field, long value);
	long incrBy(String key, long value);
	long decrBy(String key, long value);

	/**
	 * 只在 key 指定的哈希集中不存在指定的字段时，设置字段的值。如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key 关联。如果字段已存在，该操作无效果。
	 * @param key
	 * @param value
	 * @return
	 */
	public long hsetnx(String key, String filed, String value);

	//------ Set -------

	<T> long sadd(String key, T member);
	<T> long srem(String key, T member);

	long sadds(String key, String[] members);

	<T> Set<T> sdiff(Class<T> clazz, String... key);

	<T> Set<T> sunion(Class<T> clazz, String... key);

	long sunionStore(String key, String... keys);

	<T> Set<T> smembers(String key, Class<T> clazz);
	<T> boolean sisMember(String key, T member);
	long scard(String key);
	<T> T spop(String key, Class<T> clazz);
	<T> T srandMember(String key, Class<T> clazz);

	//------ List -------

	Long llength(String key);
	String ltrim(String key, long startIndex, long endIndex);
	<T> List<T> lrange(String key, long start, long size, Class<T> clazz);
	List<String> lrange(String key, long start, long end);
	<T> T lindex(String key, long index, Class<T> clazz);
	<T> Long lpush(String key, T value);
	<T> Long lpushList(String key, List<T> valueList);
	<T> T lpop(String key, Class<T> clazz);
	<T> Long rpush(String key, T value);
	<T> T rpop(String key, Class<T> clazz);
	<T> Long lrem(String key, T value);
	<T> Long lrem(String key, long count, T value);


	//------ ZSet -------

	<T> Long zadd(String key, Number score, T member);
	<T> Long zrem(String key, T member);
	Long zremrangeByRank(String key, long start, long size);
	Long zremrangeByScore(String key, Number min, Number max);

	long zcard(String key);
	<T> Number zincrby(String key, Number increment, T member);

	<T> long zrank(String key, T member);
	<T> long zrevrank(String key, T member);

	<T> Number zscore(String key, T member);
	long zcount(String key, Number min, Number max);

	<T> Set<T> zrange(String key, long start, long size, Class<T> clazz);
	<T> Set<T> zrange(String key, int start, int end, Class<T> clazz);
	<T> Set<Zuple<T>> zrangeWithScores(String key, long start, long size, Class<T> clazz);

	<T> Set<T> zrevrange(String key, long start, long size, Class<T> clazz);
	<T> Set<Zuple<T>> zrevrangeWithScores(String key, long start, long size, Class<T> clazz);

	<T> Set<T> zrangeByScore(String key, Number min, Number max, Class<T> clazz);
	<T> Set<Zuple<T>> zrangeByScoreWithScores(String key, Number min, Number max, Class<T> clazz);

	<T> Set<T> zrevrangeByScore(String key, Number min, Number max, Class<T> clazz);
	<T> Set<Zuple<T>> zrevrangeByScoreWithScores(String key, Number min, Number max, Class<T> clazz);

	long zunionStore(String newKey, String... keys);
	long zinterStore(String newKey, String... keys);


	Jedis getJedis();


	Set<String> hkeys(String cacheName);

	/**
	 * @author yuands
	 * @date 2019/5/7
	 * @desc 获取一个锁对象，调用 lock() 和 unlock() 可以限定指定代码块根据key只执行一次
	 * @param key
	 * @return redis锁对象
	 */
	RedisLock getLock(String key);

	/**
	 * @desc 获取一个锁对象，调用 lock() 和 unlock() 可以限定指定代码块根据key只执行一次
	 * @param key  锁定的key
	 * @param lockTime 锁定的时间(秒)
	 * @return
	 */
	public RedisLock getLock(String key, int lockTime);
}
