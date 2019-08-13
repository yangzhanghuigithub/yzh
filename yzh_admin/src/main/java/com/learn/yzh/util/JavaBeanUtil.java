package com.learn.yzh.util;

import com.learn.yzh.model.BaseReqParam;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * javaBean的基本构成字符串转换方法
 * @author wl
 *
 */
public class JavaBeanUtil {
	
	private static final char SEPARATOR = '_';

	/**
	 * 将属性样式字符串转成驼峰样式字符串<br>
	 * (例:branchNo -> branch_no)<br>
	 * 
	 * @param inputString
	 * @return
	 */
	public static String toUnderlineString(String inputString) {
		if (inputString == null)
			return null;
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < inputString.length(); i++) {
			char c = inputString.charAt(i);

			boolean nextUpperCase = true;

			if (i < (inputString.length() - 1)) {
				nextUpperCase = Character.isUpperCase(inputString.charAt(i + 1));
			}

			if ((i >= 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					if (i > 0)
						sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * 将驼峰字段转成属性字符串<br>
	 * (例:branch_no -> branchNo )<br>
	 * 
	 * @param inputString
	 *            输入字符串
	 * @return
	 */
	public static String toCamelCaseString(String inputString) {
		return toCamelCaseString(inputString, false);
	}

	/**
	 * 将驼峰字段转成属性字符串<br>
	 * (例:branch_no -> branchNo )<br>
	 * 
	 * @param inputString
	 *            输入字符串
	 * @param firstCharacterUppercase
	 *            是否首字母大写
	 * @return
	 */
	public static String toCamelCaseString(String inputString, boolean firstCharacterUppercase) {
		if (inputString == null)
			return null;
		StringBuilder sb = new StringBuilder();
		boolean nextUpperCase = false;
		for (int i = 0; i < inputString.length(); i++) {
			char c = inputString.charAt(i);

			switch (c) {
			case '_':
			case '-':
			case '@':
			case '$':
			case '#':
			case ' ':
			case '/':
			case '&':
				if (sb.length() > 0) {
					nextUpperCase = true;
				}
				break;

			default:
				if (nextUpperCase) {
					sb.append(Character.toUpperCase(c));
					nextUpperCase = false;
				} else {
					sb.append(Character.toLowerCase(c));
				}
				break;
			}
		}

		if (firstCharacterUppercase) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}

		return sb.toString();
	}

	/**
	 * 得到标准字段名称
	 * 
	 * @param inputString
	 *            输入字符串
	 * @return
	 */
	public static String getValidPropertyName(String inputString) {
		String answer;
		if (inputString == null) {
			answer = null;
		} else if (inputString.length() < 2) {
			answer = inputString.toLowerCase(Locale.US);
		} else {
			if (Character.isUpperCase(inputString.charAt(0)) && !Character.isUpperCase(inputString.charAt(1))) {
				answer = inputString.substring(0, 1).toLowerCase(Locale.US) + inputString.substring(1);
			} else {
				answer = inputString;
			}
		}
		return answer;
	}

	/**
	 * 将属性转换成标准set方法名字符串<br>
	 * 
	 * @param property
	 * @return
	 */
	public static String getSetterMethodName(String property) {
		StringBuilder sb = new StringBuilder();

		sb.append(property);
		if (Character.isLowerCase(sb.charAt(0))) {
			if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
				sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			}
		}
		sb.insert(0, "set");
		return sb.toString();
	}

	/**
	 * 将属性转换成标准get方法名字符串<br>
	 * 
	 * @param property
	 * @return
	 */
	public static String getGetterMethodName(String property) {
		StringBuilder sb = new StringBuilder();

		sb.append(property);
		if (Character.isLowerCase(sb.charAt(0))) {
			if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
				sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			}
		}
		sb.insert(0, "get");
		return sb.toString();
	}

    /**
     * 转换bean成map
	 * @param obj
     * @return
     */
	public static Map<String, Object> beanToMap(Object obj) {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	/**
     *  把map转成bean
	 * @param map 源map
	 * @param obj 目标对象
	 */
	public static void transMap2Bean(Map<String, Object> map, Object obj) {

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (map.containsKey(key)) {
					Object value = map.get(key);
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {

		BaseReqParam baseVo = new BaseReqParam();
		baseVo.setCinemaCode("1001");
		//baseVo.setMemberCode("1002");
		baseVo.setCVersion("haha");
		baseVo.setOS("lalalal");
		baseVo.setToken("1111");

        Map<String,Object> maps = JavaBeanUtil.beanToMap(baseVo);
		System.out.println(JavaBeanUtil.toUnderlineString("ISOCertifiedStaff"));
		System.out.println(JavaBeanUtil.getValidPropertyName("CertifiedStaff"));
		System.out.println(JavaBeanUtil.getSetterMethodName("userID"));
		System.out.println(JavaBeanUtil.getGetterMethodName("userID"));
		System.out.println(JavaBeanUtil.toCamelCaseString("iso_certified_staff", true));
		System.out.println(JavaBeanUtil.getValidPropertyName("certified_staff"));
		System.out.println(JavaBeanUtil.toCamelCaseString("site_Id"));
	}
}
