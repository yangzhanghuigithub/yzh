package com.learn.yzh.test;

import com.learn.yzh.TestConfig;
import com.learn.yzh.component.DubboConsumer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: yzh->DubboConsumerTest
 * @description: DubboConsumerTest
 * @author: yangzhanghui
 * @create: 2019-08-22 13:36
 **/
public class DubboConsumerTest extends TestConfig {

    @Autowired
    private DubboConsumer dubboConsumer;

    @Test
    public void test(){
        String s = dubboConsumer.callHello();
        System.out.println(s);
    }
}
