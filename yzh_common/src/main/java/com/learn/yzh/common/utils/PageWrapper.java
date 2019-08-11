package com.learn.yzh.common.utils;


import com.learn.yzh.common.page.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class PageWrapper<T>{
	private Page<T> pageBean; 				// 分页组件
	private HttpServletRequest request;
	private Map<String, Object> result; 	// 返回到页面的结果
	private int page; 						// 每页记录的开始位置
	private int rows; 						// 每页记录的数目长度
	private String sort; 					// 要排序的字段名称,多条件逗号分开
	private String order;					// 排序方式，升序，降序,多条件逗号分开
	private Map<String, Object> searchMap = new HashMap<String, Object>(); // 搜索map
	
	private String[] columns = null;
	
	public PageWrapper(HttpServletRequest request) {
		if (null == request) {
			return;
		}
		this.request = request;
		this.result = new HashMap<String, Object>();
		pageBean = new Page<T>();
		if (request.getParameter("page") != null) {
			this.page = Integer.parseInt(request.getParameter("page"));
		}
		if (request.getParameter("rows") != null) {
			this.rows = Integer.parseInt(request.getParameter("rows"));
		}
		// 要排序的字段
		this.sort = request.getParameter("sort");
		this.order = request.getParameter("order");

		if (page == -1) {// 长度为-1，不支持分页
			pageBean.setPageNo(1);
			pageBean.setPageSize(100);
		} else {
			// 计算下一个页码
			// int page = (int) Math.floor(sStart/sLength)+1;
			pageBean.setPageNo(page);
			pageBean.setPageSize(rows);
		}
		// 设置排序字段,多个排序字段时用','分隔.
		if (sort != null && sort.length() > 0) {
			pageBean.setOrderBy(sort);
		}
		// 设置排序方式，可选值为desc或asc,多个排序字段时用','分隔.
		if (order != null && order.length() > 0) {
			pageBean.setOrder(order);
		}
	}
	
	public PageWrapper(Map<String, String> keys) {
		this.result = new HashMap<String, Object>();
		pageBean = new Page<T>();
		if (keys.get("page") != null) {
			this.page = Integer.parseInt(keys.get("page"));
		}
		if (keys.get("rows") != null) {
			this.rows = Integer.parseInt(keys.get("rows"));
		}
		this.sort = keys.get("sort");
		this.order = keys.get("order");

		if (page == -1) {// 长度为-1，不支持分页
			pageBean.setPageNo(1);
			pageBean.setPageSize(100);
		} else {
			// 计算下一个页码
			// int page = (int) Math.floor(sStart/sLength)+1;
			pageBean.setPageNo(page);
			pageBean.setPageSize(rows);
		}
		// 设置排序字段,多个排序字段时用','分隔.
		if (sort != null && sort.trim().length() > 0) {
			pageBean.setOrderBy(sort);
		}
		// 设置排序方式，可选值为desc或asc,多个排序字段时用','分隔.
		if (order != null && order.trim().length() > 0) {
			pageBean.setOrder(order);
		}
	}

	public Page<T> getPageBean() {
		return pageBean;
	}

	public void setPageBean(Page<T> pageBean) {
		this.pageBean = pageBean;
	}

	public Map<String, Object> getResult() {
		result.put("total", pageBean.getTotalCount() == -1 ? 0 : pageBean.getTotalCount()); // 过滤前总记录数
		result.put("rows", pageBean.getResult()); // 返回的列表数据
		return result;
	}

	// public Map<String, Object> getResultMap() {
	// result.put("total",pageBean.getTotalCount()==-1?0:pageBean.getTotalCount());
	// //过滤前总记录数
	// result.put("rows",pageBean.getResult());//过滤后总记录数
	// return result;
	// }
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	// 返回 搜索和排序条件组合
	public Map<String, Object> getConditionsMap() {
		return searchMap;
	}

	// 添加搜索条件
	public void addSearch(String key, Object value) {
		this.searchMap.put(key, value);
	}

	// 添加一些额外的数据到页面
	public void addResult(String key, Object value) {
		this.result.put(key, value);
	}

	public String getSort() {
		return sort;
	}
	
	/**
	 * 设置排序字段,多个排序字段时用','分隔.
	 */
	public void setSort(String sort) {
		pageBean.setOrderBy(sort);// 把排序设置到Page上
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式,可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(String order) {
		pageBean.setOrder(order);// 把排序设置到Page上
		this.order = order;
	}

	public String toString() {
		return this.getPageBean().toString();
	}
	
}
