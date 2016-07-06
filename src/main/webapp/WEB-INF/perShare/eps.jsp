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
<title>投资数据网 - 每股收益</title>
<meta name="viewport" content="width=1010" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="css/basic.css" type="text/css" rel="stylesheet" />
<link href="css/list.css" type="text/css" rel="stylesheet" />
<link href="css/login.css" type="text/css" rel="stylesheet" />
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
	<div class="tag" style="width:1300px; height:90px;position:relative;border:2px solid #fdd304; margin:10 auto; padding:10px;">
		每股收益：<br>
		&nbsp;&nbsp;&nbsp;&nbsp;EPS(每股盈余)=盈余/总股本，传统的每股收益指标计算公式为：每股收益=期末净利润÷期末总股本。EPS为公司获利能力的最后结果。每股盈余高代表着公司每单位资本额的获利能力高,这表示公司具有某种较佳的能力——产品行销、技术能力、管理能力等等,使得公司可以用较少的资源创造出较高的获利。 
		企业的每股获利,通常也代表着该年度所能配发的股利。如果以股利收入的角度来选股,则可以eps作为替代变数。
	</div>
	<!-- 图标展示区 -->
	<div id="main" style="width: 1302px; height: 500px; min-height: 620px;-webkit-tap-highlight-color: transparent; -webkit-user-select: none; position: relative; background-color: transparent;margin: 0 auto;">
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
					//data : [ 2.0, 4.9, 711.0, 23.2, 125.6, 61.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3 ],
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
