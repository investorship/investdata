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
import com.investdata.utils.StringUtils;
import com.investdata.utils.ThreeDes;

/**
 * 与密码有关的操作类
 * 1、重置密码
 * 2、修改密码
 * @author HaiLong.Guo
 * 
 */
public class PasswordOperAction extends BaseAction implements RequestAware {
	private static final long serialVersionUID = -4003526420872337090L;
	Logger _log = Logger.getLogger(PasswordOperAction.class);
	
	private String email;
	private String userName;
	private String pwdOperFlag; //1:修改密码  2：重置密码
	private String resetPwdLink;
	private Map<String,Object> request;
	private SimpleMailMessage mailMessage;

	public String execute() throws Exception {
		_log.info(String.format("密码操作pwdOperFlag=[%s]", pwdOperFlag));
		
		if (UPDATE_PWD_OPERATION_FLAG.equals(pwdOperFlag)) {
			return UPDATE_PWD_INPUT;
		} else if (RESET_PWD_OPERATION_FLAG.equals(pwdOperFlag)) {
			return RESET_PWD_INPUT;			
		} else {
			return ERROR;
		}
	}
	
	/**
	 * 发送重置密码邮件
	 * @return
	 * @throws Exception
	 */
	public String sendResetPwdMail() throws Exception{
		TUserDao userDao = DaoFactory.getTUserDao();
		User user = new User();
		user.setEmail(email);
		//根据用户输入的邮箱查找该用户信息
		User userObj = userDao.getUser(user);
		if (userObj == null) { //该用户不存在
			//跳转到全局excepiton视图
		}
		userName = userObj.getUserName();
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
		
		request.put("userName", userName);
		request.put("email", email);
		
		return RESET_PWD_MAIL;
	}
	
	/**
	 * 重置密码
	 * 1、随机生成新6位数密码
	 * 2、密码加密
	 * 3、更新数据库
	 * @return
	 * @throws Exception
	 */
	public String resetPwd() throws Exception {
		String[] resetParmas = resetPwdLink.split("&");
		if (resetParmas.length != 2) {
			_log.info(String.format("重设密码链接参数异常resetPwdLink=[%s]", resetPwdLink));
			//激活失败
			return ACTIVE_FAIL;
		}
		
		String userNameParam = resetParmas[0]; //userName=abcd
		userName = userNameParam.substring(userNameParam.indexOf("=") + 1);
		String newPassword = StringUtils.getRandomString(6); //生成6位随机字符串作为重置后的密码
		String encKey = PropertiesUtils.getPropsValue("enc3desKey",""); //获取加密串
		String encPwdStr = Coder.encryptBASE64(ThreeDes.encryptMode(encKey.getBytes(), newPassword.getBytes())); //密码加密
		
		User user = new User();
		user.setUserName(userName);
		user.setPassword(encPwdStr);
		
		TUserDao userDao = DaoFactory.getTUserDao();
		userDao.update(user);
		
		request.put("userName", userName); //新生成的密码在界面提供给用户
		request.put("newPwd", newPassword); //新生成的密码在界面提供给用户
		
		return RESET_PWD_SUCC;
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
	
	//解密邮件中加密数据
	public void setResetPwdLink(String resetPwdLink) throws Exception {
		resetPwdLink = resetPwdLink.replace(" ", "+");
		String encKey = PropertiesUtils.getPropsValue("enc3desKey",""); //获取加密密钥
		String desresetPwdLink = new String (ThreeDes.decryptMode(encKey.getBytes(), Coder.decryptBASE64(resetPwdLink))); //解密
		this.resetPwdLink = desresetPwdLink;
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

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	
	public void setPwdOperFlag(String pwdOperFlag) {
		this.pwdOperFlag = pwdOperFlag;
	}

}
