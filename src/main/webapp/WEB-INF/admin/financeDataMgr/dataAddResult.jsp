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
	</c:choose>


</body>
</html>