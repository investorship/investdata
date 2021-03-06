package com.investdata.dao.po;

import java.sql.Timestamp;

import org.json.JSONObject;

//管理员用户
public class AdminUser {
	private String userName;
	private String password;
	private String email;
	private Integer permLevel; //权限等级 1-录入员 2-审核员
	private Integer flag;
	private Timestamp inTime;
	public Timestamp getInTime() {
		return inTime;
	}
	public void setInTime(Timestamp inTime) {
		this.inTime = inTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		if (password != null) {
			password = password.trim();
		} else {
			password = "";
		}
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getPermLevel() {
		return permLevel;
	}
	public void setPermLevel(Integer permLevel) {
		this.permLevel = permLevel;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	public JSONObject toJson() throws Exception {
		JSONObject jsonUser = new JSONObject();
		jsonUser.put("userName", this.userName);
		jsonUser.put("password", this.password);
		jsonUser.put("email", this.email);
		jsonUser.put("permLevel", this.permLevel);
		jsonUser.put("flag", this.flag);
		jsonUser.put("inTime", this.inTime);
		return jsonUser;
	}
	
}
