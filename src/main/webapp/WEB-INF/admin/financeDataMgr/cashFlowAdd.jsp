<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
</head>

<body>
<form id="cashFlowForm" name="cashFlowForm" method="post" action="">
  <label>��Ʊ����
  <input name="code" type="text" id="code" />
  </label>
  <label>�������
  <input name="year" type="text" id="year" />
  </label>
  <p>
    <label>��Ӫ��������ֽ���������
    <input name="operaActiveCash" type="text" id="operaActiveCash" />
    </label>
    <label>�ֽ��ֽ�ȼ������Ӷ�
    <input name="cashAndCashequ" type="text" id="cashAndCashequ" />
    </label>
  </p>
  <p>״̬ 
    <label>
    <input name="flag" type="radio" value="1" checked="checked" />
    ����</label>
    <label>
    <input type="radio" name="flag" value="0" />
    ͣ��</label>
  </p>
  <p>
    <label>
    <div align="center">
      <input type="submit" name="Submit" value="�ύ" />
    </div>
    </label>
  </p>
</form>
</body>
</html>
