package com.investdata.dao.po;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 综合数据项表 po
 * @author hailong
 *
 */
public class GendataSheet implements Serializable {
	private String year;
	private String code;
	private String totalStocks;
	private String roeWa;
	private String roeWaKf;
	private String dividenPaySum;
	private String eps;
	private String epsDiluted;
	private Integer flag;
	private Timestamp inTime;
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
	public String getTotalStocks() {
		return totalStocks;
	}
	public void setTotalStocks(String totalStocks) {
		this.totalStocks = totalStocks;
	}
	public String getRoeWa() {
		return roeWa;
	}
	public void setRoeWa(String roeWa) {
		this.roeWa = roeWa;
	}
	public String getRoeWaKf() {
		return roeWaKf;
	}
	public void setRoeWaKf(String roeWaKf) {
		this.roeWaKf = roeWaKf;
	}
	public String getDividenPaySum() {
		return dividenPaySum;
	}
	public void setDividenPaySum(String dividenPaySum) {
		this.dividenPaySum = dividenPaySum;
	}
	public String getEps() {
		return eps;
	}
	public void setEps(String eps) {
		this.eps = eps;
	}
	public String getEpsDiluted() {
		return epsDiluted;
	}
	public void setEpsDiluted(String epsDiluted) {
		this.epsDiluted = epsDiluted;
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
	public String getModUser() {
		return modUser;
	}
	public void setModUser(String modUser) {
		this.modUser = modUser;
	}
	
}
