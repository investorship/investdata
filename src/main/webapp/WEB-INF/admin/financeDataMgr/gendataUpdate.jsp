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
<title>综合数据表数据修改</title>

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
        url: "financeDataMgr/financeDataMgr!loadGendataSheetData.action?code=" + code_val + "&year=" + year_val,
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
		管理菜单 >> 财务数据管理 >> 综合数据表 >> 综合数据表数据修改 </span>
	<hr />
	<font size="5">当前股票名称：[<label id="stockName"></label>]
	</font> <br />
	<br />
	<form id="gendataForm" name="gendataForm" method="post"
		action="financeDataMgr/financeDataMgr!updateGendata.action">
		<fieldset>
			<legend>
				请根据年度财报报表输入 <font color="red">单位:元</font>
			</legend>
			<br /> <label>股票代码 <input type="text"
				name="genDataSheet.code" id="code" />
			</label> <label>数据年份 <input type="text" name="genDataSheet.year" id="year"/>
			</label><input type="button" value="加载" onclick="javascript:load()" /> <label>总股本
				<input type="text" name="genDataSheet.totalStocks" id="totalStocks"/>
			</label>
			<p>
				<label>加权平均净资产收益率 <input type="text"
					name="genDataSheet.roeWa" id="roeWa"/>
				</label> <label></label> 加权平均净资产收益率(扣非) <input type="text"
					name="genDataSheet.roeWaKf"  id="roeWaKf"/> <label>本年度发放的现金股利总和 <input
					type="text" name="genDataSheet.dividenPaySum" id="dividenPaySum"/>
				</label>
			</p>
			<p>
				</label> <label>基本每股收益 <input type="text" name="genDataSheet.eps" id="eps"/>
				</label> <label> 稀释每股收益</label> <input type="text"
					name="genDataSheet.epsDiluted" id="epsDiluted"/>  <br/> <br/>
					状态 <inputtype="radio" name="genDataSheet.flag" value="1" checked="checked" id="flag" />
					启用 <input type="radio" name="genDataSheet.flag" value="0" id="flag"/> 停用 
			</p>
			<p>&nbsp;</p>
			<p>
				<label>
					<div align="center">
						<div align="center">
							<input type="submit" name="Submit" value="提交"
								onclick="javascript:gendataFormValid()" />
						</div>
				</label>
		</fieldset>
	</form>
</body>
</html>
