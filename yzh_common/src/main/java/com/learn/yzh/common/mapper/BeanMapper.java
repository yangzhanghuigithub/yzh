/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.learn.yzh.common.mapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.dozer.DozerBeanMapper;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 简单封装Dozer, 实现深度转换Bean<->Bean的Mapper.实现:
 *  
 * 1. 持有Mapper的单例. 
 * 2. 返回值类型转换.
 * 3. 批量转换Collection中的所有对象.
 * 4. 区分创建新的B对象与将对象A值复制到已存在的B对象两种函数.
 * 
 * @author calvin, Lomis
 * @version 2013-01-15
 */
public class BeanMapper {

	/**
	 * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
	 */
	private static DozerBeanMapper dozer = new DozerBeanMapper();

	/**
	 * 基于Dozer转换对象的类型.
	 * 将map转换成对象
	 * @param source 资源数据
	 * @param destinationClass 目标对象类型
	 * @return
	 */
	public static <T> T map(Object source, Class<T> destinationClass) {
		return dozer.map(source, destinationClass);
	}
	
	/**
	 * 将对象转换成Map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object obj) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		try {
			Class<?> c = obj.getClass();
			Method m[] = c.getDeclaredMethods();
			
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName().indexOf("get") == 0) {
					hashMap.put(m[i].getName().substring(3, 4).toLowerCase() + m[i].getName().substring(4), m[i].invoke(obj, new Object[0]));
				}
			}
			if (!"java.lang.Object".equals(c.getSuperclass().getName())) {
				Method sm[] = c.getSuperclass().getDeclaredMethods();
				
				for (int i = 0; i < sm.length; i++) {
					if (sm[i].getName().indexOf("get") == 0) {
						hashMap.put(sm[i].getName().substring(3, 4).toLowerCase() + sm[i].getName().substring(4), sm[i].invoke(obj, new Object[0]));
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return hashMap;
	}

	/**
	 * 基于Dozer转换Collection中对象的类型.
	 * @param sourceList 资源数据列表
	 * @param destinationClass 目标对象类型
	 * @return
	 */
	public static <T> List<T> mapList(Collection<T> sourceList, Class<T> destinationClass) {
		List<T> destinationList = Lists.newArrayList();
		for (Object sourceObject : sourceList) {
			T destinationObject = dozer.map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}
		return destinationList;
	}

	/**
	 * 基于Dozer将对象A的值拷贝到对象B中.
	 */
	public static void copy(Object source, Object destinationObject) {
		dozer.map(source, destinationObject);
	}
	
	/**
	 * 利用反射实现对象之间属性复制 
	 * @param from 源对象
	 * @param to 目标对象
	 * @throws Exception
	 */
	public static void copyProperties(Object from, Object to) throws Exception {  
        copyPropertiesExclude(from, to, null);  
    }
	
	/**
	 * 复制对象属性
	 * @param from 源对象
	 * @param to 目标对象
	 * @param excludsArray 排除对象列表
	 * @throws Exception
	 */
	public static void copyPropertiesExclude(Object from, Object to, String[] excludsArray) throws Exception {  
		List<String> excludesList = null;
		if (excludsArray != null && excludsArray.length > 0) {
			excludesList = Arrays.asList(excludsArray); // 构造列表对象
		}
		Method[] fromMethods = from.getClass().getDeclaredMethods();
		Method[] toMethods   = to.getClass().getDeclaredMethods();
		Map<String, Method> toMethodMap = methodArrToMap(toMethods);
		Method fromMethod     = null, toMethod     = null;
		String fromMethodName = null, toMethodName = null;
		for (int i = 0; i < fromMethods.length; i++) {
			fromMethod     = fromMethods[i];
			fromMethodName = fromMethod.getName();
			if (!fromMethodName.contains("get")) {
				continue;
			}
			// 排除列表检测
			if (excludesList != null && excludesList.contains(fromMethodName.substring(3).toLowerCase())) {
				continue;
			}
			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = toMethodMap.get(toMethodName);
			if (toMethod == null) {
				continue;
			}
			Object value = fromMethod.invoke(from, new Object[0]);
			if (value == null)
				continue;
			// 集合类判空处理
			if (value instanceof Collection) {
				Collection<?> newValue = (Collection<?>) value;
				if (newValue.size() <= 0) {
					continue;
				}
			}
			toMethod.invoke(to, new Object[] { value });
		} 
    }
	
	/** 
     * 对象属性值复制，仅复制指定名称的属性值 
     * @param from  源对象
     * @param to 目标对象
     * @param includsArray 包含对象列表 
     * @throws Exception 
     */  
    public static void copyPropertiesInclude(Object from, Object to, String[] includsArray) throws Exception {  
        List<String> includesList = null;  
        if(includsArray != null && includsArray.length > 0) {  
            includesList = Arrays.asList(includsArray); //构造列表对象  
        } else {  
            return;  
        }  
        Method[] fromMethods = from.getClass().getDeclaredMethods();  
        Method[] toMethods = to.getClass().getDeclaredMethods();  
        Map<String, Method> toMethodMap = methodArrToMap(toMethods);
        Method fromMethod = null, toMethod = null;  
        String fromMethodName = null, toMethodName = null;  
        for (int i = 0; i < fromMethods.length; i++) {  
            fromMethod = fromMethods[i];  
            fromMethodName = fromMethod.getName();  
            if (!fromMethodName.contains("get")){
            	continue;  
            }  
            //排除列表检测  
            String str = fromMethodName.substring(3);  
            if(!includesList.contains(str.substring(0,1).toLowerCase() + str.substring(1))) {  
                continue;  
            }  
            toMethodName = "set" + fromMethodName.substring(3);  
            toMethod = toMethodMap.get(toMethodName);  
            if (toMethod == null){
            	continue;  
            }  
            Object value = fromMethod.invoke(from, new Object[0]);  
            if(value == null){
            	continue;  
            }  
            //集合类判空处理  
            if(value instanceof Collection) {  
                Collection<?> newValue = (Collection<?>)value;  
                if(newValue.size() <= 0){
                	continue;  
                }  
            }  
            toMethod.invoke(to, new Object[] {value});  
        }  
    }
	
	/**
	 * 把method数组转换成map
	 * @param methods
	 * @param name
	 * @return
	 */
	public static Map<String, Method> methodArrToMap(Method[] methods) {
		Map<String, Method> toMethodMap = Maps.newHashMap();
		for (int i = 0; i < methods.length; i++) {
			toMethodMap.put(methods[i].getName(), methods[i]);
		}
		return toMethodMap;
	}
	
	/**
	 * 将一个数据库字段名转成一个java对象属性名
	 * @param s 数据库字段名
	 * @return 转换后的属性名
	 */
	public static String getJavaNameFromDBColumnName(String s) {
		if ("".equals(s)) {
			return s;
		}
		StringBuffer result = new StringBuffer();
		boolean capitalize = true;
		boolean lastCapital = false;
		boolean lastDecapitalized = false;
		String p = null;
		for (int i = 0; i < s.length(); i++) {
			String c = s.substring(i, i + 1);
			if ("_".equals(c) || " ".equals(c) || "-".equals(c)) {
				capitalize = true;
				continue;
			}
			if (c.toUpperCase().equals(c)) {
				if (lastDecapitalized && !lastCapital) {
					capitalize = true;
				}
				lastCapital = true;
			} else {
				lastCapital = false;
			}

			if (capitalize) {
				if (p == null || !p.equals("_")) {
					result.append(c.toUpperCase());
					capitalize = false;
					p = c;
				} else {
					result.append(c.toLowerCase());
					capitalize = false;
					p = c;
				}
			} else {
				result.append(c.toLowerCase());
				lastDecapitalized = true;
				p = c;
			}

		}
		String r = result.toString();
		return r;
	}
	
	/**
     * 对象属性名转成数据库字段名
     * @param str
     * @return
     */
	public static String getDBColumnNameFromJavaName(String str) {
		StringBuffer newStr = new StringBuffer();
		char[] str1 = str.toCharArray();
		for (int i = 0; i < str1.length; i++) {
			if ('A' <= str1[i] && str1[i] <= 'Z') {
				newStr.append("_" + String.valueOf(str1[i]).toLowerCase());
			} else {
				newStr.append(String.valueOf(str1[i]));
			}
		}
		return newStr.toString().toUpperCase();
	}
}