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
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册用户管理</title>
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="dataTables/css/dataTables.jqueryui.css" />
<script src="js/jquery-2.2.3.min.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf8" src="dataTables/js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf8" src="dataTables/js/dataTables.jqueryui.js"></script>
<script type="text/javascript">
	$(document).ready( function () {
	    $('#table_id').DataTable(
	    		{
	    			"dom":"lrtip",
	    			"sPaginationType": "full_numbers", //分页风格，full_number会把所有页码显示出来
	    			"iDisplayLength": 10,//每页显示10条数据
	                "bAutoWidth": false,//宽度是否自动，感觉不好使的时候关掉试试
	                "oLanguage": {//下面是一些汉语翻译
	                    "sSearch": "搜索",
	                    "sLengthMenu": "每页显示 _MENU_ 条记录",
	                    "sZeroRecords": "没有检索到数据",
	                    "sInfo": "显示 _START_ 至 _END_ 条 &nbsp;&nbsp;共 _TOTAL_ 条",
	                    "sInfoFiltered": "(筛选自 _MAX_ 条数据)",
	                    "sInfoEmtpy": "没有数据",
	                    "sProcessing": "正在加载数据...",
	                    "sProcessing": "<img src='{{rootUrl}}global/img/ajaxLoader/loader01.gif' />", //这里是给服务器发请求后到等待时间显示的 加载gif
	                            "oPaginate":
	                            {
	                                "sFirst": "首页",
	                                "sPrevious": "前一页",
	                                "sNext": "后一页",
	                                "sLast": "末页"
	                            }
	                },
	                "processing": true, //开启读取服务器数据时显示正在加载中……特别是大数据量的时候，开启此功能比较好
	                "serverSide": true, //开启服务器模式，使用服务器端处理配置datatable
	                "ajax":{
	                	"url":"userMgr/userMgr!query.action",
	                	"dataType": 'json'
	                }, 
	                "columns": [ //这个属性下的设置会应用到所有列，按顺序没有是空
	                               {"data": 'userName'}, //data 表示发请求时候本列的列名，返回的数据中相同下标名字的数据会填充到这一列
	                               {"data": 'email'},
	                               {"data": 'isPayer'},
	                               {"data": 'payDate'},
	                               {"data": 'endDate'},
	                               {"data": 'flag'}
	                           ]
	    		}
	    );
	} );
</script>
</head>
<body>
	<div>导航区</div>
	<div>
		<table id="table_id" class="display">
		    <thead>
		    	<th>姓名</th>
		    	<th>邮箱</th>
		    	<th>是否付费用户</th>
		    	<th>付费日期</th>
		    	<th>截止日期</th>
		    	<th>状态</th>
		    </thead>
		</table>
	</div>
</body>
</html>