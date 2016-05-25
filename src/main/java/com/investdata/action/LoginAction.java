package com.investdata.action;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.investdata.common.BaseAction;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TUserDao;
import com.investdata.dao.po.User;
import com.investdata.utils.PropertiesUtils;
import com.investdata.utils.ThreeDes;

/**
 * 登录Action
 */
public class LoginAction extends BaseAction implements SessionAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger logger = Logger.getLogger(LoginAction.class);	
	private Map<String,Object> session;
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String execute() throws Exception {
		logger.info("进入登录流程..");
		return INPUT;
	}
	
	//用户登录流程
	public String login() throws Exception {
		String userName = user.getUserName();
		int index = userName.indexOf("@");
		
		if (index != -1 ) { //用邮箱当做用户名
			user.setEmail(userName);
			user.setUserName("");
		}
		
		String encKey = PropertiesUtils.getPropsValue("enc3desKey");
//		String encPwd = ThreeDes.encryptMode(encKey.getBytes(), user.getPassword().trim());
		
		
		TUserDao userDao = DaoFactory.getTUserDao();
		User userObj = userDao.getUser(user);
		
		if (userObj != null) { //查询到用户信息后放入session
			session.put("user", userObj);
			return LOGIN_SUCC;
		} else {
			return LOGIN_FAIL;
		}
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
	
	
}
