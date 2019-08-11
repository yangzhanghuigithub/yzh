package com.learn.yzh.model;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class Book {
    @TableId
    private String id; //价格
    private Integer price; //书名
    private String name; //简介
    private String info; //出版社
    private String publish; //创建时间
    private Date createTime; //修改时间
    private Date updateTime;
}