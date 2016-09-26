<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<!-- 禁止网页被搜索引擎抓取 -->
<meta name="robots" content="none" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>缓存加载结果</title>

</head>
<body>
		<h2>【${funcName}】缓存数据已更新...</h2>	
</body>
</html>