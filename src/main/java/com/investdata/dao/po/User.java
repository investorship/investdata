package com.investdata.dao.po;

import java.sql.Timestamp;

public class User {
	private String userName;
	private String password;
	private String email;
	private String activeCode;
	private int isPayer;
	private String payDate;
	private String endDate;
	private int flag;
	private Timestamp inTime;

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public int getIsPayer() {
		return isPayer;
	}

	public void setIsPayer(int isPayer) {
		this.isPayer = isPayer;
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

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	

	@Override
	public String toString() {
		// 忽略密码
		String userToString = "userName=" + userName + "-password=" + password + "-email=" + email
				+ "-ispayer=" + isPayer + "-payDate=" + payDate + "-endDate="
				+ endDate + "-flag=" + flag + "-intime=" + inTime;
		return userToString;
	}

}
