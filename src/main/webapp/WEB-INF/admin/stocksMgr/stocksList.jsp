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
<title>股票信息查询</title>

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
	    			"bScrollCollapse": true,
	    			"sScrollX": "100%",
	    			"sScrollXInner": "110%",
	    			"sScrollY": "450px",
	                "bAutoWidth": true,//宽度是否自动，感觉不好使的时候关掉试试
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
	                	"url":"stocksMgr/stocksMgr!query.action",
	                	"dataType": 'json'
	                }, 
	                "columns": [ //这个属性下的设置会应用到所有列，按顺序没有是空
	                               {"data": 'code'}, //data 表示发请求时候本列的列名，返回的数据中相同下标名字的数据会填充到这一列
	                               {"data": 'name'},
	                               {"data": 'shortName'},
	                               {"data": 'market'},
	                               {"data": 'ipoTime'},
	                               {"data": 'ipoStocks'},
	                               {"data": 'category'},
	                               {"data": 'issuedPE'},
	                               {"data": 'issuedPrice'},
	                               {"data": 'address'},
	                               {"data": 'compyWebsite'},
	                               {"data": 'reportAddress'},
	                               {"data": 'phone'},
	                               {"data": 'legaler'},
	                               {"data": 'flag'},
	                               {"data": 'inTime'}
	                ],
	                "columnDefs":[
									
	                              {
	                            	  "targets": [14], // 目标列位置，下标从0开始
	                                  "data": "flag", // 数据列名
	                                  "render": function(data, type, full) { // 返回自定义内容
	                                	  		if (data == 0)  return "停用";
	                                  			if (data == 1) return "启用";
	                                		 
	                                   }
	                              },
	                              {
	                                  "targets": [16], // //增加一列,目标列位置，下标从0开始
	                                  "data": "userName", // 数据列名
	                                  "render": function(data, type, full) { // 返回自定义内容
	                                	  	
		                                  	  var flag = full["flag"];
		                                  	  if (0 == flag) {
		                                  		return "<a href='userMgr/userMgr!updateState.action?userName=" + data + "&flag=1'>启用</a>";
		                                  	  }
		                                  	  
		                                  	  if (1 == flag) {
		                                  		return "<a href='userMgr/userMgr!updateState.action?userName=" + data + "&flag=0'>停用</a>";
		                                  	  }
		                                  	  
	                              	 }
	                              }
	                ]
	    		}
	    );
	} );
</script>
</head>
<body>
	<span style="font-weight:bold;">
			管理菜单 >> 财务指标管理  >> 财务指标查询
	</span>
	<div><br>
	<font size="2">
		<table id="table_id" class="display">
		    <thead>
		    	<th width="70px">股票代码</th>
		    	<th width="70px">股票名称</th>
		    	<th width="70px">字母简写</th>
		    	<th width="70px">所属市场</th>
		    	<th width="70px">上市时间</th>
		    	<th width="70px">发行数量</th>
		    	<th width="70px">所属行业</th>
		    	<th width="80px">发行市盈率</th>
		    	<th width="70px">发行价格</th>
		    	<th width="70px">公司地址</th>
		    	<th >公司网站</th>
		    	<th >报告地址</th>
		    	<th>联系电话</th>
		    	<th>企业法人</th>
		    	<th>标志位</th>
		    	<th>入库时间</th>
		    	<th>操作</th>
		    </thead>
		</table>
		</font>
	</div>
</body>
</html>