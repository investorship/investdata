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
<title>管理员用户管理</title>
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="dataTables/css/dataTables.jqueryui.css" />
<script src="js/jquery-2.2.3.min.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf8" src="dataTables/js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf8" src="dataTables/js/dataTables.jqueryui.js"></script>
<script type="text/javascript">
	$(document).ready( function () {
	    $('#table_id').DataTable(
	    		{
	    			"sPaginationType": "full_numbers", //分页风格，full_number会把所有页码显示出来
	    			"iDisplayLength": 10,//每页显示10条数据
	                "bAutoWidth": false,//宽度是否自动，感觉不好使的时候关掉试试
	                "oLanguage": {//下面是一些汉语翻译
	                    "sSearch": "模糊查询",
	                    "sLengthMenu": "每页显示 _MENU_ 条记录",
	                    "sZeroRecords": "没有检索到数据",
	                    "sInfo": "显示 _START_ 至 _END_ 条 &nbsp;&nbsp;共 _TOTAL_ 条",
	                    "sInfoFiltered": "(筛选自 _MAX_ 条数据)",
	                    "sInfoEmtpy": "没有数据",
	                    "sProcessing": "正在加载数据...",
	                    "sProcessing": "<img src='images/admin/loading.gif' />", //这里是给服务器发请求后到等待时间显示的 加载gif
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
	                	"url":"adminUserMgr/adminUserMgr!query.action",
	                	"dataType": 'json'
	                }, 
	                "columns": [ //这个属性下的设置会应用到所有列，按顺序没有是空
	                               {"data": 'userName'}, //data 表示发请求时候本列的列名，返回的数据中相同下标名字的数据会填充到这一列
	                               {"data": 'email'},
	                               {"data": 'permLevel'},
	                               {"data": 'flag'},
	                               {"data": 'inTime'}
	                ],
	                "order": [
	                          [1, "asc"]
	                 ],
	                "columnDefs":[
								{
									"targets": [2], // 目标列位置，下标从0开始
								    "data": "permLevel", // 数据列名
								    "render": function(data, type, full) { // 返回自定义内容
								  	  		if (data == 1)  return "录入员";
								    		if (data == 2) return "审核员";
								  		 
								     }
								},
	                              {
	                            	  "targets": [3], // 目标列位置，下标从0开始
	                                  "data": "flag", // 数据列名
	                                  "render": function(data, type, full) { // 返回自定义内容
	                                	  		if (data == 0)  return "停用";
	                                  			if (data == 1) return "启用";
	                                		 
	                                   }
	                              }
	                ]
	    		}
	    );
	} );
</script>
</head>
<body>
	<span style="font-weight:bold;font-size:7px">
			管理菜单 >> 管理员用户管理   >> 管理员用户查询
	</span>
	<div><br>
		<font size="2">
		<table id="table_id" class="display">
		    <thead>
		    	<th >用户名</th>
		    	<th>邮箱</th>
		    	<th>权限等级</th>
		    	<th>状态</th>
		    	<th>注册时间</th>
		    </thead>
		</table>
		</font>
	</div>
		</span>
</body>
</html>