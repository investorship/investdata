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
	private String ajaxResult;

	
	
	/**
	 * 用户注册流程
	 * @return
	 * @throws Exception
	 */
	public String reg() throws Exception {
		//加密用户密码
		String encKey = PropertiesUtils.getPropsValue("enc3desKey",""); //获取加密串
		String encPwdStr = new String(ThreeDes.encryptMode(encKey.getBytes(), password.getBytes())); //密码加密

		User user = new User();
		user.setUserName(userName);
		user.setPassword(Coder.encryptBASE64(encPwdStr.getBytes()));
		user.setEmail(email);
		
		_log.info(String.format("新增用户信息user=[%s]", user));
		TUserDao userDao = DaoFactory.getTUserDao();
		userDao.add(user);
		
		//生成邮箱激活连接参数
		String activeParams = "userName="+userName + "&email=" + email + "regTime=" + StringUtils.get14Time();
		String encActiveParams = new String(ThreeDes.encryptMode(encKey.getBytes(),activeParams.getBytes()));
		encActiveParams = Coder.encryptBASE64(encActiveParams.getBytes());
		_log.info(String.format("生成账户激活链接参数,activeParams=[%s],encActiveParams=[%s]",activeParams,encActiveParams));
		
		String activeLink = PropertiesUtils.getPropsValue("activeUrl","");
		activeLink += encActiveParams;
		_log.info(String.format("最终生成的激活链接为:[%s]", activeLink));
		//设置邮件内容
		mailMessage.setTo(email);
		mailMessage.setText(genActiveMailText(userName,activeLink));
		//发送邮件
		MailSendWrapper.SendMailNoPic(mailMessage);
		
		request.put("userName", userName);
		request.put("email", email);
		
		return REG_SUCC;
	}
	
	/**
	 * ajax 校验用户名是否已经被注册
	 * @return
	 */
	public String checkUserName() throws Exception{
		if (StringUtils.isEmpty(userName)) { //如果没获取到userName值，按失败处理
			ajaxResult = "false";
		}
		
		User user = new User();
		user.setUserName(userName);
		
		TUserDao userDao = DaoFactory.getTUserDao();
		User userObj = userDao.getUser(user);
		
		if (userObj == null) { //如果没查询到该用户名，则允许该用户注册
			ajaxResult = "true";
		} else {
			ajaxResult = "false";
		}
		return AJAX;
	}
	
	/**
	 * ajax 校验邮箱是否已经被注册
	 * @return
	 */
	public String checkEmail() throws Exception {
		if (StringUtils.isEmpty(email)) { //如果没获取到email值，按失败处理
			ajaxResult = "false";
		}
		User user = new User();
		user.setEmail(email);
		
		TUserDao userDao = DaoFactory.getTUserDao();
		User userObj = userDao.getUser(user);
		
		if (userObj == null) { //如果没查询到该邮箱，则允许该用户注册
			ajaxResult = "true";
		} else {
			ajaxResult = "false";
		}
		
		return AJAX;
	}
	
	/**
	 * 
	 * @return
	 */
	/*public String activeAccount() {
		
	}*/
	
	/**
	 * 生成激活账户邮件内容
	 * @param activeLink
	 * @return
	 */
	private String genActiveMailText(String userName,String activeLink) {
		String mailText = "尊敬的用户:" + userName + "\n";
		mailText += "您的账户激活链接为:" + activeLink + "您可以点击链接或将链接在浏览器打开激活您的账户。\n";
		mailText += "\n\n";
		mailText += "感谢您对我们的关注与支持,祝您投资顺利。\n";
		mailText += "投资数据网";
		return mailText;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	
	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setRandCode(String randCode) {
		this.randCode = randCode;
	}
	
	public String getAjaxResult() {
		return ajaxResult;
	}


	public String execute() throws Exception {
		return INPUT;
	}
	
	public static void main(String[] args) {
		String value = "lin";
		String str = "{userName:" + "\"" + value+"\"}";
		System.err.println(str);
	}
	
}
