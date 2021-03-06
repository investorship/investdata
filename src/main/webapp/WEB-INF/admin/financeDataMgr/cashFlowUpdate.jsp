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
<title>现金流量表数据修改</title>
<jsp:include page="../autocomplete_admin.jsp" />
<jsp:include page="../jquery_validate_admin.jsp" />

<script type="text/javascript">
function load() {
	var code_val = $("#code").val();
	var year_val = $("#year").val();
	
	if(code_val == '' || year_val == '') {
		alert('请先填写 股票代码、数据年份');
		return false;
	}
	
	
  $.ajax({
        type: "post",
        async: false,
        url: "financeDataMgr/financeDataMgr!loadCashFlowSheetData.action?code=" + code_val + "&year=" + year_val,
        data: "{}",
       // contentType: "application/json; charset=utf-8",
       	dataType: "json",
        success: function(data) {
    	 	$.each(data,function(i,n){
    	 		if (i == 'stockName') { //给Label标签赋值
    	 			document.getElementById(i).innerHTML= n;     	 			
    	 		} else if (i== 'flag') { //给单选按钮赋值
    	 			$("input:radio[value='" + n +"']").attr('checked','true');
    	 		} else { //给input域赋值。
    	 			$("#" + i).val(n);
    	 		}
    	    });  
        },
        error: function(err) { //如果出现异常，做界面提示
        	alert(error);
        }
    });
}
</script>


</head>

<body>
	<span style="font-weight: bold; font-size: 7px; color: #ff0000">
		管理菜单 >> 财务数据管理 >> 现金流量表 >> 现金流量表数据修改 </span>
	<hr />
	<font size="5">当前股票名称：[<label id="stockName"></label>]
	</font>
	<br /><br />
	<form id="cashFlowForm" name="cashFlowForm" method="post"
		action="financeDataMgr/financeDataMgr!updateCashFlow.action">
		<fieldset>
			<legend>
				请根据年度财报报表输入 <font color="red">单位:元</font>
			</legend><br />
			<label>股票代码 <input name="cashFlowSheet.code" type="text"
				id="code" />
			</label> 
			<label>数据年份 <input name="cashFlowSheet.year" type="text"
				id="year" />
			</label><input type="button" value="加载" onclick="javascript:load()"/>
			<p>
				<label>经营活动产生的现金流量净额 <input
					name="cashFlowSheet.operaActiveCash" type="text"
					id="operaActiveCash" />
				</label> <label>现金及现金等价物增加额 <input
					name="cashFlowSheet.cashAndCashequ" type="text" id="cashAndCashequ" />
				</label>
			</p>
			<p>
				状态 <label> <input name="cashFlowSheet.flag" type="radio"
					value="1" checked="checked" /> 启用
				</label> <label> <input type="radio" name="cashFlowSheet.flag"
					value="0" /> 停用
				</label>
			</p>
			<p>
				<label>
					<div align="center">
						<input type="submit" name="Submit" value="提交" onclick="javascript:cashFlowFormValid()"/>
					</div>
				</label>
			</p>
		</fieldset>
	</form>
</body>
</html>
