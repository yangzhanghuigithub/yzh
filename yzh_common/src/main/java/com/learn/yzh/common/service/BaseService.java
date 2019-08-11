package com.learn.yzh.common.service;

/**
 * 基础service所有service实现类都需要继承此类
 * ClassName:BaseService
 *
 * @author wl
 */
public interface BaseService<P, R>  {

    /**
     * 业务层处理
     * @param param 参数
     * @return 处理结果
     */
    public R service(P param);

}
