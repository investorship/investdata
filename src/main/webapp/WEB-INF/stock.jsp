<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath(); 
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <base href="<%=basePath%>">
    <title>投资数据网 - 信息列表</title>
    <meta name="viewport" content="width=1010"/>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <link href="css/list.css" type="text/css" rel="stylesheet"/>
    <link href="css/login.css" type="text/css" rel="stylesheet"/>
    <link href="css/basic.css" type="text/css" rel="stylesheet"/>
    <jsp:include page="autocomplete.jsp" />
</head>
<body>
<div class="wbyTop fn-clear">
    <div class="wbyLinks fn-right"><span><a target="_blank" href="login/login.action">登录</a> <a target="_blank" href="reg/reg.action">注册</a>|<a href="">关于我们</a></span></div>
    </div>
</div>
<!-- header start -->
<div class="header">
    <div class="wrap clearfix">
        <div class="logo" data-sudaclick="toplogo"><a href=""><img src="images/logo2.png" alt="" title=""/></a></div>
        <div class="search" data-sudaclick="topsearch" >
            <form action="stock/stock.action" method="get" target="_blank" id="search_f">
                <input type="text" name="k" class="search_k" value="请输入您要查找的股票代码"
                       onfocus="if(this.value === '请输入您要查找的股票代码'){this.value = '';}"
                       onblur="if(this.value === ''){this.value = '请输入您要查找的股票代码';}" id="keyword"/>
                <input type="submit" class="search_smt" value="快速查找"/>
            </form>
        </div>
    </div>
</div>

<div class="content">
    <div class="hotcities">
        <h2>
            进入<a href="#">财务指标一览<span>»</span></a></h2>

        <div class="headInf">
            投资数据网站平台，投资者的在线手册提供开放性大数据信息金融数据信息等各类数据分类信息的免费查询，为大众服务。提供每股指标、偿债能力、营运能力等八大栏目，你只需点击相应栏目搜索即可。
        </div>
    </div>
    <div class="province_choose">
        <label>
            请选择分类：</label>
        <span>
            <select id="selectProvince">
                <option value="0">每股指标</option>
                <option value="1">偿债能力</option>
            </select>
        </span>
        <span>
            <select id="selectCity">
                <option value="0">每股收益</option>
                <option value="1">稀释每股收益</option>
            </select>&nbsp;
        </span>
        <input type="button" name="btn" id="btn"  value="确定">

        <div class="share">
            <div class="bdsharebuttonbox"><a href="#" class="bds_more" data-cmd="more"></a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a></div>
            <script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"24"},"share":{},"image":{"viewList":["qzone","tsina","tqq","renren","weixin"],"viewText":"分享到：","viewSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
        </div>
    </div>
    <div class="city_box">
        <ul class="letter-list" id="J-city-list">
            <li class="city-list"><span class="arrow"></span><span class="letter">每股指标</span>
                <ul class="cities">
                    <li><a href=" ">每股收益</a></li>
                    <li><a href=" ">稀释每股收益</a></li>
                    <li><a href=" ">每股净资产</a></li>
                    <li><a href=" ">每股营业收入</a></li>
                    <li><a href=" ">每股营业利润</a></li>
                    <li><a href=" ">每股息税前利润</a></li>
                </ul>
            </li>
            <li class="city-list"><span class="arrow"></span><span class="letter">偿债能力</span>
                <ul class="cities">
                    <li><a href="#"><span>流动比率</span></a></li>
                    <li><a href="#">速动比率</a></li>
                    <li><a href="#">现金比率</a></li>
                    <li><a href="#">现金流量比率</a></li>
                    <li><a href="#">资产负债率</a></li>
                    <li><a href="#">股东权益比率</a></li>
                    <li><a href="#">权益乘数</a></li>
                    <li><a href="#">产权比率</a></li>
                    <li><a href="#">偿债保障比率</a></li>
                    <li><a href="#">利息保障倍数</a></li>
                    <li><a href="#">现金利息保障倍数</a></li>
                </ul>
            </li>
            <li class="city-list"><span class="arrow"></span><span class="letter">运营能力</span>
                <ul class="cities">
                    <li><a href="#"><span>应收账款周转率</span></a></li>
                    <li><a href="#"><span>存货周转率</span></a></li>
                    <li><a href="#"><span>营业周期</span></a></li>
                    <li><a href="#"><span>存货周转天数</span></a></li>
                    <li><a href="#">应收账款周转天数</a></li>
                    <li><a href="#">流动资产周转率</a></li>
                    <li><a href="#">固定资产周转率</a></li>
                    <li><a href="#">总资产周转率</a></li>
                </ul>
            </li>
            <li class="city-list"><span class="arrow"></span><span class="letter">盈利能力</span>
                <ul class="cities">
                    <li><a href="#">净资产收益率</a></li>
                    <li><a href="#"><span>资产息税前利润率</span></a></li>
                    <li><a href="#">资产利润率</a></li>
                    <li><a href="#">资产净利率</a></li>
                    <li><a href="#">东权益报酬率</a></li>
                    <li><a href="#">平均权益乘数</a></li>
                    <li><a href="#">销售净利率</a></li>
                    <li><a href="#">销售毛利率</a></li>
                    <li><a href="#">成本费用净利率</a></li>
                    <li><a href="#">净资产收益率</a></li>
                    <li><a href="#">净资产收益率</a></li>
                </ul>
            </li>
            <li class="city-list"><span class="arrow"></span><span class="letter">发展能力</span>
                <ul class="cities">
                    <li><a href="#">销售增长率</a></li>
                    <li><a href="#">资产增长率</a></li>
                    <li><a href="#">股权资本增长率</a></li>
                    <li><a href="#">利润增长率</a></li>
                </ul>
            </li>
            <li class="city-list"><span class="arrow"></span><span class="letter">现金流量</span>
                <ul class="cities">
                    <li><a href="#"><span>销售现金比率</span></a></li>
                    <li><a href="#"><span>每股经营现金流</span></a></li>
                    <li><a href="#"><span>现金股利支付率</span></a></li>
                    <li><a href="#"><span>主营业务现金比率</span></a></li>
                    <li><a href="#"><span>自由现金流量</span></a></li>
                </ul>
            </li>
            <li class="city-list"><span class="arrow"></span><span class="letter">资本结构</span>
                <ul class="cities">
                    <li><a href="#"><span>固定资产比率</span></a></li>
                    <li><a href="#"><span>无形资产比率</span></a></li>
                    <li><a href="#"><span>股东权益比率</span></a></li>
                    <li><a href="#"><span>权益乘数</span></a></li>
                    <li><a href="#"><span>负债结构比率</span></a></li>
                    <li><a href="#"><span>长期负债权益比率</span></a></li>
                    <li><a href="#"><span>营运资金</span></a></li>
                </ul>
            </li>
            <li class="city-list"><span class="arrow"></span><span class="letter">核心业务质量</span>
                <ul class="cities">
                    <li><a href="#"><span>非经常性损益比率</span></a></li>
                    <li><a href="#"><span>扣非净利润</span></a></li>
                    <li><a href="#"><span>所得税/利润总额</span></a></li>
                    <li><a href="#"><span> 营业外收支净额／利润总额</span></a></li>
                </ul>
            </li>

        </ul>
    </div>
</div>

<div id="footer">
    <p>Copyright © 2016 投资数据网 &nbsp;京ICP证160506号&nbsp;<a href="" title="雪球" target="_blank">雪球</a> &nbsp; <a href="" title="巨潮资讯网" target="_blank">巨潮资讯网</a> <br>
        <span class="lianxi"></span> </p>
</div>
</body>
</html>