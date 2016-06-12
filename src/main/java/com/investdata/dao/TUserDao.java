package com.investdata.dao;

import java.util.List;
import java.util.Map;

import com.investdata.dao.po.User;


public interface TUserDao {
	public void add(User user) throws Exception;
	public User getUser(User user) throws Exception;
	public int update(User user) throws Exception;
	public List<User> getUsers(Map map) throws Exception;
	
}
