<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<!-- 禁止网页被搜索引擎抓取 -->
<meta name="robots" content="none" />
<title>股票信息修改</title>

<jsp:include page="../autocomplete_admin.jsp" />
<jsp:include page="../jquery_validate_admin.jsp" />

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
	        url: "stocksMgr/stocksMgr!loadStockInfo.action?code=" + code_val,
	        data: "{}",
	       // contentType: "application/json; charset=utf-8",
	       	dataType: "json",
	        success: function(data) {
	    	 	$.each(data,function(i,n){
	    	 		if (i== 'flag') { //给单选按钮赋值
	    	 			$("input:radio[value='" + n +"']").attr('checked','true');
	    	 		}else {
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
	<span style="font-weight:bold;font-size:7px;color:#ff0000">
			管理菜单 >> 股票信息管理   >> 股票信息修改
	</span>
	<hr /><br />
<form id="stockForm" name="stockForm" method="post" action="stocksMgr/stocksMgr!addStock.action">
		<fieldset>
			<legend>股票信息修改</legend>
			<br /> <label>股票代码 <input name="stock.code" type="text" id="code" /><input type="button" value="加载" onclick="javascript:load()"/>
			</label> <label>股票名称 <input name="stock.name" type="text" id="name" />
			</label> <label>字母简写 <input name="stock.shortName" type="text" id="shortName" />
			</label> <label>所属市场&nbsp; <input name="stock.market" type="text" id="market" />
			</label>
			<p>&nbsp;</p>
			<p>
				<label></label> <label>上市时间 <input name="stock.ipoTime"
					type="text" id="ipoTime" />
				</label> <label>&nbsp;&nbsp;&nbsp;&nbsp;发行数量 <input name="stock.ipoStocks" type="text" id="ipoStocks" />
				</label> <label>所属行业 
				<select name="stock.category" id="category" style="width:160px;" >
					<c:forEach items="${industryCategorys }" var="idcg">
						<option value="${idcg.id }">${idcg.name}</option>
					</c:forEach>
				</select>
				
				</label> <label>发行市盈率 <input name="stock.issuedPE" type="text" id="issuedPE" />
				</label>
			</p>
			<p>&nbsp;</p>
			<p>
				<label>发行价格 <input name="stock.issuedPrice" type="text" id="issuedPrice" />
				</label> <label>&nbsp;&nbsp;&nbsp;&nbsp;企业法人 <input name="stock.legaler" type="text" id="legaler" />
				</label> <label>联系电话 <input name="stock.phone" type="text" id="phone" />
				</label> 
			</p>
			<p>&nbsp;</p>
			<p>
				<label>公司地址 <input name="stock.address" type="text" id="address"
					size="60" />
				</label>
			</p>
			<p>&nbsp;</p>
			<p>
				<label>公司网址 <input name="stock.compyWebsite" type="text" id="compyWebsite" size="60" />
				</label>
			</p>
			<p>&nbsp;</p>
			<p>
				<label>报告地址 <input name="stock.reportAddress" type="text"
					id="reportAddress" size="60" />
				</label>
			</p>
			<p>&nbsp;</p>
			<p>
				<label>状态 </label> <label> <input type="radio" name="stock.flag"
					value="1" id="flag"/> 启用
				</label> <label> <input type="radio" name="stock.flag" value="0" id="flag"/>
					停用
				</label>
			</p>
			<p>
				<label>
					<div align="center">
						<div align="center">
							<input type="submit" name="Submit" onclick="javascript:stockFormValid()" value="修改" />
						</div>
				</label>
				<p>&nbsp;</p>
		</fieldset>
	</form>
</body>
</html>
