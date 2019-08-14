package com.learn.yzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.yzh.entity.Employee;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yj
 * @since 2019-07-25
 */
public interface EmployeeService extends IService<Employee> {

    Employee updateEmployeeById(Employee employee);

}
