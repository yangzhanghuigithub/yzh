package com.learn.yzh.common.utils;

import com.google.common.collect.Lists;
import com.learn.yzh.common.utils.secure.CommonProperty;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 文件上传保存工具
 * @author Lomis
 *
 */
public class FileUploadUtils {
	private  static  final org.slf4j.Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);
	private CommonProperty commonProperty =SpringContextUtils.getBean(CommonProperty.class);
	//private static final String Resource_Base_Path = PlatformProperties.getProperty("resource.base.path");
	private  final String Resource_Base_Path =commonProperty.getPropertyForStr("key");
	private static List<String> picType = Lists.newArrayList(new String[]{"gif", "GIF", "JPEN", "png", "PNG", "bmp", "BMP"});
	
	/**
	 * 接受图片, 返回图片地址
	 * @param fileType 文件类型, 用此类型对应文件的类型目录
	 * 		   剧照:film_still_picture 海报:film_poster_picture 头像:user_head_picture
	 * @param file
	 * @return
	 */
	private  String storeIOc(String fileType, byte[] data, String suffix) {
		String fName = null;
		if (data != null && data.length > 0 && StringUtils.isNotBlank(suffix)) {
			suffix = suffix.toLowerCase();
			StringBuffer sb = new StringBuffer(Resource_Base_Path).append(FileUtils.getSep());
			boolean needCon = false;
			if ("jpg".equals(suffix)) {
				sb.append("images")
				.append(FileUtils.getSep())
				.append(PlatformProperties.getProperty("image.path.picture." + fileType))
				.append(FileUtils.getSep());
			} else if (picType.contains(suffix)) {
				needCon = true;
			} else if ("mp4".equals(suffix)) {
				sb.append("videos").append(FileUtils.getSep());
			} else {
				sb.append("other").append(FileUtils.getSep());
			}
			String todayStr = DateUtils.getDate("yyyyMMdd");
			fName = todayStr + RandomNoUtils.getInstance().getRandomNo() + fileType;
			/*String originalFileName = file.getOriginalFilename();
			String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));*/
			sb.append(todayStr.substring(0, 4)).append(FileUtils.getSep()).append(todayStr.substring(4, 6)).append(FileUtils.getSep()).append(fName).append(".").append(suffix);
			File restore = new File(sb.toString());
			try {
				if (needCon) {
					FileUtils.createDirectory(sb.substring(0, sb.lastIndexOf(fName)));
					img2jpg(data, restore);
				} else {
					FileUtils.writeByteArrayToFile(restore, data);
				}
				//file.transferTo(restore);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		// 返回默认的图片地址
		return fName;
	}
	
	/**
	 * 上传多张图片
	 * @param request
	 * @param fileType
	 * @param fileSuffix
	 * @param fileData
	 * @return
	 */
	public  String uploadMoreFile(HttpServletRequest request, String fileType, String fileSuffix, String fileData) {
		if (StringUtils.isNotBlank(fileData)) {
			return uploadMoreFile(fileData, fileType, fileSuffix);
		}
		StringBuffer imgs = new StringBuffer();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			String mp4 = null;
			for (Map.Entry<String, MultipartFile> set : fileMap.entrySet()) {
				MultipartFile file = set.getValue();
				String originalFileName = file.getOriginalFilename();
				String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
				String filmName = storeIOc(fileSuffix, file.getBytes(), suffix);
				if ("V".equals(fileType) && "mp4".equals(fileType)) {
					mp4 = filmName;
				} else {
					imgs.append(filmName).append(",");
				}
			}
			if ("V".equals(fileType)) {
				imgs.append(mp4).append(",");
			}
			if (imgs.length() > 0) {
				imgs.setLength(imgs.length() - 1);
			}
		} catch (Exception e) {
			logger.info("上传多张图片时出现异常", e);
			e.printStackTrace();
		}
		// 返回默认的图片地址
		return imgs.toString();
	}
	
	/**
	 * 上传多张图片
	 * @param data		// 图片数据,数据格式为 二进制数据进行base64编码, 多个文件数据中间用#隔开
	 * @param fileType	// 文件类型
	 * @param fileSuffix
	 * @return
	 */
	public  String uploadMoreFile(String data, String fileType, String fileSuffix) {
		StringBuffer imgs = new StringBuffer();
		if (StringUtils.isNotBlank(data)) {
			try {
				String[] datas = data.split("#");
				String[] fileSuffixs = fileSuffix.split("#");
				if (datas != null && datas.length > 0) {
					if ("V".equals(fileType)) { // 处理预告片文件上传
						for (int i = 0; i < datas.length; i++) {
							String dataStr = datas[i];
							String[] ds = dataStr.split("\\|");
							String[] sfs = fileSuffixs[i].split("\\|");
							if (ds != null && ds.length == 2) {
								imgs.append(storeIOc(fileType, Encodes.decodeBase64(ds[0]), sfs[0])).append("|");
								imgs.append(storeIOc(fileType, Encodes.decodeBase64(ds[1]), sfs[1])).append(",");
							} else if (ds != null && ds.length == 1) {
								imgs.append(storeIOc(fileType, Encodes.decodeBase64(ds[0]), sfs[0])).append(",");
							}
						}
					} else {
						for (int i = 0; i < datas.length; i++) {
							String dataStr = datas[i];
							imgs.append(storeIOc(fileType, Encodes.decodeBase64(dataStr), fileSuffixs[i])).append(",");
						}
					}
					if (imgs.length() > 0) {
						imgs.setLength(imgs.length() - 1);
					}
				}
				
			} catch (Exception e) {
				logger.info("上传多张图片时出现异常", e);
				e.printStackTrace();
			}
		}
		// 返回默认的图片地址
		return imgs.toString();
	}



	public   String  getNewUrl(String typePic){
		if(StringUtils.isNotBlank(typePic)){
			String url="";
			String pics=typePic;
			try {
				if(typePic.startsWith("OSS_")){
					typePic=typePic.substring(4, typePic.length());
				}
				String dir=typePic.substring(0,4)+"/"+typePic.substring(4,6)+"/";
				String pic="";
				if(typePic.lastIndexOf("A")>0){
					pic=typePic+".jpg";

					//url=PlatformProperties.getProperty("image.path.picture.NEWURL")+PlatformProperties.getProperty("image.path.picture.A")+"/"+dir+pic;
					url=commonProperty.getPropertyForStr("image.path.picture.NEWURL")+commonProperty.getPropertyForStr("image.path.picture.A")+"/"+dir+pic;
				}else if(typePic.lastIndexOf("P")>0){
					pic=typePic+".jpg";
					//url=PlatformProperties.getProperty("image.path.picture.NEWURL")+PlatformProperties.getProperty("image.path.picture.P")+"/"+dir+pic;
					url=commonProperty.getPropertyForStr("image.path.picture.NEWURL")+commonProperty.getPropertyForStr("image.path.picture.P")+"/"+dir+pic;
				}else if(typePic.lastIndexOf("S")>0){
					pic=typePic+".jpg";
					//url=PlatformProperties.getProperty("image.path.picture.NEWURL")+PlatformProperties.getProperty("image.path.picture.S")+"/"+dir+pic;
					url=commonProperty.getPropertyForStr("image.path.picture.NEWURL")+commonProperty.getPropertyForStr("image.path.picture.S")+"/"+dir+pic;
				}else if(typePic.lastIndexOf("G")>0){
					pic=typePic+".jpg";
					//url=PlatformProperties.getProperty("image.path.picture.NEWURL")+PlatformProperties.getProperty("image.path.picture.G")+"/"+dir+pic;
					url=commonProperty.getPropertyForStr("image.path.picture.NEWURL")+commonProperty.getPropertyForStr("image.path.picture.G")+"/"+dir+pic;
				}else if(typePic.lastIndexOf("V")>0){
					pic=typePic+".mp4";
					//url=PlatformProperties.getProperty("image.path.picture.NEWURL")+PlatformProperties.getProperty("image.path.picture.V")+"/"+dir+pic;
					url=commonProperty.getPropertyForStr("image.path.picture.NEWURL")+commonProperty.getPropertyForStr("image.path.picture.V")+"/"+dir+pic;
				}else if(typePic.lastIndexOf("H")>0){
					pic=typePic+".jpg";
					//url=PlatformProperties.getProperty("image.path.picture.NEWURL")+PlatformProperties.getProperty("image.path.picture.H")+"/"+dir+pic;
					url=commonProperty.getPropertyForStr("image.path.picture.NEWURL")+commonProperty.getPropertyForStr("image.path.picture.H")+"/"+dir+pic;
				}else if(typePic.lastIndexOf("F")>0){
					pic=typePic+".jpg";
					//url=PlatformProperties.getProperty("image.path.picture.NEWURL")+PlatformProperties.getProperty("image.path.picture.H")+"/"+dir+pic;
					url=commonProperty.getPropertyForStr("image.path.picture.NEWURL")+commonProperty.getPropertyForStr("image.path.picture.F")+"/"+dir+pic;
				}
				if(!pics.startsWith("OSS_")){
					url=PlatformProperties.getProperty("image.path.picture.OLDURL")+pic;
				}
				
				/*bis=checkInputisNull(url);//判断新的地址是否能取到图片
				if(bis==null||bis.available()==0 ){
					url=PlatformProperties.getProperty("image.path.picture.OLDURL")+pic;
					bis=checkInputisNull(url);//判断老的地址是否能取到图片
					if(bis==null||bis.available()==0){
						return   "";
					}
					return  url;
				}*/
				return  url;
			}catch (Exception e){
				e.printStackTrace();
			}/*finally {
				if (bis != null){
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}*/

		}

		return "";
	}

	private static BufferedInputStream checkInputisNull(String url) {
		BufferedInputStream bis = null;
		URL	urls = null;
		HttpURLConnection httpUrl = null;
		try {
			urls = new URL(url);
			httpUrl = (HttpURLConnection) urls.openConnection();
			httpUrl.setConnectTimeout(5000);
			httpUrl.setReadTimeout(5000);
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
		}catch (Exception e){
//			logger.error(e.getMessage()+"OSS服务器上图片不存在URL为:"+url);
//			e.printStackTrace();
		}
		return bis;
	}

	/**
	 * 把图片格式转成jpg
	 * @param data		// 需要转换的图片数据
	 * @param destFile	// 需要保存后的目标文件
	 */
	public static void img2jpg(byte[] data, File destFile) {	
		try {
			BufferedImage bi = ImageIO.read(new ByteArrayInputStream(data));
			ImageIO.write(bi, "jpg", destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			byte[] data = FileUtils.readFileToByteArray(new File("e:/project/test/test.bmp"));
			img2jpg(data, new File("e:/project/test/test.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
