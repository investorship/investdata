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
<title>利润表数据修改</title>

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
        url: "financeDataMgr/financeDataMgr!loadIncstateSheetData.action?code=" + code_val + "&year=" + year_val,
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
		管理菜单 >> 财务数据管理 >> 利润表 >> 利润表数据修改 </span>
	<hr />
	<font size="5">当前股票名称：[<label id="stockName"></label>]</font>
	<br />
	<br />
	<form id="incstateForm" name="incstateForm" method="post"
		action="financeDataMgr/financeDataMgr!updateIncstate.action">
		<fieldset>
			<legend>
				请根据年度财报报表输入 <font color="red">单位:元</font>
			</legend>
			<p>
				<label>股票代码 <input name="incstateSheet.code" type="text"
					id="code" />
				</label>  <label>数据年份&nbsp; <input name="incstateSheet.year"
					type="text" id="year" /><input type="button" value="加载" onclick="javascript:load()"/>
				</label> <label>本期营业收入 <input name="incstateSheet.busiIncomeThis"
					type="text" id="busiIncomeThis" />
				</label> <label>上期营业收入 <input name="incstateSheet.busiIncomeLast"
					type="text" id="busiIncomeLast" />
				</label>
			</p>

			<p>
				<label>本期营业总收入 <input name="incstateSheet.totalBusiIncThis"
					type="text" id="totalBusiIncThis" />
				</label> <label>上期营业总收入 <input name="incstateSheet.totalBusiIncLast"
					type="text" id="totalBusiIncLast" />
				</label> <label>本期营业利润&nbsp; <input
					name="incstateSheet.operaProfitsThis" type="text"
					id="operaProfitsThis" />
				</label> <label>上期营业利润 <input type="text"
					name="incstateSheet.operaProfitsLast" id="operaProfitsLast"/>
				</label>
			</p>

			<p>

				<label>所得税&nbsp; <input name="incstateSheet.incomeTax"
					type="text" id="incomeTax" />
				</label> <label>固定资产折旧 <input name="incstateSheet.fixedAssDepre"
					type="text" id="fixedAssDepre" />
				</label> <label>期初利润总额 <input name="incstateSheet.totalProfitStart"
					type="text" id="totalProfitStart" />
				</label> <label>期末利润总额 <input name="incstateSheet.totalProfitEnd"
					type="text" id="totalProfitEnd" />
				</label>
			</p>


			<p>
				<label>本期销售费用 <input name="incstateSheet.marketConstsThis"
					type="text" id="marketConstsThis" />
				</label> <label>上期销售费用 <input name="incstateSheet.marketConstsLast"
					type="text" id="marketConstsLast" />
				</label> <label>本期财务费用 <input name="incstateSheet.financeConstsThis"
					type="text" id="financeConstsThis" />
				</label> <label>上期财务费用 <input name="incstateSheet.financeConstsLast"
					type="text" id="financeConstsLast" />
				</label>
			</p>
			<p>
				<label>本期管理费用 <input name="incstateSheet.mgrConstsThis"
					type="text" id="mgrConstsThis" />
				</label> <label>上期管理费用 <input name="incstateSheet.mgrConstsLast"
					type="text" id="mgrConstsLast" />
				</label> <label>长期待摊费用摊销<input name="incstateSheet.longPreAmort"
					type="text" id="longPreAmort" />
				</label> <label>财务费用（利息支出） <input name="incstateSheet.interExpense"
					type="text" id="interExpense" />
				</label>
			</p>

			<p>
				<label>本期净利润 <input name="incstateSheet.netProfitsThis"
					type="text" id="netProfitsThis" />
				</label> <label>上期净利润&nbsp; <input
					name="incstateSheet.netProfitsLast" type="text" id="netProfitsLast" />
					<label> 本期净利润（扣非） <input
						name="incstateSheet.netProfitsKfThis" type="text"
						id="netProfitsKfThis" />
				</label> <label>上期净利润（扣非） <input
						name="incstateSheet.netProfitsKfLast" type="text"
						id="netProfitsKfLast" /></label>
			</p>

			<p>
				</label> <label> 公允价值变动 <input name="incstateSheet.fairValChange"
					type="text" id="fairValChange" />
				</label> <label>营业税金及附加 <input name="incstateSheet.busiTaxSurcharge"
					type="text" id="busiTaxSurcharge" />
				</label> <label> 营业收入（扣非） <input name="incstateSheet.busiIncomeKf"
					type="text" id="busiIncomeKf" /></label> <label>营业成本 <input
					name="incstateSheet.operatCost" type="text" id="operatCost" />
				</label> <label>投资收益 <input name="incstateSheet.investIncome"
					type="text" id="investIncome" /></label>
			</p>

			<p>
				<label></label> <label>营业外收入 <input
					name="incstateSheet.nonOperaIncome" type="text" id="nonOperaIncome" />
				</label> 营业外支出 <input name="incstateSheet.nonOperaOutcome" type="text"
					id="nonOperaOutcome" />

			</p>

			<p>
				状态 <label> <input name="incstateSheet.flag" type="radio"
					value="1" checked="checked" /> 启用
				</label> <label> <input type="radio" name="incstateSheet.flag"
					value="0" /> 停用
				</label>
			</p>
			<p>&nbsp;</p>
			<p>
				<label>
					<div align="center">
						<div align="center">
							<input type="submit" name="Submit" value="提交" onclick="javascript:incstateFormValid()" />
						</div>
				</label>
		</fieldset>
	</form>
</body>
</html>
