package com.learn.yzh.common.dao;

import com.learn.yzh.common.utils.KeyValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据Entity类
 */
public  class DataEntity implements Serializable {


	public final static String STRING = "String";
	public final static String NUMBER = "Number";
	public final static String DATE = "Date";
	private static final long serialVersionUID = -1L;
	private String orderByClause;
	protected List<KeyValue> whereClause = new ArrayList<KeyValue>();

	private boolean distinct;

	public String getOrderByClause() {
		return orderByClause;
	}
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}
	public void resetWhereClause() {
		whereClause.clear();
	}

	public void addWhereClause(String column, Object columnValue, String columnType) {
		KeyValue keyvalue = new KeyValue(column, columnValue, columnType);
		whereClause.add(keyvalue);
	}
	public boolean isDistinct() {
		return distinct;
	}
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	
}
