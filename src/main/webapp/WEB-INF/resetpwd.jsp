<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <base href="<%=basePath%>">
    <title>投资数据网 - 忘记密码</title>
    <meta name="viewport" content="width=1010"/>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <link href="css/list.css" type="text/css" rel="stylesheet"/>
    <link href="css/basic.css" type="text/css" rel="stylesheet"/>
    <link href="css/login.css" type="text/css" rel="stylesheet"/>
    <jsp:include page="jquery_validate.jsp" />
	<script type="text/javascript">
	$(document).ready(function() {
		$("#resetPwdForm").validate({
			rules : {
				email : {
					required : true,
					email : true
				}
			},
			messages : {
				email : {
					required : "邮箱不能为空",
					email : "邮箱地址格式错误"
				}
			}
		});
	});
	
	</script>
</head>
<body>
<div class="wbyTop fn-clear">
    <div class="wbyLinks fn-right"><span><a target="_blank" href="">登录</a> <a target="_blank" href="">注册</a>|<a href="">关于我们</a></span></div>
</div>
<!-- header start -->
<div class="header">
    <div class="wrap clearfix">
        <div class="logo" data-sudaclick="toplogo"><a href=""><img src="images/logo2.png" alt=" " title=" " /></a></div>
    </div>
</div>
<div class="maincontent">
    <div id="pg-reg">
        <div class="container_12_1080">
            <div class="color-white-bg ui-box-white-bg regbox" >
                <div class="ui-tab-content ui-tab-content-current">
                    <div class="step1">
                        <p class="go-login-pwd">找回密码</p>
                        <form id="resetPwdForm" class="ui-form left" method="post" action="login/login!sendResetMail.action" >
                            <fieldset>
                                <div class="ui-form-item">
                                    <label class="ui-label"><span class="ui-form-required">*</span>电子邮箱</label>
                                    <input class="ui-input input-icon" type="text" name="email" placeholder="请输入您的邮箱">
                                </div>
                               
                                <div class="ui-form-item" style="padding-bottom:65px">
                                    <div>
                                        <input type="submit"  class="ui-button-register ui-button-orange ui-button-register-1" value="确 认">
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
    <p>Copyright © 2016 投资数据网 &nbsp;京ICP证160506号&nbsp;<a href="" title="雪球" target="_blank">雪球</a> &nbsp; <a href="" title="巨潮资讯网" target="_blank">巨潮资讯网</a> <br>
        <span class="lianxi"></span> </p>
</div>
</body>
</html>