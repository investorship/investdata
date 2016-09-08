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
    <title>投资数据网 - 修改密码</title>
    <meta name="viewport" content="width=1010"/>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <link href="css/list.css" type="text/css" rel="stylesheet"/>
    <link href="css/basic.css" type="text/css" rel="stylesheet"/>
    <link href="css/login.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery.js"></script>
    <jsp:include page="jquery_validate.jsp" />
	<script type="text/javascript" src="js/form_valid.js"></script>
</head>
<body>
<div class="wbyTop fn-clear">
    <c:choose>
		<c:when test="${user == null}">
			<div class="wbyLinks fn-right"><span><a href="login/login.action">登录</a> <a href="reg/reg.action">注册</a>|<a href="about_us.jsp">关于我们</a></span></div>
		</c:when>
		<c:otherwise>
			<div class="wbyLinks fn-right">${user.userName}<span><a href="login/login!logout.action">退出</a>|<a href="about_us.jsp">关于我们</a></span></div>
		</c:otherwise>
	</c:choose>
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
                        <p class="go-login-pwd">修改密码</p>
                        <form id="updatePwdForm" class="ui-form left" method="post" action="login/passwordOperAction!updatePwd.action" >
                            <fieldset>
                                <div class="ui-form-item">
                                    <label class="ui-label"><span class="ui-form-required">*</span>原密码</label>
                                    <input class="ui-input input-icon" type="password" name="oldPwd" id="oldpwd" placeholder="请输入现在的密码">
                                </div>
                                <div class="ui-form-item">
                                    <label class="ui-label"><span class="ui-form-required">*</span>新密码</label>
                                    <input class="ui-input input-icon" type="password" name="newPwd" id="newPwd" placeholder="请输入新密码">
                                </div>
                                <div class="ui-form-item">
                                    <label class="ui-label"><span class="ui-form-required">*</span>确认新密码</label>
                                    <input class="ui-input input-icon" type="password" name="reNewPwd" id="reNewPwd" placeholder="请确认新密码">
                                </div>
                               
                                <div class="ui-form-item" style="padding-bottom:65px">
                                    <div>
                                        <input type="submit"  onclick="javascript:updatePwdFormValid()" class="ui-button-register ui-button-orange ui-button-register-1" value="确 认">
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
<jsp:include page="/bottom.html" />
</body>
</html>