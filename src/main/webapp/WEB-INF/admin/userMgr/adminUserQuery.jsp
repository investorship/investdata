<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<style>
html,body {
	margin: 0;
	padding: 0;
	font-size: 75%;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>管理员用户信息查询</title>
<link rel="stylesheet" type="text/css" href="css/admin/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />
<script src="js/jquery-2.2.3.min.js" type="text/javascript"></script>
<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/admin/grid.locale-en.js" type="text/javascript"></script>
<script src="js/admin/jquery.jqGrid.min.js" type="text/javascript"></script>

<script type="text/javascript">
	var data = [ {
		userName : "xiao",
		email : "hello@umpay.com",
		perm_level : "1",
		flag : "1",
		intime : "2012-09-11"
	}, {
		userName : "xiaso",
		email : "hell1o@umpay.com",
		perm_level : "1",
		flag : "1",
		intime : "2013-09-11"
	} ];

	$(document).ready(function() {
		$("#jqGrid").jqGrid({
			url : 'menuMgr/adminUser!query.action',
			datatype : "json",
			colModel : [ {
				label : 'userName',
				name : 'userName',
				width : 75
			}, {
				label : 'email',
				name : 'email',
				width : 90
			}, {
				label : 'perm_level',
				name : 'perm_level',
				width : 100
			}, {
				label : 'flag',
				name : 'flag',
				width : 80,
				sorttype : 'integer'
			},
			// sorttype is used only if the data is loaded locally or loadonce is set to true
			{
				label : 'intime',
				name : 'intime',
				width : 80,
				sorttype : 'number'
			}],
			viewrecords : true, // show the current page, data rang and total records on the toolbar
			width : 780,
			height : 200,
			rowNum : 30,
			loadonce : true, // this is just for the demo
			pager : "#jqGridPager"
		});
	});

	/* $(document).ready(
	 function() {
	 jQuery("#list2").jqGrid({
	 //url:"menuMgr/adminUser!query.action",
	 datatype: "local",//前后交互的格式是json数据
	 data: data,
	 //mtype: 'GET',//交互的方式是发送httpget请求
	 colNames:['userName','email','perm_level','flag','intime'], //表格的列名
	 colModel:[
	 {name:'userName',index:'userName', width:30},//每一列的具体信息，index是索引名，当需要排序时，会传这个参数给后端
	 {name:'email',index:'email', width:65,align:"right"},
	 {name:'perm_level',index:'perm_level', width:65,align:"right"}
	 {name:'flag',index:'flag', width:65,align:"right"}
	 {name:'intime',index:'intime', width:65,align:"right"}
	 ],
	 rowNum:100,//每一页的行数
	 height: 'auto',
	 rowList:[100,200,300],
	 pager: '#pager2',
	 sortname: 'id',
	 viewrecords: true,
	 sortorder: "desc",
	 jsonReader: {//读取后端json数据的格式
	 root: "rows",//保存详细记录的名称
	 total: "total",//总共有页
	 page: "page",//当前是哪一页
	 records: "records",//总共记录数
	 repeatitems: false
	 },
	 caption: "My jqgrid test"//表格名称
	 });
	
	 jQuery("#list2").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false});

	 }); */
</script>
</head>
<body>sfdsdfdsfsfd
	<table id="jqGrid"></table>
	<div id="jqGridPager"></div>
</body>
</html>