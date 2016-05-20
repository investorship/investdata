package com.investdata.dao;

import com.investdata.dao.po.User;


public interface TUserDao {
	public void add(User user) throws Exception;
	public User getByPk(User user) throws Exception;
	
}
