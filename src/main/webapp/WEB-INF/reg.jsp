<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta charset="utf-8" />
<base href="<%=basePath%>">
<title>投资数据网 - 注册</title>
<meta name="viewport" content="width=1010" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="css/basic.css" type="text/css" rel="stylesheet" />
<link href="css/list.css" type="text/css" rel="stylesheet" />
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
					<span><a href="login/login.action">登录</a> <a href="reg/reg.action">注册</a>|<a href="about_us.html">关于我们</a></span>
				</div>
			</c:when>
			<c:otherwise>
				<div class="wbyLinks fn-right">
					[${user.userName}]<span><a href="login/login!logout.action">退出</a>|<a
						href="about_us.html">关于我们</a></span>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<!-- header start -->
	<div class="header">
		<div class="wrap clearfix">
			<div class="logo" data-sudaclick="toplogo">
				<a href=""><img src="images/logo2.png" alt="新浪网导航" title="新浪网导航" /></a>
			</div>
			<div class="search" data-sudaclick="topsearch">
				<form action="stock/stock.action" method="get" target="_blank"
					id="search_f">
					<input type="text" name="keyword" class="search_k"
						placeholder="输入股票代码,名称或拼音首字母"
						onfocus="if(this.value === '请输入您要查找的股票代码'){this.value = '';}"
						onblur="if(this.value === ''){this.value = '请输入您要查找的股票代码';}"
						id="keyword" /> <input type="submit" class="search_smt"
						value="快速查找" />
				</form>
			</div>
		</div>
	</div>
	<div class="maincontent">
		<div id="pg-reg">
			<div class="container_12_1080">
				<div class="color-white-bg ui-box-white-bg regbox">
					<div class="ui-tab-content ui-tab-content-current">
						<div class="step1">
							<div class="ad">
								<hr class="hr0" />
							</div>

							<p class="go-login">
								已有账号？<a href="login/login.action">立即登录</a>
							</p>
							<form class="ui-form left" id="regForm" method="post"
								action="reg/reg!reg.action">
								<fieldset>
									<div class="ui-form-item">
										<label class="ui-label"><span class="ui-form-required">*</span>用户名</label>
										<input class="ui-input input-icon" name="userName" type="text"
											placeholder="请输入用户名" tip="3~25个字符">
									</div>
									<!--  -->
									<div class="ui-form-item">
										<label class="ui-label"><span class="ui-form-required">*</span>登录密码</label>
										<input class="ui-input input-icon" id="password"
											name="password" type="password" placeholder="请输入您的密码"
											tip="6~25个字符">
									</div>
									<div class="ui-form-item">
										<label class="ui-label"><span class="ui-form-required">*</span>确认密码</label>
										<input class="ui-input input-icon" name="repassword"
											type="password" placeholder="请确认您的密码" tip="需与密码相同">
									</div>
									<div class="ui-form-item">
										<label class="ui-label"><span class="ui-form-required">*</span>电子邮箱</label>
										<input class="ui-input input-icon" name="email" type="text"
											placeholder="请输入您的邮箱地址" tip="邮箱用于接收账户激活链接，请确保邮箱可用">
									</div>
									<div class="ui-form-item code-item">
										<label class="ui-label"><span class="ui-form-required">*</span>验证码</label>
										<input class="ui-input input-icon code" type="text"
											name="randCode" id="randCode" placeholder="不区分大小写"> <a
											href="javascript:changeImageAuth()"> <img border="0"
											id="imageAuth" class="code-box"
											src="imageAuth/imageAuth.action" alt="验证码">
										</a>
									</div>
									<div class="ui-form-item" style="height: 50px">
										<input type="checkbox" name="agreee"> 我已阅读并同意<a
											href="service_agreement.html" target="_blank">《投资数据网注册服务协议》</a>
									</div>
									<div class="ui-form-item" style="padding-bottom: 65px">
										<div>
											<input type="submit"
												class="ui-button-register ui-button-orange ui-button-register-1"
												value="注 册" onclick="javascript:regFormValid()" >
										</div>
									</div>
								</fieldset>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
		<p>
			Copyright © 2016 投资数据网 &nbsp;京ICP证160506号&nbsp;<a href="" title="雪球"
				target="_blank">雪球</a> &nbsp; <a href="" title="巨潮资讯网"
				target="_blank">巨潮资讯网</a> <br> <span class="lianxi"></span>
		</p>
	</div>
</body>
</html>
