package com.investdata.action;

import org.apache.log4j.Logger;

import com.investdata.common.BaseAction;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TUserDao;
import com.investdata.dao.po.User;
import com.investdata.utils.Coder;
import com.investdata.utils.PropertiesUtils;
import com.investdata.utils.ThreeDes;

/**
 * 注册Action
 */
public class RegAction extends BaseAction {
	private static final long serialVersionUID = -4003526420872337090L;
	Logger _log = Logger.getLogger(RegAction.class);
	
	private String userName;
	private String password;
	private String repassword;
	private String email;
	private String randCode;

	public String execute() throws Exception {
		return INPUT;
	}
	
	
	public String reg() throws Exception {
		_log.info("进入注册流程..");
		User user = new User();
		String encKey = PropertiesUtils.getPropsValue("enc3desKey"); //获取加密串
		String encPwdStr = new String(ThreeDes.encryptMode(encKey.getBytes(), user.getPassword().getBytes())); //密码加密
		user.setPassword(Coder.encryptBASE64(encPwdStr.getBytes()));
		user.setUserName(userName);
		user.setEmail(email);
		
		_log.info(String.format("新增用户信息user=[%s]", user));
		TUserDao userDao = DaoFactory.getTUserDao();
		userDao.add(user);
		_log.info("新增用户成功!");
		
		return SUCCESS;
	}

}
