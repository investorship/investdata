<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<meta charset="utf-8" />
<base href="<%=basePath%>">
<title>投资数据网 - 每股留存收益</title>
<meta name="viewport" content="width=1010" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="css/basic.css" type="text/css" rel="stylesheet" />
<link href="css/list.css" type="text/css" rel="stylesheet" />
<link href="css/login.css" type="text/css" rel="stylesheet" />
<style type="text/css">
.tag{ width:1500px; height:90px;position:relative; border:5px solid #09F;}
</style>
<jsp:include page="/WEB-INF/autocomplete.jsp" />
<script src="js/echarts.min.js"></script>
</head>
<body>
	<div class="wbyTop fn-clear">
		<c:choose>
			<c:when test="${user == null}">
				<div class="wbyLinks fn-right">
					<span><a href="login/login.action">登录</a> <a
						href="reg/reg.action">注册</a>|<a href="about_us.html">关于我们</a></span>
				</div>
			</c:when>
			<c:otherwise>
				<div class="wbyLinks fn-right">
					[${user.userName}]<span><a href="login/login!logout.action">退出</a>|<a
						href="about_us.html">关于我们</a></span>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<!-- header start -->
	<div class="header">
		<div class="wrap clearfix">
			<div class="logo" data-sudaclick="toplogo">
				<a href=""><img src="images/logo2.png" alt="新浪网导航" title="新浪网导航" /></a>
			</div>
			<div class="search" data-sudaclick="topsearch">
				<form action="stock/stock.action" method="get" target="_blank"
					id="search_f">
					<input type="text" name="keyword" class="search_k"
						placeholder="输入股票代码,名称或拼音首字母"
						onfocus="if(this.value === '请输入您要查找的股票代码'){this.value = '';}"
						onblur="if(this.value === ''){this.value = '请输入您要查找的股票代码';}"
						id="keyword" /> <input type="submit" class="search_smt"
						value="快速查找" />
				</form>
			</div>
		</div>
	</div>
	<div class="tag">
		每股留存收益：<br>
		&nbsp;&nbsp;&nbsp;&nbsp;每股留存收益= 未分配利润 + 盈余公积 / 期末总股本
	 留存收益包括企业的盈余公积和未分配利润两个部分，其中盈余公积是有特定用途的累积盈余，未分配利润是没有指定用途的累积盈余。每股未分利润可以用分现金和送股的形势分配给股东和持有此股票的投资者
	</div>
	<!-- 图标展示区 -->
	<div id="main" style="width: 1300px; height: 500px;">
	</div>
	<script type="text/javascript">
//基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));
option = {
	    title : {
	        text: '${chart.text}',
	        subtext: '${chart.subtext}',
	        textStyle: {
	        	color:'#33CCFF'
	        }
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['${chart.legendData}']
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            dataView : {show: true, readOnly: true
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar' ]
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
//			data : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月','10月', '11月', '12月' ],
			data : [${chart.xAxis}],
			nameTextStyle: {
				color:'#33CCFF'
			},
			axisLabel: {
				textStyle: {
					color: '#888888'
				}
			}
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : [
				{
					name : '${chart.legendData}',
					type : 'bar',
					barWidth: 36, //柱状图的宽度
					data : [ ${chart.data}],
					markPoint : {
						data : [ {
							type : 'max',
							name : '最大值'
						}, {
							type : 'min',
							name : '最小值'
						} ]
					},
					markLine : {
						data : [ {
							type : 'average',
							name : '平均值'
						} ]
					}
				}
				]
	};
	
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
</script>


	<div id="footer">
		<p>
			Copyright © 2016 投资数据网 &nbsp;京ICP证160506号&nbsp;<a href="" title="雪球"
				target="_blank">雪球</a> &nbsp; <a href="" title="巨潮资讯网"
				target="_blank">巨潮资讯网</a> <br> <span class="lianxi"></span>
		</p>
	</div>
</body>
</html>
