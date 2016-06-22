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
<title>Insert title here</title>
<script src="js/echarts.min.js"></script>
</head>
<body>
<div id="main" style="width: 1000px;height:700px;"></div>
<script type="text/javascript">
//基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));
option = {
	    title : {
	        text: '某地区蒸发量',
	        subtext: '纯属虚构',
	        textStyle: {
	        	color:'#33CCFF'
	        }
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['蒸发量','降水量']
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
			data : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月','10月', '11月', '12月' ],
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
					name : '蒸发量',
					type : 'bar',
					data : [ 2.0, 4.9, 711.0, 23.2, 125.6, 61.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3 ],
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
</body>
</html>