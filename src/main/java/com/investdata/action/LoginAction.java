package com.investdata.action;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.investdata.common.BaseAction;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TUserDao;
import com.investdata.dao.po.User;
import com.investdata.utils.Coder;
import com.investdata.utils.PropertiesUtils;
import com.investdata.utils.ThreeDes;

/**
 * 登录Action
 */
public class LoginAction extends BaseAction implements SessionAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger logger = Logger.getLogger(LoginAction.class);	
	private Map<String,Object> session;
	private String userName;
	private String password;
	
	public String execute() throws Exception {
		logger.info("进入登录流程..");
		return INPUT;
	}
	
	//用户登录流程
	public String login() throws Exception {
		User user = new User();
		int index = userName.indexOf("@");
		if (index != -1 ) { //用邮箱当做用户名
			user.setEmail(userName);
			user.setUserName("");
		}
		
		String encKey = PropertiesUtils.getPropsValue("enc3desKey","");
		String encPwdStr = new String(ThreeDes.encryptMode(encKey.getBytes(), password.getBytes()));
		user.setPassword(Coder.encryptBASE64(encPwdStr.getBytes()));
		
		TUserDao userDao = DaoFactory.getTUserDao();
		User userObj = userDao.getUser(user);
		
		if (userObj != null) { //查询到用户信息后放入session
			session.put("user", userObj);
			return LOGIN_SUCC; //跳转到首页
		} else {
			return LOGIN_FAIL; //登录失败
		}
	}
	
	//退出
	public String logout() throws Exception {
		//清除session中用户信息。
		session.remove("user");
		return LOGOUT;
	}
	
	/**
	 * 管理员登录
	 * @return
	 */
	public String adminLogin() {
		
		return INPUT;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
