package com.learn.yzh.common.utils.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

public class ChangeImageSize {
	
	// 对图片的大小进行等比例缩放
	public static int[] changeImage(File file, int maxWidth, int maxHeight) {
		int[] wh = new int[2];
		try {
			BufferedImage img = ImageIO.read(file);
			int oldWidth = img.getWidth();
			int oldHeight = img.getHeight();

			if (oldWidth > 0 && oldHeight > 0) {
				if (oldWidth / oldHeight >= maxWidth / maxHeight) {
					if (oldWidth > maxWidth) {
						wh[0] = maxWidth;
						wh[1] = (oldHeight * maxWidth) / oldWidth;
					} else {
						wh[0] = oldWidth;
						wh[1] = oldHeight;
					}
				} else {
					if (oldHeight > maxHeight) {
						wh[1] = maxHeight;
						wh[0] = (oldWidth * maxHeight) / oldHeight;
					} else {
						wh[0] = oldWidth;
						wh[1] = oldHeight;
					}
				}
			}

			if (wh[0] > maxWidth) {
				wh[0] = maxWidth;
			}

			if (wh[1] > maxHeight) {
				wh[1] = maxHeight;
			}

			return wh;
		} catch (IOException e) {
			wh[0] = maxWidth;
			wh[1] = maxHeight;
			return wh;
		}

	}

	/**
	 * 宽度一定，自适应高度
	 * 
	 * @param imageURL
	 * @param maxWidth
	 * @return 宽和高
	 */
	public static int[] maxWidth(File file, int maxWidth) {
		int oldWidth = 0;
		int oldHeight = 0;
		try {
			BufferedImage img = ImageIO.read(file);
			oldWidth = img.getWidth();
			oldHeight = img.getHeight();
		} catch (IOException e) {
		}
		int[] wh = new int[2];
		if (oldWidth >= maxWidth) {
			wh[0] = maxWidth;
			wh[1] = maxWidth * oldHeight / oldWidth;
		} else {
			wh[0] = oldWidth;
			wh[1] = oldHeight;
		}
		return wh;

	}

	/**
	 * 
	 * @param sourceName
	 *            源文件的名称
	 * @param formatName
	 *            要转换成的格式 例如 png,gif,
	 * @param folderName
	 *            原文件所在的webRoot下面的文件夹的名称 例如 uploadImages icon
	 * @throws IOException
	 */
	public static String convert(File file, String formatName)
			throws IOException {
		File f = file;
		f.canRead();
		f.canWrite();
		BufferedImage src = ImageIO.read(f);
		String uuid = UUID.randomUUID().toString();
		String fileName = file.getPath().substring(0,
				file.getPath().lastIndexOf("."));
		ImageIO.write(src, formatName, new File(fileName + "." + formatName));
		return uuid + "." + formatName;
	}

}
