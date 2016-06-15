package com.investdata.action;

import org.apache.log4j.Logger;
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
 * 重设密码
 */
public class PasswordOperAction extends BaseAction {
	private static final long serialVersionUID = -4003526420872337090L;
	Logger _log = Logger.getLogger(PasswordOperAction.class);
	
	private String email;
	private String userName;
	private SimpleMailMessage mailMessage;

	public String execute() throws Exception {
		_log.info("进入重设登录密码流程");
		return RESET_PWD_INPUT;
	}
	
	public String sendResetMail() throws Exception{
		TUserDao userDao = DaoFactory.getTUserDao();
		User user = new User();
		user.setEmail(email);
		//根据用户输入的邮箱查找该用户信息
		User userObj = userDao.getUser(user);
		if (userObj == null) { //该用户不存在
			//跳转到全局excepiton视图
		}
		userName = user.getUserName();
		String resetPwdParams = "userName="+userName + "&email=" + email;
		String encKey = PropertiesUtils.getPropsValue("enc3desKey",""); //获取加密串
		byte[] encResetpwdParamsByte = ThreeDes.encryptMode(encKey.getBytes(),resetPwdParams.getBytes());
		String encResetpwdParamsStr = Coder.encryptBASE64(encResetpwdParamsByte);
		_log.info(String.format("重置密码链接参数,activeParams=[%s],encActiveParams=[%s]",resetPwdParams,encResetpwdParamsStr));
		
		String resetpwdLink = PropertiesUtils.getPropsValue("resetpwdUrl","");
		resetpwdLink += encResetpwdParamsStr;
		_log.info(String.format("最终生成的重置密码链接为:[%s]", resetpwdLink));
		//设置邮件内容
		mailMessage.setTo(email);
		mailMessage.setText(genResetPwdMailText(userName,resetpwdLink));
		mailMessage.setSubject(PropertiesUtils.getPropsValue("resetPwd.mail.subject",""));
		//发送邮件
		MailSendWrapper.SendMailNoPic(mailMessage);
		
		return INPUT;
	}

	private String genResetPwdMailText(String userName, String resetpwdLink) {
		String mailText = "尊敬的用户:" + userName + "\n";
		mailText += "请复制下面地址在浏览器打开重置您的密码。\n";
		mailText += resetpwdLink + "\n";
		mailText += "\n\n";
		mailText += "感谢您对我们的关注与支持,祝您投资顺利。\n";
		mailText += "投资数据网";
		return mailText;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
