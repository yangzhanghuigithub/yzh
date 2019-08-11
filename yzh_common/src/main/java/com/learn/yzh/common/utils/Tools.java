package com.learn.yzh.common.utils;

import com.learn.yzh.common.redis.IRedis;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * 说明：常用工具
 * 创建人：renbo
 * 修改时间：20160523
 * @version
 */
public class Tools {
	static protected Logger logger = LoggerFactory.getLogger(Tools.class);

	private IRedis redis;
	/**
	 * 随机生成六位数验证码 
	 * @return
	 */
	public static int getRandomNum(){
		 Random r = new Random();
		 return r.nextInt(900000)+100000;//(Math.random()*(999999-100000)+100000)
	}
	
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	
	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}
	
	/**
	 * 字符串转换为字符串数组
	 * @param str 字符串
	 * @param splitRegex 分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str,String splitRegex){
		if(isEmpty(str)){
			return null;
		}
		return str.split(splitRegex);
	}
	
	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * @param str	字符串
	 * @return
	 */
	public static String[] str2StrArray(String str){
		return str2StrArray(str,",\\s*");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date){
		return date2Str(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date){
		if(notEmpty(date)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Date();
		}else{
			return null;
		}
	}
	
	/**
	 * 按照参数format的格式，日期转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date,String format){
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}else{
			return "";
		}
	}
	
	/**
	 * 把时间根据时、分、秒转换为时间段
	 * @param StrDate
	 */
	public static String getTimes(String StrDate){
		String resultTimes = "";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date now;

	    try {
	    	now = new Date();
	    	Date date=df.parse(StrDate);
	    	long times = now.getTime()-date.getTime();
	    	long day  =  times/(24*60*60*1000);
	    	long hour = (times/(60*60*1000)-day*24);
	    	long min  = ((times/(60*1000))-day*24*60-hour*60);
	    	long sec  = (times/1000-day*24*60*60-hour*60*60-min*60);
	        
	    	StringBuffer sb = new StringBuffer();
	    	//sb.append("发表于：");
	    	if(hour>0 ){
	    		sb.append(hour+"小时前");
	    	} else if(min>0){
	    		sb.append(min+"分钟前");
	    	} else{
	    		sb.append(sec+"秒前");
	    	}
	    		
	    	resultTimes = sb.toString();
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }
	    
	    return resultTimes;
	}
	
	/**
	 * 写txt里的单行内容
	 * @param filePath  文件路径
	 * @param content  写入的内容
	 */
	public static void writeFile(String fileP,String content){
		String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
		filePath = (filePath.trim() + fileP.trim()).substring(6).trim();
		if(filePath.indexOf(":") != 1){
			filePath = File.separator + filePath;
		}
		try {
	        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath),"utf-8");      
	        BufferedWriter writer=new BufferedWriter(write);          
	        writer.write(content);      
	        writer.close(); 

	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * 验证邮箱
	  * @param email
	  * @return
	  */
	 public static boolean checkEmail(String email){
	  boolean flag = false;
	  try{
	    String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    Pattern regex = Pattern.compile(check);
	    Matcher matcher = regex.matcher(email);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }
	
	 /**
	  * 验证手机号码
	  * @param mobiles
	  * @return
	  */
	 public static boolean checkMobileNumber(String mobileNumber){
	  boolean flag = false;
	  try{
	    Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
	    Matcher matcher = regex.matcher(mobileNumber);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }
	 
	/**
	 * 检测KEY是否正确
	 * @param pd  传入参数
	 * @return 匹配返回true，不否则返回false
	 */
	public  boolean checkKey(String pd){
		logger.debug("检查参数开始Userid", pd);
		DateUtil.getDays();
		if(StringUtils.isEmpty(pd)){
			logger.debug("检查参数开始Userid为空`````````````````````````````````````````");
			return false;
		}
		String token = getToken(pd);
		logger.debug("用户id："+pd);
		logger.debug("用户token:"+token);
		
		return true;
	}
	 
		private String getToken(String userid) {
			return getRedis().get(userid,String.class);
		}

	/**
	 * 读取txt里的单行内容
	 * @param filePath  文件路径
	 */
	public static String readTxtFile(String fileP) {
		try {
			
			String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"/";	//项目路径
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.replaceAll("%20", " ");
			filePath = filePath.trim() + fileP.trim();
			if(filePath.indexOf(":") != 1){
				filePath = File.separator + filePath;
			}
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { 		// 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), encoding);	// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					return lineTxt;
				}
				read.close();
			}else{
				System.out.println("找不到指定的文件,查看此路径是否正确:"+filePath);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		}
		return "";
	}
	
	
	private static Random random = new Random();
	
	/***产生[0-max)范围的随机数**/
	public static int rand(int max){
		return random.nextInt(max);
	}
	/***产生[min-max)范围的随机数
	 *如果@param min>=max 直接返回 min
	 *如果 min<max && min<0 则返回[0-max)范围的随机数 
	*/
	public static int rand(int min, int max){
		if(min<max){
			if(min>0){
				return rand(max-min)+min;
			}else{
				return rand(max);
			}
		}else{
			return min;
		}
	}
	
	/** 
	 * <br>描 述: 返回 指定长度的 数字字符串
	 * <br>作 者: cuicheng
	 * <br>历 史: (版本) 作者 时间 注释 
	 * @param length 长度
	 * @return 参数长度的 数字
	 */
   	public static String generateNumStringAsLength(int length) {
   		final int maxLength = 9;
   		length = length > maxLength ? maxLength : length;
   		int maxInt = Integer.valueOf(StringUtils.rightPad("1", length + 1, "0"));
		return StringUtils.leftPad(String.valueOf(rand(0, maxInt)), length, "0");
   }
	
	
	/*public static void main(String[] args) {
		System.out.println(MD5.md5("a8759e5c-dcd4-40e2-9775-a999b83287b8"+DateUtil.getDays()));
	}
	*/
	/**
	 * 判断定时任务是否启动
	 * @return
	 */
	public static boolean isRun(IRedis redis,Class c){
		try {
			String scheduleIp = InetAddress.getLocalHost().getHostAddress();
			String key = scheduleIp+c.getSimpleName();
			String run = redis.get(key,String.class);
			if (StringUtils.isBlank(run) || ObjectUtils.equals(run,"0")){
				return false;
			}
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean scheduleSetting(IRedis redis, String clazzName, String flag) {
		try {
			String scheduleIp = redis.get("scheduleIp",String.class);
			if (StringUtils.isBlank(scheduleIp)){
				scheduleIp = InetAddress.getLocalHost().getHostAddress();
				redis.set("scheduleIp",scheduleIp);
			}
//			String ip = InetAddress.getLocalHost().getHostAddress();
			String key = scheduleIp+clazzName;
			redis.set(key,flag);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public static boolean scheduleShutdown(IRedis redis) {
		try {
			String scheduleIp = redis.get("scheduleIp",String.class);
			Set<String> keys = redis.keys(scheduleIp+"*");
			for (String key : keys) {
				redis.del(key);
			}
			redis.del(scheduleIp);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public static Long getNextMinuteTime(int minute) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		System.out.println("当前时间：" + sdf.format(now));
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, minute);
		System.out.println(sdf.format(nowTime.getTime()));
		Long nowtime = sdf.parse(sdf.format(nowTime.getTime())).getTime();
		System.out.println(sdf.format(nowTime.getTime()));
		return  nowtime;
	}

	/**
	 *  获取唯一订单号
	 * @return
	 */
	public static String getOrderIdByUUId() {
		int machineId = 1;//最大支持1-9个集群机器部署
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if(hashCodeV < 0) {//有可能是负数
			hashCodeV = - hashCodeV;
		}
		// 0 代表前面补充0
		// 4 代表长度为4
		// d 代表参数为正数型
		return machineId+String.format("%015d", hashCodeV);
	}
	private IRedis getRedis(){
		if (redis == null){
			redis = SpringContextUtils.getBean(IRedis.class);
		}
		return redis;
	}
	/**
	 * 获得32个长度的十六进制的UUID
	 * @return UUID
	 */
	public static String get32UUID(){
		String getid=UUID.randomUUID().toString().replace("-", "");
		return getid;
	}
}
