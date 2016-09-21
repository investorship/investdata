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
<title>无标题文档</title>
</head>

<body>
	<span style="font-weight: bold; font-size: 7px; color: #ff0000">
		管理菜单 >> 财务数据管理 >> 现金流量表 >> 现金流量表数据修改 </span>
	<hr />
	<br />
	<form id="cashFlowForm" name="cashFlowForm" method="post" action="">
		<label>股票代码 <input name="code" type="text" id="code" />
		</label> <label> <input type="submit" name="Submit2" value="加载" />
			数据年份 <input name="year" type="text" id="year" />
		</label>
		<p>
			<label>经营活动产生的现金流量净额 <input name="operaActiveCash"
				type="text" id="operaActiveCash" />
			</label> <label>现金及现金等价物增加额 <input name="cashAndCashequ" type="text"
				id="cashAndCashequ" />
			</label>
		</p>
		<p>
			状态 <label> <input name="flag" type="radio" value="1"
				checked="checked" /> 启用
			</label> <label> <input type="radio" name="flag" value="0" /> 停用
			</label>
		</p>
		<p>
			<div align="center">
				<input type="submit" name="Submit" value="提交" />
			</div>
		</p>
	</form>
</body>
</html>
