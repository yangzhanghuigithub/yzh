package com.learn.yzh.common.utils;

import net.sf.json.JSONObject;

/**
 * 安全工具
 * @author hapday
 * @date 2016年9月28日 下午2:38:16
 */
public class SecureUtils {
	private static String CHANNEL_CODE = "dingxin";
	private static String SECRET_KEY = "1234567890";
	private static float VERSION = 1.0f;
	private static String PREFIX_URL = "http://api.mplus.net.cn/mplus/admin/order/pos/route";	// 阿里云测试
	
	static {
//		PREFIX_URL = "http://localhost/mplus-server10/admin/order/pos/route";	// 本地测试
//		PREFIX_URL = "http://123.57.59.9:8080/mplus/admin/order/pos/route";	// 阿里云测试
	}
	
	/**
	 * 加密
	 * @return
	 */
	/*public static String clientEncrypt() {
		System.out.println("--------------- 客户端加密 start -----------------");
		String interfaceName = "admin/order/pos/createPOSOrder";
		String cardId = "02763a005d9f4ae8875825b119fc1e0e";
		String cinemaCode = "11061501"; 
		String seatInfo = "[{\"seatId\":\"1\",\"filmCode\":\"001105972016\",\"showCode\":\"33845096\",\"hallId\":\"3\",\"lowestPrice\":\"30\",\"price\":150,\"servicePrice\":\"10\"}]";
		String productInfo = "[{\"productCode\":\"333\",\"productType\":\"single\",\"price\":10}]"; 
		
		Map<String, Object> parameterMap = new TreeMap<String, Object>();
		parameterMap.put("cardId", cardId);
		parameterMap.put("cinemaCode", cinemaCode);
		parameterMap.put("seatInfo", seatInfo);
		parameterMap.put("productInfo", productInfo);
		System.out.println("parameterMap = " + JsonMapper.getJsonMapper().toJson(parameterMap));
		
		JSONObject paramJSONObject = JSONObject.fromObject(parameterMap);
		System.out.println("paramJSONObject = " + paramJSONObject);
		
		long currentTimeMillis = System.currentTimeMillis();
		
		String unsigned = "api" + interfaceName + "channelCode" + CHANNEL_CODE + "data" + paramJSONObject + "timestamp" + currentTimeMillis + "version" + VERSION + SECRET_KEY;
		System.out.println("unsigned = " + unsigned);
		
		String paramTemp = paramJSONObject.toString().replace("{", "%7b").replace("}", "%7d").replace("\"", "%22");
		System.out.println("paramTemp = " + paramTemp);
		
		String signature = MD5Util.getStringMD5(unsigned);		// 签名
		System.out.println("signature = " + signature);
		
		String url = PREFIX_URL + "?api=" + interfaceName + "&channelCode=" + CHANNEL_CODE + "&data=" + paramTemp + "&timestamp=" + currentTimeMillis + "&version=" + VERSION + "&sign=" + signature;
		System.out.println("url = " + url);
		
		System.out.println("--------------- 客户端加密 end -----------------");
		
		return url;
	}*/

	/**
	 * 加密
	 * @param clientChannelCode
	 * @param clientVersion
	 * @param clientInterfaceName
	 * @param clientTimestamp
	 * @param clientParameters
	 * @return
	 */
	public static String serverEncrypt(String serverChannelCode, Float serverVersion, String serverSecretKey, String clientInterfaceName, String clientTimestamp, String clientParameters) {
		System.out.println("--------------- 服务器端加密 start -----------------");
		System.out.println("clientParameters = " + clientParameters);
		
		JSONObject paramJSONObject = new JSONObject();
		
		String unsigned = "api" + clientInterfaceName + "channelCode" + serverChannelCode + "data" + clientParameters + "timestamp" + clientTimestamp + "version" + serverVersion + serverSecretKey;
		System.out.println("unsigned = " + unsigned);
		
		String paramTemp = paramJSONObject.toString().replace("{", "%7b").replace("}", "%7d").replace("\"", "%22");
		System.out.println("paramTemp = " + paramTemp);
		
		String signature = MD5Util.getStringMD5(unsigned);		// 签名
		System.out.println("signature = " + signature);
		
		String url = PREFIX_URL + "?api=" + clientInterfaceName + "&channelCode=" + serverChannelCode + "&data=" + paramTemp + "&timestamp=" + clientTimestamp + "&version=" + serverVersion + "&sign=" + signature;
		System.out.println("url = " + url);
		
		System.out.println("--------------- 服务器端加密 end -----------------");
		
		return signature;
	}
	
	/**
	 * 解码
	 * @param source
	 * @return
	 */
	public static String decode(String source) {
		if(StringUtils.isBlank(source)) {
			return null;
		}
		
		System.out.println("--------------- 服务端解码 start -----------------");
		System.out.println("source = " + source);
		int questionMarkIndex = source.indexOf("?");
		System.out.println("questionMarkIndex = " + questionMarkIndex);
		String parameters = source.substring(questionMarkIndex + 1, source.length());
		System.out.println("parameters = " + parameters);
		String parameters_ [] = null;
		if(StringUtils.isNotBlank(parameters)) {
			parameters_ = parameters.split("&"); 
		}
		System.out.println("--------------------------------");
		String clientInterfaceName = null;
		String clientParameters = null;
		String clientSignature = null;
		String clientTimestamp = null;
		String clientVersion = null;
		String clientChannelCode = null;
		
		if(null != parameters_ && 0 < parameters_.length) {
			for(int index = 0; index < parameters_.length; index++) {
				String parameter = parameters_[index];
				
				if(StringUtils.isBlank(parameter)) {
					continue;
				}
				
				System.out.println(parameter);
				
				if(parameter.startsWith("api=")) {
					clientInterfaceName = parameter.substring(parameter.indexOf("api=") + 4, parameter.length());
				}
				if(parameter.startsWith("data=")) {
					clientParameters = parameter.substring(parameter.indexOf("data=") + 5, parameter.length());
				}
				if(parameter.startsWith("sign=")) {
					clientSignature = parameter.substring(parameter.indexOf("sign=") + 5, parameter.length());
				}
				if(parameter.startsWith("timestamp=")) {
					clientTimestamp = parameter.substring(parameter.indexOf("timestamp=") + 10, parameter.length());
				}
				if(parameter.startsWith("version=")) {
					clientVersion = parameter.substring(parameter.indexOf("version=") + 8, parameter.length());
				}
				if(parameter.startsWith("channelCode=")) {
					clientChannelCode = parameter.substring(parameter.indexOf("channelCode=") + 12, parameter.length());
				}
			}
		}
		System.out.println("--------------------------------");
		clientParameters = clientParameters.replace("%7b", "{").replace("%7d", "}").replace("%22", "\"").replace("%5b", "\"[").replace("%5d", "]\"").replace("%20", " ");
		System.out.println("clientInterfaceName = " + clientInterfaceName);
		System.out.println("clientChannelCode = " + clientChannelCode);
		System.out.println("clientParameters = " + clientParameters);
		System.out.println("clientSignature = " + clientSignature);
		System.out.println("clientTimestamp = " + clientTimestamp);
		System.out.println("clientVersion = " + clientVersion);
		System.out.println("--------------------------------");
		System.out.println("--------------- 解码 end -----------------");

		String serverSignature = serverEncrypt(CHANNEL_CODE, VERSION, SECRET_KEY, clientInterfaceName, clientTimestamp, clientParameters);
		
		if(CHANNEL_CODE.equals(clientChannelCode)) {
			System.out.println("通道号 - 正确。");

			if(clientSignature.equals(serverSignature)) {
				System.out.println("签名 - 正确。");
			} else {
				System.out.println("签名 - 错误。");
			}
		}
		
		return source;
	}
}
