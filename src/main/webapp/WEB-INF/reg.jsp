<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html> 
<head> 
<meta charset="utf-8" />
<base href="<%=basePath%>">
<title>投资数据网 - 注册</title>
<meta name="viewport" content="width=1010"/>
<meta name="keywords" content="" />
<meta name="description" content="" />

<link href="css/basic.css" type="text/css" rel="stylesheet"/>
<link href="css/list.css" type="text/css" rel="stylesheet"/>
<link href="css/login.css" type="text/css" rel="stylesheet"/>
<jsp:include page="autocomplete.jsp" />
<script type="text/javascript">
	//更换验证码 防止缓存，使用时间戳
	function changeImageAuth() {
		$("#imageAuth").attr("src","imageAuth/imageAuth.action?timestamp="+new Date().getTime());
	}
</script>
<jsp:include page="jquery_validate.jsp" />
<script type="text/javascript">
$(document).ready(function(){
    $("#regForm").validate({
        rules: {
        	userName:{
                required: true,
                minlength: 2
            },
            password:{
                required: true,
                minlength: 6,
                maxlength: 16
            },
            repassword:{
                required: true,
                equalTo: "#repassword"
            },
            email:{
            	required:true,
            	email:true
            },
            randCode: {
            	required: true,
            	rangelength:[6,6]
            }
            
        },
        messages:{
        	userName:{
                required: "用户名不能为空",
                minlength: "用户名的最小长度为2"
            },
            password:{
                required: "密码不能为空",
                minlength: "密码长度不能少于6个字符",
                maxlength: "密码长度不能超过16个字符"
            },
            repassword:{
                required: "确认密码不能为空",
                equalTo: "确认密码和密码不一致"
            },
            email:{
            	required : "邮箱不能为空",
            	email : "请输入正确的邮箱地址"
            },randCode: {
            	required: "验证码不能为空",
            	rangelength: "验证码位数错误"
            }
        }
    });
});
</script>
</head>
<body>
<div class="wbyTop fn-clear">
  <c:choose>
		<c:when test="${user == null}">
			<div class="wbyLinks fn-right"><span><a href="login/login.action">登录</a> <a target="_blank" href="reg/reg.action">注册</a>|<a href="">关于我们</a></span></div>
		</c:when>
		<c:otherwise>
			<div class="wbyLinks fn-right">[${user.userName}]<span><a href="login/login!logout.action">退出</a>|<a href="">关于我们</a></span></div>
		</c:otherwise>
	</c:choose>
</div>
<!-- header start -->
<div class="header">
  <div class="wrap clearfix">
    <div class="logo" data-sudaclick="toplogo"><a href=""><img src="images/logo2.png" alt="新浪网导航" title="新浪网导航" /></a></div>
    <div class="search"  data-sudaclick="topsearch">
      <form action="stock/stock.action" method="get" target="_blank" id="search_f">
        <input type="text" name="k" class="search_k" placeholder="输入股票代码,名称或拼音首字母" onfocus="if(this.value === '请输入您要查找的股票代码'){this.value = '';}" onblur="if(this.value === ''){this.value = '请输入您要查找的股票代码';}" id="keyword" />
        <input type="submit" class="search_smt" value="快速查找" />
      </form>
    </div>
  </div>
</div>
<div class="maincontent">
    <div id="pg-reg">
        <div class="container_12_1080">
            <div class="color-white-bg ui-box-white-bg regbox" >
                <div class="ui-tab-content ui-tab-content-current">
                    <div class="step1">
                        <div class="ad"><hr class="hr0" /></div>
						
                        <p class="go-login">已有账号？<a href="login/login.action">立即登录</a>   </p>
                        <form class="ui-form left" id="regForm" method="post" action="reg/reg!reg.action" >
                            <fieldset>
                             	<div class="ui-form-item">
                                    <label class="ui-label"><span class="ui-form-required">*</span>用户名</label>
                                    <input class="ui-input input-icon" name="userName" type="text" placeholder="请输入用户名">
                                </div>
                                <div class="ui-form-item">
                                    <label class="ui-label"><span class="ui-form-required">*</span>登录密码</label>
                                    <input class="ui-input input-icon" name="password" type="password" placeholder="请输入您的密码">
                                </div>
                                <div class="ui-form-item">
                                    <label class="ui-label"><span class="ui-form-required">*</span>重复密码</label>
                                    <input class="ui-input input-icon" name="repassword" type="password" placeholder="请确认您的密码">
                                </div>
                                <div class="ui-form-item">
                                    <label class="ui-label"><span class="ui-form-required">*</span>电子邮箱</label>
                                    <input class="ui-input input-icon" name="email" type="text" placeholder="用户接收账户激活链接">
                                </div>
                                <div class="ui-form-item code-item">
                                    <label class="ui-label"><span class="ui-form-required">*</span>验证码</label>
                                    <input class="ui-input input-icon code" type="text" name="randCode" id="randCode">
                                    <a href="javascript:changeImageAuth()">
                                    <img border="0" id="imageAuth" class="code-box" src="imageAuth/imageAuth.action" alt="验证码">
                                    </a>
                                </div>
                                <div class="ui-form-item" style="height: 50px">
                                    <input type="checkbox"> 我已阅读并同意<a href=" " target="_blank">《投资数据网注册服务协议》</a>
                                </div>
                                <div class="ui-form-item" style="padding-bottom:65px">
                                    <div>
                                        <input type="submit"  class="ui-button-register ui-button-orange ui-button-register-1" value="注 册">
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