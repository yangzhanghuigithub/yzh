package com.learn.yzh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.yzh.entity.Employee;
import com.learn.yzh.mapper.EmployeeMapper;
import com.learn.yzh.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yj
 * @since 2019-07-25
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Cacheable(value = "emp" , key = "#root.args[0]" , condition = "#id > 0" , unless = "#result eq null")
    @Override
    public Employee getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    @CachePut(value = "emp", key = "#root.args[0].id", unless = "#result eq null ")
    public Employee updateEmployeeById(Employee entity) {
        boolean res = super.updateById( entity );
        if (res){
            return entity;
        }
        return null;
    }

    @CacheEvict(value = "emp", key = "#root.args[0]", condition = "#result eq true")
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById( id );
    }
}
