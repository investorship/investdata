package com.investdata.dao.impl;

import java.util.List;

import com.investdata.dao.TMgrMenuDao;
import com.investdata.dao.po.MgrMenu;

public class MgrMenuDaoImpl extends BaseDao implements TMgrMenuDao {

	public List<MgrMenu> getMgrMenus(MgrMenu mgrMenu) throws Exception {
		List<MgrMenu> mgrMenuList = dao.queryForList("mgrMenu.getMgrMenus", mgrMenu);
		return mgrMenuList;
	}
}