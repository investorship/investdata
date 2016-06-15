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
    <title>投资数据网 - 邮箱激活成功</title>
    <meta name="viewport" content="width=1010"/>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <link href="css/basic.css" type="text/css" rel="stylesheet"/>
    <link href="css/success.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div class="wbyTop fn-clear">
    <c:choose>
			<c:when test="${user == null}">
				<div class="wbyLinks fn-right">
					<span><a target="_blank" href="login/login.action">登录</a> <a
						href="reg/reg.action">注册</a>|<a href="about_us.html">关于我们</a></span>
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
        <div class="logo" data-sudaclick="toplogo"><a href=""><img src="images/logo2.png" alt="" title=""/></a></div>
    </div>
</div>
<div class="content clear">
    <div class="mainbody">
        <div id="msg" style="display:none"></div>
        <div class="uccontainer02">
            <ul>
                <li style="border-bottom:1px solid #EEEEEE !important">
                    <h5 class="blue">${userName}: 账户激活成功!</h5>
               		 <a href="login/login.action" class="again blue">登录</a>
               		 <br><br></br><br><br></br>
                </li>
            </ul>
        </div>
    </div>
</div>

<div id="footer">
    <p>Copyright © 2016 投资数据网 &nbsp;京ICP证160506号&nbsp;
        <a href="" title="雪球" target="_blank">雪球</a> &nbsp;
        <a href="" title="巨潮资讯网" target="_blank">巨潮资讯网</a>
        <br>
        <span class="lianxi"></span></p>
</div>
</body>
</html>