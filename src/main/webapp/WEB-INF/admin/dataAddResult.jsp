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
<meta http-equiv="Content-Type" content="text/html" />
<!-- 禁止网页被搜索引擎抓取 -->
<meta name="robots" content="none" />
<title>数据新增结果</title>
</head>

<body>
	<c:choose>
		<c:when test="${operMethod == 'addGendata'}">
			<span style="font-weight: bold; font-size: 7px; color: #ff0000">
				管理菜单 >> 财务数据管理 >> 综合数据表 >> 数据新增结果 </span>
			<hr />
			<br />
				添加综合数据表数据成功 <a href="financeDataMgr/financeDataMgr!addGendataInput.action">继续添加</a>
		</c:when>
		<c:when test="${operMethod == 'addCashFlow'}">
			<span style="font-weight: bold; font-size: 7px; color: #ff0000">
				管理菜单 >> 财务数据管理 >> 现金流量表 >> 数据新增结果 </span>
			<hr />
			<br />
				添加现金流量数据成功 <a href="financeDataMgr/financeDataMgr!addCashFlowInput.action">继续添加</a>
		</c:when>
		
		<c:when test="${operMethod == 'addIncstate'}">
			<span style="font-weight: bold; font-size: 7px; color: #ff0000">
				管理菜单 >> 财务数据管理 >> 利润表 >> 数据新增结果 </span>
			<hr />
			<br />
				添加利润表数据成功 <a href="financeDataMgr/financeDataMgr!addIncstateInput.action">继续添加</a>
		</c:when>
		
		<c:when test="${operMethod == 'addBalance'}">
			<span style="font-weight: bold; font-size: 7px; color: #ff0000">
				管理菜单 >> 财务数据管理 >> 资产负债表 >> 数据新增结果 </span>
			<hr />
			<br />
				添加资产负债表数据成功 <a href="financeDataMgr/financeDataMgr!addBalanceInput.action">继续添加</a>
		</c:when>
		<c:when test="${operMethod == 'addStock'}">
			<span style="font-weight: bold; font-size: 7px; color: #ff0000">
				管理菜单 >> 股票信息管理 >> 股票信息新增 >> 数据新增结果 </span>
			<hr />
			<br />
				添加股票信息数据成功 <a href="stocksMgr/stocksMgr!addStockInput.action">继续添加</a>
		</c:when>
		<c:when test="${operMethod == 'addFinanceIndexInfo'}">
			<span style="font-weight: bold; font-size: 7px; color: #ff0000">
				管理菜单 >> 财务指标管理 >> 财务指标新增 >> 数据新增结果 </span>
			<hr />
			<br />
				添加财务指标数据成功 <a href="financeIndexMgr/financeIndexMgr!addInput.action">继续添加</a>
		</c:when>
	</c:choose>


</body>
</html>
