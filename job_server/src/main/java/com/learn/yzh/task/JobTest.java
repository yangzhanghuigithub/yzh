package com.learn.yzh.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: yj->JobTest
 * @description: 测试定时任务
 * @author: yangzhanghui
 * @create: 2019-07-30 13:11
 **/
@Component
public class JobTest {
    @Scheduled(cron = "0/1 * * * * ?")
    public void test(){
        System.out.println("---------------aaaaaaaaaaaaa---------------");
    }
}
