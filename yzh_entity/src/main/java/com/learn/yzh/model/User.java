package com.learn.yzh.model;


import lombok.Data;

import java.util.Date;

/**
 * @program: yj->User
 * @description: User
 * @author: yangzhanghui
 * @create: 2019-07-27 20:18
 **/
@Data
public class User {
    private int id;

    private String username;

    private int age;

    private Date ctm;
}