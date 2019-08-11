package com.learn.yzh.common.utils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件服务
 * @author Lomis
 *
 */
public class MailUtil {

	private static MailUtil instance;
	
	private final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	
	private final String username = "service@bbqunhu.com" ;
	private final String password = "chq01191" ;

	private MailUtil() {

	}

	public static MailUtil getInstance() {
		synchronized (MailUtil.class) {
			if (instance == null) {
				instance = new MailUtil();
			}
		}
		return instance;
	}
	
	/**
	 * 发送邮件
	 * @param to 信息接收邮件列表
	 * @param content 邮件内容
	 */
	@SuppressWarnings("static-access")
	public void send(String[] to, String title, String content) {
		try {
			Properties p = new Properties(); 
			p.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			p.put("mail.transport.protocol", "smtp");
			p.setProperty("mail.smtp.socketFactory", "false");
			p.put("mail.smtp.port", "465");
			p.setProperty("mail.smtp.socketFactory.port", "465");
			p.put("mail.smtp.host", "smtp.exmail.qq.com");
			p.put("mail.smtp.auth", "true");
			// 建立会话
			Session session = Session.getDefaultInstance(p, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(username, password);
				}
			});
			Message msg = new MimeMessage(session); // 建立信息
			msg.setFrom(new InternetAddress("service@bbqunhu.com")); // 发件人

			String toList = getMailList(to);
			InternetAddress[] iaToList = new InternetAddress().parse(toList, false);

			msg.setRecipients(Message.RecipientType.TO, iaToList); // 收件人
			msg.setSentDate(new Date()); 		// 发送日期
			msg.setSubject(title); 				// 主题
			msg.setText(content); 				// 内容
			
			Transport transport = session.getTransport() ;
			transport.send(msg) ;
			transport.close() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	public void send(String[] to, String title, String content, String filePath, String fileName) {
		try {
			Properties p = new Properties(); 
			p.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			p.put("mail.transport.protocol", "smtp");
			p.setProperty("mail.smtp.socketFactory", "false");
			p.put("mail.smtp.port", "465");
			p.setProperty("mail.smtp.socketFactory.port", "465");
			p.put("mail.smtp.host", "smtp.exmail.qq.com");
			p.put("mail.smtp.auth", "true");
			// 建立会话
			Session session = Session.getDefaultInstance(p, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(username, password);
				}
			});
			Message msg = new MimeMessage(session); // 建立信息
			msg.setFrom(new InternetAddress("service@bbqunhu.com")); // 发件人

			String toList = getMailList(to);
			InternetAddress[] iaToList = new InternetAddress().parse(toList, false);

			msg.setRecipients(Message.RecipientType.TO, iaToList); // 收件人
			msg.setSentDate(new Date()); 		// 发送日期
			msg.setSubject(title); 				// 主题
			
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();         
            //设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(content);
            multipart.addBodyPart(contentPart);
            //添加附件
            BodyPart messageBodyPart= new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            //添加附件的内容
            messageBodyPart.setDataHandler(new DataHandler(source));
            //添加附件的标题
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
            //将multipart对象放到message中
            msg.setContent(multipart);
			
			Transport transport = session.getTransport("smtp") ;
			transport.send(msg) ;
			transport.close() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getMailList(String[] mailArray) {

		StringBuffer toList = new StringBuffer();
		if (mailArray != null ) {
			int length = mailArray.length;
			if (length == 1) {
				toList.append(mailArray[0]);
			} else {
				for (int i = 0; i < length; i++) {
					toList.append(mailArray[i]);
					if (i != (length - 1)) {
						toList.append(",");
					}
					
				}
			}
		}
		return toList.toString();

	}
	
	@SuppressWarnings("static-access")
	public void send(String title, String content) {
		try {
			String to[] = { "jibin@taihemedia.com","yangdexin@taihemedia.com"};
			Properties p = new Properties(); // Properties p =
												// System.getProperties();
			p.put("mail.smtp.auth", "true");
			p.put("mail.transport.protocol", "smtp");
			p.put("mail.smtp.host", "smtp.163.com");
			p.put("mail.smtp.port", "25");
			// 建立会话
			Session session = Session.getInstance(p);
			Message msg = new MimeMessage(session); // 建立信息

			msg.setFrom(new InternetAddress("wandaapp@163.com")); // 发件人

			String toList = getMailList(to);
			InternetAddress[] iaToList = new InternetAddress().parse(toList);

			msg.setRecipients(Message.RecipientType.TO, iaToList); // 收件人

			msg.setSentDate(new Date()); // 发送日期
			msg.setSubject(title); // 主题
			msg.setText(content); // 内容
			Transport tran = session.getTransport("smtp");
			tran.connect("smtp.163.com", "wandaapp", "Admin@123");
			tran.sendMessage(msg, msg.getAllRecipients()); // 发送

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		initProxy("10.199.75.12", "8080", "v_fankun", "1qazXSW@");
		MailUtil sendMail = MailUtil.getInstance() ;
		sendMail.send(new String[]{"87529669@qq.com"}, "test","test", "d://test/1.xlsx", "test.xlsx") ;
	}
	/**
	 * 设置代理
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 */
	public static void initProxy(String host, String port, final String username, final String password) {
		java.net.Authenticator.setDefault(new java.net.Authenticator() {
			protected java.net.PasswordAuthentication getPasswordAuthentication() {
				return new java.net.PasswordAuthentication(username,
						new String(password).toCharArray());
			}
		});

		System.setProperty("http.proxyType", "4");
		System.setProperty("http.proxyPort", port);
		System.setProperty("http.proxyHost", host);
		System.setProperty("http.proxySet", "true");
	}
}