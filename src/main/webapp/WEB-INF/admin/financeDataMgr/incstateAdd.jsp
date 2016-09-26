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
<title>利润表数据新增</title>
</head>

<body>
<span style="font-weight: bold; font-size: 7px; color: #ff0000">
		管理菜单 >> 财务数据管理 >> 利润表 >> 利润表数据新增 </span>
	<hr />
	<br />
	<form id="incstateForm" name="incstateForm" method="post" action="">
		<label>股票代码 <input name="code" type="text" id="code" />
		</label> <label>数据年份&nbsp; <input name="year" type="text" id="year" />
		</label> <label>本期营业收入 <input name="busiIncomeThis" type="text"
			id="busiIncomeThis" />
		</label> <label>上期营业收入 <input name="busiIncomeLast" type="text"
			id="busiIncomeLast" />
		</label>
		<p>
			<label>所得税&nbsp; <input name="incomeTax" type="text"
				id="incomeTax" />
			</label> <label>本期净利润 <input name="netProfitsThis" type="text"
				id="netProfitsThis" />
			</label> <label>上期净利润&nbsp; <input name="netProfitsLast" type="text"
				id="netProfitsLast" />
			</label> <label>上期营业利润 <input type="text" name="operaProfitsLast" />
			</label>
		</p>
		<p>
			<label>本期营业总收入 <input name="totalBusiIncThis" type="text"
				id="totalBusiIncThis" />
			</label> <label>上期营业总收入 <input name="totalBusiIncLast" type="text"
				id="totalBusiIncLast" />
			</label> <label>营业税金及附加 <input name="busiTaxSurcharge" type="text"
				id="busiTaxSurcharge" />
			</label>
		</p>
		<p>
			<label>本期营业利润&nbsp; <input name="operaProfitsThis"
				type="text" id="operaProfitsThis" />
			</label> <label>上期营业利润&nbsp; <input name="operaProfitsLast"
				type="text" id="operaProfitsLast" />
			</label> <label>财务费用（利息支出） <input name="interExpense" type="text"
				id="interExpense" />
			</label>
		</p>
		<p>
			<label>固定资产折旧 <input name="fixedAssDepre" type="text"
				id="fixedAssDepre" />
			</label> <label>期初利润总额 <input name="totalProfitStart" type="text"
				id="totalProfitStart" />
			</label> <label>期末利润总额 <input name="totalProfitEnd" type="text"
				id="totalProfitEnd" />
			</label>
		</p>
		<p>
			<label>本期销售费用 <input name="marketConstsThis" type="text"
				id="marketConstsThis" />
			</label> <label>上期销售费用 <input name="marketConstsLast" type="text"
				id="marketConstsLast" />
			</label> <label>本期财务费用 <input name="financeConstsThis" type="text"
				id="financeConstsThis" />
			</label>
		</p>
		<p>
			<label>上期财务费用 <input name="financeConstsThis" type="text"
				id="financeConstsThis" />
			</label> <label>本期管理费用 <input name="mgrConstsThis" type="text"
				id="mgrConstsThis" />
			</label> <label>上期管理费用 <input name="mgrConstsLast" type="text"
				id="mgrConstsLast" />
			</label>
		</p>
		<p>
			<label></label> 本期净利润（扣非） <input name="netProfitsKfThis" type="text"
				id="netProfitsKfThis" /> 上期净利润（扣非） <input name="netProfitsKfLast"
				type="text" id="netProfitsKfLast" /> 营业收入（扣非） <input
				name="busiIncomeKf" type="text" id="busiIncomeKf" />
		</p>
		<p>
			<label></label> <label></label> <label>营业成本 <input
				name="operatCost" type="text" id="operatCost" />
			</label> 公允价值变动 <input name="fairValChange" type="text" id="fairValChange" />
			投资收益 <input name="investIncome" type="text" id="investIncome" />
		</p>
		<p>
			<label></label> <label></label> <label>营业外收入 <input
				name="nonOperaIncome" type="text" id="nonOperaIncome" />
			</label> 营业外支出 <input name="nonOperaOutcome" type="text" id="nonOperaOutcome" />
			长期待摊费用摊销 <input name="longPreAmort" type="text" id="longPreAmort" />
		</p>
		<p>
			<label></label> <label></label> <label></label>
		</p>
		<p>
			状态 <label> <input name="flag" type="radio" value="1"
				checked="checked" /> 启用
			</label> <label> <input type="radio" name="flag" value="0" /> 停用
			</label>
		</p>
		<p>&nbsp;</p>
		<p>
			<label>
				<div align="center">
					<div align="center">
						<input type="submit" name="Submit" value="提交" />
					</div>
			</label>
	</form>
</body>
</html>
