package com.learn.yzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.yzh.entity.HrRole;
import com.learn.yzh.mapper.HrRoleMapper;
import com.learn.yzh.service.HrRoleService;
import io.github.lvyahui8.spring.annotation.DataProvider;
import io.github.lvyahui8.spring.annotation.InvokeParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yj
 * @since 2019-07-25
 */
@Service
public class HrRoleServiceImpl extends ServiceImpl<HrRoleMapper, HrRole> implements HrRoleService {

    @Autowired
    private HrRoleMapper hrRoleMapper;

    @Override
    @DataProvider("getHrRoles")
    public List<HrRole> getHrRoles(@InvokeParameter("hrId") String hrId) {
        return hrRoleMapper.selectList(new QueryWrapper<HrRole>().eq("hrId", hrId));
    }
}
