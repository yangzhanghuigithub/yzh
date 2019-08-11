package com.learn.yzh.common.utils;


import com.alibaba.fastjson.JSON;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import com.learn.yzh.common.constants.ResultConstants;
import com.learn.yzh.common.model.Result;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

@Component
public class OSSFileUploadUtils {
   private  static  Environment environment;
	private static  final Logger logger= LoggerFactory.getLogger(OSSFileUploadUtils.class);
	public static   String bucket;
	public static String endpoint;
	public static String key;
	public static String secret;
	public static String ossurl;
	private static OSSClient client;

	/**
	  * APP包上传配置
	  * **/
	 private static OSSClient clientApp;
	 private static String bucketApp;
	 private static String endpointApp;




	 private static void init() {
		 InputStream inputStream=null;
		 try {
			 environment=SpringContextUtils.getBean(Environment.class);
			 String fileName="";

			 String[] envActives=environment.getActiveProfiles();
			 /*根据spring.profiles.active 参数 判断当前运行环境 若是在该参数则读取learn_common-1.0.0-RELEASE.jar 中的 配置文件
			 **因tomcat 未配置 spring.profiles.active 则读取classpath 下的common.properties 后期可在tomcat 启动脚本中添加该参数
			 *
			 */
			 if(Arrays.asList(envActives).contains("dev")||Arrays.asList(envActives).contains("test")||Arrays.asList(envActives).contains("testwo")||Arrays.asList(envActives).contains("spare")){
			 	logger.info("<---------------start by dev or test -------------------->");
				 fileName="common-dev.properties";
				 inputStream=OSSFileUploadUtils.class.getClassLoader().getResourceAsStream(fileName);
			 }else  if(Arrays.asList(envActives).contains("prod")){
				 logger.info("<---------------start by production -------------------->");
				 fileName="common-prod.properties";
				  inputStream=OSSFileUploadUtils.class.getClassLoader().getResourceAsStream(fileName);
			 }else {
				 logger.info("<---------------start by tomcat  -------------------->");
				 fileName="common.properties";
				 inputStream=new FileInputStream(ResourceUtils.getFile("classpath:"+fileName));
			 }
			 Properties properties= new Properties();
			 properties.load(inputStream);
			 bucket= properties.getProperty("oss.bucket");
			 endpoint= properties.getProperty("oss.endpoint");
			 key= properties.getProperty("oss.key");
			 secret= properties.getProperty("oss.secret");
			 ossurl=properties.getProperty("oss.url");
			 client=new OSSClient(endpoint,key,secret);
			 //app上传
			 bucketApp=properties.getProperty("oss.bucket.app");
			 endpointApp= properties.getProperty("oss.endpoint.app");
			 clientApp=new OSSClient(endpointApp,key,secret);
		 } catch (Exception e) {
			 logger.error("<----------------OSS上传异常 请检查代码------------------>",e);
		 }finally {
		 	if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("**************关闭文件流异常**********",e);
				}
			}
		 }
		 // clientApp=new OSSClient(endpointApp,key,secret);
	 }

	 public static Result<String> upload(InputStream input, String fileType){
		 String relateivepaht="";
		 Result result= new Result();
		 try {
			 if(null == client){
                init();
             }
			 String todayStr = DateFormatUtils.format(new Date(), "yyyyMMdd");
			 String fName = todayStr + RandomNoUtils.getInstance().getRandomNo()+fileType;
			  relateivepaht= getRelativePath(fileType,fName);
			 PutObjectResult putObjectResult=  client.putObject(bucket,relateivepaht,input);
			 logger.info("文件上传结果*******************{}", JSON.toJSON(putObjectResult));
			 result.setResultData(relateivepaht);
		 } catch (OSSException e) {
		 	result.setResultDesc("文件上传异常");
			 result.setResultCode(ResultConstants.Result_Error_Code_500);
			 logger.error("OSS异常 上传文件类型{},*****************异常信息{}",fileType,e);
		 } catch (ClientException e) {
			 result.setResultDesc("文件上传异常");
			 result.setResultCode(ResultConstants.Result_Error_Code_500);
			 logger.error("OSS异常 上传文件类型{},****************异常信息{}",fileType,e);
		 }/*finally {
		 	if(client!=null){
		 		client.shutdown();
			}
		 }*/
		 return result;
	 }




	private static String getRelativePath(String fileType,String fileName) {
		StringBuffer path=new StringBuffer();
		String yeardir=fileName.substring(0,4);
		String monthdir=fileName.substring(4,6);
		String fileFormat=".jpg";
		if(StringUtils.equals("A",fileType)){
			path.append("advert_picture/");
		}
		if(StringUtils.equals("L",fileType)){
			path.append("cinema_logo_picture/");
		}
		if(StringUtils.equals("E",fileType)){
			path.append("event_picture/");
		}
		if(StringUtils.equals("P",fileType)){
			path.append("film_poster_picture/");
		}
		if(StringUtils.equals("S",fileType)){
			path.append("film_still_picture/");
		}
		if(StringUtils.equals("V",fileType)){
			fileFormat=".MP4";
			path.append("film_video_picture/");
		}
		if(StringUtils.equals("G",fileType)){
			path.append("goods_picture/");
		}
		if(StringUtils.equals("H",fileType)){
			path.append("member_head_picture/");
		}
		if(StringUtils.equals("N",fileType)){
			path.append("nearby_picture/");
		}
		if(StringUtils.equals("F",fileType)){
			path.append("feedback_picture/");
		}
		if(StringUtils.equals("I",fileType)){
			path.append("company_picture/");
		}
		path.append(yeardir).append("/"+monthdir+"/").append(fileName).append(fileFormat);
		return path.toString();
	}

	public static boolean uploadApk(InputStream input, String dir, String name){
		 if(null == clientApp){
			init();
		 }
		 clientApp.putObject(bucketApp,dir+"/"+name,input);
		 return true;
	 }
/*	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if(null!=client){
			client.shutdown();
		}
	}*/

	/**
	 * @param destUrl  目标网络图片
	 * @param dir  文件夹名
	 * @param name  文件名字
	 * @return 0正常   -1：未存在该文
	 */
	public static int saveToFile(String destUrl,String dir,String name) {
		int code = 0;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			//upload(httpUrl.getInputStream(),dir,name);
		} catch (FileNotFoundException e) {
			code=-1;
		} catch (IOException e) {
			code=-2;
			e.printStackTrace();
		} catch (ClassCastException e) {
			code=-3;
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				httpUrl.disconnect();
			} catch (IOException e) {
			} catch (NullPointerException e) {
			}
		}
		return code;
	}
	public boolean deleteFile(String filePath){
		if(null == client){
			init();
		}
		client.deleteObject(bucket,filePath);
		return true;
	}
	/**
	 *
	 * @param base64string
	 * @return
	 */
	public static InputStream BaseToInputStream(String base64string){
		ByteArrayInputStream stream = null;
		try {
			byte[] bytes1=	Encodes.decodeBase64(base64string);

			stream = new ByteArrayInputStream(bytes1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return stream;
	}
	public static void main(String[] args) {
		String imgName = "2017041711492395353801P";//UUID.randomUUID().toString();
		System.out.println(imgName.substring(0,4));
		System.out.println(imgName.substring(4,6));
		/*saveToFile("http://image01.mplus.net.cn/2017041711492395353801P.jpg"
				,"companydir",imgName+".jpg");
		System.out.println("img/poster/"+imgName+".jpg");

		InputStream is;
		try {
			is = new URL("http://image01.mplus.net.cn/2017041711492395353801P.jpgg").openStream();

			BufferedImage sourceImg =ImageIO.read(is);
//       // System.out.println(String.format("%.1f",picture.length()/1024.0));
			System.out.println(sourceImg.getWidth());
			System.out.println(sourceImg.getHeight());

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	 
}
