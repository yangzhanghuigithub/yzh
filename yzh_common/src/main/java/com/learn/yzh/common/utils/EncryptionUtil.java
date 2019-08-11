package com.learn.yzh.common.utils;

/**
 * 密码验证
 * @author Lomis
 *
 */
public class EncryptionUtil {
	/**
	 * 验证密码
	 * @param password
	 * @param mdPassword
	 * @return
	 */
	public static boolean compareSHA(String password,String mdPassword){
		String secretValue = PlatformProperties.getProperty("secret_key");////盐值
		password = MD5Util.getStringMD5(password + "_" + secretValue);
		if(password.equals(mdPassword)){
			return true;
		}
		return false;
	}
	/**
	 * 生成密码平台默认密码
	 * @return
	 */
	public static String productSHA(){
		String secretValue = PlatformProperties.getProperty("secret_key");//盐值
		String password = PlatformProperties.getProperty("default_password");//配置的默密码
		return MD5Util.getStringMD5(password.trim() + "_" + secretValue).trim();
	}
	/**
	 * 生成密码平台默认
	 * @return
	 */
	public static String productSHA(String password){
		String secretValue = PlatformProperties.getProperty("secret_key");//盐值
		return MD5Util.getStringMD5(password.trim() + "_" + secretValue).trim();
	}
	
}
