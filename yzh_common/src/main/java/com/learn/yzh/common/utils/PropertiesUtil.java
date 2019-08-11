package com.learn.yzh.common.utils;


public class PropertiesUtil {
	/**
	 * 在properties文件中读取参数
	 * @param propPath properties文件路径
	 * @param param 参数名
	 * @return
	 */
	public static String getParamInProp(String propPath, String param){

		return PlatformProperties.getProperty(param);
	}
	
	public static void main(String[] args) {
		String ss = PropertiesUtil.getParamInProp("common.properties", "upfile_url");
		//System.out.println(ss);
	}
}