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
public class ResetPwdAction extends BaseAction {
	private static final long serialVersionUID = -4003526420872337090L;
	Logger _log = Logger.getLogger(ResetPwdAction.class);
	private String userName;
	public void setUserName(String userName) {
		this.userName = userName;
	}

	private String email;
	private SimpleMailMessage mailMessage;

	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String execute() throws Exception {
		_log.info("进入重设登录密码流程");
		return INPUT;
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
		String activeParams = "userName="+userName + "&email=" + email;
		String encKey = PropertiesUtils.getPropsValue("enc3desKey",""); //获取加密串
		byte[] encActiveParamsByte = ThreeDes.encryptMode(encKey.getBytes(),activeParams.getBytes());
		String encActiveParamsStr = Coder.encryptBASE64(encActiveParamsByte);
		_log.info(String.format("重置密码链接参数,activeParams=[%s],encActiveParams=[%s]",activeParams,encActiveParamsStr));
		
		String activeLink = PropertiesUtils.getPropsValue("activeUrl","");
		activeLink += encActiveParamsStr;
		_log.info(String.format("最终生成的重置密码链接为:[%s]", activeLink));
		//设置邮件内容
		mailMessage.setTo(email);
		mailMessage.setText(genActiveMailText(userName,activeLink));
		//发送邮件
		MailSendWrapper.SendMailNoPic(mailMessage);
		
		return INPUT;
	}

	private String genActiveMailText(String userName2, String activeLink) {
		String mailText = "尊敬的用户:" + userName + "\n";
		mailText += "您的账户激活链接为:\n" + activeLink + "请复制下面地址在浏览器打开激活您的账户。\n";
		mailText += "\n\n";
		mailText += "感谢您对我们的关注与支持,祝您投资顺利。\n";
		mailText += "投资数据网";
		return mailText;
	}

}
