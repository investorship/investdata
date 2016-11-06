<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<!-- 禁止网页被搜索引擎抓取 -->
<meta name="robots" content="none" />
<title>查询资产负债数据</title>

<jsp:include page="../autocomplete_admin.jsp" />
<jsp:include page="../jquery_validate_admin.jsp" />
<jsp:include page="../dataTable.jsp" />

<script type="text/javascript">
	function load() {
		var code_val = $("#code").val();
		if(code_val == '') {
			alert('请先填写 股票代码');
			return false;
		}
		
	      $.ajax({
		        type: "post",
		        async: false,
		        url: "financeDataMgr/financeDataMgr!loadBalanceSheetData.action?loadFlag=0&code=" + code_val,
		        data: "{}",
		       // contentType: "application/json; charset=utf-8",
		       	dataType: "json",
		        success: function(data) {
	        	 	$.each(data,function(i,n){
	        	 		document.getElementById(i).innerHTML= n; 
	        	    });  
		        },
		        error: function(err) { //如果出现异常，做界面提示
		        	alert(error);
		        }
		    });
	}
	
	var index =0;
	
	function loadBalanceList() {
		index = index +1;
		
		if (index > 1) {
			$('#table_id').dataTable().fnDestroy();
		}
		
		$(document).ready( function () {
		    $('#table_id').DataTable(
		    		{
		    			"sPaginationType": "full_numbers", //分页风格，full_number会把所有页码显示出来
		    			"iDisplayLength": 7,//每页显示10条数据
		                "bAutoWidth": true,//宽度是否自动，感觉不好使的时候关掉试试
		          
		                "oLanguage": {//下面是一些汉语翻译
		                    "sSearch": "模糊查询",
		                    "sLengthMenu": "每页显示 _MENU_ 条记录",
		                    "sZeroRecords": "没有检索到数据",
		                    "sInfo": "显示 _START_ 至 _END_ 条 &nbsp;&nbsp;共 _TOTAL_ 条",
		                    "sInfoFiltered": "(筛选自 _MAX_ 条数据)",
		                    "sInfoEmtpy": "没有数据",
		                    "sProcessing": "正在加载数据...",
		                    "sProcessing": "<img src='<%=basePath%>images/admin/loading.gif' />", //这里是给服务器发请求后到等待时间显示的 加载gif
		                            "oPaginate":
		                            {
		                                "sFirst": "首页",
		                                "sPrevious": "前一页",
		                                "sNext": "后一页",
		                                "sLast": "末页"
		                            }
		                },
		                "processing": false, //开启读取服务器数据时显示正在加载中……特别是大数据量的时候，开启此功能比较好
		                "serverSide": true, //开启服务器模式，使用服务器端处理配置datatable
		                "ajax":{
		                	"url":"financeDataMgr/financeDataMgr!queryBalanceSheetList.action",
		                	"type": "POST",
		                	"data":{"code": $("#code").val(),"year":$("#year").val()},
		                	"dataType": 'json'
		                }, 
		                "columns": [ //这个属性下的设置会应用到所有列，按顺序没有是空
		                               {"data": 'code'},
		                               {"data": 'year'}, //data 表示发请求时候本列的列名，返回的数据中相同下标名字的数据会填充到这一列
		                               {"data": 'noteRecable'},
		                               {"data": 'advCustomers'},
		                               {"data": 'accPayable'},
		                               {"data": 'constrInPro'},
		                               {"data": 'lntangAssets'},
		                               {"data": 'lntangAssetsAmortize'},
		                               {"data": 'goodWill'},
		                               {"data": 'shortTermLoans'},
		                               {"data": 'notePayable'},
		                               {"data": 'debitWithinYear'},
		                               {"data": 'longTermLoans'},
		                               {"data": 'boundsPayable'},
		                               {"data": 'longAccPayable'},
		                               {"data": 'liquidAssetsStart'},
		                               {"data": 'liquidAssetsEnd'},
		                               {"data": 'currLiab'},
		                               {"data": 'currLiabNon'},
		                               {"data": 'goodsStart'},
		                               {"data": 'goodsEnd'},
		                               {"data": 'cash'},
		                               {"data": 'tradAssets'},
		                               {"data": 'totalLiabStart'},
		                               {"data": 'totalLiabEnd'},
		                               {"data": 'totalAssStart'},
		                               {"data": 'totalAssEnd'},
		                               {"data": 'shareHolderStart'},
		                               {"data": 'shareHolderEnd'},
		                               {"data": 'fixedAssetsStart'},
		                               {"data": 'fixedAssetsEnd'},
		                               {"data": 'retainEarnings'},
		                               {"data": 'accRecableStart'},
		                               {"data": 'accRecableEnd'},
		                               {"data": 'capitalSurplus'},
		                               {"data": 'surplusReserve'},
		                               {"data": 'flag'},
		                               {"data": 'inTime'},
		                               {"data": 'modUser'}
		                ]
		    		}
		    );
		} );
		
	}
	
	
</script>

</head>

<body> 
<span style="font-weight:bold;font-size:7px;color:#ff0000">
			管理菜单 >> 财务数据管理   >> 资产负债表  >> 资产负债表数据查询
	</span>
	<hr />
	<font size="5">当前股票名称：[<label id="stockName"></label>]</font>
	<br /><br />
	<form id="balForm" method="post" action="financeDataMgr/financeDataMgr!addBalance.action" onsubmit="return balFormValid()">
		<fieldset>
			<legend>请输入以下条件查询</font></legend>
			<label>股票代码 <font color="red">*</font><input name="balanceSheet.code" type="text" id="code" size="25" /><input type="button" value="加载" onclick="javascript:load()"/>
			</label> <label>数据年份 <input name="balanceSheet.year" type="text" id="year"
				size="25" /><input type="button" value="查询" onclick="javascript:loadBalanceList()"/>
		</fieldset>
	</form>
	
	<!-- 数据列表 -->
	<div style="width:5000; height:2000; overflow:scroll;"><br>
	<font size="2">
		<table id="table_id" class="display" width="5000">
		    <thead>
		    	<tr>
			    	<th >股票代码</th>
			    	<th>数据年份</th>
			    	<th>应收票据</th>
			    	<th>预收账款</th>
					<th>应付账款</th>
					<th>在建工程</th>
					<th>无形资产</th>
					<th>无形资产摊销</th>
					<th>商誉</th>
					<th>短期借款</th>
					<th>应付票据</th>
					<th>一年内到期的非流动负债</th>
					<th>长期借款</th>
					 <th>应付债券</th>
					<th>长期应付款</th>
					<th>期初流动资产</th>
					<th>期末流动资产</th>
					<th>流动负债</th>
					<th>非流动负债</th>
					<th>期初存货</th>
					<th>期末存货</th>
					<th>货币资金</th>
					<th>交易性金融资产</th>
					<th>期初负债总额</th>
					<th>期末负债总额</th>
					<th>期初资产总额</th>
					<th>期末资产总额</th>
					<th>期初股东权益</th>
					<th>期末股东权益</th>
					<th>期初固定资产</th>
					<th>期末固定资产</th>
					<th>未分配利润</th>
					<th>期初应收账款</th>
					<th>期末应收账款</th>
					<th>资本公积</th>
					<th>盈余公积</th>
					<th>启用标志</th>
					<th>入库时间</th>
					<th>操作人</th>
		    	</tr>
		    </thead>
		</table>
		</font>
	</div>
	
</body>
</html>
