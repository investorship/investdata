<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<script type="text/javascript">
	var rootpath = '${pageContext.request.contextPath}';
	</script>

</head>
</head>
<body>
<h2>Hello World!</h2>
	<form action="login/login.action">
		用户名：<input name="userName" type="text" >
		密码：   <input name="pwd" type="password" >
		<input type="submit" value="提交">
	</form>
</body>
</html>
