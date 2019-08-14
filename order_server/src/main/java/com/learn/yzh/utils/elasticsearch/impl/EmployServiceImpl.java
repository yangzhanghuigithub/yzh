package com.learn.yzh.utils.elasticsearch.impl;

import com.learn.yzh.entity.Employee;
import com.learn.yzh.utils.elasticsearch.EmployeeRepository;
import com.learn.yzh.utils.elasticsearch.EmployService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: yzh->EmployeeServiceImpl
 * @description: EmployeeServiceImpl
 * @author: yangzhanghui
 * @create: 2019-08-13 20:22
 **/
@Service
public class EmployServiceImpl implements EmployService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public long count() {
        return employeeRepository.count();
    }

    @Override
    public Employee save(Employee Employee) {
        return employeeRepository.save(Employee);
    }

    @Override
    public void delete(Employee Employee) {
        employeeRepository.delete(Employee);
//        EmployeeRepository.deleteById(Employee.getSkuId());
    }

    @Override
    public Iterable<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getByName(String name) {
        List<Employee> list = new ArrayList<>();
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", name);
        Iterable<Employee> iterable = employeeRepository.search(matchQueryBuilder);
        iterable.forEach(e->list.add(e));
        return list;
    }

    @Override
    public Page<Employee> pageQuery(Integer pageNo, Integer pageSize, String kw) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("name", kw))
                .withPageable(PageRequest.of(pageNo, pageSize))
                .build();
        return employeeRepository.search(searchQuery);
    }
}