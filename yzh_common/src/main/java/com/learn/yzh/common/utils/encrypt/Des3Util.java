package com.learn.yzh.common.utils.encrypt;
import com.learn.yzh.common.constants.SystemConstants;
import com.learn.yzh.common.utils.Encodes;
import com.learn.yzh.common.utils.PlatformProperties;
import com.learn.yzh.common.utils.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

/**
 * 3DES加密工具
 * @author Lomis
 *
 */
public class Des3Util {
	
	private final static String encoding = "utf-8";						// 编码方式
	private String secretKey = "taiheyouchuang_mplus@159753qaz$#8426#$";	// 密钥
	private String iv = "01234567";										// 向量
	
	private static Des3Util instance;
	
	private Des3Util (String secretKey, String iv) {
		if (StringUtils.isNotBlank(secretKey) && StringUtils.isNotBlank(iv)) {
			this.secretKey = secretKey;
			this.iv = iv;
		}
	}
	
	public static Des3Util getInstance () {
		synchronized (Des3Util.class) {
			if (instance == null) {
				String secretKey = new SystemConstants().getDes3SecretKey();	// 密钥
				String iv = new SystemConstants().getDes3Iv();				// 向量
				instance = new Des3Util(secretKey, iv);
			}
		}
		return instance;
	}

	/**
	 * 3DES加密
	 * @param plainText 普通文本
	 * @return
	 * @throws Exception
	 */
	public String encode(String plainText) throws Exception {
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		Key deskey  = keyfactory.generateSecret(spec);

		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
		return Encodes.encodeBase64(encryptData);
	}

	/**
	 * 3DES解密
	 * @param encryptText 加密文本
	 * @return
	 * @throws Exception
	 */
	public String decode(String encryptText) throws Exception {
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		Key deskey = keyfactory.generateSecret(spec);
		
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

		byte[] decryptData = cipher.doFinal(Encodes.decodeBase64(encryptText));
		return new String(decryptData, encoding);
	}
}
