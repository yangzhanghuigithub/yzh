package com.learn.yzh.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 资源文件工具类
 * @author Lomis
 *
 */
public class PlatformProperties {
	private static final Logger logger = LoggerFactory.getLogger(PlatformProperties.class);
	private static List<ResourceBundle> platforms = new ArrayList<ResourceBundle>();

	/**
	 * 构造方法
	 * @param paths
	 */
	private PlatformProperties(List<String> paths) {
		try {

			for(int i = 0 ; i < paths.size() ; i++){
				String path = paths.get(i);
				if(path != null ){
					loadProperties(path,i);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 从给定的配置文件中加载参数配置
	 * @param file
	 * @throws Exception
	 */
	private static void loadProperties(String file,int i) throws Exception {
		platforms.add(ResourceBundle.getBundle(file, Locale.getDefault()));
		printResouceBundle(platforms.get(i));
		
	}

	/**
	 * 输出资源参数文件的内容
	 * @param rb
	 */
	private static void printResouceBundle(ResourceBundle rb) {
		logger.info("\n开始加载系统参数信息");
		if (rb != null) {
			Enumeration<String> keys = rb.getKeys();
			while (keys.hasMoreElements()) {
				String k = keys.nextElement();
				logger.info("\n" + k + " = " + rb.getString(k));
			}
		}
		logger.info("\n完成加载系统参数信息");
	}

	/**
	 * 从配置文件取信息
	 * @param key
	 * @return String
	 */
	public static String getProperty(String key) {
		return getProperty(platforms.get(0), key, 0);
	}
	
	private static String getProperty(ResourceBundle rb, String key, int i) {
		try {
			return rb.getString(key);
		} catch (MissingResourceException e) {
			i++;
			if (platforms.size() > i) {
				return getProperty(platforms.get(i), key, i);
			}
		}
		return "";
	}
		
}
