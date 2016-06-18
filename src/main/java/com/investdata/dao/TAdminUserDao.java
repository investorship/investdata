package com.investdata.dao;

import java.util.List;
import java.util.Map;

import com.investdata.dao.po.AdminUser;

public interface TAdminUserDao {
	public List<AdminUser> getAdminUsers(AdminUser adminUser) throws Exception;
	public List<AdminUser> findAdminUsersByPage(Map map) throws Exception;
	public int getTotalCount() throws Exception;
}
