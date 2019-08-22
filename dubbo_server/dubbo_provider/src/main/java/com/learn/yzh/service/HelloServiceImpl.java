package com.learn.yzh.service;

/**
 * Created by caibosi on 2018-08-02.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello() {
        return "hello at " + System.currentTimeMillis();
    }
}
