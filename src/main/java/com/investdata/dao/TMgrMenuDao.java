package com.investdata.dao;

import java.util.List;

import com.investdata.dao.po.MgrMenu;



public interface TMgrMenuDao {
	public List<MgrMenu> getMgrMenus(MgrMenu mgrMenu) throws Exception;
	
}
