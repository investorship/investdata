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
<meta charset="utf-8" />
<base href="<%=basePath%>">
<title>投资数据网 - 忘记密码</title>
<meta name="viewport" content="width=1010" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="css/list.css" type="text/css" rel="stylesheet" />
<link href="css/basic.css" type="text/css" rel="stylesheet" />
<link href="css/login.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery.js"></script>
<jsp:include page="jquery_validate.jsp" />
<script type="text/javascript" src="js/form_valid.js"></script>
</head>
<body>
	<div class="wbyTop fn-clear">
		<div class="wbyLinks fn-right">
			<span><a href="login/login.action">登录</a> <a
				href="reg/reg.action">注册</a>|<a href="about_us.jsp">关于我们</a></span>
		</div>
		<!-- header start -->
		<div class="header">
			<div class="wrap clearfix">
				<div class="logo" data-sudaclick="toplogo">
					<a href=""><img src="images/logo2.png" alt=" " title=" " /></a>
				</div>
			</div>
		</div>
		<div class="maincontent">
			<div id="pg-reg">
				<div class="container_12_1080">
					<div class="color-white-bg ui-box-white-bg regbox">
						<div class="ui-tab-content ui-tab-content-current">
							<div class="step1">
								<p class="go-login-pwd">找回密码</p>
								<form id="resetPwdForm" class="ui-form left" method="post"
									action="login/passwordOperAction!sendResetPwdMail.action">
									<fieldset>
										<div class="ui-form-item">
											<label class="ui-label"><span
												class="ui-form-required">*</span>电子邮箱</label> <input
												class="ui-input input-icon" type="text" name="email"
												placeholder="请输入您的邮箱">
										</div>

										<div class="ui-form-item" style="padding-bottom: 65px">
											<div>
												<input type="submit"
													class="ui-button-register ui-button-orange ui-button-register-1" onclick="javascript:resetPwdFormValid()" value="确 认">
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
<jsp:include page="/bottom.html" /></body>
</html>