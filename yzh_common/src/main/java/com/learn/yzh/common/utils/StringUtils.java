package com.learn.yzh.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	
	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : str.toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}
	
	/**
	 * 获得i18n字符串
	 */
	public static String getMessage(String code, Object[] args) {
		LocaleResolver localLocaleResolver = (LocaleResolver) SpringContextUtils.getBean(LocaleResolver.class);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Locale localLocale = localLocaleResolver.resolveLocale(request);
		return SpringContextUtils.getApplicationContext().getMessage(code, args, localLocale);
	}
	
	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("X-Forwarded-For");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("Proxy-Client-IP");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}
	
	/**
	 * 将字符串按照指定的字符进行分割
	 * @param str
	 * @param devideStr
	 * @return
	 */
	public static String[] toArrry(String str,String devideStr){
		StringTokenizer commaToker = new StringTokenizer(str, devideStr);
    	String[] result = new String[commaToker.countTokens()];
    	int k = 0;
    	while (commaToker.hasMoreTokens()) {
    		result[k] = commaToker.nextToken();
    		k++;
    	}
    	return result;
	}
	public static  void main(String[] args){
		System.out.println(getOrderCode());
	}
	public static boolean isNullOrEmpty(Object obj) {	
		return obj == null || "".equals(obj.toString()) || "null".equals(obj);
	}
	public static Double IFDouble(Object obj){
		if(obj==null||obj.equals("")||obj.equals("null")){
			return 0.0;
		}else{
			return  Double.parseDouble(obj.toString());
		}
	}
	
	public static String toString(Object obj){
		if(obj == null) return "null";
		return obj.toString();
	}
	
	
	@SuppressWarnings("rawtypes")
	public static String join(Collection s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator iter = s.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }
	
	public static String toStringToLong(Object obj){
		if(obj == null) return "0";		
		return obj.toString();
	}
	
	public static Long toLongbyselect(Object obj){
		if(obj == null||obj.equals("")||obj.equals("null")) return -1L;
		double obj1 = Double.parseDouble(obj.toString());
		Long obj2 = (long) ((int)obj1*10);
		obj2 = obj2/10;
		return obj2;
	}
	
	public static String subStringByCode (String string, String code) {
		if (isBlank(string)) {
			return "" ;
		}
		if (!string.contains(code)) {
			return string ;
		}
		return string.substring(0, string.indexOf(code)) ;
		
	}
	
    /**
     * 判断字符数组，不为空
     * 
     * @param values 字符数组
     * @return true or false
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
                if (!result) {
                    return result;
                }
            }
        }
        return result;
    }
    
    /**
     * 比较2个字符串是否相同
     * @param str1
     * @param str2
     * @return
     */
    public static boolean different (String str1, String str2) {
    	if (str1 != str2) {
    		if (str1 == null || !str1.equals(str2)) {
    			return true ;
    		} 
    		if (str2 == null || !str2.equals(str1)) {
    			return true ;
    		}
    	}
    	return false ;
    }
    
	/**
	 * 获取异常的堆栈信息
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}
	
	/**
     * 将字符串按strSign字串作为分割符分割字符串，分割到一个Vector
     * @param
     * @return return a Vector;
     * 
     */
	public static List<String> splitWithList(String strSuper, String strSign) {
		int begin = 0;
		int end = 0;
		List<String> vecResult = new ArrayList<String>();
		if (strSign == "") {
			int i;
			for (i = 0; i < strSuper.length(); i++) {
				vecResult.add(strSuper.substring(i, i + 1));
			}
			return vecResult;
		}
		end = strSuper.indexOf(strSign);
		if (end == -1) {
			vecResult.add(strSuper);
			return vecResult;
		} else {
			while (end >= 0) {
				vecResult.add(strSuper.substring(begin, end));
				begin = end + strSign.length();
				end = strSuper.indexOf(strSign, begin);
			}
			vecResult.add(strSuper.substring(begin, strSuper.length()));
			return vecResult;
		}
	}

    /**
     * 将多余字符变成..
     * @param str
     * @param length
     * @return
     * 
     */
    public static String cutString(String str, int length) {
        if (str == null) {
            return "";
        }

        if ((str.length() > length) && (length > 2)) {
            return str.substring(0, length) + ".";
        } else {
            return str;
        }
    }
    /**
	 * 
	 * 将字节转为UTF-8编码的字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String newStringUtf8(byte[] bytes) {
		String str = null;
		try {
			str = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 获取Request中参数的值
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String paramName) {
		String temp = request.getParameter(paramName);
		
		if (temp != null && !temp.equals("")) {
			return convertSingleQuot(temp);
		} else {
			return "";
		}
	}
	
	/**
	 * 获取Request中参数的值
	 * @param request
	 * @param paramName
	 * @param defaultNum
	 * @return
	 */
	public static int getIntParameter(HttpServletRequest request, String paramName, int defaultNum) {
		String temp = request.getParameter(paramName);
		if (temp != null && !temp.equals("")) {
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}
	
	public static String convertSingleQuot(String src) {
		if (src == null || src.equals("")) {
			return src;
		}
		int length = src.length();
		StringBuffer tmp = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (src.charAt(i) == '\'') {
				tmp.append("\'\'");
			} else {
				tmp.append(src.charAt(i));
			}
		}
		return tmp.toString();
	}
	
	/**
	 * 首字母大写
	 * @param name
	 * @return
	 */
	public static String captureName(String name) {
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);

	}
	
	public static  boolean isNumber(String str){
		try {
			Long l = Long.parseLong(str);
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static final double eps = 1e-10;
	private static final DecimalFormat df = new DecimalFormat("0.0");
	public static String formatDouble(Double d) {
		if (d - (double) ((int) (d + 0.5)) < eps)
			return String.valueOf(d.intValue());
		return df.format(d);
	}
	//首字母转小写
	public static String toLowerCaseFirstOne(String s){
		if(Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}


	//首字母转大写
	public static String toUpperCaseFirstOne(String s){
		if(Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	public static Integer endNo = 0;
	public static Integer cardendNo = 0;
	public synchronized static String getOrderCode(){
		if (endNo == 1999) {
			endNo = 1000;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(System.nanoTime());
		sb.append(String.format("%04d", endNo++));
		return sb.toString();
	}


	public synchronized  static String getCardOrderCode () {
		if (cardendNo >= 99) {
			cardendNo = 1;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(System.nanoTime());
		sb.append(String.format("%02d", cardendNo++));
		if (sb.length()>20){
			return getCardOrderCode();
		}
		return sb.toString();
	}

	/**
	 * 转换时分秒
	 * @param ms
	 * @return
	 */
	public static String formatTime(Long ms) {
		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;

		Long day = ms / dd;
		Long hour = (ms - day * dd) / hh;
		Long minute = (ms - day * dd - hour * hh) / mi;
		Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		StringBuffer sb = new StringBuffer();
		if(day > 0) {
			sb.append(day+"天");
		}
		if(hour > 0) {
			sb.append(hour+"小时");
		}
		if(minute > 0) {
			sb.append(minute+"分钟");
		}
		if(second > 0) {
			sb.append(second+"秒");
		}
		if(milliSecond > 0) {
			sb.append(milliSecond+"毫秒");
		}
		return sb.toString();
	}

	public static String filter(String str) {
		if(isNullOrEmpty(str)){
			return "";
		}
		String regEx = "[`~!@#$^&*()\\-+={}':;,\\[\\].<>/?￥%…（）_+|【】‘；：”“’。，、？\\s]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
}
