<%@page import="javax.sound.midi.SysexMessage"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>投资数据网</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="document-state" content="dynamic" />
<link href="css/i.css" type="text/css" rel="stylesheet" />
<link href="css/s.css" type="text/css" rel="stylesheet" />
<link href="css/jquery.autocomplete.css" type="text/css"
	rel="stylesheet" />
<link href="css/jquery-ui.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="js/jquery.autocomplete.min.js"></script>
<script type="text/javascript" src="js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript">
</script>
<script type="text/javascript">

$(function() {
	//从后台获取数据.
	var availableTags = ${stockData};
	$(function() {
		//从后台获取数据.
		var availableTags = ${stockData};
	    $("#keyword").autocomplete({
	    	source:availableTags,
	    	maxRows:12,
	    	matchSubset:true,
	    	cacheLength: 1000,
	    	scroll: true,
	    	
	    	select: function( event, ui ){
		    	var selectVal = ui.item.value.split("    ");
		    	var keyword = selectVal[1];
		    	
		      }
	    })
	 });
  });
  
  function procKeyword(keyword) {
	  $("#keyword").value=keyword;
  }
</script>
</head>
<body id="home">
	<div class="wbyTop fn-clear">
		<div class="wbyLinks fn-right">
			<span><a target="_blank" href="login/login.action">登录</a> <a
				target="_blank" href="reg/reg.action">注册</a>|<a href="">关于我们</a></span>
		</div>
	</div>
	<div id="wrapper" style="margin-bottom: 0;">
		<div id="header">
			<h1 id="logo">
				<img src="images/logo.png" height="80px" alt="投资数据网" />
			</h1>
			<div id="sbox">
				<form id="keywordForm" name="btform" action="">
					<input type="text" baiduSug="2" placeholder="代码,名称或拼音" id="keyword"
						name="q" class="stbox" /> <input type="submit"
						onmouseout="this.className=''"
						onmousedown="this.className='mousedown'"
						onmouseover="this.className='hover'" value="搜索" id="sbutton" />
				</form>
			</div>
			<div id="nums" style="font-size: 17px;">
				本站共索引 <span></span> 条财务数据
			</div>
			<div id="index_ad_top"></div>
		</div>
		<div id="container">
			<div class="main">
				<div class="hotwords">
					<div class="hwtit">
						<h4>今日热搜</h4>
						<span><a href="" title="今日热搜">更多&gt;&gt;</a></span>
					</div>
					<ul class="hwentry">

						<li><a class="HotKey" href="" target="_blank">每股收益</a></li>
						<li><a class="HotKey" href="" target="_blank">稀释每股收益</a></li>
						<li><a class="HotKey" href="" target="_blank">流动比率</a></li>
						<li><a class="HotKey" href="" target="_blank">速动比率</a></li>
						<li><a class="HotKey" href="" target="_blank">现金比率</a></li>
						<li><a class="HotKey" href="" target="_blank">产权比率</a></li>
						<li><a class="HotKey" href="" target="_blank">扣非净利润</a></li>
						<li><a class="HotKey" href="" target="_blank">固定资产比率</a></li>
						<li><a class="HotKey" href="" target="_blank">无形资产比率</a></li>
						<li><a class="HotKey" href="" target="_blank">总资金周转率</a></li>
						<li><a class="HotKey" href="" target="_blank">利润增长率</a></li>
						<li><a class="HotKey" href="" target="_blank">运营资金</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div id="ad_bottom_index" style="text-align: center; margin: auto">
		</div>
	</div>
	<div id="footer">
		<p>
			Copyright &copy; 2016 投资数据网 &nbsp;京ICP证160506号&nbsp;<a href=""
				title="雪球" target="_blank">雪球</a> &nbsp; <a href="" title="巨潮资讯网"
				target="_blank">巨潮资讯网</a> <br> <span class="lianxi"></span>
		</p>
	</div>
</body>
</html>