package com.learn.yzh.common.utils.file;

import com.learn.yzh.common.utils.HttpUtils;
import com.learn.yzh.common.utils.JsonMapper;
import com.learn.yzh.common.utils.OSSUploadUtil;
import com.learn.yzh.common.utils.PropertiesUtil;

import com.learn.yzh.common.utils.encryption.Encodes;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * 文件上传工具类
 * Created by tongy on 2016/1/4.
 */
public class FileUploadUtils {

    public static String fileServerUrl = PropertiesUtil.getParamInProp("common.properties", "base_url")+"file/upload";  //上传文件的url

    public static FileUploadResult uploadFile(String fileData, String fileType, String fileSuffix) {
    	try {
			FileUploadResult result = null;
			Map<String, String> params = new HashMap<String, String>();
			params.put("fileSuffix", 	fileSuffix);
			params.put("fileType", 		fileType);
			params.put("fileData", 		fileData.replaceAll("null",""));
			String json = JsonMapper.getInstance().toJson(params);
			byte[] data = GZipUtils.compress(Encodes.encodeBase64(json.getBytes("utf-8")).getBytes("utf-8"));
			String jsonStr = HttpUtils.post(fileServerUrl, data);
			//System.out.println("result:" + jsonStr);
			result = JsonMapper.getInstance().fromJson(jsonStr, FileUploadResult.class);
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }

    /**
     * 上传文件到服务器
     * @param file     客户端上传的文件 MultipartFile
     * @param fileType 上传的文件的类型
     * @return
     */
    public static FileUploadResult uploadFile(MultipartFile file, String fileType) {
        FileUploadResult result = null;
        try {
            if (file != null) {
            	String originalFileName = file.getOriginalFilename();
				String suffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
                result = uploadFile(Encodes.encodeBase64(file.getBytes()), fileType, suffix);
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = new FileUploadResult();
            result.setResultCode(1);
            result.setResultDesc(e.getMessage());
        }
        return result;
    }
    
    /**
     * 上传多个文件
     * @param file
     * @param fileType
     * @return
     */
    public static FileUploadResult uploadFile(MultipartFile[] files, String fileType) {
    	FileUploadResult result = null;
    	try {
    		if (files != null) {
    			int size = 1;
    			boolean isVideo = false;
    			/*if ((isVideo = "V".equals(fileType))) {
    				if (files.length % 2 == 0) {
    					size = 2;
    				} else if (files.length > 1) {
    					size = files.length;
    				}
    			}*/
    			StringBuffer fileData = new StringBuffer();
    			StringBuffer fileSuffix = new StringBuffer();
    			for (int i = 0; i < files.length; i+=size) {
    				if (isVideo) {
    					MultipartFile imgFile = files[i];
    					MultipartFile videoFile = files[i + 1];
    					
    					String imgOriginalFileName = imgFile.getOriginalFilename();
    					String videoOriginalFileName = videoFile.getOriginalFilename();
    					
    					String imgSuffix = imgOriginalFileName.substring(imgOriginalFileName.lastIndexOf(".") + 1);
    					String videoSuffix = videoOriginalFileName.substring(videoOriginalFileName.lastIndexOf(".") + 1);
    					
    					fileData.append(Encodes.encodeBase64(imgFile.getBytes())).append("|");
    					fileData.append(Encodes.encodeBase64(videoFile.getBytes())).append("#");
    					fileSuffix.append(imgSuffix).append("|");
    					fileSuffix.append(videoSuffix).append("#");
    				} else {
    					MultipartFile file = files[i];
    					String originalFileName = file.getOriginalFilename();
    					String suffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    					fileData.append(Encodes.encodeBase64(file.getBytes())).append("#");
    					fileSuffix.append(suffix).append("#");
    				}
    			}
    			if (fileData.length() > 0) {
    				fileData.setLength(fileData.length() - 1);
    			}
    			if (fileSuffix.length() > 0) {
    				fileSuffix.setLength(fileSuffix.length() - 1);
    			}
    			result = uploadFile(fileData.toString(), fileType, fileSuffix.toString());
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    		result = new FileUploadResult();
    		result.setResultCode(1);
    		result.setResultDesc(e.getMessage());
    	}
    	return result;
    }
    
    public static void main(String[] args) {
    	try {
			FileUploadResult result = uploadFile(Encodes.encodeBase64(FileUtils.readFileToByteArray(new File("E:/project\\test\\images\\film_poster_picture\\2016\\2016010000001P.jpg"))), "P", "jpg");
			//System.out.println(result.getResultCode() + ":" + result.getResultData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static FileUploadResult newUploadFile(MultipartFile[] file, String fileType, FileUploadResult resultData) {
		try {
			OSSUploadUtil ossUploadUtil=new OSSUploadUtil();
			if(StringUtils.isNotBlank(resultData.getResultData())){
				FileUploadResult fuReslut= new FileUploadResult();
				Map  m= new HashMap();
				StringBuffer sbname= new StringBuffer();
				for (int i = 0; i < file.length; i++) {
					CommonsMultipartFile cf = (CommonsMultipartFile) file[i];
					DiskFileItem fi = (DiskFileItem) cf.getFileItem();
					// String todayStr = CalendarUtil.formatDate(new Date(), "yyyyMMdd");
					// File files = fi.getStoreLocation();
					//String fName = todayStr + RandomNoUtils.getInstance().getRandomNo();
					String[] fName=resultData.getResultData().split(",");
					// InputStream inputStream = new FileInputStream(files);
					InputStream inputStream = fi.getInputStream();
					if ("V".equals(fileType)) {
						if (ossUploadUtil.upload(inputStream, PropertiesUtil.getParamInProp("common.properties", "image.path.picture." + fileType), fName[i] + ".mp4")) {
							sbname.append( "OSS_"+fName[i]).append(",");
							m.put("filename", "OSS_"+fName[i]);
							m.put("fileType", fileType);
							//vsbname.append(fName + ",");
						}
					} else if ("P".equals(fileType)) {
						if (ossUploadUtil.upload(inputStream, PropertiesUtil.getParamInProp("common.properties", "image.path.picture." + fileType),  fName[i] + ".jpg")) {
							sbname.append( "OSS_"+fName[i]).append(",");
							m.put("filename", "OSS_"+fName[i]);
							m.put("fileType", fileType);
							//psbname.append(fName + ",");
						}
					} else if ("S".equals(fileType)) {
						if (ossUploadUtil.upload(inputStream, PropertiesUtil.getParamInProp("common.properties", "image.path.picture." + fileType), fName[i] + ".jpg")) {
							sbname.append( "OSS_"+fName[i]).append(",");
							m.put("filename", "OSS_"+fName[i]);
							m.put("fileType", fileType);
							//sbname.append(fName + ",");
						}
					}else if ("A".equals(fileType)) {
						if (ossUploadUtil.upload(inputStream, PropertiesUtil.getParamInProp("common.properties", "image.path.picture." + fileType),  fName[i]  + ".jpg")) {
							sbname.append( "OSS_"+fName[i]).append(",");
							m.put("filename", "OSS_"+fName[i]);
							m.put("fileType", fileType);
							//sbname.append(fName + ",");
						}
					}
					else if ("G".equals(fileType)) {
						if (ossUploadUtil.upload(inputStream, PropertiesUtil.getParamInProp("common.properties", "image.path.picture." + fileType), fName[i]  + ".jpg")) {
							sbname.append( "OSS_"+fName[i]).append(",");
							m.put("filename", "OSS_"+fName[i]);
							m.put("fileType", fileType);
							//sbname.append(fName + ",");
						}
					}
				}
				resultData.setResultData(sbname.toString().substring(0,sbname.lastIndexOf(",")));
				return resultData;
			}
		}catch (Exception e){
			resultData.setResultCode(1);
			resultData.setResultDesc("网络原因，上传OSS失败");
			e.printStackTrace();
		}

		return resultData;
	}
}