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
		管理菜单 >> 财务数据管理 >> 综合数据表 >> 综合数据表数据新增  </span>
	<hr />
	<br />
	<form id="gendataForm" name="gendataForm" method="post" action="">
		<label>股票代码 <input type="text" name="textfield" />
		</label> <label>数据年份 <input type="text" name="textfield2" />
		</label> <label>总股本 <input type="text" name="textfield3" />
		</label>
		<p>
			<label>加权平均净资产收益率 <input type="text" name="textfield4" />
			</label> <label></label> 加权平均净资产收益率(扣非) <input type="text" name="textfield8" />
			<label>本年度发放的现金股利总和 <input type="text" name="textfield6" />
			</label>
		</p>
		<p>
			<label> 状态 <input type="radio" name="radiobutton"
				value="radiobutton" /> 启用 <input type="radio" name="radiobutton"
				value="radiobutton" /> 停用
			</label> <label>基本每股收益 <input type="text" name="textfield7" />
			</label> <label></label> 稀释每股收益 <input type="text" name="textfield5" />
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
