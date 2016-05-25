package com.investdata.dao.po;

import java.sql.Timestamp;

public class User {
	private String userName;
	private String password;
	private String email;
	private int ispayer;
	private String payDate;
	private String endDate;
	private int flag;
	private Timestamp intime;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		if (password != null) {
			return password.trim();
		} else {
			return "";
		}
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIspayer() {
		return ispayer;
	}

	public void setIspayer(int ispayer) {
		this.ispayer = ispayer;
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

	public Timestamp getIntime() {
		return intime;
	}

	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}

	@Override
	public String toString() {
		// 忽略密码
		String userToString = "userName=" + userName + "-email=" + email
				+ "-ispayer=" + ispayer + "-payDate=" + payDate + "-endDate="
				+ endDate + "-flag=" + flag + "-intime=" + intime;
		return userToString;
	}

}
