package com.learn.yzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.yzh.entity.Hr;
import io.github.lvyahui8.spring.annotation.DataProvider;
import io.github.lvyahui8.spring.annotation.InvokeParameter;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yj
 * @since 2019-07-25
 */
public interface HrService extends IService<Hr> {

    @DataProvider("getHr")
    Hr getHr(@InvokeParameter("hrId") String hrId);
}
