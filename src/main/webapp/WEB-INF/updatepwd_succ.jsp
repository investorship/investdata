<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <base href="<%=basePath%>">
    <title>投资数据网 - 密码修改成功</title>
    <meta name="viewport" content="width=1010"/>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <link href="css/basic.css" type="text/css" rel="stylesheet"/>
    <link href="css/success.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div class="wbyTop fn-clear">
    <div class="wbyLinks fn-right">
    <c:choose>
			<c:when test="${user == null}">
				<div class="wbyLinks fn-right">
					<span><a href="login/login.action">登录</a> <a href="reg/reg.action">注册</a>|<a href="about_us.jsp">关于我们</a></span>
				</div>
			</c:when>
			<c:otherwise>
				<div class="wbyLinks fn-right">
					[${user.userName}]<span><a href="login/login!logout.action">退出</a>|<a
						href="about_us.jsp">关于我们</a></span>
				</div>
			</c:otherwise>
		</c:choose>
    </div>
</div>
<!-- header start -->
<div class="header">
    <div class="wrap clearfix">
        <div class="logo" data-sudaclick="toplogo"><a href=""><img src="images/logo2.png" alt="" title=""/></a></div>
    </div>
</div>
<div class="content clear">
    <div class="mainbody">
        <div id="msg" style="display:none"></div>
        <div class="uccontainer01">
            <ul>
                <li style="border-bottom:1px solid #EEEEEE !important">
                    <h5 class="blue"><br><br>
                    		密码修改成功!</h5>
                    <br><br>
                </li>
                <ul>
                </ul>
            </ul>
        </div>
    </div>
</div>

<jsp:include page="/bottom.html" />
</body>
</html>