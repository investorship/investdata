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
<title>投资数据网 - 历史每股净资产走势图</title>
<meta name="viewport" content="width=1010" />
<meta name="keywords" content="历史每股净资产，历年每股净资产查询" />
<meta name="description" content="上市公司每股净资产，上市公司每股净资产走势图" />
<link href="css/basic.css" type="text/css" rel="stylesheet" />
<link href="css/list.css" type="text/css" rel="stylesheet" />
<link href="css/login.css" type="text/css" rel="stylesheet" />
<style type="text/css">
.report_tip {
	width:1300px; 
	height:95px;
	position:relative;
	border:2px solid #FFBB00; 
	margin:10 auto; 
	padding:10px; 
	background-color: #FFFF77
}
</style>
<jsp:include page="/WEB-INF/autocomplete.jsp" />
<script src="js/echarts.min.js"></script>
<jsp:include page="/WEB-INF/baidu_statistics.jsp"/> <!-- 百度统计 -->
</head>
<body>
	<div class="wbyTop fn-clear">
		<c:choose>
			<c:when test="${user == null}">
				<div class="wbyLinks fn-right">
					<span><a href="login/login.action">登录</a> <a
						href="reg/reg.action">注册</a>|<a href="about_us.jsp">关于我们</a></span>
				</div>
			</c:when>
			<c:otherwise>
				<div class="wbyLinks fn-right">
					[${user.userName}]<span><a href="login/login!logout.action">退出</a>|<a
						href="about_us.jsp">关于我们</a></span>
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
			<jsp:include page="../search_bar.jsp" />
		</div>
	</div>
	<div class="report_tip">
		<span style="font-weight:bold;">指标名称：</span>每股净资产<br>
		<span style="font-weight:bold;">计算公式：</span>净资产/ 总股本<br>
		<span style="font-weight:bold;">说明：</span><br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这一指标反映每股股票所拥有的资产现值。每股净资产越高，股东拥有的资产现值越多；每股净资产越少，股东拥有的资产现值越少。通常每股净资产越高越好。
		在基本分析（Fundamental Analysis ）的各种指标中，每股净资产是判断企业内在价值最重要的参考指标之一。
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
<jsp:include page="/bottom.html" />
</body>
</html>
