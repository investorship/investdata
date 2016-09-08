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
    <title>投资数据网 - 邮箱注册成功</title>
    <meta name="viewport" content="width=1010"/>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <link href="css/basic.css" type="text/css" rel="stylesheet"/>
    <link href="css/success.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div class="wbyTop fn-clear">
    <div class="wbyLinks fn-right"><span><a href="login/login.action">登录</a> <a href="reg/reg.action">注册</a>|<a href="about_us.jsp">关于我们</a></span>
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

                    <h5 class="blue">${userName}: 恭喜注册完成！<br><br>
                    		激活信息已寄至您的${email}邮箱,请查邮箱并激活账号</h5>
                    <br>
                </li>
                <li style="padding-top:30px; padding-bottom:40px;color:#333">
                    <dl>
                        <dt>还没有收到确认邮件怎么办?</dt>
                        <dd>1.看看是否在邮箱垃圾箱里</dd>
                        <!-- 
                        <dd>2.如您想修改注册邮箱，请您联系QQ客服 : 123456789</dd> -->
                        <dd>
                            <span>2.如果您还没有收到激活的邮件，请试着<a href="javascript:void(0)" class="again blue">重发激活邮件</a></span>
                            <span class="msg2 s_again_msg"></span></dd>
                    </dl>
                </li>
                <ul>
                </ul>
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