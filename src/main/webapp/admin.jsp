<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<title>后台管理登录界面</title>
<link href="css/admin/admlogin.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<form id="form1" runat="server">
		<div class="Main">
			<ul>
				<li class="top"></li>
				<li class="top2"></li>
				<li class="topA"></li>
				<li class="topB"><span> <img src="images/admin/logo.gif"
						alt="" style="" />
				</span></li>
				<li class="topC"></li>
				<li class="topD">
					<ul class="login">
						<li><span class="left">用户名：</span> <span style=""> <input
								id="Text1" type="text" class="txt" />

						</span></li>
						<li><span class="left">密 码：</span> <span style=""> <input
								id="Text2" type="text" class="txt" />
						</span></li>
						<li><span class="left">验证码：</span> <span style=""> <input
								id="Text3" type="text" class="txtCode" />
						</span></li>
					</ul>
				</li>
				<li class="topE"></li>
				<li class="middle_A"></li>
				<li class="middle_B"></li>
				<li class="middle_C"><span class="btn"> <img alt=""
						src="images/admin/btnlogin.gif" />


				</span></li>
				<li class="middle_D"></li>
				<li class="bottom_A"></li>
				<li class="bottom_B"></li>
			</ul>
		</div>
	</form>
</body>
</html>
