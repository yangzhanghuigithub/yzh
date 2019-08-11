package com.learn.yzh.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 属性配置
 * @author hapday
 * @date 2016年7月13日 下午6:22:01
 */
public class PropertiesUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);
	
	/**
     * 读取属性文件
     * @param fileName 文件名
     * @return
     */
    public static Properties getProperties(String fileName) {
    	LOGGER.info("开始读取配置文件【{}】...", new Object[]{fileName} );
        
        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); 
        
        Properties properties = new Properties();
        try {
            properties.load(bufferedReader);
            if(null != inputStream){
                inputStream.close();
            }
        } catch (IOException e) {
        	LOGGER.error("Exception:【{}】"+e);
        }
        
        LOGGER.info("读取配置文件【{}】结束...", new Object[]{fileName} );
        
        return properties;
    }
}
