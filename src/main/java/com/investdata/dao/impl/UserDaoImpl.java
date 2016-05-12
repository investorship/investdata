package com.investdata.dao.impl;

import com.investdata.dao.TUserDao;
import com.investdata.dao.po.User;

public class UserDaoImpl extends BaseDao implements TUserDao {

	public void add(User user) throws Exception {
		dao.insert("",user);
	}

	public User getByPk(User user) throws Exception {
		User userObj = (User)dao.queryForObject("", "");
		return userObj;
	}

}
