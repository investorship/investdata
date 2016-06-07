package com.investdata.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONObject;

import com.investdata.common.BaseAction;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TAdminUserDao;
import com.investdata.dao.TMgrMenuDao;
import com.investdata.dao.TUserDao;
import com.investdata.dao.po.AdminUser;
import com.investdata.dao.po.MgrMenu;
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
	private String ajaxResult;
	private boolean loginFlag; //登录验证标识

	public String execute() throws Exception {
		logger.info("进入登录流程..");
		return INPUT;
	}
	
	//用户登录流程
	public String login() throws Exception {
		checkLogin();
		if (loginFlag) {
			return LOGIN_SUCC; //跳转到首页
		} else {
			return LOGIN_FAIL; //登录失败
		}
	}
	
	
	/**
	 * ajax校验用户名 密码是否成功
	 * @return
	 * @throws Exception
	 */
	public String checkLogin() throws Exception {
		User user = new User();
		int index = userName.indexOf("@");
		if (index != -1 ) { //用邮箱当做用户名
			user.setEmail(userName);
		} else {
			user.setUserName(userName);
		}
		
		String encKey = PropertiesUtils.getPropsValue("enc3desKey","");
		user.setPassword(Coder.encryptBASE64(ThreeDes.encryptMode(encKey.getBytes(), password.getBytes())));
		TUserDao userDao = DaoFactory.getTUserDao();
		User userObj = userDao.getUser(user);
		
		if (userObj != null) { //查询到用户信息后放入session
			ajaxResult = "true";
			loginFlag = true;
			session.put("user", userObj);
		} else {
			ajaxResult = "false";
		}
		
		return AJAX;
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
	public String checkAdminLogin() throws Exception {
		AdminUser admUser = new AdminUser();
		admUser.setUserName(userName);
		
		String encKey = PropertiesUtils.getPropsValue("enc3desKey","");
		admUser.setPassword(Coder.encryptBASE64(ThreeDes.encryptMode(encKey.getBytes(), password.getBytes())));
		admUser.setFlag(1);
		
		TAdminUserDao admUserDao = DaoFactory.getTAdminUserDao();
		List<AdminUser> adminUsers = admUserDao.getAdminUsers(admUser);
		if (adminUsers != null && adminUsers.size() == 1) {
			ajaxResult = "true";
			loginFlag = true;
			session.put("admUser", adminUsers.get(0));
		} else {
			ajaxResult = "false";
		}
		return AJAX_ADMIN;
	}
	
	/**
	 * 考虑到登录界面的ajax校验可以完全绕过，比如可以用httpClient模拟表单提交直接
	 * 向action提交后台数据，恶意猜密码等，在跳转进入登录流程后依然要再次判断一遍
	 * @return
	 * @throws Exception
	 */
	public String adminLogin() throws Exception {
		//再次验证
		checkAdminLogin();
		if (loginFlag) {
			//加载后台管理菜单
			TMgrMenuDao mgrMenuDao = DaoFactory.getTMgrMenuDao();
			MgrMenu mgrMenu = new MgrMenu();
			mgrMenu.setFlag(1);
			List <MgrMenu> menusList = mgrMenuDao.getMgrMenus(mgrMenu);
			
			StringBuilder menusJsonBuilders = new StringBuilder();
			for (MgrMenu menu : menusList) {
				JSONObject menuJson = new JSONObject();
				menuJson.put("id", menu.getId());
				menuJson.put("pId", menu.getPid());
				menuJson.put("name", menu.getName());
				int isLeaf = menu.getIsLeaf();
				if (isLeaf == 1) {
					menuJson.put("url", menu.getReqUrl());
					menuJson.put("target", "content");
				}
				
				menusJsonBuilders.append(menuJson).append(",").append("\n");
			}
			
			menusJsonBuilders.delete(menusJsonBuilders.length()-2,menusJsonBuilders.length() -1);
			session.put("jsonMenus", menusJsonBuilders);
			return INPUT;
		} else {
			return FAIL;
		}
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

	public String getAjaxResult() {
		return ajaxResult;
	}
	
	public static void main(String[] args) throws Exception {
		StringBuilder aa = new StringBuilder();
		JSONObject json = new JSONObject();
		json.put("userName", "zhangsan");
		
		aa.append(json).append(",\n");
		json = new JSONObject();
		json.put("paw", 123);
		aa.append(json).append("\n");
		System.err.println(aa);
	}
}
