/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.learn.yzh.common.utils.file;

import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Utils - 图片处理(支持JDK、GraphicsMagick、ImageMagick)
 * 
 * @author echoliv Team
 * @version 3.0
 */
public final class ImgHelper {

	
	/**
	 * 把base64编码的图片字符串转为文件
	 * @param base64Str
	 * @param imgPath
	 * @return
	 */
	public static boolean generateImage(String base64Str,File file) {// 对字节数组字符串进行Base64解码并生成图片
		if (base64Str == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(base64Str);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			File parent = file.getParentFile(); 
			if(parent!=null&&!parent.exists()){ 
				parent.mkdirs(); 
			} 
			
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(file);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}