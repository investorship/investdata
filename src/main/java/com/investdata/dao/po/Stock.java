package com.investdata.dao.po;

import java.sql.Timestamp;

public class Stock {
	private String code;	//股票代码
	private String name;	//股票名称
	private String shortName; //字母检查
	private String market; //所属市场
	private String ipotime;	//上市时间
	private double ipoStocks; //发行数量
	private String category; //所属类别
	private double issuedPE; //发行市盈率
	private double issuedPrice; //发行价格
	private String address; //公司地址
	private String compyWebsite; //公司网站
	private String reportAddress; //报告下载地址
	private String phone; //联系电话
	private String legaler;//企业法人
	private int flag; //标志位
	private Timestamp intime; //入库时间
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getIpotime() {
		return ipotime;
	}
	public void setIpotime(String ipotime) {
		this.ipotime = ipotime;
	}
	public double getIpoStocks() {
		return ipoStocks;
	}
	public void setIpoStocks(double ipoStocks) {
		this.ipoStocks = ipoStocks;
	}
	public double getIssuedPE() {
		return issuedPE;
	}
	public void setIssuedPE(double issuedPE) {
		this.issuedPE = issuedPE;
	}
	public double getIssuedPrice() {
		return issuedPrice;
	}
	public void setIssuedPrice(double issuedPrice) {
		this.issuedPrice = issuedPrice;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setIssuedPrice(int issuedPrice) {
		this.issuedPrice = issuedPrice;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCompyWebsite() {
		return compyWebsite;
	}
	public void setCompyWebsite(String compyWebsite) {
		this.compyWebsite = compyWebsite;
	}
	public String getReportAddress() {
		return reportAddress;
	}
	public void setReportAddress(String reportAddress) {
		this.reportAddress = reportAddress;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLegaler() {
		return legaler;
	}
	public void setLegaler(String legaler) {
		this.legaler = legaler;
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
		StringBuilder stockBuilder = new StringBuilder();
		stockBuilder.append("code="+code + "-name=" + name + "-shorName="+ shortName + "-market=" + market + "-ipotime=" +ipotime + "-ipoStocks=" + ipoStocks
				+ "-category=" + category + "-issuedPE=" + issuedPE + "-issuedPrice=" + issuedPrice + "-address=" + address + "-compyWebsite=" + compyWebsite
				+"-reportAddress=" + reportAddress + "-phone=" + phone + "-legaler=" + legaler + "-flag=" + flag + "-intime=" + intime);
		return stockBuilder.toString();
	}	
	
}
