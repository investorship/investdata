package com.investdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.investdata.dao.TAdminUserDao;
import com.investdata.dao.po.AdminUser;

public class AdminUserDaoImpl extends BaseDao implements TAdminUserDao {

	public List<AdminUser> getAdminUsers(AdminUser adminUser) throws Exception {
		return dao.queryForList("adminUser.getAdminUser", adminUser);
	}

	@Override
	public int getTotalCount() throws Exception {
		return (Integer)dao.queryForObject("adminUser.getTotalCount");
	}

	@Override
	public List<AdminUser> findAdminUsersByPage(Map map) throws Exception {
		return dao.queryForList("adminUser.FindByPage", map);
	}

}