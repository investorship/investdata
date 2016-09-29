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
		<c:when test="${operMethod == 'updateGendata'}">
			<span style="font-weight: bold; font-size: 7px; color: #ff0000">
				管理菜单 >> 财务数据管理 >> 综合数据表 >> 数据修改结果 </span>
			<hr />
			<br />
				修改综合数据表数据成功 <a href="financeDataMgr/financeDataMgr!updateGendataInput.action">继续修改</a>
		</c:when>
		
		<c:when test="${operMethod == 'updateCashFlow'}">
			<span style="font-weight: bold; font-size: 7px; color: #ff0000">
				管理菜单 >> 财务数据管理 >> 现金流量表 >> 数据修改结果 </span>
			<hr />
			<br />
				修改现金流量表数据成功 <a href="financeDataMgr/financeDataMgr!updateCashFlowInput.action">继续修改</a>
		</c:when>
		
		<c:when test="${operMethod == 'updateIncstate'}">
			<span style="font-weight: bold; font-size: 7px; color: #ff0000">
				管理菜单 >> 财务数据管理 >> 利润表 >> 数据修改结果 </span>
			<hr />
			<br />
				修改利润表数据成功 <a href="financeDataMgr/financeDataMgr!updateIncstateInput.action">继续修改</a>
		</c:when>
		
		<c:when test="${operMethod == 'updateBalance'}">
			<span style="font-weight: bold; font-size: 7px; color: #ff0000">
				管理菜单 >> 财务数据管理 >> 资产负债表 >> 数据修改结果 </span>
			<hr />
			<br />
				修改资产负债表数据成功 <a href="financeDataMgr/financeDataMgr!updateBalanceInput.action">继续修改</a>
		</c:when>
	</c:choose>


</body>
</html>
