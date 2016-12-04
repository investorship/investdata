<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 

<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>投资数据网-上市公司历史财务数据查询</title>
<meta name="keywords" content="上市公司拟历年财务数据、财务指标走势"/>
<meta name="description" content="上市公司历史财务数据查询，历年财务数据、财务指标走势"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta name="document-state" content="dynamic"/>
<link href="css/i.css" type="text/css" rel="stylesheet" />
<jsp:include page="autocomplete.jsp" />
<script type="text/javascript">
	function checkSubmit() {
		var keyword = $("#keyword").val();
		keyword = trim(keyword);
		if (isEmpty(keyword)) {
			return false;
		} else {
			return true;
		}
	}
	
	function trim(str){
	    return str.replace(/^(\s|\u00A0)+/,'').replace(/(\s|\u00A0)+$/,'');
	}
	
	function isEmpty(str){  
	    if(str == null || typeof str == "undefined" ||   
	            str == ""){  
	        return true;  
	    }  
	    return false;  
	};
</script>
<jsp:include page="/WEB-INF/baidu_statistics.jsp"/> <!-- 百度统计 -->
</head>
<body id="home">
<div class="wbyTop fn-clear">
	<c:choose>
		<c:when test="${user == null}">
			<div class="wbyLinks fn-right"><span><a href="login/login.action">登录</a> <a href="reg/reg.action">注册</a>|<a href="about_us.jsp">关于我们</a></span></div>
		</c:when>
		<c:otherwise>
			<div class="wbyLinks fn-right">[${user.userName}]<span><a href="login/passwordOperAction.action?pwdOperFlag=1">修改密码</a><a href="login/login!logout.action">退出</a>|<a href="about_us.jsp">关于我们</a></span></div>
		</c:otherwise>
	</c:choose>
  
</div>
<!-- 根据配置动态展示
<div class="note">
    尊敬的用户：目前股票财务数据正在维护中，请您随时关注我们，给您带来的不便敬请谅解。
</div>
 -->
<div id="wrapper" style="margin-bottom:0;">
  <div id="header">
    <h1 id="logo"><img src="images/logo.png" height="80px" alt="投资数据网"/></h1>
    <div id="sbox">
      <form id="search_f" method="post" action="stock/stock.action">
        <input type="text" baiduSug="2" autocomplete="off" placeholder="输入股票代码,名称或拼音首字母" id="keyword" name="keyword" class="stbox"/>
        <input type="submit" onmouseout="this.className=''" onmousedown="this.className='mousedown'" onmouseover="this.className='hover'" value="搜索" id="sbutton" onclick="return checkSubmit()"/>
      </form>
    </div>
    <div id="index_ad_top"></div>
  </div>
  <div id="container">
    <div class="main">
    <div class="hotwords">
        <div class="hwtit">
          <h4>股票  <font color="#FF0000">搜索排行榜!</font></h4>
          <span><a href="" title="今日热搜"></a></span></div>
        	<ul class="hwentry">       
		   	<c:forEach items="${searchIndexList }" var="searchIndex">
		   		<c:choose>
		   			<c:when test="${searchIndex.code == null }">
		   				<li><a class="HotKey" href="#">${searchIndex.name }</a></li>
		   			</c:when>
		   			<c:otherwise>
		   				<li><a class="HotKey" href="stock/stock.action?keyword=${searchIndex.code }" target="_blank">${searchIndex.name }</a></li>
		   			</c:otherwise>
		   		</c:choose>
		   		
		   	</c:forEach>
        </ul>
      </div>
    	
    </div>
  </div>
  <div id="ad_bottom_index" style="text-align:center;margin:auto"> </div>
</div>
	<jsp:include page="../bottom.html" />
</body>
</html>
