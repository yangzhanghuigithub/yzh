package com.learn.yzh.common.utils;

public class RandomNoUtils {
	
	private static RandomNoUtils _instance;
	private int index;
	
	/**
	 * 获取创建对象
	 * @return
	 */
	public static RandomNoUtils getInstance () {
		if (_instance == null) {
			_instance = new RandomNoUtils();
		}
		return _instance;
	}
	
	/**
	 * 获取随机数字编码
	 * @return
	 */
	public synchronized String getRandomNo() {
		StringBuffer sb = new StringBuffer();
		if (index == 10) {
			index = 0;
		}
		sb.append(index++);
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}

}
