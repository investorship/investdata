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
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>管理员用户信息查询</title>
<link rel="stylesheet" type="text/css" href="css/admin/jquery-ui.theme.css" />
<link rel="stylesheet" type="text/css" href="css/admin/ui.jqgrid.css" />
<style>
html, body {
    margin: 0;
    padding: 0;
    font-size: 75%;
}
</style>
<script src="js/jquery-2.2.3.min.js" type="text/javascript"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.js"></script>
<script src="js/admin/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/admin/grid.locale-en.js" type="text/javascript"></script>

<script type="text/javascript">
$(document).ready(
		   function() {
		jQuery("#list4").jqGrid({
		datatype: "local",
		height: 250,
		    colNames:['Inv No','Date', 'Client', 'Amount','Tax','Total','Notes'],
		    colModel:[
		    {name:'id',index:'id', width:60, sorttype:"int"},
		    {name:'invdate',index:'invdate', width:90, sorttype:"date"},
		    {name:'name',index:'name', width:100},
		    {name:'amount',index:'amount', width:80, align:"right",sorttype:"float"},
		    {name:'tax',index:'tax', width:80, align:"right",sorttype:"float"},
		    {name:'total',index:'total', width:80,align:"right",sorttype:"float"},
		    {name:'note',index:'note', width:150, sortable:false}
		    ],
		    multiselect: true,
		    caption: "Manipulating Array Data"
		  });
		var mydata = [
		{id:"1",invdate:"2007-10-01",name:"test",note:"note",amount:"200.00",tax:"10.00",total:"210.00"},
		{id:"2",invdate:"2007-10-02",name:"test2",note:"note2",amount:"300.00",tax:"20.00",total:"320.00"},
		{id:"3",invdate:"2007-09-01",name:"test3",note:"note3",amount:"400.00",tax:"30.00",total:"430.00"},
		{id:"4",invdate:"2007-10-04",name:"test",note:"note",amount:"200.00",tax:"10.00",total:"210.00"},
		{id:"5",invdate:"2007-10-05",name:"test2",note:"note2",amount:"300.00",tax:"20.00",total:"320.00"},
		{id:"6",invdate:"2007-09-06",name:"test3",note:"note3",amount:"400.00",tax:"30.00",total:"430.00"},
		{id:"7",invdate:"2007-10-04",name:"test",note:"note",amount:"200.00",tax:"10.00",total:"210.00"},
		{id:"8",invdate:"2007-10-03",name:"test2",note:"note2",amount:"300.00",tax:"20.00",total:"320.00"},
		{id:"9",invdate:"2007-09-01",name:"test3",note:"note3",amount:"400.00",tax:"30.00",total:"430.00"}
		];
		
		for(var i=0;i<=mydata.length;i++)
		jQuery("#list4").jqGrid('addRowData',i+1,mydata[i]);
		});
		
		</script>
		</head>
		<body>
		<table id="list4"></table>
		</body>
		</html>