package com.learn.yzh.component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.learn.yzh.service.HelloService;
import org.springframework.stereotype.Component;

/**
 * @program: yzh->DubboConsumer
 * @description: DobboConsumer
 * @author: yangzhanghui
 * @create: 2019-08-22 13:33
 **/
@Component
public class DubboConsumer {

    @Reference
    private HelloService helloService;

    public String callHello(){
        return helloService.hello();
    }
}
