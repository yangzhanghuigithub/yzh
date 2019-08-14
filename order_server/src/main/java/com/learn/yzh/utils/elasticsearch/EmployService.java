package com.learn.yzh.utils.elasticsearch;

import com.learn.yzh.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployService {

    long count();

    Employee save(Employee Employee);

    void delete(Employee Employee);

    Iterable<Employee> getAll();

    List<Employee> getByName(String name);

    Page<Employee> pageQuery(Integer pageNo, Integer pageSize, String kw);

}