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
	<form id="balForm" name="balForm" method="post" action="">
		<label>股票代码 <input name="code" type="text" id="code" size="25" />
		</label>
			<label> <input name="load" type="button" id="load" value="加载" />
		</label> 
		<label>数据年份 <input name="year" type="text" id="year"
			size="25" />
		</label> <label>应收票据 <input name="noteRecable" type="text"
			id="noteRecable" size="25" />
		</label> 预收账款 <input name="advCustomers" type="text" id="advCustomers"
			size="25" />

		<p>
			应付账款 <input name="accPayable" type="text" id="accPayable" size="25" />
			在建工程 <input name="constrInPro" type="text" id="constrInPro" size="25" />
			无形资产 <input name="lntangAssets" type="text" id="lntangAssets"
				size="25" /> 商&nbsp;&nbsp;誉 <input name="goodwill" type="text"
				id="goodwill" size="25" />
		</p>
		<p>
			短期借款 <input name="shortTermLoans" type="text" id="shortTermLoans"
				size="25" /> 应付票据 <input name="notePayable" type="text"
				id="notePayable" size="25" /> 资本公积 <input name="capitalSurplus"
				type="text" id="capitalSurplus" size="25" /> 盈余公积 <input
				name="surplusReserve" type="text" id="surplusReserve" size="25" />
		</p>
		<p>
			长期借款 <input name="longTermLoans" type="text" id="longTermLoans"
				size="25" /> 应付债券 <input name="boundsPayable" type="text"
				id="boundsPayable" size="25" /> 期初存货 <input name="goodsStart"
				type="text" id="goodsStart" size="25" /> 期末存货 <input
				name="goodsEnd" type="text" id="goodsEnd" size="25" />
		</p>
		<p>
			货币资金 <input name="cash" type="text" id="cash" size="25" /> 流动负债 <input
				name="cash2" type="text" id="cash2" size="25" /> 非流动负债 <input
				name="goodsStart2" type="text" id="goodsStart2" size="25" /> 未分配利润
			<input name="goodsStart22" type="text" id="goodsStart22" size="25" />
		</p>
		<p>
			期初流动资产 <input name="cash3" type="text" id="cash3" size="25" />
			期初流动资产 <input name="cash32" type="text" id="cash32" size="25" />
			期初资产总额 <input name="cash33" type="text" id="cash33" size="25" />
			期末资产总额 <input name="cash322" type="text" id="cash322" size="25" />
		</p>
		<p>
			期初股东权益 <input name="cash34" type="text" id="cash34" size="25" />
			期末股东权益 <input name="cash323" type="text" id="cash323" size="25" />
			期初固定资产 <input name="cash35" type="text" id="cash35" size="25" />
			期末固定资产 <input name="cash324" type="text" id="cash324" size="25" />
		</p>
		<p>
			期初应收账款 <input name="cash352" type="text" id="cash352" size="25" />
			期末应收账款 <input name="cash3242" type="text" id="cash3242" size="25" />
			<label>一年内到期的非流动负债 <input type="text" name="textfield" />
			</label>
		</p>
		<p>
			状态 <label> <input name="flag" type="radio" value="1"
				checked="checked" /> 启用
			</label> <input type="radio" name="flag" value="0" /> 停用
		</p>
		<p>
			<label>
				<div align="center">
					<input type="submit" name="Submit" value="提交" />
				</div>
			</label>
		</p>
		<p>&nbsp;</p>
	</form>
</body>
</html>
