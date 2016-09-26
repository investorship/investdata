/** 封装表单验证js* */

function regFormValid() {
	$(document).ready(function() {
		$("#regForm").validate({
			rules : {
				userName : {
					userNameRepet : true,
					availChar : true,
					required : true,
					minlength : 3,
					maxlength : 25
				},
				password : {
					availChar : true,
					required : true,
					minlength : 6,
					maxlength : 25
				},
				repassword : {
					required : true,
					equalTo : "#password"
				},
				email : {
					emailRepet : true,
					required : true,
					email : true
				},
				randCode : {
					randCodeCheck : true,
					required : true,
					rangelength : [ 4, 4 ]
				},
				agreee : {
					required : true
				}

			},
			messages : {
				userName : {
					userNameRepet : "该用户名已经被注册",
					availChar : "只能由数字、字母或下划线组成",
					required : "用户名不能为空",
					minlength : "用户名的最小长度为3",
					maxlength : "用户名的最大长度为25"
				},
				password : {
					availChar : "只能由数字、字母或下划线组成",
					required : "密码不能为空",
					minlength : "密码长度不能少于6个字符",
					maxlength : "密码长度不能超过30个字符"
				},
				repassword : {
					required : "确认密码不能为空",
					equalTo : "确认密码和密码不一致"
				},
				email : {
					emailRepet : "该邮件已被注册",
					required : "邮箱不能为空",
					email : "邮箱地址格式错误"
				},
				randCode : {
					randCodeCheck : "您输入的验证码不正确",
					required : "验证码不能为空",
					rangelength : "验证码位数错误"
				},
				agreee : {
					required : "请同意我们的协议"
				}
			}
		});
	});
}


/** 更换验证码 防止缓存，使用时间戳 **/
function changeImageAuth() {
	$("#imageAuth").attr("src",
			"imageAuth/imageAuth.action?timestamp=" + new Date().getTime());
}


/** 登录表单验证 **/
function loginFormValid() {
	$(document).ready(function(){
	    $("#loginForm").validate({
	        rules: {
	        	userName:{
	        		availChar: true,
	                required: true,
	                minlength: 3,
	                maxlength: 25
	            },
	            password:{
	            	availChar: true,
	                required: true,
	                minlength: 6,
	                maxlength: 25
	            }
	            
	        },
	        messages:{
	        	userName:{
	        		availChar: "只能由数字、字母或下划线组成",
	                required: "用户名不能为空",
	                minlength: "用户名的最小长度为3",
	                maxlength: "用户名的最大长度为25"
	            },
	            password:{
	            	availChar: "只能由数字、字母或下划线组成",
	                required: "密码不能为空",
	                minlength: "密码长度不能少于6个字符",
	                maxlength: "密码长度不能超过30个字符"
	            }
	        }
	    });
	});
}

/**登录界面-ajax校验用户名、密码是否正确**/
function checkLogin() {
	var userName = $("#userName").val();
	var password = $("#password").val();
	var resultVal = "";
	$.ajax({
        type: "post",
        async: false,
        url: "login/login!checkLogin.action?userName=" + userName + "&password=" + password,
        //方法传参的写法一定要对，与后台一致，区分大小写，不能为数组等，str1为形参的名字,str2为第二个形参的名字 
        data: "{}",
       // contentType: "application/json; charset=utf-8",
       	dataType: "json",
        success: function(data) {
           resultVal = data.ajaxResult;
        },
        error: function(err) { //如果出现异常，不做界面提示
        	resultVal = false;
        }
        
    });
	
		if ('true' == resultVal) {
			return true;
		} else {
			$("#loginFail").attr("color","#FF0000");
			$("#loginFail").text("用户名或密码错误！");
			return false;
		}
}

/**重置密码界面表单验证**/
function resetPwdFormValid() {
	$(document).ready(function() {
		$("#resetPwdForm").validate({
			rules : {
				email : {
					required : true,
					email : true,
					mailExistCheck : true
				}
			},
			messages : {
				email : {
					required : "邮箱不能为空",
					email : "邮箱地址格式错误",
					mailExistCheck : "该邮箱不存在，请确认"
				}
			}
		});
	});
}

/**修改密码界面表单验证**/
function updatePwdFormValid() {
	$(document).ready(function() {
		$("#updatePwdForm").validate({
			rules : {
				oldPwd : {
					required : true,
					pwdIsCorrect: true
				},
				newPwd: {
					required : true,
					availChar : true,
					minlength : 6,
					maxlength : 25
				},
				reNewPwd: {
					required : true,
					equalTo : "#newPwd"
				}
			},
			messages : {
				oldPwd : {
					required : "原密码不能为空",
					pwdIsCorrect : "原密码不正确"
				},
				newPwd: {
					required : "新密码不能为空",
					availChar : "只能由数字、字母或下划线组成",
					minlength : "密码长度不能少于6个字符",
					maxlength : "密码长度不能超过30个字符"
				},
				reNewPwd: {
					required : "确认新密码不能为空",
					equalTo : "新密码与确认新密码必须一致"
				}
			}
		});
	});
}

/** 管理员登录验证 **/
function checkAdminLogin() {
	var userName = $("#userName").val();
	var password = $("#password").val();
	
	var resultVal = "";
	$.ajax({
        type: "post",
        async: false,
        url: "login/login!checkAdminLogin.action?userName=" + userName + "&password=" + password,
        //方法传参的写法一定要对，与后台一致，区分大小写，不能为数组等，str1为形参的名字,str2为第二个形参的名字 
        data: "{}",
       // contentType: "application/json; charset=utf-8",
       	dataType: "json",
        success: function(data) {
           resultVal = data.ajaxResult;
        },
        error: function(err) { //如果出现异常，不做界面提示
        	resultVal = false;
        }
        
    });
	
		if ('true' == resultVal) {
			return true;
		} else {
			$("#loginFail").attr("color","#FF0000");
			$("#loginFail").text("用户名或密码错误！");
			return false;
		}
}

/**管理员登录表单验证**/
function adminLoginFormValid() {
	$(document).ready(function() {
		$("#adminLoginForm").validate({
			rules : {
				userName : {
					required : true
				},
				password : {
					availChar : true,
					required : true,
					minlength : 6,
					maxlength : 25
				},
				randCode : {
					randCodeCheck: true,
					required : true,
					rangelength : [ 4, 4 ]
				},
			},
			messages : {
				userName : {
					required : "用户名不能为空"
				},
				password : {
					required : "密码不能为空"
				},
				randCode : {
					randCodeCheck: "您输入的验证码不正确",
					required : "验证码不能为空",
					rangelength : "验证码位数错误"
				}
			}
		});
	});
}

