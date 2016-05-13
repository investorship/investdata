<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
<HEAD>
<base href="<%=basePath%>">
<TITLE>ZTREE DEMO - Standard Data</TITLE>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/admin/tree.css" type="text/css">
<link rel="stylesheet" href="css/admin/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="js/admin/jquery.ztree.core.js"></script>
<SCRIPT type="text/javascript">
<!--
	var setting = {};

	var zNodes = [ {
		name : "管理菜单",
		open : true,
		children : [ {
			name : "父节点11 - 折叠",
			children : [ {
				name : "叶子节点111"
			}, {
				name : "叶子节点112"
			}, {
				name : "叶子节点113"
			}, {
				name : "叶子节点114"
			} ]
		}, {
			name : "父节点12 - 折叠",
			children : [ {
				name : "叶子节点121"
			}, {
				name : "叶子节点122"
			}, {
				name : "叶子节点123"
			}, {
				name : "叶子节点124"
			} ]
		} ]
	} ];

	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});
//-->
</SCRIPT>
</HEAD>

<BODY>
	<h1>最简单的树 -- 标准 JSON 数据</h1>
	<h6>[ 文件路径: core/standardData.html ]</h6>
	<div class="content_wrap">
		<div class="zTreeDemoBackground left">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
</BODY>
</HTML>