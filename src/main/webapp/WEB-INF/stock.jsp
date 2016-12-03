<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
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
    <base href="<%=basePath%>">
    <title>投资数据网 - 上市公司财务指标历史数据查询</title>
    <meta name="viewport" content="width=1010"/>
    <meta name="keywords" content="上市公司历史财务指标查询，历史财务数据查询"/>
    <meta name="description" content="上市公司历史财务指标查询，历史财务数据查询"/>
    <link href="css/list.css" type="text/css" rel="stylesheet"/>
    <link href="css/login.css" type="text/css" rel="stylesheet"/>
    <link href="css/basic.css" type="text/css" rel="stylesheet"/>
    <jsp:include page="autocomplete.jsp" />
    <jsp:include page="/WEB-INF/baidu_statistics.jsp"/> <!-- 百度统计 -->
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
        <div class="logo" data-sudaclick="toplogo"><a href=""><img src="images/logo2.png" alt="" title=""/></a></div>
       	<jsp:include page="search_bar.jsp" />
    </div>
</div>

<div class="content">
    <div class="hotcities">
        <h2>
            <a href="javascript:void(0)">${stock.code } ${stock.name} </a>
        </h2>

        <div class="headInf">
            <ul>
                <li><span>上市时间：</span>${stock.ipoTime }</li>
                <li><span>发行量：</span>${stock.ipoStocks}（万股）</li>
                <li><span>发行市盈率：</span>${stock.issuedPE }</li>
            </ul>
            <ul>
                <li><span>发行价格：</span>${stock.issuedPrice }</li>
                <li><span>企业法人：</span>${stock.legaler}</li>
                <li><span>所属行业：</span>${stock.category }</li>
            </ul>
            <ul>
                <li><span>电话：</span>${stock.phone }</li>
                <li><span>公告查询：</span><a href="${stock.reportAddress}${stock.code}" target="_blank">巨潮资讯直达</a></li>
                <li><span>公司网址：</span>${stock.compyWebsite}</li>
            </ul>
            <ul>
                <li><span>公司地址：</span>${stock.address }</li>
            </ul>
        </div>
    </div>
    
    <div class="city_box">
        <ul class="letter-list" id="J-city-list">
        	<c:forEach items="${parentsIndexList }" var="parentsIndex">
            <li class="city-list"><span class="arrow"></span><span class="letter">${parentsIndex.name}</span>
                <ul class="cities">
                	<c:forEach items="${parentsIndex.childsFinanceIndexInfoList }" var="childFinanceIndexInfo">
	                    <li><a href="${childFinanceIndexInfo.action }?code=${stock.code }&indexName=${childFinanceIndexInfo.name}" target="_blank">${childFinanceIndexInfo.name }</a></li>
                    </c:forEach>
                </ul>
            </li>
            </c:forEach>
        </ul>
    </div>
</div>

<jsp:include page="/bottom.html" />
</body>
</html>