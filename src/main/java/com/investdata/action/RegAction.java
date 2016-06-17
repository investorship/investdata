package com.investdata.action;

import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
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
public class RegAction extends BaseAction implements RequestAware,SessionAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private static Random rand = new Random();
	Logger _log = Logger.getLogger(RegAction.class);
	private Map<String,Object> request;
	private Map<String,Object> session;
	private SimpleMailMessage mailMessage;
	private String userName;
	private String password;
	private String repassword;
	private String email;
	private String randCode;
	private String ajaxResult; //ajax调用结果返回值
	private String activeLink;


	/**
	 * 用户注册流程
	 * @return
	 * @throws Exception
	 */
	public String reg() throws Exception {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(repassword)
				|| StringUtils.isEmpty(email) || StringUtils.isEmpty(randCode)) {
			_log.error("注册界面字段校验为通过");
			return ERROR;
		}
		
		if (!password.equals(repassword)) {
			_log.error(String.format("注册界面字段验证密码与确认密码指不一致password=[%s],repassword", password,repassword));
			return ERROR;
		}
		
		checkUserName(); //用户是否被注册
		if("false".equals(ajaxResult)) {
			_log.error(String.format("注册界面字段校验--用户名[%s]已经被注册", userName));
			return ERROR;
		}
		
		ajaxResult = ""; 
		checkEmail(); //邮箱是否被注册
		if ("false".equals(ajaxResult)) {
			_log.error(String.format("注册界面字段校验--邮箱[%s]已经被注册", email));
			return ERROR;
		}
		
		ajaxResult = ""; 
		checkRandCode(); //图片验证码是否正确
		if ("false".equals(ajaxResult)) {
			_log.error(String.format("注册界面字段校验--图片验证码错误-->服务器端[%s],界面传值[%s]",(String)session.get("authImage"),randCode));
			return ERROR;
		}
		
		//加密用户密码
		String encKey = PropertiesUtils.getPropsValue("enc3desKey",""); //获取加密串
		String activeCode = String.valueOf(rand.nextInt(100000000)); //生成账户激活码
		String activeCodeMd5 = String.valueOf(Coder.getMD5Code(activeCode));
		
		User user = new User();
		user.setUserName(userName);
		user.setPassword(Coder.encryptBASE64(ThreeDes.encryptMode(encKey.getBytes(), password.getBytes())));
		user.setEmail(email);
		user.setActiveCode(activeCodeMd5);
		user.setIsPayer(0);
		user.setFlag(0);
		
		_log.info(String.format("新增用户信息user=[%s]", user));
		TUserDao userDao = DaoFactory.getTUserDao();
		userDao.add(user);
		
		//生成邮箱激活连接参数
		String activeParams = "userName="+userName + "&activeCode=" + activeCode;
		byte[] encActiveParamsByte = ThreeDes.encryptMode(encKey.getBytes(),activeParams.getBytes());
		String encActiveParamsStr = Coder.encryptBASE64(encActiveParamsByte);
		_log.info(String.format("生成账户激活链接参数,activeParams=[%s],encActiveParams=[%s]",activeParams,encActiveParamsStr));
		
		String activeLink = PropertiesUtils.getPropsValue("activeUrl","");
		activeLink += encActiveParamsStr;
		_log.info(String.format("最终生成的激活链接为:[%s]", activeLink));
		
		
		//设置邮件内容
		mailMessage.setTo(email);
		mailMessage.setText(genActiveMailText(userName,activeLink));
		mailMessage.setSubject(PropertiesUtils.getPropsValue("active.mail.subject",""));
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
	
	/**校验用户输入的验证码是否正确**/
	public String checkRandCode() throws Exception {
		if (StringUtils.isEmpty(randCode)) {
			ajaxResult = "false";
		}
		String sessionRandCode = (String)session.get("authImage"); //获取session中的验证码
		//验证码不区分大小写。
		if (StringUtils.trim(sessionRandCode).equals(randCode.toUpperCase())) {
			ajaxResult = "true";
		} else {
			ajaxResult = "false";
		}
		return AJAX;
	}
	
	/**
	 * 
	 * @return
	 * 激活账户
	 */
	public String activeAccount() throws Exception {
		String[] activeParmas = activeLink.split("&");
		if (activeParmas.length != 2) {
			_log.info(String.format("激活链接参数异常activeLink=[%s]", activeLink));
			//激活失败
			return ACTIVE_FAIL;
		}
		
		String activeUserName = activeParmas[0];
		String activeCode = activeParmas[1];
		
		User u1 = new User(); //按用户名 + 激活码查用户
		u1.setUserName(activeUserName.substring(activeUserName.indexOf("=") + 1));
		u1.setActiveCode(Coder.getMD5Code(activeCode.substring(activeCode.indexOf("=") + 1)));
		
		TUserDao userDao = DaoFactory.getTUserDao();
		User user = userDao.getUser(u1);
		
		if (user == null) {
			//该用户不存在，激活失败！
			return ACTIVE_FAIL;
		} 
		
		User u2 = new User();
		u2.setUserName(activeUserName.substring(activeUserName.indexOf("=") + 1));
		u2.setFlag(1);
		userDao.update(u2);
		
		request.put("userName", user.getUserName());
		_log.info(String.format("账户[%s]激活成功", activeUserName));
		
		return ACTIVE_SUCC;
	}
	
	/**
	 * 生成激活账户邮件内容
	 * @param activeLink
	 * @return
	 */
	private String genActiveMailText(String userName,String activeLink) throws Exception {
		String mailText = "尊敬的用户:" + userName + "\n";
		mailText += "您的账户激活链接为:\n" + activeLink + "请复制下面地址在浏览器打开激活您的账户。\n";
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
	
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public void setActiveLink(String activeLink) throws Exception{
		activeLink = activeLink.replace(" ", "+");
		String encKey = PropertiesUtils.getPropsValue("enc3desKey",""); //获取加密密钥
		String desActiveLink = new String (ThreeDes.decryptMode(encKey.getBytes(), Coder.decryptBASE64(activeLink))); //解密
		this.activeLink = desActiveLink;
	}
	
}
