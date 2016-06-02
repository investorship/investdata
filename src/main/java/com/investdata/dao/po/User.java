package com.investdata.dao.po;

import java.sql.Timestamp;

public class User {
	private String userName;
	private String password;
	private String email;
	private String activeCode;
	private Integer isPayer;
	private String payDate;
	private String endDate;
	private Integer flag;
	private Timestamp inTime;

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}


	public Integer getIsPayer() {
		return isPayer;
	}

	public void setIsPayer(Integer isPayer) {
		this.isPayer = isPayer;
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


	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	

	@Override
	public String toString() {
		// 忽略密码
		String userToString = "userName=" + userName + "-password=" + password + "-email=" + email
				+ "-activeCode=" + activeCode  +"-ispayer=" + isPayer + "-payDate=" + payDate + "-endDate="
				+ endDate + "-flag=" + flag + "-intime=" + inTime;
		return userToString;
	}

}
