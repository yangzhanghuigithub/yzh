package com.learn.yzh.common.utils;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.learn.yzh.common.constants.SysDictConstants;
import com.learn.yzh.common.model.Member;
import com.learn.yzh.common.model.MovieCompany;
import com.thoughtworks.xstream.core.util.Base64Encoder;

import org.apache.commons.lang3.*;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.DefaultResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 向用户端发送消息或通知
 * @author Lomis
 * @date 2015年12月20日 14:16:45
 */
public class JPushMessageUtils {
	  private static Environment env;

	  //美嘉安卓 
	  private static final String appKey_mj_android 		= "81b9fec7d2fcca69569102be";
	  private static final String masterSecret_mj_android	= "467cbe9775e65ed48e9618e0";//每个应用都对应一个masterSecret
	  private static JPushClient jpush_mj_android 			= new JPushClient(masterSecret_mj_android, appKey_mj_android ,3);
	  //美嘉苹果
	  private static final String appKey_mj_ios 		= "d2cc00fad804dc3957480ad7";
	  private static final String masterSecret_mj_ios	= "516f04b0e876266679b85497";//每个应用都对应一个masterSecret
	  private static JPushClient jpush_mj_ios 			= new JPushClient(masterSecret_mj_ios, appKey_mj_ios ,3);

	private static Logger logger = LoggerFactory.getLogger(JPushMessageUtils.class);
	private static boolean profiles=true;
	static {
		env=SpringContextUtils.getBean(Environment.class);
		String[] propertis=env.getActiveProfiles();
		if(Arrays.asList(propertis).contains("dev")||Arrays.asList(propertis).contains("test")){
			profiles=false;
			logger.info("<---------------------测试环境---------------------------------->");
		}else {
			logger.info("<--------------------线上环境---------------------------------->");
		}

	}

	public static void jPushAppMsgCompanyCode(String  companyJson, String memberListJson, String group_id, String cinemaName, String pushMsg, String pushTitle ,String ...msgType){
		MovieCompany company=JSON.parseObject(companyJson,new TypeReference< MovieCompany>(){});
	    List<Member>  memberList= JSONArray.parseArray(memberListJson,Member.class);

		Map<Integer,List<Member>> memberGroupByPhoneType=memberList.stream().collect(Collectors.groupingBy(Member::getPhoneType));
		List<Member> androidMemberGroup=memberGroupByPhoneType.get(0);
		List<Member> iosMemberGroup=memberGroupByPhoneType.get(1);

		if(androidMemberGroup!=null&&androidMemberGroup.size()>0){
			sendByType(androidMemberGroup,0,company,group_id,cinemaName,pushMsg,pushTitle);
		}
		if(iosMemberGroup!=null&&iosMemberGroup.size()>0){
			sendByType(iosMemberGroup,1,company,group_id,cinemaName,pushMsg,pushTitle);

		}
	}


	public static  void sendByType(List<Member> memberList, int sendtype, MovieCompany company, String group_id, String cinemaName, String pushMsg, String pushTitle){
		int num = 1000;
		int pushNum = 1;
		if(memberList.size()>num){
			pushNum = memberList.size()%num==0?memberList.size()/num:memberList.size()/num+1;
		}else{
			num = memberList.size();
		}
		try {
			Boolean createResult = true;
			String type = "n";
			String msg = pushMsg;
			String title =cinemaName + "["+pushTitle+"]";
			Map<String,String> map = new HashMap<String,String>();
			map.put("type", type);
			map.put("title", title);
			map.put("msg", msg);
			for(int i=0;i<pushNum;i++){
				List<Member> newList = memberList.subList(i*num, i+1==pushNum?memberList.size():num+(i*num));
				//推送组人员的信息
				Map<String,Set<String>> memberPushMap=	getMemberPushMapByPhoneType(newList,sendtype);
				// 调用极光创建推送用户组
				String add_status = addgroupByPhongType(group_id ,memberPushMap,company.getjPushMasterSecret(),company.getJpushKey() ,company.getCompanyCode(),sendtype);
				sendMsgToUserByTagAndPhoneType(company.getCompanyCode(), title, JSON.toJSONString(map), company.getjPushMasterSecret(),company.getJpushKey(),group_id,sendtype);
				if("200".equals(add_status)){ // 200为推送成功

				}else{
					createResult = false;
					break;
				}
			}

		} catch (Exception e) {
			logger.error("手动推送APP消息异常，异常信息："+e.getMessage(),e);
		}
	}

	public static Map<String,Set<String>> getMemberPushMapByPhoneType(List<Member> memberGroupTabList, int phoneType) {
		Map<String,Set<String>> memberPushMap = new LinkedHashMap<String,Set<String>>();
		Set<String> set = new HashSet<String>();
		if(memberGroupTabList != null){
			if(0 == phoneType){
				for (Member groupMemberTab : memberGroupTabList) {
					set.add(groupMemberTab.getPushId());
				}
				memberPushMap.put("android",set);
			}else if(1 == phoneType){
				for (Member groupMemberTab : memberGroupTabList) {
					set.add(groupMemberTab.getPushId());
				}
				memberPushMap.put("ios",set);
			}

		}
		return memberPushMap ;
	}


	public static String  addgroupByPhongType(String tag ,Map<String,Set<String>> map,String masterSecret,String jpushKey ,String companycode,int phone_type)throws APIRequestException {
		String status="200";
		DefaultResult result=null;
		DefaultResult resultIos=null;
		DefaultResult resultAndroid=null;
		JPushClient jPushClient=null;
		try {
			if(StringUtils.equals(companycode,"a32126ab9cde447e8c6e264a24b27f46")){
				if(phone_type == 1){
					jpush_mj_ios = new JPushClient(masterSecret_mj_ios, appKey_mj_ios ,3);
					resultIos= jpush_mj_ios.addRemoveDevicesFromTag(tag, map.get("ios"), null);
				}
				if(phone_type == 0){
					jpush_mj_android = new JPushClient(masterSecret_mj_android, appKey_mj_android ,3);
					resultAndroid= jpush_mj_android.addRemoveDevicesFromTag(tag,  map.get("android"), null);
				}
			}else {
				if(StringUtils.isNotBlank(masterSecret)||StringUtils.isNotBlank(jpushKey)){
					jPushClient= new JPushClient(masterSecret,jpushKey,3);
					if(phone_type == 1){
						result=jPushClient.addRemoveDevicesFromTag(tag,map.get("ios"),null);
					}else{
						result=jPushClient.addRemoveDevicesFromTag(tag,map.get("android"),null);
					}

				}else{
					status = "101"; //为空
				}
			}
		} catch (APIConnectionException e) {
			status="100";
			e.printStackTrace();
		} catch (APIRequestException e) {
			status="100";
			e.printStackTrace();
		}
		return  status;
	}

	public static boolean sendMsgToUserByTagAndPhoneType(String companyCode, String title, String msgContent, String masterSecret, String appKey, String tag,int phone_type) {
		boolean flg =true;
		try {


			JPushClient jpush = null;
			//美嘉特殊处理
			if ("a32126ab9cde447e8c6e264a24b27f46".equals(companyCode)){
				if(phone_type==1){
					try {
						PushPayload pushPayloadIos = buildPushObjectIos_tag_alertWithTitle(tag,title,msgContent);
						jpush_mj_ios = new JPushClient(masterSecret_mj_ios, appKey_mj_ios ,3);
						jpush_mj_ios.sendPush(pushPayloadIos);
						jpush_mj_ios.close();
					}catch (Exception e){
						logger.error("美嘉IOS 推送失败 tag : "+tag);
						e.printStackTrace();
						flg =false;
					}
				}else{
					try {
						PushPayload pushPayloadAndroid = buildPushObject_android_tag_alertWithTitle(tag,title,msgContent);
					//	PushPayload pushPayloadIos = buildPushObjectIos_tag_alertWithTitle(tag,title,msgContent);
						jpush_mj_android = new JPushClient(masterSecret_mj_android, appKey_mj_android ,3);
						//jpush_mj_android = new JPushClient(masterSecret_mj_android, appKey_mj_android ,3,null,null,true,60*60*24*10);
						jpush_mj_android.sendPush(pushPayloadAndroid);
						jpush_mj_android.close();
					}catch (Exception e){
						logger.error("美嘉Android 推送失败 tag : "+tag);
						e.printStackTrace();
						flg =false;
					}
				}
				//jpush.close();

			}else{
				PushPayload pushPayloadAndrod = buildPushObject_android_tag_alertWithTitle(tag,title,msgContent);
				PushPayload pushPayloadIos = buildPushObjectIos_tag_alertWithTitle(tag,title,msgContent);
				jpush = new JPushClient(masterSecret, appKey, 3);

				if(phone_type==1){
					try {
						jpush.sendPush(pushPayloadIos);
					}catch (Exception e){
						logger.error("公司code "+companyCode+"推送失败IOS  tag : "+tag);
						e.printStackTrace();
						flg =false;
					}
				}else{
					try {
						jpush.sendPush(pushPayloadAndrod);
					}catch (Exception e){
						logger.error("公司code "+companyCode+" 推送失败Android tag : "+tag);
						e.printStackTrace();
						flg =false;
					}
				}


				jpush.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			flg =false;
		}
		if(flg) {
			logger.info("极光推送发送信息 - 成功！ 消息内容为："+ msgContent+"目标组："+tag);
		} else {
			logger.error("极光推送发送信息 - 失败！ 消息内容为："+ msgContent+"目标组："+tag);
		}

		return flg;
	}

	public static PushPayload buildPushObject_android_tag_alertWithTitle(String tag, String title, String msgContent) {
		PushPayload payload = null;
		payload = PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(Audience.newBuilder()
						.addAudienceTarget(AudienceTarget.tag(tag))
						.build())
				.setNotification(Notification.newBuilder()
						.setAlert(title)
						.addPlatformNotification(IosNotification.newBuilder()
								.addExtra("json", msgContent).setContentAvailable(true)
								.build())
						.build())
				.setOptions(Options.newBuilder()
						.setApnsProduction(profiles)
						.build())
				.build();
		return payload;
	}

	public static PushPayload buildPushObjectIos_tag_alertWithTitle(String tag, String title, String msgContent) {



		PushPayload payload = null;
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(msgContent);
		IosAlert alert = IosAlert.newBuilder()
				.setTitleAndBody(title, "", jsonObject.getString("msg"))
				.setActionLocKey("PLAY")
				.build();
		payload = PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(Audience.newBuilder()
						.addAudienceTarget(AudienceTarget.tag(tag))
						.build())
				.setNotification(Notification.newBuilder()
						.setAlert(alert)
						.addPlatformNotification(IosNotification.newBuilder()
								.addExtra("json", msgContent).setContentAvailable(true)
								.build())
						.build())
				.setOptions(Options.newBuilder()
						.setApnsProduction(profiles)
						.build())
				.build();
		return payload;
	}


}
