package com.investdata.mail;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SingleMailSend {
	/*static ApplicationContext actx = new ClassPathXmlApplicationContext("applicationContext.xml");
	static MailSender sender = (MailSender) actx.getBean("mailSender");
	static SimpleMailMessage mailMessage = (SimpleMailMessage) actx.getBean("mailMessage");*/

	public static void main(String args[]) throws Exception {
		/*mailMessage.setSubject("你好");
		mailMessage.setText("这个是一个通过Spring框架来发送邮件的小程序");
		mailMessage.setTo("844360369@qq.com");
		sender.send(mailMessage);*/
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		JavaMailSenderImpl sender = (JavaMailSenderImpl) ctx
				.getBean("mailSender");
		MimeMessage mailMessage = sender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true);
		messageHelper.setFrom("investdata@126.com");
//		messageHelper.setTo("844360369@qq.com");
		messageHelper.setTo("lizhibin@umpay.com");

		messageHelper.setSubject("投资数据网注册成功");
		// true 表示启动HTML格式的邮件
		messageHelper.setText(
				"<html><head></head><body><img src=cid:image/>"
				+ "<h3 font='red'>欢迎您注册投资数据网</h3> 您的账户激活链接为："
				+"<a href=http://www.baidu.com>http://www.investdata.info/reg/reg.action</a>", true);

		FileSystemResource img = new FileSystemResource(new File("d:\\mail.jpg"));

		messageHelper.addInline("image", img);//跟cid一致

		sender.send(mailMessage);
		System.out.println("邮件发送成功...");

	}
}
