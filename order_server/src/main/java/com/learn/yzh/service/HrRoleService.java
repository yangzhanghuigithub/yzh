package com.learn.yzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.yzh.entity.HrRole;
import io.github.lvyahui8.spring.annotation.DataProvider;
import io.github.lvyahui8.spring.annotation.InvokeParameter;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yj
 * @since 2019-07-25
 */
public interface HrRoleService extends IService<HrRole> {
    @DataProvider("getHrRoles")
    List<HrRole> getHrRoles(@InvokeParameter("hrId") String hrId);
}
