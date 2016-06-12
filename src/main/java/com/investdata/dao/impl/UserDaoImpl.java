package com.investdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.investdata.dao.TUserDao;
import com.investdata.dao.po.User;

public class UserDaoImpl extends BaseDao implements TUserDao {

	public void add(User user) throws Exception {
		dao.insert("user.insertUser",user);
	}
	
	public User getUser(User user) throws Exception {
		User userObj = (User)dao.queryForObject("user.getUser", user);
		return userObj;
	}

	public int update(User user) throws Exception {
		return dao.update("user.updateFlag",user);
	}
	
	public List<User> getUsers(Map map) throws Exception {
		return dao.queryForList("user.FindByPage", map);
	}

}