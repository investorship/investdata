package com.investdata.dao.po;

import java.sql.Timestamp;

/**
 * 行业类别Po
 * @author Administrator
 *
 */
public class IndustryCategory {
	private Integer id;
	private String name;
	private Integer flag;
	private Timestamp inTime;
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
