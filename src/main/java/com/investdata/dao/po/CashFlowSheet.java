package com.investdata.dao.po;

import java.io.Serializable;
import java.sql.Timestamp;

//现金流量表po
public class CashFlowSheet implements Serializable{
	private String code;
	private String year;
	private String operaActiveCash; //经营活动产生的现金流量净额
	private String cashAndCashequ; //现金及现金等价物净增加额
	private String flag;
	private Timestamp inTime;
	public Timestamp getInTime() {
		return inTime;
	}
	public void setInTime(Timestamp inTime) {
		this.inTime = inTime;
	}
	private String modUser;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOperaActiveCash() {
		return operaActiveCash;
	}
	public void setOperaActiveCash(String operaActiveCash) {
		this.operaActiveCash = operaActiveCash;
	}
	public String getCashAndCashequ() {
		return cashAndCashequ;
	}
	public void setCashAndCashequ(String cashAndCashequ) {
		this.cashAndCashequ = cashAndCashequ;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getModUser() {
		return modUser;
	}
	public void setModUser(String modUser) {
		this.modUser = modUser;
	}
}
