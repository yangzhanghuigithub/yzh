package com.learn.yzh.common.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

/**
* 通过DES算法，加密或解密数据
* @author Lomis
* @version 2014年5月25日23:01:17
*/
public class DESUtil {
	
	private static final String DES = "DES";
	
	private static final String TRANSFORMATION = "DES/CBC/PKCS5Padding";
	
	/**
	 * DES加密KEY
	 */
	public static final String DES_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCtMrmq19rB1rlCXpHBdkksB2W8lNVTYmG6qfC00J5waNBQmDU9O9j9yZY0czto5hupRMWHJIhgPfQd1oYp2VIcgwmG1KFEEdEpTweQP2tPeEiB1n4oPGBJeDvlDuDmXhN+1vt6sc6hW9iLLTtGUY5GlmULxkE4IeeTlZ0WLAnIcwIDAQAB";

	public static final String SEC_IV = "12345678";
	
	/**
     * DES加密
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @param secIv 初始化向量
     * @return    返回加密后的数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] src, byte[] key, byte[] secIv) throws Exception {
    		// 1.创建MD5散列对象
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(key);									// 散列密钥
			DESKeySpec dks = new DESKeySpec(md.digest());	// 获得DES密钥
    		
			// 2.创建DES加密密钥工厂
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);
            
            // 3.创建初始化向量对象
    		IvParameterSpec iv = new IvParameterSpec(secIv);
    		AlgorithmParameterSpec paramSpec = iv;
    		
            // 4.为加密算法指定填充方式，创建加密会话对象
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            
            // 5.初始化加解密会话对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, paramSpec);
            
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(src);
    }
     
    /**
     * DES解密
     * @param data   数据源
     * @param key   密钥，长度必须是8的倍数
     * @param secIv 初始化向量
     * @return      返回解密后的原始数据
     * @throws NoSuchAlgorithmException 
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key, byte[] secIv) throws Exception {
    	// 1.创建MD5散列对象
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(key);									// 散列密钥
		DESKeySpec dks = new DESKeySpec(md.digest());	// 获得DES密钥
		
		// 2.创建DES加密密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        
        // 3.创建初始化向量对象
		IvParameterSpec iv = new IvParameterSpec(secIv);
		AlgorithmParameterSpec paramSpec = iv;
		
        // 4.为加密算法指定填充方式，创建加密会话对象
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, paramSpec);
        
        // 现在，获取数据并解密
        // 正式执行解密操作
        return cipher.doFinal(data);
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
    	// 加密
//    	String data = encrypt("汉字123abc", "abcdefgh", "abcdefgh");
//    	System.out.println(data);
//    	
//    	data = "{\"cpu\":\"AMDcccddddd Athlon(tm)II Dual-Core M321 2.10GHz\",\"disk\":\"500G\",\"memory\":\"3.00GB\",\"mac\":\"1A-9B-92-AC-9F-C4\",\"sys\":[{\"osv\":\"Window7\",\"osb\":\"32位\"}]}";
//    	data = encrypt(data, DESUtil.DES_KEY, DESUtil.SEC_IV);
//    	System.out.println(data);
//    	System.out.println(data.length());
    	
//    	data// = "8xb70aZSyRU6kO0BeNGx+mTqZaU7P9nvqatVjq09sSlHOb0r6AV5fuEDEcxmk1Dv9T6bv/98Ial3b0lsq3c8fGy6QIB1POFkoA/LoSaGYgN+KiNnwS4TLxcK/mp70nX3tC2bdgEt7HYwH+Hnoa7aTOvGaNHlGhAof/3zJCOUy+dTJeX4rnhejZp4fVdbbAvGzU0aXwXNzcwSbEiKb8Eplw==";
//    	="8xb70aZSyRU6kO0BeNGx+mTqZaU7P9nvqatVjq09sSlHOb0r6AV5fuEDEcxmk1Dv9T6bv/98Ial3"
//    	+"b0lsq3c8fGy6QIB1POFkoA/LoSaGYgN+KiNnwS4TLxcK/mp70nX3tC2bdgEt7HYwH+Hnoa7aTOvG"
//    	+"aNHlGhAof/3zJCOUy+dTJeX4rnhejZp4fVdbbAvGzU0aXwXNzcwSbEiKb8Eplw==";
    	
    	// 解密
//    	System.out.println(decrypt("SFDv5SpWguS1FrzSb+0cfKvOEjuzzOkYe+Sb9/+DaQZPFAYnv9TLXWnritov6bL/aRKxlL/Xp0YM7ouwCetkHjZwsaBXq6ZuaBWY2X2AbuM=", DES_KEY, SEC_IV));
//    	System.out.println(encodeBASE64("test".getBytes()));
	}
	
}