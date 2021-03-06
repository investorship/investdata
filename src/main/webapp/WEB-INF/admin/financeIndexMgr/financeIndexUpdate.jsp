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
<title>财务指标修改</title>

<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
<jsp:include page="../jquery_validate_admin.jsp" />


<script type="text/javascript">
	function load() {
		var id_val = $("#id").val();
		if(id_val == '') {
			alert('请先填写 财务指标ID号');
			return false;
		}
	  $.ajax({
	        type: "post",
	        async: false,
	        url: "financeIndexMgr/financeIndexMgr!loadFinanceIndexInfo.action?id=" + id_val,
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
	<span style="font-weight: bold; font-size: 7px; color: #ff0000">
		管理菜单 >> 财务指标管理 >> 财务指标修改 </span>
	<hr />
	<br />
	<form id="financeIndexForm" name="financeIndexForm" method="post" action="financeIndexMgr/financeIndexMgr!updateFinanceIndexInfo.action">
		<fieldset>
				<legend>修改财务指标</legend><br />
				<label>指标ID&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="financeIndexInfo.id" id="id"/></label>
				<input type="button" value="加载" onclick="javascript:load()"/>
				<br /><br />
			<label>指标名称 <input name="financeIndexInfo.name" type="text" id="name" />
			</label> <label>所属父节点 
			<select name="financeIndexInfo.pid" id="pid">
				<option value="0">根节点</option>
				<c:forEach items="${financeIndexInfos }" var="fii">
					<option value="${fii.id }"> ${fii.name } </option>
				</c:forEach>
			</select>
			
			</label> <label>URL访问地址 <input name="financeIndexInfo.action" type="text" id="action" size="40" />
			</label>
			<p>&nbsp;</p>
			<p>
				状态 <label> <input type="radio" name="financeIndexInfo.flag" value="1" id="flag"/> 启用
				</label> <label> <input type="radio" name="financeIndexInfo.flag" value="0" id="flag"/> 停用
				</label>
			</p>
			<p>&nbsp;</p>
			<p>
				<label>
					<div align="center">
						<input type="submit" name="Submit" value="添加" onclick="javascript:financeIndexFormValid()"/>
					</div>
				</label>
			</p>
		</fieldset>
	</form>
</body>
</html>

