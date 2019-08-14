package com.learn.yzh.common.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis配置类
 * ClassName:RedisCacheConfiguration
 *
 * @author yw
 * @create 2017-11-10 15:15
 */
@Configuration
//@PropertySource(value = "{classpath:/redis.properties, classpath:/application.properties}")
@EnableCaching
public class RedisPoolConfiguration extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.database}")
    private Integer database;

    @Value("${spring.redis.port}")
    private int port;

    private int timeout = 3000;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    private long maxWaitMillis = 30000;

    @Value("${spring.redis.password}")
    private String password;

    @Bean(name = "redisCacheManager")
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("entities");
    }

    @Bean
    public JedisPool redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        if(database == null) {
            database = 5;
        }
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);

        return jedisPool;
    }
}
