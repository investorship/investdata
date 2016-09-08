<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<base href="<%=basePath%>">
<title>投资数据网 - 登录</title>
<meta name="viewport" content="width=1010" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="css/basic.css" type="text/css" rel="stylesheet" />
<link href="css/login.css" type="text/css" rel="stylesheet" />
<jsp:include page="autocomplete.jsp" />
<jsp:include page="jquery_validate.jsp" />
<script type="text/javascript" src="js/form_valid.js"></script>
</head>
<body>
	<div class="wbyTop fn-clear">
		<c:choose>
			<c:when test="${user == null}">
				<div class="wbyLinks fn-right">
					<span><a href="login/login.action">登录</a> <a
						href="reg/reg.action">注册</a>|<a href="about_us.jsp">关于我们</a></span>
				</div>
			</c:when>
			<c:otherwise>
				<div class="wbyLinks fn-right">
					[${user.userName}]<span><a href="login/login!logout.action">退出</a>|<a
						href="">关于我们</a></span>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	</div>
	<!-- header start -->
	<div class="header">
		<div class="wrap clearfix">
			<div class="logo" data-sudaclick="toplogo">
				<a href=""><img src="images/logo2.png" alt="" title="" /></a>
			</div>
			<div class="search" data-sudaclick="topsearch">
				<form action="stock/stock.action" method="get" target="_blank"
					id="search_f">
					<input type="text" class="search_k" placeholder="请输入您要查找的股票代码"
						onfocus="if(this.value === '请输入您要查找的股票代码'){this.value = '';}"
						onblur="if(this.value === ''){this.value = '请输入您要查找的股票代码';}"
						id="keyword"  name="keyword"/> <input type="submit" class="search_smt"
						value="快速查找" />
				</form>
			</div>
		</div>
	</div>
	<div class="maincontent">
		<div id="pg-login" class="container_12_1080 mt30">
			<div class="ui-box-main-bg  h355"></div>
			<div class="ui-box-main  h315 ">
				<div class="fund-manager">公共场所不建议自动登录，以防账号丢失</div>
				<div class="content loginbox clearfix" id="loginBox">
					<form id="loginForm" class="ui-form" method="post"
						action="login/login!login.action"
						onsubmit="return checkLogin()">
						<fieldset>
							<legend>登录</legend>
							<div id="loginFail" class="top-msg " style="color:#F00;font-weight:blod"></div>
							<div class="ui-form-item">
								<input id="userName" class="ui-input" name="userName" type="text"
									maxlength="48" placeholder="请输入用户名/邮箱">
							</div>
							<div class="ui-form-item">
								<input class="ui-input " name="password" type="password" id="password"
									placeholder="请输入您的密码">
							</div>
							<div class="ui-form-item text-center">
								<input type="submit" class="login-btn" onclick="javascript:loginFormValid()" value="立即登录">
							</div>
							<div class="ui-form-item text-center ui-form-item-no-name">
								<p class="go-reg">
									没有账号？ <a href="reg/reg.action">免费注册</a> <a class="findpsw"
										href="login/passwordOperAction.action?pwdOperFlag=2">忘记密码</a>
								</p>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>


	</div>
	<div id="footer">
		<p>
			Copyright © 2016 投资数据网 &nbsp;京ICP证160506号&nbsp; <a href="" title="雪球"
				target="_blank">雪球</a> &nbsp; <a href="" title="巨潮资讯网"
				target="_blank">巨潮资讯网</a> <br> <span class="lianxi"></span>
		</p>
	</div>
</body>
</html>
