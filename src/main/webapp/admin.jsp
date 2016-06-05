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
<script type="text/javascript">
	$(document).ready(function() {
		$("#admin").validate({
			rules : {
				userName : {
					required : true
				},
				password : {
					availChar : true,
					required : true,
					minlength : 6,
					maxlength : 25
				},
				randCode : {
					randCodeCheck: true,
					required : true,
					rangelength : [ 6, 6 ]
				},
			},
			messages : {
				userName : {
					required : "用户名不能为空"
				},
				password : {
					required : "密码不能为空"
				},
				randCode : {
					randCodeCheck: "您输入的验证码不正确",
					required : "验证码不能为空",
					rangelength : "验证码位数错误"
				}
			}
		});
	});
</script>

<script type="text/javascript">
	//更换验证码 防止缓存，使用时间戳
	function changeImageAuth() {
		$("#imageAuth").attr("src",
				"imageAuth/imageAuth.action?timestamp=" + new Date().getTime());
	}
</script>
<script type="text/javascript">
	function checkLogin() {
		var userName = $("#userName").val();
		var password = $("#password").val();
		
		var resultVal = "";
		$.ajax({
	        type: "post",
	        async: false,
	        url: "login/login!checkAdminLogin.action?userName=" + userName + "&password=" + password,
	        //方法传参的写法一定要对，与后台一致，区分大小写，不能为数组等，str1为形参的名字,str2为第二个形参的名字 
	        data: "{}",
	       // contentType: "application/json; charset=utf-8",
	       	dataType: "json",
	        success: function(data) {
	           resultVal = data.ajaxResult;
	        },
	        error: function(err) { //如果出现异常，不做界面提示
	        	resultVal = false;
	        }
	        
	    });
		
			if ('true' == resultVal) {
				return true;
			} else {
				$("#loginFail").attr("color","#FF0000");
				$("#loginFail").text("用户名或密码错误！");
				return false;
			}
	}
</script>
</head>
<body>
	<form id="admin" action="login/adminLogin!adminLogin.action"
		method="post" onsubmit="return checkLogin()">
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
						<li><span class="left">密 码：</span> <span style=""> <input
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
						type="image" src="images/admin/btnlogin.gif">
				</span></li>
				<li class="middle_D"></li>
				<li class="bottom_A"></li>
				<li class="bottom_B"></li>
			</ul>
		</div>
	</form>
</body>
</html>
