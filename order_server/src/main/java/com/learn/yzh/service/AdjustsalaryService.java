package com.learn.yzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.yzh.config.LogLevelConfig;
import com.learn.yzh.entity.Adjustsalary;
import com.learn.yzh.service.impl.AdjustsalaryServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yj
 * @since 2019-07-25
 */
@FeignClient(name = "base-server", fallback = AdjustsalaryServiceImpl.class, configuration = LogLevelConfig.class)
public interface AdjustsalaryService extends IService<Adjustsalary> {

}
