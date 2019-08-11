package com.learn.yzh.common.utils.file;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class CutImage {
	
	
	/**
	 * 对图片进行剪裁,规定宽度和高度
	 * @param file源文件
	 * @param width 宽度
	 * @param height 高度
	 * @return
	 * @throws IOException
	 */
	public static String  cutImage(File file,int width,int height) throws IOException{
		String fileName=file.getName().replace(".", "_"+width+height+".");		
		return cutImage(file.getPath(),file.getParentFile()+File.separator+fileName,width, height);
	}
	
	/**
	 * 对固定宽度图片的剪裁
	 * @param srcPath
	 * @param newFileName 生成说略图的文件名(xxxx_1010.jpg)
	 * @param isDelete  是否删除原始图片
	 * @return
	 * @throws IOException
	 */
	public static boolean cutImage(String srcPath,int maxWidth) throws IOException {
		//List list = new ArrayList();
		File srcFile =new File(srcPath);
			BufferedImage image = ImageIO.read(srcFile);
			int srcWidth = image.getWidth(null);
			//int srcHeight = image.getHeight(null);
			String newFilePath=srcPath.replace(".", "_"+maxWidth+".");
			if (srcWidth < maxWidth) {
				CopyFile.create(srcPath, newFilePath);
				return true;
			}
			int x = 0, y = 0;
			int[] wh=ChangeImageSize.maxWidth(srcFile, maxWidth);
			BufferedImage newImage = new BufferedImage(wh[0], wh[1],
					BufferedImage.TYPE_INT_RGB);
			newImage.getGraphics().drawImage(
					image.getScaledInstance(wh[0], wh[1],
							Image.SCALE_SMOOTH), 0, 0, null);
			// 保存缩放后的图片
			String fileSufix = srcFile.getName().substring(srcFile.getName().lastIndexOf(".") + 1);
			File destFile = new File(newFilePath);
			// 保存裁剪后的图片(这里如果是800*600的图片) x的值为16，原newWidth的值约为132，则调用方法getSubimage，就是从 newWidth左边的16开始取值，取长度100，后面的16就去掉了，也就是两边都去掉16
			ImageIO.write(newImage.getSubimage(x, y, wh[0], wh[1]), fileSufix,destFile);
			return true;		
	}
	
	
	/**
	 * 图片的剪裁
	 * @param srcPath 原来文件
	 * @param newFileName 生成说略图的文件名(xxxx_1010.jpg)
	 * @param isDelete  是否删除原始图片
	 * @return
	 * @throws IOException 
	 */
	private static String cutImage(String srcPath, String newFilePath,int width,int height) throws IOException {
			File dir = new File(newFilePath).getParentFile();
			if(!dir.exists()){
				dir.mkdirs();
			}
			File srcFile = new File(srcPath);
			BufferedImage image = ImageIO.read(srcFile);
			int srcWidth = image.getWidth(null);
			int srcHeight = image.getHeight(null);

			if (srcWidth < width || srcHeight < height) {
				CopyFile.create(srcPath, newFilePath);
				return "success";
			}
			int newWidth = 0, newHeight = 0;
			int x = 0, y = 0;
			double scale_w = (double) width / srcWidth;
			double scale_h = (double) height / srcHeight;

			// 按原比例缩放图片(以长度值小的一边的比例值进行缩放，比例值越大，长度越小。)
			if (scale_w < scale_h) {
				//scale_w越小，表示源图片的width越大，则要按较小的边 height(scale_h缩放比例) 为标准缩放图片了，
				//较大的边 width 也得按scale_h值的比例缩放，才能保证图像清晰高。但缩放后的newWidth值一定比newHeight大，只能把newWidth-width多余的值在两边都剪切一般走
				newHeight = height;
				newWidth = (int) (srcWidth * scale_h);
				x = (newWidth - width) / 2;
			} else if(scale_w > scale_h) {
				newHeight = (int) (srcHeight * scale_w);
				newWidth = width;
				y = (newHeight - height) / 2;
			}
			else if(scale_w == scale_h)//之前没有考虑相等的情况，并且388*388,不是一个整数型的400*400或者500*500之类的。
			{//这里之所以加入这个判断，是为了防止程序出现错误。
			 //我上传一个388*388的图片，生成一个100*100的缩略图时，会出现错误。newHeight=99,newWidth=100，这样就会导致最后一个方法
			 //newImage.getSubimage(0, 0, 100, 100)出现错误。因为图片的大小都只有100*99，却要生成一个100*100的图片。
			 //所以报错：java.awt.image.RasterFormatException:( y + height ) is out of Raster(目的是装载图片，运行时抛出异常)
			 //之所以newHeight=99,而不是=100，是因为(int) (srcHeight * scale_w);得到的数字不是整数，强制转换时，其值就为99了。
				newWidth=newHeight=width;
			}
			BufferedImage newImage = new BufferedImage(newWidth, newHeight,
					BufferedImage.TYPE_INT_RGB);
			newImage.getGraphics().drawImage(
					image.getScaledInstance(newWidth, newHeight,
							Image.SCALE_SMOOTH), 0, 0, null);
			// 保存缩放后的图片
			String fileSufix = srcFile.getName().substring(
					srcFile.getName().lastIndexOf(".") + 1);
			File destFile = new File(newFilePath);
			// 保存裁剪后的图片(这里如果是800*600的图片) x的值为16，原newWidth的值约为132，则调用方法getSubimage，就是从 newWidth左边的16开始取值，取长度100，后面的16就去掉了，也就是两边都去掉16
			ImageIO.write(newImage.getSubimage(x, y, width, height), fileSufix,destFile);
			return "success";
	}

	
	
}
