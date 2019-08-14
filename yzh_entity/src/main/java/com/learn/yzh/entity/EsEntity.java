package com.learn.yzh.entity;

/**
 * @program: yzh->EsEntity
 * @description: EsEntity
 * @author: yangzhanghui
 * @create: 2019-08-14 18:55
 **/
public final class EsEntity<T> {

    private String id;
    private T data;

    public EsEntity() {
    }

    public EsEntity(String id, T data) {
        this.data = data;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}