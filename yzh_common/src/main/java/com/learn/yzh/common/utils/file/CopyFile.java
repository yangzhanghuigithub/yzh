package com.learn.yzh.common.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//复制一个文件
public class CopyFile {

	/**
	 * 复制一个文件
	 * 
	 * @param 旧的文件路径
	 * @parm 新的文件路径
	 * @return 生成的文件图片名称
	 * @throws IOException
	 */
	public static void create(String oldFilePath, String newfilePath)
			throws IOException {
		File dir = new File(newfilePath).getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(oldFilePath), 5096);
			File file = new File(newfilePath);
			out = new BufferedOutputStream(new FileOutputStream(file), 5096);
			int index = 0;
			byte buffer[] = new byte[5096];
			while ((index = in.read(buffer)) != -1) {
				out.write(buffer, 0, index);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
