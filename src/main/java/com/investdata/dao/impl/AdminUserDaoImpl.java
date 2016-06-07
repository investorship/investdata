package com.investdata.dao.impl;

import java.util.List;

import com.investdata.dao.TAdminUserDao;
import com.investdata.dao.po.AdminUser;

public class AdminUserDaoImpl extends BaseDao implements TAdminUserDao {

	public List<AdminUser> getAdminUsers(AdminUser adminUser) throws Exception {
		return dao.queryForList("adminUser.getAdminUser", adminUser);
	}

}