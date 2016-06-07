package com.investdata.dao;

import java.util.List;

import com.investdata.dao.po.AdminUser;

public interface TAdminUserDao {
	public List<AdminUser> getAdminUsers(AdminUser adminUser) throws Exception;
}
