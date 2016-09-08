<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>投资数据网 - 关于我们</title>
    <meta name="viewport" content="width=1010"/>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <link href="css/basic.css" type="text/css" rel="stylesheet"/>
    <link href="css/about_us.css" type="text/css" rel="stylesheet"/>
</head>
<body style="background:#fff;">

<!-- header start -->
<div class="header">
    <div class="wrap clearfix">
        <div class="logo" data-sudaclick="toplogo"><a href="index.action"><img src="images/logo2.png" alt="" title=""/></a></div>
    </div>
</div>
<div class="reg_container">
    <div class="banner">
        <h1 class="ad">欢迎来到投资数据网</h1>
    </div>
    <div class="info">
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎使用投资数据网提供的数据来辅助您进行相关的投资分析。投资数据网是由一群投资爱好者在2016年建立的，在最初的投资过程中，我们发现在对股票或者债券投资之前需要进行详尽的公司基本面
  分析，在整个过程中对于企业财务的分析是不可或缺的。而整个财务报表年报上百页的内容中，如何才能把握住相关的分析重点并且能进行最直观的展示呢? 基于此想法，我们创建了投资数据网(www.investdata.info),本质上是想为投资者提供与投资
  相关的数据，使投资者仅仅专注于分析，而不再为繁杂和寻找大量的数据而浪费时间。在网站建立前期，首先对一家企业要分析的要点进行了分类，确定哪些指标数据对投资者是有价值的，在通用的分析分类之外，又通过与多位比较出色的
  投资者进行沟通，把他们在投资过程中的关注点也纳入进来，尽可能的保证指标的完善。<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 投资数据网图表中展示的数据均来自于上市公司公布的年度财务报表，为尽可能的保证数据的准确性,所有数据通过手工录入。原始财报数据通过后台计算后，以图表的方式形象、直观的展示出来。在
每个指标的详尽界面上，有对具体指标的一个简短的介绍，以帮助初级的投资者能明白该指标的分析意义。投资者在使用数据时可以通过图表右上角的按钮来切换显示方式：可以通过柱状图、折线图或者直接通过具体的数据来展示。<br><br>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目前网站的数据只包含股票并且财务指标数据都是以年度财务报表(分为 季报、半年报，年报)为基础，后续会陆续增加其他功能，完善网站相关功能和数据，以便为广大投资者提供更多、更好的服务。
 希望如我们网站首页LOGO所言，让投资数据网成为您身边的投资手册，快速找到您所需的投资数据。<br><br>
 
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;虽然我们尽力保证数据的准确，但遗漏或者数据有误的现象仍有可能发生，希望得到您的谅解，并烦请将错误信息通过邮件提供给我们，我们将及时修正。关于网站的建设，您有任何的建议或者意见可以通过
 下面的邮箱联系我们，最后：祝大家投资顺利，早日实现财务自由！！<br><br><br>
 	
 	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;电子邮箱：investdata@126.com
 
    </div>
</div>
<jsp:include page="bottom.html" />
</body>
</html>