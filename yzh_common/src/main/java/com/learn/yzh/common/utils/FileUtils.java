package com.learn.yzh.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 文件操作工具类
 * 实现文件的创建、删除、复制、压缩、解压以及目录的创建、删除、复制、压缩解压等功能
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	
	private static Logger log = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * 复制单个文件，如果目标文件存在，则不覆盖
	 * @param srcFileName 待复制的文件名
	 * @param descFileName 目标文件名
	 * @return 如果复制成功，则返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String descFileName) {
		return FileUtils.copyFileCover(srcFileName, descFileName, false);
	}

	/**
	 * 复制单个文件
	 * @param srcFileName 待复制的文件名
	 * @param descFileName 目标文件名
	 * @param coverlay 如果目标文件已存在，是否覆盖
	 * @return 如果复制成功，则返回true，否则返回false
	 */
	public static boolean copyFileCover(String srcFileName, String descFileName, boolean coverlay) {
		File srcFile = new File(srcFileName);
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			log.debug("复制文件失败，源文件 " + srcFileName + " 不存在!");
			return false;
		}
		// 判断源文件是否是合法的文件
		else if (!srcFile.isFile()) {
			log.debug("复制文件失败，" + srcFileName + " 不是一个文件!");
			return false;
		}
		File descFile = new File(descFileName);
		// 判断目标文件是否存在
		if (descFile.exists()) {
			// 如果目标文件存在，并且允许覆盖
			if (coverlay) {
				log.debug("目标文件已存在，准备删除!");
				if (!FileUtils.delFile(descFileName)) {
					log.debug("删除目标文件 " + descFileName + " 失败!");
					return false;
				}
			} else {
				log.debug("复制文件失败，目标文件 " + descFileName + " 已存在!");
				return false;
			}
		} else {
			if (!descFile.getParentFile().exists()) {
				// 如果目标文件所在的目录不存在，则创建目录
				log.debug("目标文件所在的目录不存在，创建目录!");
				// 创建目标文件所在的目录
				if (!descFile.getParentFile().mkdirs()) {
					log.debug("创建目标文件所在的目录失败!");
					return false;
				}
			}
		}

		// 准备复制文件
		// 读取的位数
		int readByte = 0;
		InputStream ins = null;
		OutputStream outs = null;
		try {
			// 打开源文件
			ins = new FileInputStream(srcFile);
			// 打开目标文件的输出流
			outs = new FileOutputStream(descFile);
			byte[] buf = new byte[1024];
			// 一次读取1024个字节，当readByte为-1时表示文件已经读取完毕
			while ((readByte = ins.read(buf)) != -1) {
				// 将读取的字节流写入到输出流
				outs.write(buf, 0, readByte);
			}
			log.debug("复制单个文件 " + srcFileName + " 到" + descFileName
					+ "成功!");
			return true;
		} catch (Exception e) {
			log.debug("复制文件失败：" + e.getMessage());
			return false;
		} finally {
			// 关闭输入输出流，首先关闭输出流，然后再关闭输入流
			if (outs != null) {
				try {
					outs.close();
				} catch (IOException oute) {
					oute.printStackTrace();
				}
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException ine) {
					ine.printStackTrace();
				}
			}
		}
	}

	/**
	 * 复制整个目录的内容，如果目标目录存在，则不覆盖
	 * @param srcDirName 源目录名
	 * @param descDirName 目标目录名
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectory(String srcDirName, String descDirName) {
		return FileUtils.copyDirectoryCover(srcDirName, descDirName,
				false);
	}

	/**
	 * 复制整个目录的内容 
	 * @param srcDirName 源目录名
	 * @param descDirName 目标目录名
	 * @param coverlay 如果目标目录存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectoryCover(String srcDirName, String descDirName, boolean coverlay) {
		File srcDir = new File(srcDirName);
		// 判断源目录是否存在
		if (!srcDir.exists()) {
			log.debug("复制目录失败，源目录 " + srcDirName + " 不存在!");
			return false;
		}
		// 判断源目录是否是目录
		else if (!srcDir.isDirectory()) {
			log.debug("复制目录失败，" + srcDirName + " 不是一个目录!");
			return false;
		}
		// 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
		String descDirNames = descDirName;
		if (!descDirNames.endsWith(File.separator)) {
			descDirNames = descDirNames + File.separator;
		}
		File descDir = new File(descDirNames);
		// 如果目标文件夹存在
		if (descDir.exists()) {
			if (coverlay) {
				// 允许覆盖目标目录
				log.debug("目标目录已存在，准备删除!");
				if (!FileUtils.delFile(descDirNames)) {
					log.debug("删除目录 " + descDirNames + " 失败!");
					return false;
				}
			} else {
				log.debug("目标目录复制失败，目标目录 " + descDirNames + " 已存在!");
				return false;
			}
		} else {
			// 创建目标目录
			log.debug("目标目录不存在，准备创建!");
			if (!descDir.mkdirs()) {
				log.debug("创建目标目录失败!");
				return false;
			}

		}

		boolean flag = true;
		// 列出源目录下的所有文件名和子目录名
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 如果是一个单个文件，则直接复制
			if (files[i].isFile()) {
				flag = FileUtils.copyFile(files[i].getAbsolutePath(),
						descDirName + files[i].getName());
				// 如果拷贝文件失败，则退出循环
				if (!flag) {
					break;
				}
			}
			// 如果是子目录，则继续复制目录
			if (files[i].isDirectory()) {
				flag = FileUtils.copyDirectory(files[i]
						.getAbsolutePath(), descDirName + files[i].getName());
				// 如果拷贝目录失败，则退出循环
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			log.debug("复制目录 " + srcDirName + " 到 " + descDirName + " 失败!");
			return false;
		}
		log.debug("复制目录 " + srcDirName + " 到 " + descDirName + " 成功!");
		return true;

	}

	/**
	 * 
	 * 删除文件，可以删除单个文件或文件夹
	 * @param fileName 被删除的文件名
	 * @return 如果删除成功，则返回true，否是返回false
	 */
	public static boolean delFile(String fileName) {
 		File file = new File(fileName);
		if (!file.exists()) {
			log.debug(fileName + " 文件不存在!");
			return true;
		} else {
			if (file.isFile()) {
				return FileUtils.deleteFile(fileName);
			} else {
				return FileUtils.deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 
	 * 删除单个文件
	 * @param fileName 被删除的文件名
	 * @return 如果删除成功，则返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				log.debug("删除单个文件 " + fileName + " 成功!");
				return true;
			} else {
				log.debug("删除单个文件 " + fileName + " 失败!");
				return false;
			}
		} else {
			log.debug(fileName + " 文件不存在!");
			return true;
		}
	}

	/**
	 * 
	 * 删除目录及目录下的文件
	 * @param dirName 被删除的目录所在的文件路径
	 * @return 如果目录删除成功，则返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dirName) {
		String dirNames = dirName;
		if (!dirNames.endsWith(File.separator)) {
			dirNames = dirNames + File.separator;
		}
		File dirFile = new File(dirNames);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			log.debug(dirNames + " 目录不存在!");
			return true;
		}
		boolean flag = true;
		// 列出全部文件及子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = FileUtils.deleteFile(files[i].getAbsolutePath());
				// 如果删除文件失败，则退出循环
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = FileUtils.deleteDirectory(files[i]
						.getAbsolutePath());
				// 如果删除子目录失败，则退出循环
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			log.debug("删除目录失败!");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			log.debug("删除目录 " + dirName + " 成功!");
			return true;
		} else {
			log.debug("删除目录 " + dirName + " 失败!");
			return false;
		}

	}

	/**
	 * 创建单个文件
	 * @param descFileName 文件名，包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static boolean createFile(String descFileName) {
		File file = new File(descFileName);
		if (file.exists()) {
			log.debug("文件 " + descFileName + " 已存在!");
			return false;
		}
		if (descFileName.endsWith(File.separator)) {
			log.debug(descFileName + " 为目录，不能创建目录!");
			return false;
		}
		if (!file.getParentFile().exists()) {
			// 如果文件所在的目录不存在，则创建目录
			if (!file.getParentFile().mkdirs()) {
				log.debug("创建文件所在的目录失败!");
				return false;
			}
		}

		// 创建文件
		try {
			if (file.createNewFile()) {
				log.debug(descFileName + " 文件创建成功!");
				return true;
			} else {
				log.debug(descFileName + " 文件创建失败!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(descFileName + " 文件创建失败!");
			return false;
		}

	}

	/**
	 * 创建目录
	 * @param descDirName 目录名,包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static boolean createDirectory(String descDirName) {
		String descDirNames = descDirName;
		if (!descDirNames.endsWith(File.separator)) {
			descDirNames = descDirNames + File.separator;
		}
		File descDir = new File(descDirNames);
		if (descDir.exists()) {
			log.debug("目录 " + descDirNames + " 已存在!");
			return false;
		}
		// 创建目录
		if (descDir.mkdirs()) {
			log.debug("目录 " + descDirNames + " 创建成功!");
			return true;
		} else {
			log.debug("目录 " + descDirNames + " 创建失败!");
			return false;
		}

	}

	/**
	 * 多个文件打成zip包
	 * @param zipFilePath
	 * @param files
	 * @return
	 */
	public static boolean zipFiles(String zipFilePath,List<File> files){
		boolean isSuccess=false;
		ZipOutputStream zipOut=null;
		FileInputStream fin=null;
		byte[] buffer=new byte[1024];
		int len=0;
		try {
			zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath));
			for(int i=0;i<files.size();i++){
				fin=new FileInputStream(files.get(i));
				zipOut.putNextEntry(new ZipEntry(files.get(i).getName()));
				
				while((len = fin.read(buffer))>0) {
					zipOut.write(buffer,0,len);
				}
				zipOut.closeEntry();
				fin.close();
				files.get(i).delete();
			}
			isSuccess=true;
		} catch (Exception e) {
			isSuccess=false;
			e.printStackTrace();
		}finally{
			if(zipOut!=null)
				try {
					zipOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return isSuccess;
	}
	
	/**
	 * 解压zip文件
	 * @param inputStream
	 * @return
	 */
	public static List<File> tarZipFiles(InputStream inputStream){
		List<File> files=new ArrayList<File>();
		ZipInputStream zin=null;
		ZipEntry zipEntry=null;
		File outFile=null;
		
		FileOutputStream fos=null;
		byte[] buffer=new byte[1024];
		int len=0;
		try {
			zin=new ZipInputStream(inputStream);
			while((zipEntry=zin.getNextEntry())!=null){
				outFile=new File(zipEntry.getName());
				fos=new FileOutputStream(outFile);
				while((len=zin.read(buffer))>0){
					fos.write(buffer,0,len);
				}
				fos.close();
				files.add(outFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(zin!=null) zin.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return files;
	}
	
	/**
	 * 将文件转化为字节流使用
	 * @param file
	 * @return
	 */
	public static byte[] getFileBytes(File file){
		FileInputStream fin=null;
		byte[] buffer=new byte[1024];
		int len=0;
		ByteArrayOutputStream bos=null;
		try {
			fin=new FileInputStream(file);
			bos=new ByteArrayOutputStream();
			while((len=fin.read(buffer))>0){
				bos.write(buffer,0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(bos!=null) bos.close();
				if(fin!=null) fin.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return bos.toByteArray();
	}
	
	/**
	 * 将文字内容输出到指定文件
	 * @param filePath
	 * @param content
	 * @return
	 */
	public static File createFile(String filePath,String content){
		FileOutputStream fos=null;
		byte[] buffer=new byte[1024];
		int len=0;
		//创建流程图文件
		ByteArrayInputStream jpdlXmlInputStream=null;
		try {
			jpdlXmlInputStream = new ByteArrayInputStream(content.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			fos=new FileOutputStream(filePath);
			while((len = jpdlXmlInputStream.read(buffer))>0) {
				fos.write(buffer,0,len);
				fos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fos!=null)
				try {
					fos.close();
					jpdlXmlInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return new File(filePath);
	}
	/**
	 * 获取指定（fileName）的扩展名称
	 * @param fileName
	 * @return
	 */
	public static String getFileExtensionName(String filename){
		 if ((filename != null) && (filename.length() > 0)) {   
	          int dot = filename.lastIndexOf('.');   
	          if ((dot > -1) && (dot < (filename.length() - 1))) {   
	             return filename.substring(dot + 1);   
	          }   
	     }   
	     return filename; 
	}
	/**
	 * 获取不带扩展名的文件名称
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int dot = filename.lastIndexOf('.');   
            if ((dot >-1) && (dot < (filename.length()))) {   
                return filename.substring(0, dot);   
            }   
        }   
        return filename;   
    } 
	/**
	 * 根据文件名称，获取文件icon
	 * @param filename
	 * @return
	 */
	public static String getFileIcon(String filename){
		String icon = "";
		String exName = getFileExtensionName(filename);
		if(exName.equals("avi")){// avi
			icon = "avi.jpg";
		} else if(exName.equals("zip")){//zip
			return "zip.jpg";
		} else if(exName.equals("rar")){//rar
			return "rar.jpg";
		} else if(exName.equals("exe")){//exe
			return "exe.jpg";
		} else if(exName.equals("xls") || exName.equals("xlsx")){//xls
			return "excel.jpg";
		} else if(exName.equals("doc") || exName.equals("docx")){//doc
			return "word.jpg";
		} else if(exName.equals("ppt") || exName.equals("pptx") ){//ppt
			return "ppt.jpg";
		} else if(exName.equals("txt")){//txt
			return "txt.jpg";
		} else if(exName.equals("chm")){//chm
			return "chm.jpg";
		} else if(exName.equals("pdf")){//pdf
			return "pdf.jpg";
		} else if(exName.equals("jpg")){//jpg
			return "jpg.jpg";
		} else if(exName.equals("bmp")){//bmp
			return "bmp.jpg";
		} else if(exName.equals("gif")){//gif
			return "pic.jpg";
		} else if(exName.equals("html") || exName.equals("htm") || exName.equals("xhtml")){//html
			return "html.jpg";
		} else{
			return "normal.jpg";
		}
		return icon;
	}
	
	/**
	 *  获取操作系统文件路径分隔符
	 */
	public static String getSep(){
	    return System.getProperties().getProperty("file.separator"); 
	}
	
	/**
	 * 检查文件路径是否有效
	 * @param path
	 * @return
	 */
	public static void checkPath(String path) throws IOException{
		File filePath = new File(path);
		if(!filePath.exists()){
			throw new IOException("无效的文件路径【" + path + "】;");
		}
	}

}
