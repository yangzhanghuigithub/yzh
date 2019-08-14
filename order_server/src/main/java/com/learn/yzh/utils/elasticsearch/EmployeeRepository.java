package com.learn.yzh.utils.elasticsearch;

import com.learn.yzh.entity.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends ElasticsearchRepository<Employee, String> {

}