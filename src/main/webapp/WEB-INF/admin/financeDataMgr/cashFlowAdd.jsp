<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<form id="cashFlowForm" name="cashFlowForm" method="post" action="">
  <label>股票代码
  <input name="code" type="text" id="code" />
  </label>
  <label>数据年份
  <input name="year" type="text" id="year" />
  </label>
  <p>
    <label>经营活动产生的现金流量净额
    <input name="operaActiveCash" type="text" id="operaActiveCash" />
    </label>
    <label>现金及现金等价物增加额
    <input name="cashAndCashequ" type="text" id="cashAndCashequ" />
    </label>
  </p>
  <p>状态 
    <label>
    <input name="flag" type="radio" value="1" checked="checked" />
    启用</label>
    <label>
    <input type="radio" name="flag" value="0" />
    停用</label>
  </p>
  <p>
    <label>
    <div align="center">
      <input type="submit" name="Submit" value="提交" />
    </div>
    </label>
  </p>
</form>
</body>
</html>
