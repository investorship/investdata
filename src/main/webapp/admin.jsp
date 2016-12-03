<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<title>后台管理登录界面</title>
<link href="css/admin/admlogin.css" rel="stylesheet" type="text/css" />
<!-- 这一句必须放到jsp:inluce这句前面，否则不起作用 -->
<script type="text/javascript" src="js/jquery.js"></script>
<jsp:include page="WEB-INF/jquery_validate.jsp" />
<script type="text/javascript" src="js/form_valid.js"></script>
</head>
<body>
	<form id="adminLoginForm" action="login/adminLogin!adminLogin.action"
		method="post" onsubmit="return checkAdminLogin()">
		<div class="Main">
			<ul>
				<li class="top"></li>
				<li class="top2"></li>
				<li class="topA"></li>
				<li class="topB"><span> <img src="images/admin/logo.gif"
						alt="" style="" />
				</span></li>
				<li class="topC"></li>
				<li class="topD">
					<ul class="login">
						<li><span class="left">用户名：</span> <span style=""> <input
								id="userName" type="text" class="txt" name="userName" />

						</span></li>
						<li><span class="left">密&nbsp;&nbsp;&nbsp;码：</span> <span style=""> <input
								id="password" type="password" class="txt" name="password" />
						</span></li>
						<li><span class="left">验证码：</span> <span style=""> <input
								id="Text3" type="text" class="txtCode" name="randCode" />
						</span>  
						<a href="javascript:changeImageAuth()">
						<img height="30px" id="imageAuth" src="imageAuth/imageAuth.action" alt="验证码"></a>
						</li>
						<li>
						<div id="loginFail" class="top-msg " style="color:#F00;font-weight:blod"></div>
						</li>
					</ul>
				</li>
				<li class="topE"></li>
				<li class="middle_A"></li>
				<li class="middle_B"></li>
				<li class="middle_C"><span class="btn"> <input
						type="image" src="images/admin/btnlogin.gif" onclick="javascript:adminLoginFormValid()">
				</span></li>
				<li class="middle_D"></li>
				<li class="bottom_A"></li>
				<li class="bottom_B"></li>
			</ul>
		</div>
	</form>
</body>
</html>
