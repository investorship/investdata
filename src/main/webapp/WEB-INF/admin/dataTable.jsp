<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<link rel="stylesheet" type="text/css" href="<%=basePath %>dataTables/css/dataTables.jqueryui.css" />
<script type="text/javascript" charset="utf8" src="<%=basePath %>dataTables/js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf8" src="<%=basePath %>dataTables/js/dataTables.jqueryui.js"></script>
