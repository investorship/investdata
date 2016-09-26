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
</head>

<body>
	<span style="font-weight:bold;font-size:7px;color:#ff0000">
			管理菜单 >> 股票信息管理   >> 股票信息修改
	</span>
	<hr /><br />
<form id="form1" name="form1" method="post" action="">
  <label>股票代码
  <input name="code" type="text" id="code" />
  </label>
  <label>
  <input name="load" type="button" id="load" value="加载" />
  </label>
  <label>股票名称
  <input name="name" type="text" id="name" />
  </label>
  <label>字母简写
  <input name="shortname" type="text" id="shortname" />
  </label>
  <label>所属市场&nbsp;
  <input name="market" type="text" id="market" />
  </label>
  <p>&nbsp;</p>
  <p>
    <label></label>
    <label>上市时间
    <input name="ipotime" type="text" id="ipotime" />
    </label>
    <label>&nbsp;&nbsp;&nbsp;&nbsp;发行数量
    <input name="ipostocks" type="text" id="ipostocks" />
    </label>
    <label>所属行业
    <input name="category" type="text" id="category" />
    </label>
    <label>发行市盈率
    <input name="issuedPE" type="text" id="issuedPE" />
    </label>
  </p>
  <p>&nbsp;</p>
  <p>
    <label>发行价格
    <input name="issuedprice" type="text" id="issuedprice" />
    </label>
    <label>&nbsp;&nbsp;&nbsp;&nbsp;企业法人
    <input name="legaler" type="text" id="legaler" />
    </label>
    <label>联系电话
    <input name="phone" type="text" id="phone" />
    </label>
    <label>入库时间&nbsp;
    <input name="intime" type="text" id="intime" />
    </label>
  </p>
  <p>&nbsp;</p>
  <p>
    <label>公司地址
    <input name="address" type="text" id="address" size="60" />
    </label>
  </p>
  <p>&nbsp;</p>
  <p>
    <label>公司网址
    <input name="compywebsite" type="text" id="compywebsite" size="60" />
    </label>
  </p>
  <p>&nbsp;</p>
  <p>
    <label>报告地址
    <input name="reportaddress" type="text" id="reportaddress" size="60" />
    </label>
  </p>
  <p>
  <label>
    <div align="center">
      <div align="center">
        <input type="submit" name="Submit" value="修改" />
        </div>
  </label>
    <p>&nbsp;</p>
</form>
</body>
</html>
