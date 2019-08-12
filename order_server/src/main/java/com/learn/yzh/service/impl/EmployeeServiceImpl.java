package com.learn.yzh.service.impl;

import com.learn.yzh.entity.Employee;
import com.learn.yzh.mapper.EmployeeMapper;
import com.learn.yzh.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
