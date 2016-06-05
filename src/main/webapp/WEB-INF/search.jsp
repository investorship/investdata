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
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>投资数据网</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta name="document-state" content="dynamic"/>
<link href="css/i.css" type="text/css" rel="stylesheet" />
<jsp:include page="autocomplete.jsp" />
</head>
<body id="home">
<div class="wbyTop fn-clear">
	<c:choose>
		<c:when test="${user == null}">
			<div class="wbyLinks fn-right"><span><a target="_blank" href="login/login.action">登录</a> <a target="_blank" href="reg/reg.action">注册</a>|<a href="">关于我们</a></span></div>
		</c:when>
		<c:otherwise>
			<div class="wbyLinks fn-right">[${user.userName}]<span><a href="login/login!logout.action">退出</a>|<a href="">关于我们</a></span></div>
		</c:otherwise>
	</c:choose>
  
</div>
<div id="wrapper" style="margin-bottom:0;">
  <div id="header">
    <h1 id="logo"><img src="images/logo.png" height="80px" alt="投资数据网"/></h1>
    <div id="sbox">
      <form id="search_f" action="stock/stock.action">
        <input type="text" baiduSug="2" autocomplete="off" placeholder="输入股票代码,名称或拼音首字母" id="keyword" name="q" class="stbox"/>
        <input type="submit" onmouseout="this.className=''" onmousedown="this.className='mousedown'" onmouseover="this.className='hover'" value="搜索" id="sbutton"/>
      </form>
    </div>
    <div id="index_ad_top"></div>
  </div>
  <div id="container">
    <div class="main">
    <div class="hotwords">
        <div class="hwtit">
          <h4>股票  & 财务指标  <font color="#FF0000">热搜榜!</font></h4>
          <span><a href="" title="今日热搜"></a></span></div>
        	<ul class="hwentry">       
   
          <li><a class="HotKey" href="" target="_blank">格力电器</a></li>
          <li><a class="HotKey" href="" target="_blank">贵州茅台</a></li>
          <li><a class="HotKey" href="" target="_blank">福耀玻璃</a></li>
          <li><a class="HotKey" href="" target="_blank">宇通客车</a></li>
          <li><a class="HotKey" href="" target="_blank">兴业银行</a></li>
          <li><a class="HotKey" href="" target="_blank">中国平安</a></li>
          <li><a class="HotKey" href="" target="_blank">保利地产</a></li>
          <li><a class="HotKey" href="" target="_blank">海立美达</a></li>
          <li><a class="HotKey" href="" target="_blank">每股收益</a></li>
          <li><a class="HotKey" href="" target="_blank">稀释每股收益</a></li>
          <li><a class="HotKey" href="" target="_blank">流动比率</a></li>
          <li><a class="HotKey" href="" target="_blank">速动比率</a></li>
          <li><a class="HotKey" href="" target="_blank">现金比率</a></li>
          <li><a class="HotKey" href="" target="_blank">产权比率</a></li>
          <li><a class="HotKey" href="" target="_blank">扣非净利润</a></li>
          <li><a class="HotKey" href="" target="_blank">固定资产比率</a></li>
         
        </ul>
      </div>
    	
    </div>
  </div>
  <div id="ad_bottom_index" style="text-align:center;margin:auto"> </div>
</div>
<div id="footer">
  <p>Copyright &copy; 2016 投资数据网 &nbsp;京ICP证160506号&nbsp;<a href="" title="雪球" target="_blank">雪球</a> &nbsp; <a href="" title="巨潮资讯网" target="_blank">巨潮资讯网</a> <br>
    <span class="lianxi"></span> </p>
</div>
</body>
</html>
