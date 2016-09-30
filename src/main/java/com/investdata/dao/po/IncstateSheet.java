package com.investdata.dao.po;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 利润表 po
 * @author hailong
 *
 */
public class IncstateSheet implements Serializable{
	private String code;
	private String year;
	private String busiIncomeThis; //本期营业收入
	private String busiIncomeLast; //上期营业收入
	private String totalBusiIncThis;//本期营业总收入
	private String totalBusiIncLast;//上期营业总收入
	private String operaProfitsThis;//本期营业利润
	private String operaProfitsLast;//上期营业利润
	private String incomeTax; //所得税
	private String fixedAssDepre; //固定资产折旧
	private String longPreAmort; //长期待摊费用摊销--
	private String totalProfitStart; //期初利润总额
	private String totalProfitEnd; //期末利润总额
	private String marketConstsThis; //本期销售费用
	private String marketConstsLast;//上期销售费用
	private String financeConstsThis; //本期财务费用
	private String financeConstsLast;//上期财务费用
	private String mgrConstsThis; //本期管理费用
	private String mgrConstsLast; //上期管理费用
	private String interExpense; //财务费用-利息支出
	private String busiTaxSurcharge; //营业税金及附加
	private String netProfitsThis; //本期净利润
	private String netProfitsLast; //上期净利润
	private String netProfitsKfThis; //本期净利润（扣非）
	private String netProfitsKfLast;//上期净利润（扣非）
	private String operatCost; //营业成本
	private String busiIncomeKf; //营业收入(扣非)
	private String fairValChange; //公允价值变动
	private String investIncome; //投资收益
	private String nonOperaIncome; //营业外收入
	private String nonOperaOutcome; //营业外支出
	private Integer flag;
	private Timestamp inTime;
	private String modUser;
	
	public Timestamp getInTime() {
		return inTime;
	}
	public void setInTime(Timestamp inTime) {
		this.inTime = inTime;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getBusiIncomeThis() {
		return busiIncomeThis;
	}
	public void setBusiIncomeThis(String busiIncomeThis) {
		this.busiIncomeThis = busiIncomeThis;
	}
	public String getBusiIncomeLast() {
		return busiIncomeLast;
	}
	public void setBusiIncomeLast(String busiIncomeLast) {
		this.busiIncomeLast = busiIncomeLast;
	}
	
	public String getTotalBusiIncThis() {
		return totalBusiIncThis;
	}
	public void setTotalBusiIncThis(String totalBusiIncThis) {
		this.totalBusiIncThis = totalBusiIncThis;
	}
	public String getTotalBusiIncLast() {
		return totalBusiIncLast;
	}
	public void setTotalBusiIncLast(String totalBusiIncLast) {
		this.totalBusiIncLast = totalBusiIncLast;
	}
	public String getOperaProfitsThis() {
		return operaProfitsThis;
	}
	public void setOperaProfitsThis(String operaProfitsThis) {
		this.operaProfitsThis = operaProfitsThis;
	}
	public String getOperaProfitsLast() {
		return operaProfitsLast;
	}
	public void setOperaProfitsLast(String operaProfitsLast) {
		this.operaProfitsLast = operaProfitsLast;
	}
	public String getIncomeTax() {
		return incomeTax;
	}
	public void setIncomeTax(String incomeTax) {
		this.incomeTax = incomeTax;
	}
	public String getFixedAssDepre() {
		return fixedAssDepre;
	}
	public void setFixedAssDepre(String fixedAssDepre) {
		this.fixedAssDepre = fixedAssDepre;
	}
	public String getLongPreAmort() {
		return longPreAmort;
	}
	public void setLongPreAmort(String longPreAmort) {
		this.longPreAmort = longPreAmort;
	}
	public String getTotalProfitStart() {
		return totalProfitStart;
	}
	public void setTotalProfitStart(String totalProfitStart) {
		this.totalProfitStart = totalProfitStart;
	}
	public String getTotalProfitEnd() {
		return totalProfitEnd;
	}
	public void setTotalProfitEnd(String totalProfitEnd) {
		this.totalProfitEnd = totalProfitEnd;
	}
	public String getMarketConstsThis() {
		return marketConstsThis;
	}
	public void setMarketConstsThis(String marketConstsThis) {
		this.marketConstsThis = marketConstsThis;
	}
	public String getMarketConstsLast() {
		return marketConstsLast;
	}
	public void setMarketConstsLast(String marketConstsLast) {
		this.marketConstsLast = marketConstsLast;
	}
	
	public String getInterExpense() {
		return interExpense;
	}
	public void setInterExpense(String interExpense) {
		this.interExpense = interExpense;
	}
	public String getFinanceConstsThis() {
		return financeConstsThis;
	}
	public void setFinanceConstsThis(String financeConstsThis) {
		this.financeConstsThis = financeConstsThis;
	}
	public String getFinanceConstsLast() {
		return financeConstsLast;
	}
	public void setFinanceConstsLast(String financeConstsLast) {
		this.financeConstsLast = financeConstsLast;
	}
	public String getMgrConstsThis() {
		return mgrConstsThis;
	}
	public void setMgrConstsThis(String mgrConstsThis) {
		this.mgrConstsThis = mgrConstsThis;
	}
	public String getMgrConstsLast() {
		return mgrConstsLast;
	}
	public void setMgrConstsLast(String mgrConstsLast) {
		this.mgrConstsLast = mgrConstsLast;
	}
	public String getBusiTaxSurcharge() {
		return busiTaxSurcharge;
	}
	public void setBusiTaxSurcharge(String busiTaxSurcharge) {
		this.busiTaxSurcharge = busiTaxSurcharge;
	}
	public String getNetProfitsThis() {
		return netProfitsThis;
	}
	public void setNetProfitsThis(String netProfitsThis) {
		this.netProfitsThis = netProfitsThis;
	}
	public String getNetProfitsLast() {
		return netProfitsLast;
	}
	public void setNetProfitsLast(String netProfitsLast) {
		this.netProfitsLast = netProfitsLast;
	}
	public String getNetProfitsKfThis() {
		return netProfitsKfThis;
	}
	public void setNetProfitsKfThis(String netProfitsKfThis) {
		this.netProfitsKfThis = netProfitsKfThis;
	}
	public String getNetProfitsKfLast() {
		return netProfitsKfLast;
	}
	public void setNetProfitsKfLast(String netProfitsKfLast) {
		this.netProfitsKfLast = netProfitsKfLast;
	}
	public String getOperatCost() {
		return operatCost;
	}
	public void setOperatCost(String operatCost) {
		this.operatCost = operatCost;
	}
	public String getBusiIncomeKf() {
		return busiIncomeKf;
	}
	public void setBusiIncomeKf(String busiIncomeKf) {
		this.busiIncomeKf = busiIncomeKf;
	}
	public String getFairValChange() {
		return fairValChange;
	}
	public void setFairValChange(String fairValChange) {
		this.fairValChange = fairValChange;
	}
	public String getInvestIncome() {
		return investIncome;
	}
	public void setInvestIncome(String investIncome) {
		this.investIncome = investIncome;
	}
	public String getNonOperaIncome() {
		return nonOperaIncome;
	}
	public void setNonOperaIncome(String nonOperaIncome) {
		this.nonOperaIncome = nonOperaIncome;
	}
	public String getNonOperaOutcome() {
		return nonOperaOutcome;
	}
	public void setNonOperaOutcome(String nonOperaOutcome) {
		this.nonOperaOutcome = nonOperaOutcome;
	}
	
	
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getModUser() {
		return modUser;
	}
	public void setModUser(String modUser) {
		this.modUser = modUser;
	}
}
