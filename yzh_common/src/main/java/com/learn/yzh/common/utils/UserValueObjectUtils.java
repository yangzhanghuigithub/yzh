package com.learn.yzh.common.utils;


import com.learn.yzh.common.constants.SystemConstants;
import com.learn.yzh.common.redis.IRedis;
import com.learn.yzh.common.redis.Redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.UUID;

/**
 * 项目名称：
 * @author:崔程
 * 修改日期：2016/08/5
*/
public class UserValueObjectUtils {

	@Autowired
	private static SystemConstants systemConstants;

	private static Redis redis;
	/**
	 * 日志输出
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserValueObjectUtils.class);
	public static String USER_TOKEN = "UserToken:";
	
	  static {
		if(systemConstants==null){
			systemConstants =SpringContextUtils.getBean(SystemConstants.class);

		}
	}
	
	/**
	 * 设定用户数据对象
	 * 
	 * @author setUserValueObject. <br/>
	 */
	public static void setUserToken(Map<String, Object> map, IRedis redis) {
		logger.debug("设置token开始");

		String token = null;
		if (token == null) {
			token = UUID.randomUUID().toString();
		}
		map.put(systemConstants.getToken(), token);
		logger.debug("保存token开始"+token);
		//保存用户token
//		RedisCacheUtils.saveUserToken(String.valueOf(map.get("memberCode")), token);
		redis.set(USER_TOKEN+ String.valueOf(map.get("memberCode")), token);
		//保存用户对象
		map.put("token", token);
//		RedisCacheUtils.setMapR(Constants.USER_TOKEN + ":" + token, map1);
//		redis.hmset(Constants.USER_TOKEN+":"+map.get("memberCode"), map1);
		logger.debug("保存token结束"+token);
//		logger.debug("保存LoginUser,token:{}", token);
	}
	
	/**
	 * 设定用户数据对象
	 * 
	 * @author setUserValueObject. <br/>
	 */
	public static void setUser(Map<String, String> map) {
		logger.debug("设置token开始");
		String token = null;
		if (token == null) {
			token = UUID.randomUUID().toString();
		}
		map.put(systemConstants.getToken(), token);
		logger.debug("保存token开始"+token);
		//RedisCacheUtils.setMapR(systemConstants.getToken()+":"+ String.valueOf(map.get("memberCode")), map);
		redis.hmset(systemConstants.getToken()+":"+ String.valueOf(map.get("memberCode")), map);
		logger.debug("保存token结束"+token);
		logger.debug("保存LoginUser,token:{}", token);
	}
	
	/**
	 * 获取用户数据对象
	 * 
	 * @author setUserValueObject. <br/>
	 */
	public static Map<String, String> getUser(String token) {
		logger.debug("获取用户userToken"+token);
		//return RedisCacheUtils.getMapR(systemConstants.getToken()+":"+token);
	  return  redis.hgetAll(systemConstants.getToken()+":"+token);
	}
	/**
	 * 获取用户数据对象
	 * 
	 * @author setUserValueObject. <br/>
	 */
	public static String getUserToken(String memberCode) {
		logger.debug("获取用户userToken"+memberCode);
		//return RedisCacheUtils.getR(USER_TOKEN+":"+memberCode);
		return redis.get(USER_TOKEN+":"+memberCode,String.class);
	}

	
	/**
	 * 用户退出删除token
	 * 
	 * @author setUserValueObject. <br/>
	 */
	public static void delectUserToken(String userID) {
		logger.debug("删除用户"+userID+"token开始");
		//RedisCacheUtils.deleteR(userID);
		redis.del(userID);
		logger.debug("删除用户"+userID+"token结束");
	}

}
