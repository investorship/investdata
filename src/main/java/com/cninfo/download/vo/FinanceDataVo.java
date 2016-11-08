package com.cninfo.download.vo;

//获取财务数据的vo对象
public class FinanceDataVo {
	private String code; //代码
	private String market;//市场 sz-深圳  sh-上海
	private String type; //类型 llb-现金表  fzb-负债表 lrb-利润表
	private String orgid; //组织机构代码 需要通过api 获取 [json]
	private String minYear; //年份开始
	private String maxYear; //年份结束
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getMinYear() {
		return minYear;
	}
	public void setMinYear(String minYear) {
		this.minYear = minYear;
	}
	public String getMaxYear() {
		return maxYear;
	}
	public void setMaxYear(String maxYear) {
		this.maxYear = maxYear;
	}
	
}
