package com.learn.yzh.service.impl;

import com.learn.yzh.entity.Role;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yj
 * @since 2019-07-25
 */
@Service
public class RoleServiceImpl {
    public Role testFeign(){
        return new Role();
    }
}
