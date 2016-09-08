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
    <title>投资数据网 - 密码重置</title>
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

                    <h5 class="blue">${userName}:<br><br>
                    &nbsp;&nbsp;&nbsp;&nbsp;密码重置链接已寄至您的${email}邮箱,请登录邮箱根据提示操作</h5>
                    <br>
                </li>
                <li style="padding-top:30px; padding-bottom:40px;color:#333">
                    <dl>
                        <dt>还没有收到确认邮件怎么办?</dt>
                        <dd>1.看看是否在邮箱垃圾箱里</dd>
                        <!-- 
                        <dd>2.如您想修改注册邮箱，请您联系QQ客服 : 123456789</dd> -->
                        <dd>
                            <span>2.如果您还没有收到密码重置邮件，请试着<a href="javascript:void(0)" class="again blue">重发邮件</a></span>
                            <span class="msg2 s_again_msg"></span></dd>
                    </dl>
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