package com.learn.yzh.common.utils;

import java.util.List;

/**
 * SESSION工具类
 * @author Lomis
 *
 */
public class SessionFactory {
	
	public static SessionFactory _instance;
	
	/**
	 * 单例模式创建对象
	 * @return
	 */
	public static SessionFactory getInstance(){
		if(_instance == null){
			return new SessionFactory();
		}
		return _instance;
	}
	
	private String timeOut = PlatformProperties.getProperty("timeout"); 
	
	/**
	 * 构造方法, 默认超时时间为20min
	 */
	private SessionFactory(){
		if(timeOut == null || timeOut.trim().length() == 0){
			timeOut = String.valueOf(20 * 60 * 1000);
		}
	}
	/**
	 * 根据USERID,IP拼凑一个伪装的SESSIONID
	 * @param userId
	 * @param ip
	 * @return
	 */
	public String getSid(String userId, String ip) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(userId).append("_").append(System.currentTimeMillis()).append("_").append(ip).append("$").append(IdUtils.uuid2());
		return buffer.toString();
	}
	
	/**
	 * 判断session是否超时
	 * @param sid
	 * @return
	 */
	public boolean isTimeout(String sid){
		Long loginStartTime = this.getLoginStartTime(sid);
		Long currentTime = System.currentTimeMillis();
		Long differenceTime = currentTime - loginStartTime;
		return Long.valueOf(timeOut) >  differenceTime;
	}
	
	/**
	 * 获取登陆时间字符串
	 * @param sid
	 * @return
	 */
	private Long getLoginStartTime(String sid){
		List<String> array = StringUtils.splitWithList(sid, "_");
		return Long.valueOf(array.get(1));
	}
}
