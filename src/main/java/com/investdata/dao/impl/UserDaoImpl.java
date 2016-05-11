package com.investdata.dao.impl;

import com.investdata.dao.IUserDao;
import com.investdata.dao.po.User;

public class UserDaoImpl extends BaseDao implements IUserDao {

	public void add(User user) throws Exception {
		dao.insert("",user);
	}

	public User getByPk(User user) throws Exception {
		User userObj = (User)dao.queryForObject("", "");
		return userObj;
	}

}
