package com.learn.yzh.common.utils;

import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class SensorsUtil {
	/**
	 * 事件埋点
	 * @param loginId 用户登录id
	 * @param eventName  事件名称
	 * @param properties 事件字段信息
	 * @param flag 匿名id还是登陆id标识
	 */
	private static ScheduledExecutorService executor = newScheduledThreadPool(2);
	public static void eventBuryingPoint(String eventName, Map<String, Object> properties,boolean flag)  {
		executor.submit(new Runnable(){
			@Override
			public void run () {
				SensorsAnalytics sa = initSensors();
				try {

					String loginId = properties.get("memberId").toString();
					//String accountId="";
			/* if(userInfo!=null&&userInfo.gettAccountUser()!=null){
				  loginId=userInfo.gettAccountUser().getAccountUserId()+"";
				  accountId=userInfo.getAccountId()+"";
			 }*/

			/*if(StringUtils.isEmpty(loginId)||"null".equals(loginId)){
				loginId=getId();
				flag=false;
			}*/
			/*//公共属性
			Map<String, Object> superProperties=new HashMap<String, Object>();
			superProperties.put("platform_type", "");
			superProperties.put("userType", "影城");
			if(StringUtils.isNotEmpty(accountId)&&!"null".equals(accountId)){
				superProperties.put("cinemaId", accountId);
			}
			sa.registerSuperProperties(superProperties);*/
					sa.track(loginId, flag, eventName, properties);
				} catch (InvalidArgumentException e) {
					e.printStackTrace();
				}
				// 刷新一下，让埋点数据落到指定目录文件中
				sa.flush();
				sa.shutdown();
			}});
	}

	/*	*//**
	 * 神策埋点 绑定登录用户信息
	 * @param roleTypes 账号类型
	 * @param cinemaIds 影院账号
	 * @param cinemaNames 影院名称
	 * @param userInfo 用户信息
	 *//*
	public static void profileBuryingPoint(List<String> roleTypes,List<String> cinemaIds,List<String> cinemaNames,UserInfo userInfo) {
		SensorsAnalytics sa = initSensors();
		try {
			TAccountUser accountUser = userInfo.gettAccountUser();
			String	userName = StringUtils.isNotEmpty(accountUser.getUserName()) ? accountUser.getUserName() : "";
			String	userPhone = StringUtils.isNotEmpty(accountUser.getUserPhone()) ? accountUser.getUserPhone() : "";
			String	email = StringUtils.isNotEmpty(accountUser.getUserEmail()) ? accountUser.getUserEmail() : "";
			String	sourceType = String.valueOf(accountUser.getSourceType() != null ? accountUser.getSourceType() : "");
			String	loginId = userInfo.gettAccountUser().getAccountUserId() + "";
			if (StringUtils.isEmpty(loginId) || "null".equals(loginId)) {
				loginId = getId();
			}
			Map<String, Object> profileProp=new HashMap<String, Object>();
			profileProp.put("userName", userName);
			profileProp.put("userPhone", userPhone);
			profileProp.put("email", email);
			profileProp.put("userType", roleTypes);
			profileProp.put("userCity", "");
			profileProp.put("movieHallNumber", 0);
			profileProp.put("cinemaId", cinemaIds);
			profileProp.put("cinemaName", cinemaNames);
			profileProp.put("cinemaCity", "");
			profileProp.put("cinemaLastBox", 0);
			profileProp.put("sourceType", sourceType);
			sa.profileSet(loginId, true, profileProp);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
		 // 刷新一下，让埋点数据落到指定目录文件中
	    sa.flush();
	    sa.shutdown();
	}*/
	//初始化sdk
	public static SensorsAnalytics initSensors()  {

		// 从神策分析获取的数据接收的 URL
		//final String SA_SERVER_URL = "http://tuiying.datasink.sensorsdata.cn/sa?project=default_yhtoc&token=edd25955ac38cd3d";
		final String SA_SERVER_URL = "http://tuiying.datasink.sensorsdata.cn/sa?project=production_yhtoc&token=edd25955ac38cd3d";
		// 使用 Debug 模式，并且导入 Debug 模式下所发送的数据
		final boolean SA_WRITE_DATA = true;
		//final String SA_SERVER_URL_LOG="/data/sa/access.log";
		final String SA_DIRECTORY_PATH ="/data/sa/";
		// 使用 ConcurrentLoggingConsumer 初始化 SensorsAnalytics
		// 将数据输出到 /data/sa 下的 access.log.2017-01-11 文件中，每天一个文件。需要配合 LogAgent 导入工具，把产生的数据导入到神策分析系统中。
		SensorsAnalytics sa = null;
		/*File baseFile = new File(SA_DIRECTORY_PATH);
		if (!baseFile.exists()) {
			boolean b = new File("/data/sa").mkdirs();
		}*/
		/*try {*/
			sa = new SensorsAnalytics(
					new SensorsAnalytics
							//.ConcurrentLoggingConsumer(SA_DIRECTORY_PATH +"access.log" ));
		/*} catch (IOException e) {
			e.printStackTrace();
		}*/
		.DebugConsumer(SA_SERVER_URL, SA_WRITE_DATA));
		//⽴即发送缓存中的所有⽇志
		sa.flush();
		// 程序结束前，停止神策分析 SDK 所有服务
		sa.shutdown();
		return sa;
	}
	/**
	 * 生成主键(16位数字)
	 * 主键生成方式,年月日时分秒毫秒的时间戳+四位随机数保证不重复
	 */
	public static  String getId() {
		//当前系统时间戳精确到毫秒
		Long simple=System.currentTimeMillis();
		//三位随机数
		int random=new Random().nextInt(900)+100;//为变量赋随机值100-999;
		return simple.toString()+random;
	}

}
