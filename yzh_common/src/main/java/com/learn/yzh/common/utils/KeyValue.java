/*
 * @(#)KeyValue.java
 *
 * Copyright (c) 2013-2014 ZhongShiAn
 */
package com.learn.yzh.common.utils;

import java.io.Serializable;

/**
 * 键/值Class
 * 
 * @author wl
 *
 */
public class KeyValue implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2422686440611836535L;
	/** 键 */
	private String key;
	/** 值 */
	private Object value;
	
	/** 类型 */
	private String type;
	
	/**
	 * 默认构造函数
	 */
	public KeyValue() {};
	
	/**
	 * 构造函数
	 * @param key 键 
	 * @param value 值
	 */
	public KeyValue(String key, Object value, String type) {
		this.key = key;
		this.value = value;
		this.type = type;
	}

	/**
	 * 键取得
	 * @return 键
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 键设定
	 * @param key 键
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 值取得
	 * @return 值
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 值设定
	 * @param value 值
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}