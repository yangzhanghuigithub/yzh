package com.learn.yzh.common.redis;

/**
 * @AUTHOR yuands
 * @DATE 2019/5/7
 * @DESC redis锁
 *
 */
public interface RedisLock {

    /**
     * @author yuands
     * @date 2019/5/7
     * @desc 该方法可以限制拥有相同业务标识的操作只执行一次
     * @return true 锁获取成功，false 锁获取失败
     */
    boolean lock();

    /**
     * @author yuands
     * @date 2019/5/7
     * @desc 释放锁
     */
    void unlock();
}
