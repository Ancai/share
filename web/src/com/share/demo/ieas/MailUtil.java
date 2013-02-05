package com.share.demo.ieas;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;

/** 
 *         说      明：邮件工具类
 *
 * @author 作      者：lac
 *		  E-mail: liangancai@vrvmail.com.cn 
 * @version V1.0
 *         创建时间：2012-7-26 下午03:37:19 
 */
public final class MailUtil {
	/**
	 * (邮件)配置信息
	 */
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("mail");
	private static final String HOST = BUNDLE.getString("host");
	private static final String AUTH = BUNDLE.getString("auth");
	private static final String USERNAME = BUNDLE.getString("username");
	private static final String PASSWORD = BUNDLE.getString("password");
	private static final String FROM = BUNDLE.getString("from");
	public  static final String TO = BUNDLE.getString("to");
	private static final String SUBJECT = BUNDLE.getString("subject");
	private static final String TEXT = BUNDLE.getString("text");
	private static final String ENCODING = BUNDLE.getString("encoding");
	
	/**
	 * 初始化邮箱环境
	 * 
	 * @return Session 环境对象
	 */
	public static Session initSession() {
		Properties props = new Properties();
		Authenticator auth = new Email_Autherticator(USERNAME, PASSWORD);
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.auth", AUTH);
		Session session = Session.getInstance(props, auth);
		
		return session;
	}
	/***
	 * 初始化邮件信息
	 * 
	 * @return MimeMessage 邮件对象
	 * @throws MessagingException 
	 */
	private static MimeMessage initMail(String emailAddress) 
	throws MessagingException {
		MimeMessage mimeMessage = new MimeMessage(initSession());
		mimeMessage.setSentDate(new Date());
		mimeMessage.setFrom(new InternetAddress(FROM));
		mimeMessage.addRecipients(Message.RecipientType.TO, StringUtils.isNotBlank(emailAddress) ? emailAddress : TO);
		mimeMessage.setSubject(SUBJECT);
		mimeMessage.setText(TEXT);
		mimeMessage.setHeader("Content-Type", "text/plain;charset= " + ENCODING);
		
		return mimeMessage;
	}
	
	/**
	 * 邮件发送(默认主题和内容)
	 * @throws MessagingException 
	 */
	public static void send(String emailAddress) 
	throws MessagingException {
			Transport.send(initMail(emailAddress));
	}
	/**
	 * 邮件发送(自定义主题和内容)
	 * 
	 * @param subject 主题
	 * @param content 内容
	 * @throws MessagingException 
	 */
	public static void send(String emailAddress,String subject, String content) 
	throws MessagingException {
		MimeMessage tempMessage = initMail(emailAddress);
		tempMessage.setSubject(subject);
		tempMessage.setText(content);
		Transport.send(tempMessage);
	}
	
	/**
	 * 邮件发送(可添加附件)
	 * 
	 * @param subject 主题
	 * @param content 内容
	 * @param attachPath 附件(绝对)路径
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public static void send(String emailAddress,String subject, String content, String attachPath) 
	throws MessagingException, IOException {
		MimeMessage tempMessage = initMail(emailAddress);
		MimeMultipart mp = new MimeMultipart();
		MimeBodyPart attachPart = new MimeBodyPart();
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setText(content);
		mp.addBodyPart(textPart);
		attachPart.attachFile(attachPath);
		mp.addBodyPart(attachPart);

		tempMessage.setContent(mp, "text/html;charset= " + ENCODING);
		tempMessage.setSubject(subject);
		Transport.send(tempMessage);
	}
}

/**
 * 邮件服务器端的验证
 */
class Email_Autherticator extends Authenticator {
	private String username; 
    private String password; 
    
    public Email_Autherticator() { 
    	super(); 
    } 
    public Email_Autherticator(String user,String pwd){ 
	  super(); 
	  username = user; 
	  password = pwd; 
    } 
    @Override
	protected PasswordAuthentication getPasswordAuthentication() {
		// TODO Auto-generated method stub
		return new PasswordAuthentication(username, password);
	}
}
