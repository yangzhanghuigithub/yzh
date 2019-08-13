package com.learn.yzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.yzh.entity.Role;
import com.learn.yzh.entity.User;
import com.learn.yzh.entity.UserRole;
import com.learn.yzh.mapper.RoleMapper;
import com.learn.yzh.mapper.UserMapper;
import com.learn.yzh.mapper.UserRoleMapper;
import com.learn.yzh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzh
 * @since 2019-03-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        //查数据库
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("name", name));
        if (null != user) {
            List<UserRole> userRoles = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
            List<String> roleIds = userRoles.stream().map(ur -> ur.getRoleId()).collect(Collectors.toList());
            List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().in("id", roleIds));
            user.setRoles(roles);
        }

        return user;
    }
}
