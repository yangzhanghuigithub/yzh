package com.learn.yzh.common.utils;


import com.learn.yzh.common.utils.mjSms.SmsClientSend;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsUtils {
	private static Logger logger = LoggerFactory.getLogger(SmsUtils.class);

	/**
	 * 发送短信：创蓝文化   蜗牛 剧角 冠宇
	 * <p>
	 * 代码	说明
	 * 0	提交成功
	 * 101	无此用户
	 * 102	密码错
	 * 103	提交过快（提交速度超过流速限制）
	 * 104	系统忙（因平台侧原因，暂时无法处理提交的短信）
	 * 105	敏感短信（短信内容包含敏感词）
	 * 106	消息长度错（>536或<=0）
	 * 107	包含错误的手机号码
	 * 108	手机号码个数错（群发>50000或<=0;单发>200或<=0）
	 * 109	无发送额度（该用户可用短信数已使用完）
	 * 110	不在发送时间内
	 * 111	超出该账户当月发送额度限制
	 * 112	无此产品，用户没有订购该产品
	 * 113	extno格式错（非数字或者长度不对）
	 * 115	自动审核驳回
	 * 116	签名不合法，未带签名（用户必须带签名的前提下）
	 * 117	IP地址认证错,请求调用的IP地址不是系统登记的IP地址
	 * 118	用户没有相应的发送权限
	 * 119	用户已过期
	 * 120	短信内容不在白名单中
	 *
	 * @param mobile
	 * @param msg
	 * @return
	 */
	public static boolean sendSms(String mobile, String msg) {
		try {
			String url = "http://222.73.117.156/msg/HttpBatchSendSM";
			String account = "Thyc888";
			String pswd	   = "Thyc_888";
			boolean needstatus = true;
			Map<String, String> params = new HashMap<String, String>();
			params.put("account", 		account);
			params.put("pswd", 			pswd);
			params.put("mobile", 		mobile);
			params.put("needstatus", 	String.valueOf(needstatus));
			params.put("msg", 			msg);
			params.put("product", 		null);
			params.put("extno", 		null);
			String result = HttpUtil.doGet(url, params);
			logger.info("创蓝文化短信发送结果：" + result);
			if (result.contains(",0")) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 发送短信：创蓝文化  传奇的子账号
	 *
	 * @param mobile
	 * @param msg
	 * @return
	 */
	public static boolean sendSms_chuanqi(String mobile, String msg) {
		try {
			String url = "http://222.73.117.156/msg/HttpBatchSendSM";
			String account = "Thyc88_SAGA";
			String pswd	   = "Saga111111";
			boolean needstatus = true;
			Map<String, String> params = new HashMap<String, String>();
			params.put("account", 		account);
			params.put("pswd", 			pswd);
			params.put("mobile", 		mobile);
			params.put("needstatus", 	String.valueOf(needstatus));
			params.put("msg", 			msg);
			params.put("product", 		null);
			params.put("extno", 		null);
			String result = HttpUtil.doGet(url, params);
			if (result.contains(",0")) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 美嘉短信渠道发送短息
	 *
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static boolean mjSendSms(String mobile, String content) {
		//String send = SmsClientSend.sendSms("http://xtx.telhk.cn:8080/sms.aspx", "send", "6366", "a10474", "753951", mobile, "【美嘉影城】" + content);
		String send = SmsClientSend.sendSms("http://121.52.212.233:7890/sms.aspx", "send", "6366", "801056", "H7nZz8", mobile, "【美嘉影城】" + content);
		try {
			String result = new String(send.getBytes("UTF-8"));
			logger.info("mjSendSms美嘉短信发送结果："+result);
			if (result.contains("success")) {
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 美嘉短信渠道发送营销短信的渠道=====营销短信渠道=============
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static boolean mjSendSmsYX(String mobile, String content) {
		//String send = SmsClientSend.sendSms("http://xtx.telhk.cn:8080/sms.aspx", "send", "6367", "y00494", "aa4220521", mobile, "【美嘉影城】" + content + "退订回T");
		String send = SmsClientSend.sendSms("http://121.52.212.233:7890/sms.aspx", "send", "6367", "801056", "H7nZz8", mobile, "【美嘉影城】" + content + "退订回T");
		try {
			String result = new String(send.getBytes("UTF-8"));
			logger.info("mjSendSmsYX美嘉营销短信发送结果："+result);
			if (result.contains("success")) {
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 美嘉短信渠道发送短息
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static boolean mjSendSms2(String mobile, String content) {
		//String send = SmsClientSend.sendSms("http://xtx.telhk.cn:8080/sms.aspx", "send", "6366", "y00494", "aa4220521", mobile, "【美嘉影城】" + content + "退订回T");
		String send = SmsClientSend.sendSms("http://121.52.212.233:7890/sms.aspx", "send", "6366", "801056", "H7nZz8", mobile, "【美嘉影城】" + content + "退订回T");
		try {
			String result = new String(send.getBytes("UTF-8"));
			logger.info("mjSendSms2美嘉短信发送结果："+result);
			if (result.contains("success")) {
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 唐阁短信渠道发送短息
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static boolean tgsendSms(String mobile, String msg) {
		try {
			String url = "http://222.73.117.158/msg/HttpBatchSendSM";
			String account = "Thyc88_Tangoyizhuang";
			String pswd	   = "TangGe123";
			boolean needstatus = true;
			Map<String, String> params = new HashMap<String, String>();
			params.put("account", 		account);
			params.put("pswd", 			pswd);
			params.put("mobile", 		mobile);
			params.put("needstatus", 	String.valueOf(needstatus));
			params.put("msg", 			msg);
			params.put("product", 		null);
			params.put("extno", 		null);
			String result = HttpUtil.doGet(url, params);
			logger.info("唐阁短信发送结果："+result);
			if (result.contains(",0")) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}


	/**
	 * 美凯龙短信渠道发送短息
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static boolean mklsendSms(String mobile, String msg) {
		try {
			String url = "http://222.73.117.158/msg/HttpBatchSendSM";
			String account = "Thyc88_HongXing";
			String pswd	   = "HongXing123";
			boolean needstatus = true;
			Map<String, String> params = new HashMap<String, String>();
			params.put("account", 		account);
			params.put("pswd", 			pswd);
			params.put("mobile", 		mobile);
			params.put("needstatus", 	String.valueOf(needstatus));
			params.put("msg", 			msg);
			params.put("product", 		null);
			params.put("extno", 		null);
			String result = HttpUtil.doGet(url, params);
			logger.info("美凯龙渠道短信发送结果："+result);
			if (result.contains(",0")) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 华谊短信渠道发送短息
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static boolean hysendSms(String mobile, String content) {
		//发送POST请求
		try {
			content+="【华谊兄弟影城】";
			String sname = "dlhyxd01";
			String spwd = "bj654321";
			String sprdid = "1012818";
			String postUrl = "http://cf.51welink.com/submitdata/service.asmx/g_Submit?";
			String postData = "sname="+sname+"&spwd="+spwd+"&scorpid=&sprdid="+sprdid+"&sdst="+mobile+"&smsg="+URLEncoder.encode(content,"utf-8");
			URL url = new URL(postUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Length", "" + postData.length());
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(postData);
			out.flush();
			out.close();
			//获取响应状态
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return false;
			}
			//获取响应内容体
			String line, result = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			while ((line = in.readLine()) != null) {
				result += line + "\n";
			}
			in.close();
			logger.info("华谊渠道短信发送结果："+result);
			if(result != null && result.contains("<State>0</State>")){
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return false;
	}
	
	
	
	/**
	  * 创蓝短信渠道发送短息
	 * @param mobile 手机号
	 * @param content 内容
	 * @param account 用户名
	 * @param pswd 密码
	 * @return
	 */
	public static boolean CLsendSms(String url,String mobile, String msg,String account,String pswd) {
		try {
//			String url = "http://222.73.117.158/msg/HttpBatchSendSM";
			boolean needstatus = true;
			Map<String, String> params = new HashMap<String, String>();
			params.put("account", 		account);
			params.put("pswd", 			pswd);
			params.put("mobile", 		mobile);
			params.put("needstatus", 	String.valueOf(needstatus));
			params.put("msg", 			msg);
			params.put("product", 		null);
			params.put("extno", 		null);
			String result = HttpUtil.doGet(url, params);
			logger.info("********创蓝渠道短信接口返回结果************"+result);
			if (result.contains(",0")) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean cLsendSmsPOST(String url,String mobile, String msg,String account,String pswd) {
		try {
			boolean needstatus = true;
			Map<String, Object> params = new HashMap<>();
			params.put("account", 		account);
			params.put("pswd", 			pswd);
			params.put("mobile", 		mobile);
			params.put("needstatus", 	String.valueOf(needstatus));
			params.put("msg", 			msg);
			//params.put("product", 		null);
			//params.put("extno", 		null);
			String result = clPost(url, params);
			logger.info("********创蓝渠道短信接口返回结果************"+result);
			if (result.contains(",0")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创蓝渠道短信发送异常：",e);
		}
		return false;
	}

	
	/**
	 * 创蓝营销短信发送短息
	 * @param mobile 手机号
	 * @param content 内容
	 * @param account 用户名
	 * @param pswd 密码
	 * @return
	 */
	public static boolean CLsendSmsYX(String mobile, String msg,String account,String pswd) {
		try {
			String path = "http://smssh1.253.com/msg/send/json";
			Map<String, String> params = new HashMap<String, String>();
			params.put("account", 		account);
			params.put("password", 			pswd);
			params.put("phone", 		mobile);
			params.put("report", 	"true");
			params.put("msg", 			msg);
			
			JSONObject jo = JSONObject.fromObject(params);
			String param = jo.toString();
			URL url = new URL(path);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");// 提交模式
			httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
			httpURLConnection.setReadTimeout(2000);//读取超时 单位毫秒
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "application/json");
			httpURLConnection.connect();
			OutputStream os=httpURLConnection.getOutputStream();
			os.write(param.getBytes("UTF-8"));
			os.flush();
			int httpRspCode = httpURLConnection.getResponseCode();
			if (httpRspCode == HttpURLConnection.HTTP_OK) {
				StringBuilder sb = new StringBuilder();
				// 开始获取数据
				BufferedReader br = new BufferedReader(
						new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
				logger.info("创蓝营销短信发送结果："+sb);
				if(sb.toString().contains("\"code\":\"0\"")){
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 行天下短信平台
	 * 行业短信
	 * @param mobile
	 * @param content
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean XTXSendSms(String url,String mobile, String content,String port,String name,String psw,String signatures) {
		//String send = SmsClientSend.sendSms("http://xtx.telhk.cn:8080/sms.aspx", "send", port, name, psw, mobile, "【美嘉影城】" + content);
		//String send = SmsClientSend.sendSms("http://121.52.212.233:7803/sms", "send", port, name, psw, mobile, "【美嘉影城】" + content);
		String send = SmsClientSend.sendSms(url, "send", port, name, psw, mobile, signatures + content);
		try {
			String result = new String(send.getBytes("UTF-8"));
			Map<String, String> map =XMLUtil.doXMLParse(result);
			logger.info("行天下短信发送结果："+map);
			if (map.get("message").equals("OK")) {
				return true;
			} else {
				logger.error("美嘉发送短信返回数据========="+result);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 美嘉营销短信
	 * @param mobile
	 * @param content
	 * @return
	 */
	
	public static boolean XTXSendSmsYX(String url,String mobile, String content,String port,String name,String psw,String countNumber,String signatures) {
		//String send = SmsClientSend.sendSms("http://xtx.telhk.cn:8080/sms.aspx", "send", "6367", "y00494", "aa4220521", mobile, "【美嘉影城】" + content + "退订回T",countNumber);
		//String send = SmsClientSend.sendSms("http://121.52.209.124:8888/sms.aspx", "send", "6367", "y00494", "aa4220521", mobile, "【美嘉影城】" + content + "退订回T",countNumber);
		String send = SmsClientSend.sendSms(url, "send", port, name, psw, mobile, signatures + content + "退订回T",countNumber);
		try {
			String result = new String(send.getBytes("UTF-8"));
			logger.info("美嘉短信发送结果："+result);
			if (result.contains("success")) {
				return true;
			} else {
				logger.error("美嘉发送短信返回数据========="+result);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean XTXSendSmsYX(String mobile, String content,String port,String name,String psw) {
		//String send = SmsClientSend.sendSms("http://xtx.telhk.cn:8080/sms.aspx", "send", "6367", "y00494", "aa4220521", mobile, "【美嘉影城】" + content + "退订回T",countNumber);
		String send = SmsClientSend.sendSms("http://121.52.209.124:8888/sms.aspx", "send", "6367", "y00494", "aa4220521", mobile, "【美嘉影城】" + content + "退订回T");
		try {
			String result = new String(send.getBytes("UTF-8"));
			if (result.contains("success")) {
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
     * 反射调用方法
     * @param methodName 对象的方法名
     * @param args 参数数组
     * @return 返回值
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean invokeMethod(String methodName, Object[] args)throws Exception {
    	boolean state = true;
        Class ownerClass = new SmsUtils().getClass();
        Class[] argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        Method method = ownerClass.getMethod(methodName, argsClass);
        state = (boolean) method.invoke(ownerClass, args);
        return state;
    }
	
    
    public boolean sendMessage(String methodName, String mobile, String msg,String type) {
		boolean b = true;
		Object[] argsa = new Object[]{mobile,msg};
		try {
			b = invokeMethod(methodName,argsa);
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		return b;
	}
	
    
    public boolean sendMessage(String methodName, String mobile, String msg) {
		boolean b = true;
		Object[] argsa = new Object[]{mobile,msg};
		try {
			b = invokeMethod(methodName,argsa);
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		return b;
	}
    
    
    /**
	 * 华谊短信渠道发送短息
	 * @param mobile
	 * @param content
	 * @return
	 */
    
    public static boolean hysendSms(String postUrl,String mobile, String content,String sprdid,String sname,String spwd,String signatures) {
    	//发送POST请求
        try {
//        	String sname = "dlhyxd01";
//        	String spwd = "bj654321";
//        	String sprdid = "1012818";
//        	content+="【华谊兄弟影城】";
//        	String postUrl = "http://cf.51welink.com/submitdata/service.asmx/g_Submit";
        	content+=signatures;
        	String postData = "sname="+sname+"&spwd="+spwd+"&scorpid=&sprdid="+sprdid+"&sdst="+mobile+"&smsg="+URLEncoder.encode(content,"utf-8");
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();
            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false;
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
			logger.info("hysendSms华谊短信发送结果："+result);
            if(result != null && result.contains("<State>0</State>")){
            	return true;
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return false;
    }
    
    
    /**
   	 * 华谊短信渠道发送短息
   	 * @param mobile
   	 * @param content
   	 * @return
   	 */
       public static boolean hysendSmsYX(String mobile, String content,String sprdid,String sname,String spwd) {
       	//发送POST请求
           try {
//           	String sname = "dlhyxd01";
//           	String spwd = "bj654321";
//           	String sprdid = "1012818";
           	String postUrl = "http://cf.51welink.com/submitdata/service.asmx/g_Submit?";
           	String postData = "sname="+sname+"&spwd="+spwd+"&scorpid=&sprdid="+sprdid+"&sdst="+mobile+"&smsg="+URLEncoder.encode(content,"utf-8");
               URL url = new URL(postUrl);
               HttpURLConnection conn = (HttpURLConnection) url.openConnection();
               conn.setRequestMethod("POST");
               conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
               conn.setRequestProperty("Connection", "Keep-Alive");
               conn.setUseCaches(false);
               conn.setDoOutput(true);
               conn.setRequestProperty("Content-Length", "" + postData.length());
               OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
               out.write(postData);
               out.flush();
               out.close();
               //获取响应状态
               if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                   return false;
               }
               //获取响应内容体
               String line, result = "";
               BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
               while ((line = in.readLine()) != null) {
                   result += line + "\n";
               }
               in.close();
               logger.info("hysendSmsYX华谊短信发送结果："+result);
               if(result != null && result.contains("<State>0</State>")){
               	return true;
               }
           } catch (IOException e) {
               e.printStackTrace(System.out);
           }
           return false;
       }
	/**
	 * 星轶影城自用渠道
	 * @param postUrl
	 * @param mobile
	 * @param content
	 * @param sprdid
	 * @param sname
	 * @param spwd
	 * @param signatures
	 * @return
	 */
	public static boolean xySendSms(String postUrl, String mobile, String content, String sprdid, String sname,String spwd, String signatures) {
		// 发送POST请求
		try {
			content = URLEncoder.encode(String.valueOf(content), "GB2312");
			String postData = "CorpID=" + sname + "&Pwd=" + spwd + "&Mobile=" + mobile + "&Content=" + content + "&Cell=&SendTime=";
			URL url = new URL(postUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "GB2312");
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Length", "" + postData.length());
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(postData);
			out.flush();
			out.close();
			// 获取响应状态
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return false;
			}
			// 获取响应内容体
			String line, result = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();
			logger.info("xySendSms星轶短信发送结果："+(("1".equals(result)?"成功":"失败")));

			if (result != null && ("0".equals(result)||"1".equals(result))) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return false;
	}

	/**
	 * 鲁信渠道发送短信
	 *
	 * @param postUrl
	 * @param mobile
	 * @param content
	 * @param sprdid
	 * @param sname
	 * @param spwd
	 * @param signatures
	 * @return
	 */
	public static boolean mdSendSms(String postUrl, String mobile, String content, String sprdid, String sname, String spwd, String signatures) {
		//发送post请求
		try {
			content = URLEncoder.encode(content+signatures,  "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String result = " ";
		String ext = " ";
		String stime = " ";
		String rrid = " ";
		String msgfmt = " ";
		String soapAction = "http://entinfo.cn/mdsmssend";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<mdsmssend  xmlns=\"http://entinfo.cn/\">";
		xml += "<sn>" + sname + "</sn>";
		xml += "<pwd>" + spwd + "</pwd>";
		xml += "<mobile>" + mobile + "</mobile>";
		xml += "<content>" + content + "</content>";
		xml += "<ext>" +""+ "</ext>";
		xml += "<stime>" + "" + "</stime>";
		xml += "<rrid>" + "" + "</rrid>";
		xml += "<msgfmt>" + "" + "</msgfmt>";
		xml += "</mdsmssend>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";

		ByteArrayOutputStream bout = null;
		OutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		URL url;
		try {
			url = new URL(postUrl);

			URLConnection connection = url.openConnection();
			HttpURLConnection httpconn = (HttpURLConnection) connection;
			bout = new ByteArrayOutputStream();
			bout.write(xml.getBytes());
			byte[] b = bout.toByteArray();
			httpconn.setRequestProperty("Content-Length", String
					.valueOf(b.length));
			httpconn.setRequestProperty("Content-Type",
					"text/xml; charset=gb2312");
			httpconn.setRequestProperty("SOAPAction", soapAction);
			httpconn.setRequestMethod("POST");
			httpconn.setDoInput(true);
			httpconn.setDoOutput(true);

			out = httpconn.getOutputStream();
			out.write(b);
			out.close();

			isr = new InputStreamReader(httpconn
					.getInputStream());
			in = new BufferedReader(isr);
			String inputLine;
			while (null != (inputLine = in.readLine())) {
				Pattern pattern = Pattern.compile("<mdsmssendResult>(.*)</mdsmssendResult>");
				Matcher matcher = pattern.matcher(inputLine);
				while (matcher.find()) {
					result = matcher.group(1);
				}
				logger.info("mdSendSms鲁信短信发送结果：" + result);
			}
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (bout != null) {
				try {
					bout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}


	public static void main(String[] args) {
		//System.out.println(mjSendSmsYX("15270893081", "送您一张券"));
//		hysendSms("http://cf.51welink.com/submitdata/service.asmx/g_Submit?",
//				"13522227535","test","1012818","dlhyxd01",
//				"bj654321","【華誼兄弟影城】");

//        CLsendSms("http://222.73.117.158/msg/HttpBatchSendSM",
//                "13522227535,18901297939","您好6","Thyc88_XingMei","admin@123");


		cLsendSmsPOST("http://222.73.117.158/msg/HttpBatchSendSM",
				"13522227535","你好","Thyc88_XingMei","admin@123");
	}

//	public static String doPost(String url, Map<String,String> map, String charset){
//		CloseableHttpClient httpClient = null;
//		HttpPost httpPost = null;
//		String result = null;
//		try{
//			httpClient = HttpClients.createDefault();
//			httpPost = new HttpPost(url);
//			//设置参数
//			List<NameValuePair> list = new ArrayList<NameValuePair>();
//			Iterator iterator = map.entrySet().iterator();
//			while(iterator.hasNext()){
//				Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
//				list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
//			}
//			if(list.size() > 0){
//				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
//				httpPost.setEntity(entity);
//			}
//			HttpResponse response = httpClient.execute(httpPost);
//			if(response != null){
//				HttpEntity resEntity = response.getEntity();
//				if(resEntity != null){
//					result = EntityUtils.toString(resEntity,charset);
//				}
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//		return result;
//	}

	/**
	 * 创蓝POST提交
	 * @param url
	 * @param params
	 * @return
	 */
	public static String clPost(String url, Map<String, Object> params) {
		logger.info("确定连接地址不能为空：URL="+url+" , params="+ com.alibaba.fastjson.JSONObject.toJSONString(params));
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext(); ) {
				String key = iterator.next();
				parameters.add(new BasicNameValuePair(key, params.get(key).toString()));
			}
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(parameters, "utf-8");
			httpPost.setEntity(uefEntity);
			RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000).setSocketTimeout(10000).build();
			httpPost.setConfig(config);
			httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "utf-8");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
			}
		}
		return "";
	}


}
