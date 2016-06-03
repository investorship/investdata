package com.investdata.dao.impl;

import com.investdata.dao.TAdminUserDao;
import com.investdata.dao.po.AdminUser;

public class AdminUserDaoImpl extends BaseDao implements TAdminUserDao {


	public AdminUser getAdminUser(AdminUser adminUser) throws Exception {
		AdminUser admUser = (AdminUser)dao.queryForObject("adminUser.getAdminUser", adminUser);
		return admUser;
	}

}