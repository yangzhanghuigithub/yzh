package com.learn.yzh.common.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.learn.yzh.common.constants.SystemConstants;
import com.learn.yzh.common.mapper.JsonMapper;
import com.learn.yzh.common.utils.FileUploadUtils;
import com.learn.yzh.common.utils.MailUtil;
import com.learn.yzh.common.utils.PlatformProperties;
import com.learn.yzh.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 接口服务工具类
 * @author Lomis
 * @version 2015年11月26日 08:59:07
 *
 */
@Service
@SuppressWarnings("unchecked")
public class CommonService {
	@Value("${oss.url}")
	private String ossUrl;
	private static final Logger logger = LoggerFactory.getLogger(CommonService.class);

	@Autowired
	private SystemConstants systemConstants;
	
	/**
	 * 返回对象Json格式数据并DES3加密
	 * @param result
	 * @return
	 */
	public String toResult (Object result) {
		try {
			String jsonResult = toJson(result);
			logINFO("result:[" + jsonResult + "]", null);
			//jsonResult = Des3Util.getInstance().encode(jsonResult);
			return jsonResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 返回对象Json格式数据
	 * @param result
	 * @return
	 */
	public String toJson(Object result) {
		String json = JsonMapper.getJsonMapper().toJson(result);
		return json;
	}
	
	/**
	 * DEBUG级别日志
	 * @param content	// 调用信息
	 * @param objs		// 参数值信息
	 */
	public void logDEBUG (String content, HttpServletRequest request) {
		String jsonParam = paramsToJson(request);
//		baseMongdbTemplateDao.getMongoTemplate().save(objectToSave, collectionName);
		logger.debug(content, jsonParam);
	}

	/**
	 * INFO级别日志
	 * @param content	// 调用信息
	 * @param objs		// 参数值信息
	 */
	public void logINFO (String content, HttpServletRequest request) {
		String jsonParam = paramsToJson(request);
		logger.info(content, jsonParam);
	}
	
	/**
	 * ERROR级别日志
	 * @param content	// 调用信息
	 * @param cause		// 异常信息
	 * @param objs		// 参数值信息
	 */
	public void logERROR (String content, Throwable cause, HttpServletRequest request) {
		try {
			String jsonParam = paramsToJson(request);
			logger.error(content, jsonParam);
			try {
				final String c = content + ":" + jsonParam;
				final String msg = StringUtils.getStackTrace(cause) ;
//				new Thread() {
//					public void run() {
//						MailUtil.getInstance().send("服务器异常", c + "\r\n" + msg);
//					};
//				}.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 参数转成Json格式
	 * @param request
	 * @return
	 */
	private String paramsToJson(HttpServletRequest request) {
		if (request != null) {
			Map<String, String[]> params = request.getParameterMap();
			String jsonParam = toJson(params);
			return jsonParam;
		} else {
			return null;
		}
		
	}

	/**
	 * 图片格式转换
	 * 图片的名称生成规则 年份+月份+随机码+.jpg
	 * 存放路径规则 resource/images/图片分类/年份/月份/
	 * @param img
	 * @return
	 */
	public String convertImg(String img) {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(img) && !"null".equals(img)) {
			String[] ary = img.split(",");
			for (int index = 0; index < ary.length; index++) {
				String pic = ary[index];
				//sb.append(pic.substring(0, 4)); sb.append("/");
				//sb.append(pic.substring(4, 6)); sb.append("/");
				if (StringUtils.isNotBlank(pic) && pic.indexOf("OSS_")>-1){
					pic = pic.substring(pic.indexOf("_")+1);
				}
				sb.append(pic);
				sb.append(".jpg");
				sb.append(",");
			}
			if (sb.length() > 0) {
				sb.setLength(sb.length() - 1);
			}
		}
		return sb.toString();
	}

	public String convertImgNew(String img) {
		StringBuffer sb = new StringBuffer();
		FileUploadUtils fileUploadUtils=new FileUploadUtils();
		if (StringUtils.isNotBlank(img) && !"null".equals(img)) {
			img = img.indexOf(".jpg")>0? StringUtils.substring(img,0,img.indexOf(".")):img;
			String[] ary = img.split(",");
			for (int index = 0; index < ary.length; index++) {
				String pic = ary[index];
				//sb.append(pic.substring(0, 4)); sb.append("/");
				//sb.append(pic.substring(4, 6)); sb.append("/");
				if(StringUtils.isNotBlank(fileUploadUtils.getNewUrl(pic))){
					sb.append(fileUploadUtils.getNewUrl(pic)).append(",");
				}

			}
			if (sb.length() > 0) {
				sb.setLength(sb.length() - 1);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 图片格式转换
	 * 图片的名称生成规则 年份+月份+随机码+.jpg
	 * 存放路径规则 resource/images/图片分类/年份/月份/
	 * @param img
	 * @param num
	 * @return
	 */
	public String convertImg(String img, int num) {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(img)) {
			String[] ary = img.split(",");
			for (int index = 0; index < ary.length; index++) {
				if (index == num) break;
				String pic = ary[index];
				//sb.append(pic.substring(0, 4)); sb.append("/");
				//sb.append(pic.substring(4, 6)); sb.append("/");
				sb.append(pic); 				sb.append(".jpg");
				sb.append(",");
			}
			if (sb.length() > 0) {
				sb.setLength(sb.length() - 1);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 处理商品的图片
	 * @param img
	 * @return
	 */
	public String convertGoodsImg(String img) {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(img)) {
			String[] ary = img.split(",");
			if(ary.length > 0) {
				sb.append(ary[0]);
				sb.append(".jpg");
			}
		}
		return sb.toString();
	}

	/**
	 * 预告片格式转换
	 * 预告片的名称生成规则 年份+月份+随机码+.flv
	 * 存放路径规则 resource/videos/年份/月份/
	 * @param filmVideo
	 * @return
	 */
	public List<Map<String, String>> convertVideo(String filmVideo) {
		List<Map<String, String>> list = Lists.newArrayList();
		if (StringUtils.isNotBlank(filmVideo) && !"null".equals(filmVideo)) {
			String[] ary = filmVideo.split(",");
			Map<String, String> m = null;
			for (int index = 0; index < ary.length; index++) {
				String[] videos = ary[index].split("\\|");
				if (videos != null && videos.length > 0) {
					m = Maps.newHashMap();
					if(videos.length > 1){
						m.put("videoUrl", videos[1] + ".mp4");
					}else{
						m.put("videoUrl",  "");
					}
					m.put("imgUrl",   videos[0] + ".jpg");
					list.add(m);
				}
			}
		}
		return list;
	}


	public List<Map<String, String>> convertVideoNew(String filmVideo) {
		FileUploadUtils fileUploadUtils=new FileUploadUtils();
		List<Map<String, String>> list = Lists.newArrayList();
		if (StringUtils.isNotBlank(filmVideo) && !"null".equals(filmVideo)) {
			String[] ary = filmVideo.split(",");
			Map<String, String> m = null;
			for (int index = 0; index < ary.length; index++) {
				String[] videos = ary[index].split("\\|");
				if (videos != null && videos.length > 0) {
					m = Maps.newHashMap();
					if(videos.length > 1){
						m.put("videoUrl", fileUploadUtils.getNewUrl(videos[1]));
					}else{
						m.put("videoUrl", "");
					}
					m.put("imgUrl",   fileUploadUtils.getNewUrl(videos[0]));
					list.add(m);
				}
			}
		}
		return list;
	}

	/**
	 * 获取图片上传后存放的路径
	 * @param type
	 * @return
	 */
	public String getImgPath(String type) {
		return PlatformProperties.getProperty(type);
	}

	/**
	 * 获取满天星POS系统中座位的状态
	 * @param seatState
	 * @return
	 */
	public String getCmstsSeatStatus(String seatState) {
		if ("0".equals(seatState)) {
			return "F";
		} else if ("7".equals(seatState)) {
			return "B";
		} else {
			return "B";
		}
	}

	/**
	 * 处理价格的小数位
	 * @param filmPrice
	 * @return
	 */
	public Object cutPrice(Double filmPrice) {
		if(null == filmPrice) {
			return null;
		}
		
		int price = filmPrice.intValue();
		
		if (filmPrice > price) {
			return filmPrice;
		}
		
		return price;
	}


	public String convertOssUrl(String Img) {
		String url="";
		if(Img.indexOf("H")>-1){
			String yeardir=Img.substring(0,4);
			String monthdir=Img.substring(4,6);
			url=ossUrl+"member_head_picture/"+yeardir+"/"+monthdir+"/"+Img+".jpg";
		}else {
			url=ossUrl+Img;
		}
		return url;
	}
}
