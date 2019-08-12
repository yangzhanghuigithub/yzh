package com.learn.yzh.config;

import com.learn.yzh.entity.Hr;
import com.learn.yzh.entity.Role;
import io.github.lvyahui8.spring.aggregate.facade.DataBeanAggregateQueryFacade;
import io.github.lvyahui8.spring.annotation.DataConsumer;
import io.github.lvyahui8.spring.annotation.DataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

/**
 * @program: yj->AggregateCompnent
 * @description: 并行测试
 * @author: yangzhanghui
 * @create: 2019-08-05 14:18
 **/
@Component
public class AggregateCompnent {

    @Autowired
    private DataBeanAggregateQueryFacade dataBeanAggregateQueryFacade;

    @DataProvider("hrFullData")
    public Hr userFullData(@DataConsumer("getHr") Hr hr,
                           @DataConsumer("getHrRoles") List<Role> roles) {
        hr.setRoles(roles);
        return hr;
    }

    public Hr getUserFinal(String userId) throws InterruptedException,
            IllegalAccessException, InvocationTargetException {
        return dataBeanAggregateQueryFacade.get("hrFullData",
                Collections.singletonMap("hrId", userId), Hr.class);
    }
}
