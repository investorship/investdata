package com.investdata.mail;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailSendWrapper {
	static Logger _log = Logger.getLogger(MailSendWrapper.class);
	private static MailSender sender;
	
	/**
	 * 发送不带图片和附件的简单邮件
	 * @param mailMessage
	 */
	public static void SendMailNoPic(SimpleMailMessage mailMessage) {
		_log.info(String.format("准备发送邮件 mail=[%s]", mailMessage));
		sender.send(mailMessage);
		_log.info("邮件发送成功!");
	}
	

	public static void setSender(MailSender sender) {
		MailSendWrapper.sender = sender;
	}











	public static void main(String args[]) throws Exception {
		ApplicationContext actx = new ClassPathXmlApplicationContext("applicationContext.xml");
		MailSender sender = (MailSender) actx.getBean("mailSender");
		SimpleMailMessage mailMessage = (SimpleMailMessage) actx.getBean("mailMessage");
		mailMessage.setSubject("你好");
		mailMessage.setText("这个是一个通过Spring框架来发送邮件的小程序");
		mailMessage.setTo("844360369@qq.com");
		sender.send(mailMessage);
		
		
		/*ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		JavaMailSenderImpl sender = (JavaMailSenderImpl) ctx
				.getBean("mailSender");
		MimeMessage mailMessage = sender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true);
		//spring配置文件指定后，该处也需指定
		messageHelper.setFrom("investdata@126.com");
//		messageHelper.setTo("844360369@qq.com");
		messageHelper.setTo("guohailong@umpay.com");

		messageHelper.setSubject("投资数据网注册成功");
		// true 表示启动HTML格式的邮件
		messageHelper.setText(
				"<html><head></head><body><img src=cid:image/>"
				+ "<h3 font='red'>欢迎您注册投资数据网</h3> 您的账户激活链接为："
				+"<a href=http://www.baidu.com>http://www.investdata.info/reg/reg.action</a>", true);

		FileSystemResource img = new FileSystemResource(new File("d:\\mail1.jpg"));

		messageHelper.addInline("image", img);//跟cid一致

		sender.send(mailMessage);
		System.out.println("邮件发送成功...");*/

	}
}
