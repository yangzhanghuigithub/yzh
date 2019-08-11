package com.learn.yzh.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	/**
	 * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
	 */
	protected static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 获取文件的MD5
	 * @param fis
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(InputStream fis) throws IOException {
		try {
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
				messagedigest.update(buffer, 0, numRead);
			}
			fis.close();
			return bufferToHex(messagedigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    /**
     * 获取字节数组的MD5 
     * @param data
     * @return
     */
    public static String getByteArrayMD5String (byte[] data) {
    	try {
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");  
			 messagedigest.update(data, 0, data.length);  
			 return bufferToHex(messagedigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
    	return null ;
    }
    
    /**
     * 获取字符串的MD5
     * @param str
     * @return
     */
    public static String getStringMD5(String str){
    	try {
    		byte[] buffer = str.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			messagedigest.update(buffer);
			// 获得密文
            byte[] md = messagedigest.digest();
			return bufferToHex(md);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	return null ;
    }
    
	/**
	 * 转换成十六进制的字符串形式
	 * @param bytes
	 * @return
	 */
	public static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	/**
	 * 转换成十六进制的字符串形式
	 * @param bytes
	 * @param m
	 * @param n
	 * @return
	 */
	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}
  
	/**
	 * 连接
	 * @param bt
	 * @param stringbuffer
	 */
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {  
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换  
        // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同  
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换  
        stringbuffer.append(c0);  
        stringbuffer.append(c1);  
    }  
    
    public static String SHA1(String decript) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
    
}