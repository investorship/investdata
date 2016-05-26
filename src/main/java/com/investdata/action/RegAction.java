package com.investdata.action;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.springframework.mail.SimpleMailMessage;

import com.investdata.common.BaseAction;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TUserDao;
import com.investdata.dao.po.User;
import com.investdata.mail.MailSendWrapper;
import com.investdata.utils.Coder;
import com.investdata.utils.PropertiesUtils;
import com.investdata.utils.ThreeDes;

/**
 * 注册Action
 */
public class RegAction extends BaseAction implements RequestAware {
	private static final long serialVersionUID = -4003526420872337090L;
	Logger _log = Logger.getLogger(RegAction.class);
	private Map<String,Object> request;
	private SimpleMailMessage mailMessage;
	private String userName;
	private String password;
	private String repassword;
	private String email;
	private String randCode;

	public String execute() throws Exception {
		return INPUT;
	}
	
	
	/**
	 * 用户注册流程
	 * @return
	 * @throws Exception
	 */
	public String reg() throws Exception {
		_log.info("进入注册流程..");
		User user = new User();
		
		//加密用户密码
		String encKey = PropertiesUtils.getPropsValue("enc3desKey",""); //获取加密串
		String encPwdStr = new String(ThreeDes.encryptMode(encKey.getBytes(), user.getPassword().getBytes())); //密码加密
		user.setPassword(Coder.encryptBASE64(encPwdStr.getBytes()));
		user.setUserName(userName);
		user.setEmail(email);
		
		_log.info(String.format("新增用户信息user=[%s]", user));
		TUserDao userDao = DaoFactory.getTUserDao();
		userDao.add(user);
		_log.info("新增用户成功!");
		
		//生成邮箱激活连接参数
		String activeParams = "userName="+userName + "&email=" + email;
		String encActiveParams = new String(ThreeDes.encryptMode(encKey.getBytes(),activeParams.getBytes()));
		_log.info(String.format("生成账户激活链接参数,activeParams=[%s],encActiveParams=[%s]",activeParams,encActiveParams));
		
		String activeLink = PropertiesUtils.getPropsValue("activeUrl","");
		activeLink += encActiveParams;
		_log.info(String.format("最终生成的激活链接为:[%s]", activeLink));
		//设置邮件内容
		mailMessage.setText(genActiveMailText(activeLink));
		//发送邮件
		MailSendWrapper.SendMailNoPic(mailMessage);
		
		return REG_SUCC;
	}
	
	/**
	 * 生成激活账户邮件内容
	 * @param activeLink
	 * @return
	 */
	private String genActiveMailText(String activeLink) {
		String mailText = "";
		mailText += "html><head></head><body><h3>投资数据网注册成功</h3>";
		mailText += "\n 点击下面的链接，激活您的账户";
		mailText += "<a href=\""+ activeLink +"\" title=\"账户激活链接\" >";
		mailText += activeLink + "</a>";
		return mailText;
	}


	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	
	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

}
