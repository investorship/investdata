package com.investdata.dao.po;

import java.sql.Timestamp;
import java.util.List;

//财务指标信息类
public class FinanceIndexInfo {
	private Integer id;
	private String name;
	private Integer pid; 
	private String  pName; 
	private String action;
	private Integer flag;
	private Timestamp inTime;
	private List<FinanceIndexInfo> subFinanceIndexInfoList;
	
	public List<FinanceIndexInfo> getSubFinanceIndexInfoList() {
		return subFinanceIndexInfoList;
	}
	public void setSubFinanceIndexInfoList(List<FinanceIndexInfo> subFinanceIndexInfoList) {
		this.subFinanceIndexInfoList = subFinanceIndexInfoList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Timestamp getInTime() {
		return inTime;
	}
	public void setInTime(Timestamp inTime) {
		this.inTime = inTime;
	}
	
}
