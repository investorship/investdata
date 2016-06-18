package com.investdata.dao.po;

import java.sql.Timestamp;
import java.util.List;

import org.json.JSONObject;

import com.investdata.utils.StringUtils;

//财务指标信息类
public class FinanceIndexInfo {
	private Integer id;
	private String name;
	private Integer pid; 
	private String action;
	private Integer flag;
	private Timestamp inTime;
	private List<FinanceIndexInfo> childsFinanceIndexInfoList;
	
	
	public List<FinanceIndexInfo> getChildsFinanceIndexInfoList() {
		return childsFinanceIndexInfoList;
	}
	public void setChildsFinanceIndexInfoList(List<FinanceIndexInfo> childsFinanceIndexInfoList) {
		this.childsFinanceIndexInfoList = childsFinanceIndexInfoList;
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
	
	public JSONObject toJson() throws Exception {
		JSONObject jsonIndexInfo = new JSONObject();
		jsonIndexInfo.put("id", id);
		jsonIndexInfo.put("name", name);
		jsonIndexInfo.put("pid", pid);
		jsonIndexInfo.put("action", action);
		jsonIndexInfo.put("flag", this.flag);
		jsonIndexInfo.put("inTime", this.inTime);
		return jsonIndexInfo;
	}
	
}
