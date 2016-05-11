package com.investdata.dao;

import com.investdata.dao.po.User;


public interface IUserDao {
	public void add(User user) throws Exception;
	public User getByPk(User user) throws Exception;
	
}
