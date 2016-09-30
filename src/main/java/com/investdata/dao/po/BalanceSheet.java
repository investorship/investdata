package com.investdata.dao.po;

import java.io.Serializable;
import java.sql.Timestamp;

//资产负债表po
public class BalanceSheet implements Serializable {
	private String year;
	private String code;
	private String noteRecable; //应收票据
	private String advCustomers;//预收账款
	private String accPayable;//应付账款
	private String constrInPro;//在建工程
	private String lntangAssets;//无形资产
	private String lntangAssetsAmortize;//无形资产摊销
	private String goodWill;//商誉
	private String shortTermLoans;//短期借款
	private String notePayable;//应付票据--
	private String debitWithinYear;//一年内到期的非流动负债
	private String longTermLoans;//长期借款
	private String boundsPayable;//应付债券
	private String longAccPayable;//长期应付款
	private String liquidAssetsStart;//期初流动资产
	private String liquidAssetsEnd;//期末流动资产
	private String currLiab;//流动负债
	private String currLiabNon;//非流动负债
	private String goodsStart;//期初存货
	private String goodsEnd;//期末存货
	private String cash;//货币资金
	private String tradAssets;//交易性金融资产
	private String totalLiabStart;//期初负债总额
	private String totalLiabEnd;//期末负债总额
	private String totalAssStart;//期初资产总额
	private String totalAssEnd;//期末资产总额
	private String shareHolderStart;//期初股东权益
	private String shareHolderEnd;//期末股东权益
	private String fixedAssetsStart;//期初固定资产
	private String fixedAssetsEnd;//期末固定资产
	private String retainEarnings;//未分配利润
	private String accRecableStart;//期初应收账款
	private String accRecableEnd;//期末应收账款
	private String capitalSurplus;//资本公积--
	private String surplusReserve;//盈余公积--
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
	public String getNoteRecable() {
		return noteRecable;
	}
	public void setNoteRecable(String noteRecable) {
		this.noteRecable = noteRecable;
	}
	public String getAdvCustomers() {
		return advCustomers;
	}
	public void setAdvCustomers(String advCustomers) {
		this.advCustomers = advCustomers;
	}
	public String getAccPayable() {
		return accPayable;
	}
	public void setAccPayable(String accPayable) {
		this.accPayable = accPayable;
	}
	public String getConstrInPro() {
		return constrInPro;
	}
	public void setConstrInPro(String constrInPro) {
		this.constrInPro = constrInPro;
	}
	public String getLntangAssets() {
		return lntangAssets;
	}
	public void setLntangAssets(String lntangAssets) {
		this.lntangAssets = lntangAssets;
	}
	
	
	public String getLntangAssetsAmortize() {
		return lntangAssetsAmortize;
	}
	public void setLntangAssetsAmortize(String lntangAssetsAmortize) {
		this.lntangAssetsAmortize = lntangAssetsAmortize;
	}
	public String getGoodWill() {
		return goodWill;
	}
	public void setGoodWill(String goodWill) {
		this.goodWill = goodWill;
	}
	public String getShortTermLoans() {
		return shortTermLoans;
	}
	public void setShortTermLoans(String shortTermLoans) {
		this.shortTermLoans = shortTermLoans;
	}
	public String getNotePayable() {
		return notePayable;
	}
	public void setNotePayable(String notePayable) {
		this.notePayable = notePayable;
	}
	public String getDebitWithinYear() {
		return debitWithinYear;
	}
	public void setDebitWithinYear(String debitWithinYear) {
		this.debitWithinYear = debitWithinYear;
	}
	public String getLongTermLoans() {
		return longTermLoans;
	}
	public void setLongTermLoans(String longTermLoans) {
		this.longTermLoans = longTermLoans;
	}
	public String getBoundsPayable() {
		return boundsPayable;
	}
	public void setBoundsPayable(String boundsPayable) {
		this.boundsPayable = boundsPayable;
	}
	
	public String getLongAccPayable() {
		return longAccPayable;
	}
	public void setLongAccPayable(String longAccPayable) {
		this.longAccPayable = longAccPayable;
	}
	public String getLiquidAssetsStart() {
		return liquidAssetsStart;
	}
	public void setLiquidAssetsStart(String liquidAssetsStart) {
		this.liquidAssetsStart = liquidAssetsStart;
	}
	public String getLiquidAssetsEnd() {
		return liquidAssetsEnd;
	}
	public void setLiquidAssetsEnd(String liquidAssetsEnd) {
		this.liquidAssetsEnd = liquidAssetsEnd;
	}
	public String getCurrLiab() {
		return currLiab;
	}
	public void setCurrLiab(String currLiab) {
		this.currLiab = currLiab;
	}
	public String getCurrLiabNon() {
		return currLiabNon;
	}
	public void setCurrLiabNon(String currLiabNon) {
		this.currLiabNon = currLiabNon;
	}
	public String getGoodsStart() {
		return goodsStart;
	}
	public void setGoodsStart(String goodsStart) {
		this.goodsStart = goodsStart;
	}
	public String getGoodsEnd() {
		return goodsEnd;
	}
	public void setGoodsEnd(String goodsEnd) {
		this.goodsEnd = goodsEnd;
	}
	public String getCash() {
		return cash;
	}
	public void setCash(String cash) {
		this.cash = cash;
	}
	public String getTradAssets() {
		return tradAssets;
	}
	public void setTradAssets(String tradAssets) {
		this.tradAssets = tradAssets;
	}
	
	public String getTotalLiabStart() {
		return totalLiabStart;
	}
	public void setTotalLiabStart(String totalLiabStart) {
		this.totalLiabStart = totalLiabStart;
	}
	public String getTotalLiabEnd() {
		return totalLiabEnd;
	}
	public void setTotalLiabEnd(String totalLiabEnd) {
		this.totalLiabEnd = totalLiabEnd;
	}
	public String getTotalAssStart() {
		return totalAssStart;
	}
	public void setTotalAssStart(String totalAssStart) {
		this.totalAssStart = totalAssStart;
	}
	public String getTotalAssEnd() {
		return totalAssEnd;
	}
	public void setTotalAssEnd(String totalAssEnd) {
		this.totalAssEnd = totalAssEnd;
	}
	public String getShareHolderStart() {
		return shareHolderStart;
	}
	public void setShareHolderStart(String shareHolderStart) {
		this.shareHolderStart = shareHolderStart;
	}
	public String getShareHolderEnd() {
		return shareHolderEnd;
	}
	public void setShareHolderEnd(String shareHolderEnd) {
		this.shareHolderEnd = shareHolderEnd;
	}
	public String getFixedAssetsStart() {
		return fixedAssetsStart;
	}
	public void setFixedAssetsStart(String fixedAssetsStart) {
		this.fixedAssetsStart = fixedAssetsStart;
	}
	public String getFixedAssetsEnd() {
		return fixedAssetsEnd;
	}
	public void setFixedAssetsEnd(String fixedAssetsEnd) {
		this.fixedAssetsEnd = fixedAssetsEnd;
	}
	public String getRetainEarnings() {
		return retainEarnings;
	}
	public void setRetainEarnings(String retainEarnings) {
		this.retainEarnings = retainEarnings;
	}
	public String getAccRecableStart() {
		return accRecableStart;
	}
	public void setAccRecableStart(String accRecableStart) {
		this.accRecableStart = accRecableStart;
	}
	public String getAccRecableEnd() {
		return accRecableEnd;
	}
	public void setAccRecableEnd(String accRecableEnd) {
		this.accRecableEnd = accRecableEnd;
	}
	public String getCapitalSurplus() {
		return capitalSurplus;
	}
	public void setCapitalSurplus(String capitalSurplus) {
		this.capitalSurplus = capitalSurplus;
	}
	public String getSurplusReserve() {
		return surplusReserve;
	}
	public void setSurplusReserve(String surplusReserve) {
		this.surplusReserve = surplusReserve;
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
