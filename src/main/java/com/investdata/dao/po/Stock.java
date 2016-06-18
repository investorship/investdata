package com.investdata.dao.po;

import java.sql.Timestamp;

import org.json.JSONObject;

public class Stock {
	private String code;	//股票代码
	private String name;	//股票名称
	private String shortName; //字母检查
	private String market; //所属市场
	private String ipoTime;	//上市时间
	private Double ipoStocks; //发行数量
	private String category; //所属类别
	private Double issuedPE; //发行市盈率
	private Double issuedPrice; //发行价格
	private String address; //公司地址
	private String compyWebsite; //公司网站
	private String reportAddress; //报告下载地址
	private String phone; //联系电话
	private String legaler;//企业法人
	private Integer flag; //标志位
	private Timestamp inTime; //入库时间
	
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

	public String getIpoTime() {
		return ipoTime;
	}

	public void setIpoTime(String ipoTime) {
		this.ipoTime = ipoTime;
	}

	public Double getIpoStocks() {
		return ipoStocks;
	}

	public void setIpoStocks(Double ipoStocks) {
		this.ipoStocks = ipoStocks;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getIssuedPE() {
		return issuedPE;
	}

	public void setIssuedPE(Double issuedPE) {
		this.issuedPE = issuedPE;
	}

	public Double getIssuedPrice() {
		return issuedPrice;
	}

	public void setIssuedPrice(Double issuedPrice) {
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

	@Override
	public String toString() {
		String StockToString = 
				"code="+code + "-name=" + name + "-shorName="+ shortName + "-market=" + market + "-ipoTime=" +ipoTime + "-ipoStocks=" + ipoStocks
				+ "-category=" + category + "-issuedPE=" + issuedPE + "-issuedPrice=" + issuedPrice + "-address=" + address + "-compyWebsite=" + compyWebsite
				+"-reportAddress=" + reportAddress + "-phone=" + phone + "-legaler=" + legaler + "-flag=" + flag + "-inTime=" + inTime;
		
		return StockToString;
	}	
	
	public JSONObject toJson() throws Exception {
		JSONObject jsonIndexInfo = new JSONObject();
		jsonIndexInfo.put("code", code);
		jsonIndexInfo.put("name", name);
		jsonIndexInfo.put("shortName", shortName);
		jsonIndexInfo.put("market", market);
		jsonIndexInfo.put("ipoTime", ipoTime);
		jsonIndexInfo.put("ipoStocks", ipoStocks);
		jsonIndexInfo.put("category", category);
		jsonIndexInfo.put("issuedPE", issuedPE);
		jsonIndexInfo.put("issuedPrice", issuedPrice);
		jsonIndexInfo.put("address", address);
		jsonIndexInfo.put("compyWebsite", compyWebsite);
		jsonIndexInfo.put("reportAddress", reportAddress);
		jsonIndexInfo.put("phone", phone);
		jsonIndexInfo.put("legaler", legaler);
		jsonIndexInfo.put("flag", flag);
		jsonIndexInfo.put("inTime", inTime);
		return jsonIndexInfo;
	}
	
}
