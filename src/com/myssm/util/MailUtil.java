package com.myssm.util;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


public class MailUtil {
	public static void sendmail(String smtp, String servername,
			String serverpaswd, String subject, String from, String[] to,
			String text, String[] filenames, String mimeType) throws Exception {

		// 可以从配置文件读取相应的参数
		Properties props = new Properties();

		javax.mail.Session mailSession; // 邮件会话对象
		javax.mail.internet.MimeMessage mimeMsg; // MIME邮件对象

		props = java.lang.System.getProperties(); // 获得系统属性对象
		props.put("mail.smtp.host", smtp); // 设置SMTP主机
		props.put("mail.smtp.auth", "true"); // 是否到服务器用户名和密码验证
		MailAuthenticator myEmailAuther = new MailAuthenticator(servername,
				serverpaswd);
		// 设置邮件会话
		mailSession = javax.mail.Session.getInstance(props, myEmailAuther);

		// 设置debug打印信息
		mailSession.setDebug(true);

		// 设置传输协议
		javax.mail.Transport transport = mailSession.getTransport("smtp");
		// 设置from、to等信息
		mimeMsg = new javax.mail.internet.MimeMessage(mailSession);
		if (!from.isEmpty()) {

			InternetAddress sentFrom = new InternetAddress(from);
			mimeMsg.setFrom(sentFrom); // 设置发送人地址
		}

		InternetAddress[] sendTo = new InternetAddress[to.length];
		for (int i = 0; i < to.length; i++) {
			sendTo[i] = new InternetAddress(to[i]);
		}

		mimeMsg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO,
				sendTo);
		mimeMsg.setSubject(subject, "gb2312");

		MimeBodyPart messageBodyPart1 = new MimeBodyPart();
		messageBodyPart1.setContent(text, mimeType);

		Multipart multipart = new MimeMultipart();// 附件传输格式
		multipart.addBodyPart(messageBodyPart1);

		for (int i = 0; i < filenames.length; i++) {
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			// 选择出每一个附件名
			String filename = filenames[i].split(",")[0];
			String displayname = filenames[i].split(",")[1];
			// 得到数据源
			FileDataSource fds = new FileDataSource(filename);

			messageBodyPart2.setDataHandler(new DataHandler(fds));
			messageBodyPart2.setFileName(MimeUtility.encodeText(displayname));
			multipart.addBodyPart(messageBodyPart2);
		}
		mimeMsg.setContent(multipart);
		// 设置信件头的发送日期
		mimeMsg.setSentDate(new Date());
		mimeMsg.saveChanges();
		// 发送邮件
		Transport.send(mimeMsg);
		transport.close();
	}

}
