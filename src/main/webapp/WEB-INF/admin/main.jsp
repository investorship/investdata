<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<title>后台管理系统</title>
<link href="css/admin/frame.css" rel="Stylesheet" type="text/css" />
<link href="css/admin/menu.css" rel="Stylesheet" type="text/css" />
<link rel="stylesheet" href="css/admin/tree.css" type="text/css">
<link rel="stylesheet" href="css/admin/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="js/admin/jquery.ztree.core.js"></script>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	overflow: hidden;
	height: 100%;
	max-height: 100%;
}
</style>

<script language="javascript" type="text/javascript">
	function SetWinHeight(obj) {
		var win = obj;
		if (document.getElementById) {
			if (win && !window.opera) {
				if (win.contentDocument
						&& win.contentDocument.body.offsetHeight)
					win.height = win.contentDocument.body.offsetHeight;
				elseif(win.Document && win.Document.body.scrollHeight)
				win.height = win.Document.body.scrollHeight;
			}
		}
	}
	
</script>

</head>
<body>
	<form id="form1" runat="server">
		<div id="framecontentLeft">
			<div class="menu" id="menuDiv">
			sfd
			</div>
		</div>
		<div id="framecontentTop">
			<div style="text-align: center;">
				<div style="float: right;">
					<a href="Default.aspx?act=logout">退出</a>
				</div>
				<h1>后台管理系统</h1>
			</div>
		</div>
		<div id="maincontent">
			<iframe id="content" name="content"
				onload="Javascript:SetWinHeight(this)" frameborder="0"
				scrolling="no" width="100%"></iframe>
		</div>
	</form>

</body>
</html>