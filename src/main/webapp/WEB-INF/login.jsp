<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head> 
<meta charset="utf-8" />
<title>投资数据网 - 登录</title>
<meta name="viewport" content="width=1010"/>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="../css/list.css" type="text/css" rel="stylesheet"/>
<link href="../css/login.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
	function login() {
		document.getElementById('form').submit();
	}

</script>
</head>
<body>
<div class="wbyTop fn-clear">
  <div class="wbyLinks fn-right"><span><a target="_self" href="login/login.action">登录</a> <a target="_self" href="reg/reg.action">注册</a>|<a href="">关于我们</a></span></div>
</div>
<!-- header start -->
<div class="header">
  <div class="wrap clearfix">
    <div class="logo" data-sudaclick="toplogo"><a href=""><img src="../images/logo2.png" alt="新浪网导航" title="新浪网导航" /></a></div>
    <div class="search"  data-sudaclick="topsearch">
      <form action="login/login!login" method="post" target="_blank" id="search_f">
        <input type="text" name="k" class="search_k" value="请输入您要查找的股票代码" onfocus="if(this.value === '请输入您要查找的股票代码'){this.value = '';}" onblur="if(this.value === ''){this.value = '请输入您要查找的股票代码';}" id="search_k" />
        <input type="submit" class="search_smt" value="快速查找" />
      </form>
    </div>
  </div>
</div>
<div class="content wrap clearfix">
  <div class="center">
    <div class="wrap">
      <div class="signup-top"> 用户登录 </div>
      <div class="box">
        <div class="loginBox">
          <form method="post" id="form" action="login/login!login.action"> 
            <div class="login">
              <div class="clear"> <span>用户名：</span>
                <input type="text" name="username" id="username" value="" placeholder="请输入您的用户名" class="loginValue">
                <p id="username" class="phone"></p>
              </div>
            </div>
            <!-- 密码 -->
            <div class="login">
              <div class="clear"> <span>登录密码：</span>
                <input type="password" placeholder="6-20个英语字母、数字、符号的组合" name="pwd" id="pwd" class="loginValue">
                <p id="pwdmsg" class="phone"></p>
              </div>
            </div>
             
            
            <div class="login">
              <div class="clear"> <span><b class="importantB">*</b>验证码：</span>
                <input placeholder="请输入验证码" id="captcha" name="captcha" style="width: 80px;" class="loginValue">
                <span style="width: 5px;"></span><a style="float:left; line-height:40px; padding-left:10px;" href=""><img style="float:left;" src="../images/ico.jpg" id="captchaimg">看不清楚？换一张</a>
                <p id="captchamsg" class="phone"></p>
              </div>
            </div>
            
            <div class="loginBtn clear">
              <input type="submit" class="regBtn" value="登录" onclick="javascript:login()">
            </div>
            <div class="login_forget">
						<span><a href="">忘记密码？</a></span>
						<span>还没有账号？<a href="" class="PlateTitleA1">免费注册</a></span>
					</div>
          </form>
        </div> 
      </div>
    </div>
  </div>
</div> 
<div class="footer">
  <div class="wrap">
    <p>Copyright &copy; 2016 投资数据网 &nbsp;京ICP证160506号&nbsp;<a href="" title="雪球" target="_blank">雪球</a> &nbsp; <a href="" title="巨潮资讯网" target="_blank">巨潮资讯网</a></p>
  </div>
</div> 
</body>
</html>