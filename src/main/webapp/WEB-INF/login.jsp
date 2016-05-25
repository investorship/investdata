<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
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
    <title>投资数据网 - 登陆</title>
    <meta name="viewport" content="width=1010"/>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <link href="css/basic.css" type="text/css" rel="stylesheet"/>
    <link href="css/login.css" type="text/css" rel="stylesheet"/>
    <!-- 
    <link href="css/jquery.validate.css" type="text/css" rel="stylesheet"/> -->
	<script src="js/jquery.validate.min.js"></script>
	<script src="js/jquery.validate.extend.js"></script>
	<jsp:include page="autocomplete.jsp" />
	
</head>
<body>
<div class="wbyTop fn-clear">
    <div class="wbyLinks fn-right"><span><a target="_blank">登录</a> <a target="_blank" href="reg/reg.action">注册</a>|<a href="">关于我们</a></span>
    </div>
</div>
<!-- header start -->
<div class="header">
    <div class="wrap clearfix">
        <div class="logo" data-sudaclick="toplogo"><a href=""><img src="images/logo2.png" alt="" title=""/></a></div>
        <div class="search" data-sudaclick="topsearch">
            <form action="stock/stock.action" method="get" target="_blank" id="search_f">
                <input type="text" class="search_k" placeholder="请输入您要查找的股票代码"
                       onfocus="if(this.value === '请输入您要查找的股票代码'){this.value = '';}"
                       onblur="if(this.value === ''){this.value = '请输入您要查找的股票代码';}" id="keyword"/>
                <input type="submit" class="search_smt" value="快速查找"/>
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
                <form id="loginForm" class="ui-form" method="post" action="login/login!login.action" >
                    <fieldset>
                        <legend>登录</legend>
                        <div class="top-msg "> </div>
                        <div class="ui-form-item">
                            <input class="ui-input" name="user.userName" type="text" maxlength="48" placeholder="请输入用户名/邮箱">
                        </div>
                        <div class="ui-form-item">
                            <input class="ui-input " name="user.pwd" type="password" placeholder="请输入您的密码">
                        </div>
                        
                        <div class="ui-form-item" style="height: 50px">
                                    <input type="checkbox"> 我已阅读并同意<a href=" " target="_blank">《投资数据网注册服务协议》</a>
                        </div>
                        <div class="ui-form-item text-center">
                            <input type="submit" class="login-btn" value="立即登录">
                        </div>
                        <div class="ui-form-item text-center ui-form-item-no-name">
                            <p class="go-reg">没有账号？ <a href="reg/reg.action">免费注册</a> <a class="findpsw" href="">忘记密码</a> </p>
                        </div>
                    </fieldset>
                </form>
            </div>
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