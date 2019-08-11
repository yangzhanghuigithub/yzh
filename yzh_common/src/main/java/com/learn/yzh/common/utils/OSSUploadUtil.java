package com.learn.yzh.common.utils;




import com.aliyun.oss.OSSClient;
import com.learn.yzh.common.utils.secure.CommonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class OSSUploadUtil {
	 private static OSSClient client;
	 private static String bucket;
	 private static String endpoint;
	 private static String key;
	 private static String secret;


	 /**
	  * APP包上传配置
	  * **/
	 private static OSSClient clientApp;
	 private static String bucketApp;
	 private static String endpointApp;

	 private static Properties prop;


	 private CommonProperty commonProperty =SpringContextUtils.getBean(CommonProperty.class);

	 private void init() {
		 prop=new Properties();
	 	String fileName = "common.properties";
//	 	try {
//			File file = ResourceUtils.getFile("classpath:" + fileName);
//			prop.load(new FileInputStream(file));
//			if (prop.isEmpty()) {
//				throw new FileNotFoundException(fileName + "is null ,please check!");
//			}
//		} catch (IOException e) {
//		}

		 commonProperty.getPropertyForStr("key");
		 key= commonProperty.getPropertyForStr("key");
///		 PropertiesUtil.getParamInProp("common.properties","key");
		 secret= commonProperty.getPropertyForStr("secret");
/// PropertiesUtil.getParamInProp("common.properties","secret");
		 bucket= commonProperty.getPropertyForStr("bucket");
///		 PropertiesUtil.getParamInProp("common.properties","bucket");
		 endpoint= commonProperty.getPropertyForStr("endpoint");
///		 PropertiesUtil.getParamInProp("common.properties","endpoint");
		 bucketApp= commonProperty.getPropertyForStr("bucket-app");
///		 PropertiesUtil.getParamInProp("common.properties","bucket-app");
		 endpointApp= commonProperty.getPropertyForStr("endpoint-app");
///		 PropertiesUtil.getParamInProp("common.properties","endpoint-app");
		 client=new OSSClient(endpoint,key,secret);
		 clientApp=new OSSClient(endpointApp,key,secret);
	 }
//	 static{
//		 key="qM9scRugysmMkLI3";
//		 secret="x1WUw8cg7hnjp9h1dOL7zPQi8pILuc";
//		 bucket="azichanimg";
//		 endpoint="oss-cn-beijing.aliyuncs.com";
//		 client=new OSSClient(endpoint,key,secret);
//	 }
	/* static{//taihegroup
		 key="LTAIlx0Y0lS8Ctbq";
		 secret="PCkPAazmCppzeJzdGrpWtIa0sEBgDd";
		 bucket="mplus001";
		 endpoint="oss-cn-qingdao.aliyuncs.com";
		 client=new OSSClient(endpoint,key,secret);
	 }*/
	 public  boolean upload(InputStream input, String dir, String name){
		 if(null == client){
			init();
		 }
		 String yeardir=name.substring(0,4);
		 String monthdir=name.substring(4,6);
		 client.putObject(bucket,dir+"/"+yeardir+"/"+monthdir+"/"+name,input);
		 return true;
	 }

	 public  boolean uploadApk(InputStream input,String dir,String name){
		 if(null == clientApp){
			init();
		 }
		 clientApp.putObject(bucketApp,dir+"/"+name,input);
		 return true;
	 }
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if(null!=client){
			client.shutdown();
		}
	}

	/**
	 * @param destUrl  目标网络图片
	 * @param dir  文件夹名
	 * @param name  文件名字
	 * @return 0正常   -1：未存在该文
	 */
	public  int saveToFile(String destUrl,String dir,String name) {
		int code = 0;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			upload(httpUrl.getInputStream(),dir,name);
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
