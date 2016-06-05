<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head> 
<meta charset="utf-8" />
<title>投资数据网 - 注册</title>
<meta name="viewport" content="width=1010"/>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="../css/list.css" type="text/css" rel="stylesheet"/>
<link href="../css/login.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div class="wbyTop fn-clear">
  <div class="wbyLinks fn-right"><span><a target="_self" href="">登陆</a> <a target="_self" href="">注册</a>|<a href="">关于我们</a></span></div>
</div>
<!-- header start -->
<div class="header">
  <div class="wrap clearfix">
    <div class="logo" data-sudaclick="toplogo"><a href=""><img src="../images/logo2.png" alt="新浪网导航" title="新浪网导航" /></a></div>
    <div class="search"  data-sudaclick="topsearch">
      <form action="" method="get" target="_blank" id="search_f">
        <input type="text" name="k" class="search_k" value="请输入您要查找的股票代码" onfocus="if(this.value === '请输入您要查找的股票代码'){this.value = '';}" onblur="if(this.value === ''){this.value = '请输入您要查找的股票代码';}" id="search_k" />
        <input type="submit" class="search_smt" value="快速查找" />
      </form>
    </div>
  </div>
</div>
<div class="content wrap clearfix">
  <div class="center">
    <div class="wrap">
      <div class="signup-top"> 用户注册 </div>
      <div class="box">
        <div class="loginBox">
          <form onsubmit="return valiForm();" method="post" id="form"> 
            <div class="login">
              <div class="clear"> <span><b class="importantB">*</b>电子邮箱：</span>
                <input type="text" name="email" id="email" value="" placeholder="请输入您的邮箱" class="loginValue">
                <p id="emailmsg" class="phone"></p>
              </div>
            </div> 
            <div class="login">
              <div class="clear"> <span><b class="importantB">*</b>登录密码：</span>
                <input type="password" placeholder="6-20个英语字母、数字、符号的组合" name="pwd" id="pwd" class="loginValue">
                <p id="pwdmsg" class="phone"></p>
              </div>
            </div> 
            <div class="login">
              <div class="clear"> <span><b class="importantB">*</b>重复密码：</span>
                <input type="password" placeholder="确保两次输入完全一致" name="repwd" id="repwd" value="" class="loginValue">
                <p id="repwdmsg" class="phone"></p>
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
              <input type="submit" class="regBtn" value="同意以下协议，提交">
            </div>
            <div class="login_forget"> <span>我已经注册，现在就去<a href="" class="PlateTitleA1">登录</a></span> </div>
          </form>
        </div>
        <div style="display:block;" class="regText">
          <h4>阅读并接受《投资数据网-用户协议》</h4>
          <p class="regP"> 投资数据网平台是一个方便投资数据爱好者交流的平台，本平台不参与也不提供任何保密的买卖，买卖双方均为本站注册用户。做为注册用户， 您应保证进行的交易不侵犯任何第三方的权利或适用法律。 在法律允许范围内，本网站将不对用户的任何损害、利润损失、收入损失、业务损失、数据损失负责，除非损失是因本网站违反此服务条款所致。 本网站不对任何因网络故障、网络中断或网络延迟，以及其它任何因无法正常访问本网站而导致的损失负责。 如果本网站的内容中包括由第三方提供的其它网站链接，这些链接仅用于提供信息，我们无法对其内容负责， 本网站不对因使用其内容而导致的损失负责。 如果出现诈骗或是其他违法行为，本网站将向有关监管部门提供所有必需的用户信息。 当监管部门要求调查诈骗或其他非法行为时，用户的帐户可能会被冻结。投资数据网保留一切解释权。 </p>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- content end --> 

<!-- footer start -->
<div class="footer">
  <div class="wrap">
    <p>Copyright &copy; 2016 投资数据网 &nbsp;京ICP证160506号&nbsp;<a href="" title="雪球" target="_blank">雪球</a> &nbsp; <a href="" title="巨潮资讯网" target="_blank">巨潮资讯网</a></p>
  </div>
</div>
<!-- footer end -->
</body>
</html>