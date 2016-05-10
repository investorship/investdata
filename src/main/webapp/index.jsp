<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>投资数据网</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta name="document-state" content="dynamic"/>
<link href="css/i.css" type="text/css" rel="stylesheet"/>
<link href="css/s.css" type="text/css" rel="stylesheet"/>
<link href="css/jquery.autocomplete.css" type="text/css" rel="stylesheet"/>
<script language="javascript" src="js/jquery-2.2.3.min.js"></script>
<script language="javascript" src="js/jquery.autocomplete.min.js"></script>
<script type="text/javascript">
$( function () {
               var  data  =   " Core Selectors Attributes Traversing Manipulation CSS Events Effects Ajax Utilities " .split( "   " );
  
             $( ' #keyword ' ).autocomplete(data).result( function (event, data, formatted) {
                  alert(data);
             });
         });
</script>
</head>
<body id="home">
<div class="wbyTop fn-clear">
  <div class="wbyLinks fn-right"><span><a target="_blank" href="login.jsp">登陆</a> <a target="_blank" href="reg.jsp">注册</a>|<a href="">关于我们</a></span></div>
</div>
<div id="wrapper" style="margin-bottom:0;">
  <div id="header">
    <h1 id="logo"><img src="images/logo.png" height="80px" alt="投资数据网"/></h1>
    <div id="sbox">
      <form name="btform" action="">
        <input type="text" baiduSug="2" placeholder="股票代码" id="keyword" name="q" class="stbox"/>
        <input type="submit" onmouseout="this.className=''" onmousedown="this.className='mousedown'" onmouseover="this.className='hover'" value="搜索" id="sbutton"/>
      </form>
    </div>
    <div id="nums" style="font-size: 17px;">本站共索引 <span>7524458</span> 条磁力链资源！</div>
    <div id="index_ad_top"></div>
  </div>
  <div id="container">
    <div class="main">
      <div class="hotwords">
        <div class="hwtit">
          <h4>今日热搜</h4>
          <span><a href="" title="今日热搜">更多&gt;&gt;</a></span></div>
        <ul class="hwentry">       
   
          <li><a class="HotKey" href="" target="_blank">每股收益</a></li>
          <li><a class="HotKey" href="" target="_blank">稀释每股收益</a></li>
          <li><a class="HotKey" href="" target="_blank">流动比率</a></li>
          <li><a class="HotKey" href="" target="_blank">速动比率</a></li>
          <li><a class="HotKey" href="" target="_blank">现金比率</a></li>
          <li><a class="HotKey" href="" target="_blank">产权比率</a></li>
          <li><a class="HotKey" href="" target="_blank">扣非净利润</a></li>
          <li><a class="HotKey" href="" target="_blank">固定资产比率</a></li>
          <li><a class="HotKey" href="" target="_blank">无形资产比率</a></li>
          <li><a class="HotKey" href="" target="_blank">总资金周转率</a></li>
          <li><a class="HotKey" href="" target="_blank">利润增长率</a></li>
          <li><a class="HotKey" href="" target="_blank">运营资金</a></li> 
        </ul>
      </div>
    </div>
  </div>
  <div id="ad_bottom_index" style="text-align:center;margin:auto"> </div>
</div>
<div id="footer">
  <p>Copyright &copy; 2016 投资数据网 &nbsp;京ICP证160506号&nbsp;<a href="" title="雪球" target="_blank">雪球</a> &nbsp; <a href="" title="巨潮资讯网" target="_blank">巨潮资讯网</a> <br>
    <span class="lianxi"></span> </p>
</div>
</body>
</html>