<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath(); 
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="search" data-sudaclick="topsearch">
	<form action="stock/stock.action" method="post" target="_blank"
		id="search_f">
		<input type="text" name="keyword" class="search_k"
			value="请输入您要查找的股票代码"
			onfocus="if(this.value === '请输入您要查找的股票代码'){this.value = '';}"
			onblur="if(this.value === ''){this.value = '请输入您要查找的股票代码';}"
			id="keyword" /> <input type="submit" class="search_smt" value="快速查找" />
	</form>
</div>