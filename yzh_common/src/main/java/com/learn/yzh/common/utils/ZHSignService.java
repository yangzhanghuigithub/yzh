package com.learn.yzh.common.utils;

import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


/**
 * 
 * 数据签名和验签
 * 
 * @author wangyanwen
 * 
 */
public class ZHSignService {

	/**
	 * 中控平台接口接口请求参数签名
	 * 
	 * 签名算法 :md5(strParams + md5(authCode))
	 * 
	 * @param mapParams
	 * @param authCode
	 * @return
	 */
	public String signZH(Map mapParams, String authCode) {
		Set<Object> filters = new HashSet<Object>();
		filters.addAll(Arrays.asList(new String[] { null }));
		// 移除value 为 null的
		// mapParams = MapsUtils.filterValues(mapParams, filters);
		// 移除key 为 null的
		// mapParams = MapsUtils.filterKeys(mapParams, filters);
		List<String> sortList = new ArrayList<>(mapParams.keySet());
		Collections.sort(sortList);
		StringBuffer sb = new StringBuffer();
		for (String key : sortList) {
			sb.append("&");
			sb.append(key);
			sb.append("=");
			sb.append(mapParams.get(key));
		}
		String strParams = sb.toString();
		if (strParams.length() > 0) {
			strParams = sb.substring(1);
		}
		// return ZHMD5Util.MD5Encode(strParams + ZHMD5Util.MD5Encode(authCode, "UTF-8"), "UTF-8");
		return MD5Util.getStringMD5(strParams + MD5Util.getStringMD5(authCode));
	}

	/**
	 * 验证中控平台接口请求参数签名
	 * 
	 * @param mapParams
	 * @param authCode
	 * @param sign
	 * @return
	 */
	public boolean validationZH(Map mapParams, String authCode, String sign) {
		if (mapParams == null || StringUtils.isBlank(authCode) || StringUtils.isBlank(sign)) {
			return false;
		}
		return sign.equals(signZH(mapParams, authCode));
	}

	public static void main(String[] args) {

		System.out.println(JSONArray.fromObject("123,456,789".split(",")).toString());
	}
}