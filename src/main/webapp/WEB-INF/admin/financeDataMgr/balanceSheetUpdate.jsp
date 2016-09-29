<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<!-- 禁止网页被搜索引擎抓取 -->
<meta name="robots" content="none" />
<title>资产负责表数据修改</title>

<jsp:include page="../autocomplete_admin.jsp" />

</head>

<body>
	<span style="font-weight: bold; font-size: 7px; color: #ff0000">
		管理菜单 >> 财务数据管理 >> 资产负债表 >> 资产负债表数据修改 </span>
	<hr />
	<br />
	<form id="balForm" name="balForm" method="post" action="financeDataMgr/financeDataMgr!updateBalance.action">
		<label>股票代码 <input name="balanceSheet.code" type="text" id="code" size="25" />
		</label> <label>数据年份 <input name="balanceSheet.year" type="text" id="year"
			size="25" />
		</label> <label>应收票据 <input name="balanceSheet.noteRecable" type="text"
			id="noteRecable" size="25" />
		</label> 预收账款 <input name="balanceSheet.advCustomers" type="text" id="advCustomers"
			size="25" />

		<p>
			应付账款 <input name="balanceSheet.accPayable" type="text" id="accPayable" size="25" />
			在建工程 <input name="balanceSheet.constrInPro" type="text" id="constrInPro" size="25" />
			无形资产 <input name="balanceSheet.lntangAssets" type="text" id="lntangAssets"
				size="25" /> 商&nbsp;&nbsp;誉 <input name="balanceSheet.goodWill" type="text"
				id="goodwill" size="25" />
		</p>
		<p>
			短期借款 <input name="balanceSheet.shortTermLoans" type="text" id="shortTermLoans"
				size="25" /> 应付票据 <input name="balanceSheet.notePayable" type="text"
				id="notePayable" size="25" /> 资本公积 <input name="balanceSheet.capitalSurplus"
				type="text" id="capitalSurplus" size="25" /> 盈余公积 <input
				name="balanceSheet.surplusReserve" type="text" id="surplusReserve" size="25" />
		</p>
		<p>
			长期借款 <input name="balanceSheet.longTermLoans" type="text" id="longTermLoans"
				size="25" /> 应付债券 <input name="balanceSheet.boundsPayable" type="text"
				id="boundsPayable" size="25" /> 期初存货 <input name="balanceSheet.goodsStart"
				type="text" id="goodsStart" size="25" /> 期末存货 <input
				name="balanceSheet.goodsEnd" type="text" id="goodsEnd" size="25" />
		</p>
		<p>
			货币资金 <input name="balanceSheet.cash" type="text" id="cash" size="25" /> 流动负债 <input
				name="balanceSheet.currLiab" type="text" id="currLiab" size="25" /> 非流动负债 <input
				name="balanceSheet.currLiabNon" type="text" id="currLiabNon" size="25" /> 未分配利润
			<input name="balanceSheet.retainEarnings" type="text" id="retainEarnings" size="25" />
		</p>
		<p>
			期初流动资产 <input name="balanceSheet.liquidAssetsStart" type="text" id="liquidAssetsStart" size="25" />
			期末流动资产 <input name="balanceSheet.liquidAssetsEnd" type="text" id="liquidAssetsEnd" size="25" />
			期初资产总额 <input name="balanceSheet.totalAssStart" type="text" id="totalAssStart" size="25" />
			期末资产总额 <input name="balanceSheet.totalAssEnd" type="text" id="totalAssEnd" size="25" />
		</p>
		<p>
			期初股东权益 <input name="balanceSheet.shareHolderStart" type="text" id="shareHolderStart" size="25" />
			期末股东权益 <input name="balanceSheet.shareHolderEnd" type="text" id="shareHolderEnd" size="25" />
			期初固定资产 <input name="balanceSheet.fixedAssetsStart" type="text" id="fixedAssetsStart" size="25" />
			期末固定资产 <input name="balanceSheet.fixedAssetsEnd" type="text" id="fixedAssetsEnd" size="25" />
		</p>
		<p>
			期初应收账款 <input name="balanceSheet.accRecableStart" type="text" id="accRecableStart" size="25" />
			期末应收账款 <input name="balanceSheet.accRecableEnd" type="text" id="accRecableEnd" size="25" />
			<label>一年内到期的非流动负债 <input type="text" name="balanceSheet.debitWithinYear" />
			</label>
		</p>
		
		<p>
			无形资产摊销<input name="balanceSheet.lntangAssetsAmortize" type="text" id="lntangAssetsAmortize" size="25" />
			长期应付款<input name="balanceSheet.longAccPayable" type="text" id="longAccPayable" size="25" />
			交易性金融资产<input name="balanceSheet.tradAssets" type="text" id="tradAssets" size="25" />
		</p>
		
		<p>
			期初负债总额<input name="balanceSheet.totalLiabStart" type="text" id="totalLiabStart" size="25" />
			期末负债总额<input name="balanceSheet.totalLiabEnd" type="text" id="totalLiabEnd" size="25" />
		</p>
		
		
		
		<p>
			状态 <label> <input name="balanceSheet.flag" type="radio" value="1"
				checked="checked" /> 启用
			</label> <input type="radio" name="balanceSheet.flag" value="0" /> 停用
		</p>
		<p>
			<label>
				<div align="center">
					<input type="submit" name="Submit" value="提交" />
				</div>
			</label>
		</p>
		<p>&nbsp;</p>
	</form>
</body>
</html>
