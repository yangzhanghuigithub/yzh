package com.learn.yzh.common.utils;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 * @author hapday
 * @date 2014-6-25	@time 下午08:04:24
 * @version 1.0
 */
public class CommonUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);
	
	private static Date date = null;
	private static SimpleDateFormat simpleDateFormat = null;
	
	public CommonUtils() {
	}
	
	/**
	 * 对象的非空校验
	 * @param object 对象
	 * @return 空则返回 false，否则返回 true
	 * @author zhaojiafu
	 */
	public static boolean isEmpty(Object object) {
		if(null == object)
			return true;

		String objectType = object.getClass().getName();

		if("java.lang.Short".equals(objectType) || "java.lang.Integer".equals(objectType) || "java.lang.Long".equals(objectType) || "java.lang.Double".equals(objectType)) {
			return false;
		} else if("java.lang.Character".equals(objectType)) {
			Character character = (Character) object;
			
			if("".equals(character))
				return true;
			else 
				return false;
		} else if("java.lang.String".equals(objectType)) {
			String str = (String) object;
			
			if("".equals(str.trim()))
				return true;
			else 
				return false;
		} else if("java.util.HashMap".equals(objectType) || "java.util.TreeMap".equals(objectType) || "java.util.LinkedHashMap".equals(objectType)) {
			Map<?, ?> map = (Map<?, ?>) object;
			
			if(0 == map.size()) 
				return true;
			else 
				return false;
		} else if("java.util.ArrayList".equals(objectType) || "java.util.LinkedList".equals(objectType)) {
			List<?> list = (List<?>) object;
			
			if(0 == list.size())
				return true;
			else 
				return false;
		} else if(object.getClass().isArray()) {
			Object [] objs = (Object[]) object;
			
			if(0 == objs.length)
				return true;
			else
				return false;
		}
		return true;
	}
	
	/**
	 * 校验[对象为空]
	 * @param object 普通对象
	 * @return 空则返回 true 否则返回 false
	 * 2014-7-2 下午08:23:33
	 * @author zhaojiafu
	 */
	public static boolean isNotEmpty(Object object) { 
		if(null != object) {
			String objectType = object.getClass().getName();

			if("java.lang.Short".equals(objectType) || "java.lang.Integer".equals(objectType) || "java.lang.Long".equals(objectType) || "java.lang.Double".equals(objectType)) {
				return true;
			} else if("java.lang.Character".equals(objectType)) {
				Character character = (Character) object;
				
				if("".equals(character))
					return false;
				else 
					return true;
			} else if("java.lang.String".equals(objectType)) {
				String str = (String) object;
				
				if("".equals(str.trim()))
					return false;
				else 
					return true;
			} else if("java.util.HashMap".equals(objectType) || "java.util.TreeMap".equals(objectType) || "java.util.LinkedHashMap".equals(objectType)) {
				Map<?, ?> map = (Map<?, ?>) object;
				
				if(0 == map.size()) 
					return false;
				else 
					return true;
			} else if("java.util.ArrayList".equals(objectType) || "java.util.LinkedList".equals(objectType)) {
				List<?> list = (List<?>) object;
				
				if(0 == list.size())
					return false;
				else 
					return true;
			} else if(object.getClass().isArray()) {
				Object [] objs = (Object[]) object;
				
				if(0 == objs.length)
					return false;
				else
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取当前日期，格式YYYY-MM-DD
	 * 特点：非线程安全
	 * @return 当前日期
	 * 2014-6-30 下午02:18:15
	 * @author zhaojiafu
	 */
	public static String getCurrentDate() {
		if(null == date) {
			date = new Date();
		}
		if(null == simpleDateFormat) {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		}
		
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 获取当前日期，格式YYYYMMDD
	 * 特点：非线程安全
	 * @return 当前日期
	 * 2014-6-30 下午02:18:15
	 * @author zhaojiafu
	 */
	public static String getCurrentDate2() {
		if(null == date) {
			date = new Date();
		}
		if(null == simpleDateFormat) {
			simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		}
		
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 获取当前时间
	 * 格式：HH:mm:SS
	 * 特点：非线程安全
	 * @return 当前时间
	 * 2014-6-30 下午02:23:58
	 * @author zhaojiafu
	 */
	public static String getCurrentTime() {
		if(null == date)
			date = new Date();
		if(null == simpleDateFormat)
			simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 获取当前日期和时间
	 * 格式：yyyy-MM-ss HH:mm:ss
	 * 特点：非线程安全
	 * @return 获取当前日期和时间
	 * 2014-6-30 下午02:26:47
	 * @author zhaojiafu
	 */
	public static String getCurrentDateAndTime() {
		if(null == date)
			date = new Date();
		if(null == simpleDateFormat)
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return simpleDateFormat.format(date);
	}

	/**
	 * 获取指定格式的日期时间
	 * @param format 格式模版
	 * @return 指定格式的日期时间
	 */
	public static String getCurrentDateTime(String format) {
		if(null == date)
			date = new Date();
		if(null == simpleDateFormat)
			simpleDateFormat = new SimpleDateFormat(format);
		
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 格式化日期
	 * @param originDate 原日期
	 * @return 格式化后的日期
	 * @date 2014-12-10 @time 10:47 
	 * @author zhaojiafu
	 */
	public static String dateFormat(Date originDate) {
		if(null != originDate) {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			return simpleDateFormat.format(originDate);
		}
		
		return null;
	}
	
	/**
	 * 格式化日期时间
	 * @param originDate 原日期时间
	 * @return 格式化后的日期时间
	 * @date 2014-12-10 @time 10:47 
	 * @author zhaojiafu
	 */
	public static String dateTimeFormat(Date originDateTime) {
		if(null != originDateTime) {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			return simpleDateFormat.format(originDateTime);
		}
		
		return null;
	}
	
	/**
	 * 格式化日期
	 * @param originDate 原日期
	 * @param formatString 格式化字符串
	 * @return 格式化后的日期
	 * 2014-7-3 下午05:47:03
	 * @author zhaojiafu
	 */
	public static String dateFormat(Date originDate, String formatString) {
		if(CommonUtils.isNotEmpty(originDate)) {
			if(CommonUtils.isNotEmpty(formatString)) 
				simpleDateFormat = new SimpleDateFormat(formatString);
			else
				simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			return simpleDateFormat.format(originDate);
		}
		
		return null;
	}
	
	/**
	 * 格式化日期（将 YYYYMMDD 格式转换成 YYYY-MM-DD 格式）
	 * @param originDate 原日期
	 * @return 格式后的日期
	 * 2014-7-3 下午05:59:30
	 * @author zhaojiafu
	 */
	public static String dateFormat(String originDate) {
		if(CommonUtils.isNotEmpty(originDate)) {
			String year = originDate.substring(0, 4);
			String month = originDate.substring(4, 6);
			String day = originDate.substring(6, 8);
			
			return year + "-" + month + "-" + day;
		}
		
		return null;
	}
	
	/**
	 * 货币中国化
	 * @param bigDecimal 原货币值
	 * @return 中国化的货币值
	 * @author zhaojiafu
	 */
	public static String currencyFormat4Chinese(BigDecimal bigDecimal) {
		if(null != bigDecimal) {
			BigDecimal baseBigDecimal = new BigDecimal(100);
			NumberFormat currencyFormat4Chinese = NumberFormat.getCurrencyInstance(Locale.CHINA);
			return currencyFormat4Chinese.format(bigDecimal.divide(baseBigDecimal));
		}
		
		return null;
	}
	/**
	 * 金额中国化
	 * @param amount 金额
	 * @return 中国化的金额
	 * @author zhaojiafu
	 */
	public static String currencyFormat4Chinese(Double amount) {
		if(null != amount) {
			NumberFormat currencyFormat4Chinese = NumberFormat.getCurrencyInstance(Locale.CHINA);

			return currencyFormat4Chinese.format(amount / 100);
		}
		
		return null;
	}
	
	/**
	 * 获取当前年
	 * @return 当前年
	 * @author zhaojiafu
	 */
	public static Integer getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		Integer YEAR = Calendar.YEAR;
		
		return calendar.get(YEAR); 
	}
	
	/**
	 * 去掉字符串前后的空格
	 * @param str 字符串
	 * @return 前后无空格的字符串
	 * @author zhaojiafu
	 */
	public static String trim(String str) {
        return null == str ? "" : str.trim();
    }
	
	/**
	 * UUID 生成器 
	 * @return uuid
	 * @date 2014-09-15
	 * @author zhaojiafu
	 */
	public static String UUIDGenerator() {
		UUID uuid = UUID.randomUUID();
		String target = uuid.toString().replace("-", "");
		
		return target;
	}
	
	/**
	 * 存放 Map 的 List 集合根据指定的 Map Key 重新组合成逗号分隔的目标字符串
	 * @param mapList 存放 Map 的 List 集合
	 * @param mapKey --- Map 的 Key
	 * @return 目标字符串
	 * @date 2014-09-15
	 * @author zhaojiafu
	 */
	public static String mapList2string(List<Map<String, Object>> mapList, String mapKey) {
		if(isEmpty(mapList)) 
			return null;
		
		StringBuilder target = new StringBuilder();
		for(int index = 0, size= mapList.size(); index < size; index++) {
			Map<String, Object> map = mapList.get(index);
			if(null != map) {
				Object value = map.get(mapKey);
				if(null != value) 
					target.append(CommonUtils.trim(value.toString()) + ",");
			}
		}
		
		if(0 == target.length())
			return null;
		
		return target.substring(0, target.length() - 1);
	}
	
	/**
	 * 存放 Map 的 List 集合根据指定的 Map Key 重新组合成逗号分隔的目标字符串
	 * @param mapList 存放 Map 的 List 集合
	 * @param mapKey --- Map 的 Key
	 * @return 目标字符串
	 * @date 2014-09-15
	 * @author zhaojiafu
	 */
	public static String mapList2string(List<Map<String, Object>> mapList, String mapKey, boolean isSingleQuotes) {
		if(isEmpty(mapList)) 
			return null;
		
		StringBuilder target = new StringBuilder();
		for(int index = 0, size= mapList.size(); index < size; index++) {
			Map<String, Object> map = mapList.get(index);
			if(null != map) {
				Object value = map.get(mapKey);
				if(null != value) {
					if(isSingleQuotes)
						target.append("'" + value + "',");
					else
						target.append(value + ",");
				}
			}
		}
		
		if(0 == target.length())
			return null;
		
		return target.substring(0, target.length() - 1);
	}
	
	/**
	 * 获取字符在编码 GBK 下的长度
	 * @param character 汉字
	 * @return 获取字符在编码 GBK 下的长度
	 * @date 2014-09-16
	 * @author zhaojiafu
	 */
	public static Integer getStringLength4Charset(String character) {
		if(isEmpty(character))
			return 0;
		
		try {
			return character.getBytes("gbk").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 获取字符在指定编码下的长度
	 * @param character 汉字
	 * @return 获取字符在指定编码下的长度
	 * @date 2014-09-16
	 * @author zhaojiafu
	 */
	public static Integer getStringLength4Charset(String character, String charset) {
		if(isEmpty(character))
			return 0;
		
		try {
			if(isEmpty(character))
				return character.getBytes("gbk").length;
			else 
				return character.getBytes(charset).length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 校验手机号是否合法
	 * @param mobilePhoneCode 手机号
	 * 支持的手机段：
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
　　	 * 联通：130、131、132、152、155、156、185、186
　　	 * 电信：133、153、180、189、（1349卫通）
	 * @Date 2014-09-17
	 * @return 是手机号返回 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isMobilePhoneCode(String mobilePhoneCode) {
		if(isEmpty(mobilePhoneCode)) 
			return false;
		
//		String regex = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
//		String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		String regex = "^0{0,1}(13[0-9]|15[7-9]|153|156|17[1-9]|18[6-9])[0-9]{8}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(mobilePhoneCode);
		
		return matcher.matches();
	}
	
	/**
	 * 校验手机号是否合法
	 * @param mobilePhoneCode 手机号
	 * 支持的手机段：
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	 * 联通：130、131、132、152、155、156、185、186
	 * 电信：133、153、180、189、（1349卫通）
	 * @date 2014-09-17
	 * @return 是手机号返回 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isMobilePhoneCode2(String mobilePhoneCode) {
		if(isEmpty(mobilePhoneCode)) 
			return false;
		
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(mobilePhoneCode);
		
		return matcher.find();
	}
	
	/**
	 * 校验【电子邮箱】是否合法
	 * @param email 电子邮箱
	 * @date 2014-09-17
	 * @return 合法返回 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isEMail(String email) {
		if(null == email)
			return false;
		
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		
		return matcher.matches();
	}

	/**
	 * 校验【电子邮箱】是否合法
	 * @param email 电子邮箱
	 * @date 2014-09-17
	 * @return 合法返回 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isEMail2(String email) {
		if(isEmpty(email))
			return false;
		
		String regex = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		
		return matcher.find();
	}
	
	/**
	 * 校验是否是固话
	 * @param phoneCode 固话号
	 * @return 是固话返回 true 否则返回 false
	 * @date 2014-09-17
	 * @author zhaojiafu
	 */
	public static boolean isPhoneCode(String phoneCode) {
		if(isEmpty(phoneCode))
			return false;
		
//		String regex = "^(0[0-9]{2,3}/-)?([2-9][0-9]{6,7})+(/-[0-9]{1,4})?$";
		String regex = "^0\\d{2,3}-?\\d{7,8}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(phoneCode);
		
		return matcher.matches();
	}
	
	/**
	 * 是否是货币值（非负浮点数）
	 * @param currency 货币
	 * @return 是货币值返回 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isCurrency(String currency) {
		if(isEmpty(currency))
			return false;
		
		String regex = "^\\d+(\\.\\d+)?$";		// 非负浮点数
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(currency);
		
		return matcher.matches();
	}

	/**
	 * 是否是日期
	 * 格式：yyyy-mm-dd
	 * @param date 日期
	 * @return 是日期返还 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isDate(String date) {
		if(isEmpty(date))
			return false;
		
		String regex =  "[0-9]{4}-[0-9]{2}-[0-9]{2}";		// 格式：yyyy-mm-dd
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(date);
		
		return matcher.matches();
	}
	
	/**
	 * 对象转换为映射
	 * @author zhaojiafu
	 * @version 0.1
	 * @date 2014-9-19	@time 上午10:54:52
	 * @author zhaojiafu
	 */
	public static Map<String, Object> object2map(Object object) {
		if(null == object) 
			return null;

		Map<String, Object> map = new HashMap<String, Object>();
		Field fields [] = object.getClass().getDeclaredFields();		// 取得全部的域
		
		for(Field field : fields) {
			field.setAccessible(true);		// 设置为可读写
			if(!Modifier.isStatic(field.getModifiers()) && Modifier.isPrivate(field.getModifiers())) {
				try {
					Object myobj = field.get(object);
					String key = field.getName();
					
					if(null != myobj) {
						if(myobj instanceof String) 
							map.put(key, ((String) myobj).trim());
						else 
							map.put(key, myobj);
					} else {
						map.put(key, "");
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
		return map;
	}
	
	/**
	 * 对象转换成JSON字符串
	 * @param object 对象
	 * @return JSON 字符串
	 * @author zhaojiafu
	 */
	public static String object2JSONString(Object object) {
		if(null == object) 
			return null;
		
		StringBuilder target = new StringBuilder();
		Map<String, Object> map = object2map(object);
		Set<Entry<String,Object>> entrySet = map.entrySet();
		Iterator<Entry<String, Object>> iterator = entrySet.iterator();
		while(iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			
			if(null != value && 0 < value.toString().length()) {
				target.append(key.toString());
				target.append(":");
				target.append(value.toString());
				target.append(",");
			} else {
				target.append(key.toString());
				target.append(":");
				target.append("\"\"");
				target.append(",");
			}
		}
		
		if(0 < target.length())
			return target.substring(0, target.length() - 1).toString();
		return null;
	}
	
	/**
	 * 字符串型日期转换成时间戳型日期
	 * @param date 日期
	 * @format yyyy-MM-dd
	 * @format yyyy-MM-dd hh:mm:ss:[SSSSSS]
	 * 中括号中的内容可选
	 * @return 时间戳型日期
	 * @author zhaojiafu
	 */
	@SuppressWarnings("static-access")
	public static Timestamp string2Timestamp(String date) {
		if(isEmpty(date))
			return null;
		
		Timestamp tt = new Timestamp(System.currentTimeMillis());
		
		return tt.valueOf(date + " 00:00:00");
	}

	/**
	 * 字符串型日期转换成时间戳型日期
	 * @param date 日期
	 * @format yyyy-MM-dd
	 * @format yyyy-MM-dd hh:mm:ss:[SSSSSS]
	 * 中括号中的内容可选
	 * @return 时间戳型日期
	 * @author zhaojiafu
	 */
	@SuppressWarnings("static-access")
	public static Timestamp string2Timestamp4midnight(String date) {
		if(isEmpty(date))
			return null;
		
		Timestamp tt = new Timestamp(System.currentTimeMillis());
		
		return tt.valueOf(date + " 23:59:59");
	}
	
	/**
	 * 在字符串指定的索引位置插入指定的字符
	 * @param source 原字符串
	 * @param index 位置索引
	 * @param newString 要插入的字符
	 * @return 目标字符串
	 * @author zhaojiafu
	 */
	public static String insert(String source, Integer index, String newString) {
		if(isEmpty(source))
			return null;
		
		StringBuilder target = new StringBuilder(source);
		target.insert(index, newString);
		
		return target.toString();
	}
	
	/**
	 * 格式化卡号
	 * 1 空卡号返还 null
	 * 2 长度少于或等于 10 位的卡号原样返还
	 * 3 长度大于 10 位的卡号显示前 6 位和后 4 位，其余隐藏
	 * @param cardCode 卡号
	 * @author zhaojiafu
	 * @return 格式化后的卡号
	 */
	public static String formatCardCode(String cardCode) {
		if(isEmpty(cardCode))
			return null;
		
		if(10 >= cardCode.length())
			return cardCode;
		
		Integer length = cardCode.length();
		String cephalic = cardCode.substring(0, 6);
		String caudal = cardCode.substring(length - 4, length);
		String placeholder = "";
		
		for(int index = 0, size = length - 10; index < size; index++) {
			placeholder += "*";
		}
		
		return cephalic + placeholder +caudal;
	}
	
	/**
	 * 是否为数字（整数或浮点数）
	 * @param number 数字
	 * @return 是数字返还 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isNumber(String number) {
		if(isEmpty(number))
			return false;
		
		String regex = "^(-?\\d+)(\\.\\d+)?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(number);
		
		return matcher.matches();
	}

	/**
	 * 是否为整数
	 * @param number 数字
	 * @return 是整数返还 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isInteger(String number) {
		if(isEmpty(number))
			return false;
		
		String regex = "^-?\\d+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(number);
		
		return matcher.matches();
	}
	
	/**
	 * 是否为浮点数（含整数）
	 * @param number 数字
	 * @return 是浮点数返还 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isFloatPointingNumber(String number) {
		if(isEmpty(number))
			return false;
		
		String regex = "^(-?\\d+)(\\.\\d+)?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(number);
		
		return matcher.matches();
	}
	
	/**
	 * 是否为正整数
	 * @param number 数字
	 * @return 是正整数返还 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isPositiveInteger(String number) {
		if(isEmpty(number))
			return false;
		
		String regex = "^[0-9]*[1-9][0-9]*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(number);
		
		return matcher.matches();
	}

	/**
	 * 是否为负整数
	 * @param number 数字
	 * @return 是负整数返还 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isNegativeInteger(String number) {
		if(isEmpty(number))
			return false;
		
		String regex = "^-[0-9]*[1-9][0-9]*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(number);
		
		return matcher.matches();
	}
	
	/**
	 * 是否为非负整数（正整数 + 0）
	 * @param number 数字
	 * @return 是非负整数返还 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isNonnegativeInteger(String number) {
		if(isEmpty(number))
			return false;
		
		String regex = "^\\d+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(number);
		
		return matcher.matches();
	}
	
	/**
	 * 是否为非正整数（负整数 + 0）
	 * @param number 数字
	 * @return 是否为非正整数返还 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isNonpositiveInteger(String number) {
		if(isEmpty(number))
			return false;
		
		String regex = "^((-\\d+)|(0+))$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(number);
		
		return matcher.matches();
	}

	/**
	 * 是否为正浮点数
	 * @param number 数字
	 * @return 是否为正浮点数返还 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isPositiveFloatPointingNumber(String number) {
		if(isEmpty(number))
			return false;
		
		String regex = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(number);
		
		return matcher.matches();
	}
	
	/**
	 * 是否为负浮点数
	 * @param number 数字
	 * @return 是否为负浮点数返还 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isNegativeFloatPointingNumber(String number) {
		if(isEmpty(number))
			return false;
		
		String regex = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(number);
		
		return matcher.matches();
	}
	
	/**
	 * 是否为非负浮点数（正浮点数 + 0）
	 * @param number 数字
	 * @return 是非负浮点数返还 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isNonnegativeFloatPointingNumber(String number) {
		if(isEmpty(number))
			return false;
		
		String regex = "^\\d+(\\.\\d+)?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(number);
		
		return matcher.matches();
	}
	
	/**
	 * 是否为非正浮点数（负浮点数 + 0）
	 * @param number 数字
	 * @return 是非正浮点数返还 true 否则返回 false
	 * @author zhaojiafu
	 */
	public static boolean isNonpositiveFloatPointingNumber(String number) {
		if(isEmpty(number))
			return false;
		
		String regex = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(number);
		
		return matcher.matches();
	}
	
	/**
	 * 生成制定个数的随机整数
	 * @param num 指定的个数
	 * @return 随机整数
	 */
	public static List<Integer> getDifferentRandomNumber(int num){
		List<Integer> resultList = new ArrayList<Integer>();
		Random random = new Random();
		boolean [] bool = new boolean[num];
		int result = 0;
		for(int index = 0; index < num; index++){
			
			do {
				result = random.nextInt(num);
			} 
			while (bool[result]);
			
			bool[result] = true;
			
			resultList.add(result);
		}
		
		return resultList;
	}
	
	/**
	 * 获取指定个数的随机整数
	 * @param num 制定的个数
	 * @return 随机整数
	 */
	public static String getDifferentRandomNumber2(int num){
		StringBuilder target = new StringBuilder();
		
		Random random = new Random();
		boolean [] bool = new boolean[num];
		int result = 0;
		for(int index = 0; index < num; index++){
			do {
				result = random.nextInt(num);
			} while (bool[result]);
			
			bool[result] = true;
			target.append(result);
		}
		
		return target.toString();
	}

	/**
	 * 获取指定位数的短信验证码
	 * @param num 位数
	 * @return 短信验证码
	 */
	public static String getSmsCaptcha(int num){
		StringBuilder smsCaptcha = new StringBuilder();
		
		Random random = new Random();
		boolean [] bool = new boolean[num];
		int result = 0;
		for(int index = 0; index < num; index++){
			do {
				result = random.nextInt(num);
			} while (bool[result]);
			
			bool[result] = true;
			smsCaptcha.append(result);
		}
		
		return smsCaptcha.toString();
	}
	
	/**
	 * 对象转为 Map
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> objectToMap(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();

		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			map.put(field.getName(), field.get(obj));
		}

		return map;
	}
	
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)  
            return null;    
   
        Object obj = beanClass.newInstance();  
   
        Field[] fields = obj.getClass().getDeclaredFields();   
        for (Field field : fields) {    
            int mod = field.getModifiers();    
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
                continue;    
            }    
   
            field.setAccessible(true);    
            field.set(obj, map.get(field.getName()));   
        }   
   
        return obj;    
    }
	
	public static Object mapToObject2(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)   
            return null;    
   
        Object obj = beanClass.newInstance();  
   
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {  
            Method setter = property.getWriteMethod();    
            if (setter != null) {  
                setter.invoke(obj, map.get(property.getName()));   
            }  
        }  
   
        return obj;  
    }    
       
    public static Map<String, Object> objectToMap3(Object obj) throws Exception {    
        if(obj == null)  
            return null;      
   
        Map<String, Object> map = new HashMap<String, Object>();   
   
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {    
            String key = property.getName();    
            if (key.compareToIgnoreCase("class") == 0) {   
                continue;  
            }  
            Method getter = property.getReadMethod();  
            Object value = getter!=null ? getter.invoke(obj) : null;  
            map.put(key, value);  
        }    
   
        return map;  
    }    
    
    public static Object mapToObject4(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)  
            return null;  
   
        Object obj = beanClass.newInstance();  
   
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);  
   
        return obj;  
    }    
       
    public static Map<?, ?> objectToMap2(Object obj) {  
        if(obj == null)  
            return null;   
   
        return new org.apache.commons.beanutils.BeanMap(obj);  
    }
    
    /**
     * 读取属性文件
     * @param fileName 文件名
     * @return
     */
    public static Properties getProperties(String fileName) {
//    	logger.info("开始读取文件【{}】...", new Object[]{fileName} );
        
        InputStream inputStream = CommonUtils.class.getClassLoader().getResourceAsStream(fileName);
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
        
//        LOGGER.info("读取文件【{}】结束...", new Object[]{fileName} );
        
        return properties;
    }
    
    /**
     * 字符串倒叙
     * @param source
     * @return
     * 2016年4月13日 下午2:48:48
     */
    public static String reverseSort(String source){
		if(CommonUtils.isEmpty(source)){
			return null;
		}
		
		String target = "";
		
		for(int index = source.length() - 1; index > -1; index--){
			target += String.valueOf(source.charAt(index));
		}
		
		return target;
	}
	/**
	 * 参数转成Json格式
	 * @param request
	 * @return
	 */
	public static  String paramsToJson(HttpServletRequest request) {
		if (request != null) {
			Map<String, String[]> params = request.getParameterMap();
			String jsonParam = toJson(params);
			return jsonParam;
		} else {
			return null;
		}

	}
	/**
	 * 返回对象Json格式数据
	 * @param result
	 * @return
	 */
	public static  String toJson(Object result) {
		String json = JsonMapper.getJsonMapper().toJson(result);
		return json;
	}
}