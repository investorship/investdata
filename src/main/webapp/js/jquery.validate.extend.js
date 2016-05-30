/*******************************插件新功能-设置插件validator的默认参数*****************************************/
$.validator.setDefaults({
    /*关闭键盘输入时的实时校验*/
    onkeyup: null,
    /*添加校验成功后的执行函数--修改提示内容，并为正确提示信息添加新的样式(默认是valid)*/
    success: function(label){
        /*label的默认正确样式为valid，需要通过validClass来重置，否则这里添加的其他样式不能被清除*/
        label.text('').addClass('valid');
    },
    /*重写校验元素获得焦点后的执行函数--增加[1.光标移入元素时的帮助提示,2.校验元素的高亮显示]两个功能点*/
    onfocusin: function( element ) {
        this.lastActive = element;
        
        /*1.帮助提示功能*/
        this.addWrapper(this.errorsFor(element)).hide();
        var tip = $(element).attr('tip');
        if(tip && $(element).parent().children(".tip").length === 0){
            $(element).parent().append("<label class='tip'>" + tip + "</label>");
        }
        
        /*2.校验元素的高亮显示*/
        $(element).addClass('highlight');

        // Hide error label and remove error class on focus if enabled
        if ( this.settings.focusCleanup ) {
            if ( this.settings.unhighlight ) {
                this.settings.unhighlight.call( this, element, this.settings.errorClass, this.settings.validClass );
            }
            this.hideThese( this.errorsFor( element ) );
        }
    },
    /*重写校验元素焦点离开时的执行函数--移除[1.添加的帮助提示,2.校验元素的高亮显示]*/
    onfocusout: function( element ) {
        /*1.帮助提示信息移除*/
        $(element).parent().children(".tip").remove();

        /*2.校验元素高亮样式移除*/
        $(element).removeClass('highlight');
        
        /*3.替换下面注释的原始代码，任何时候光标离开元素都触发校验功能*/
        this.element( element );
        
        /*if ( !this.checkable( element ) && ( element.name in this.submitted || !this.optional( element ) ) ) {
            this.element( element );
        }*/
    }
});


/*******************************DIY插件字段校验*****************************************/
/**输入的值是否为字母，数字，下划线**/
$.validator.addMethod(
    "availChar",
    function(value, element){
        /*var dotPos = value.indexOf('.');^[0-9a-zA-Z_]{1,}$
        return value > 0 && dotPos < 0 && (dotPos > 0 && value.substring(dotPos + 1) <= 2);*/
        return value && /^[0-9a-zA-Z_]{1,}$/.test(value);
    }
);

/**用户名重复**/
$.validator.addMethod(
	    "userNameRepet",
	    function(value, element){
	    	var resultVal = "";
	    	$.ajax({
		        type: "post",
		        async: false,
		        url: "reg/reg!checkUserName.action?userName=" + value,
		        //方法传参的写法一定要对，与后台一致，区分大小写，不能为数组等，str1为形参的名字,str2为第二个形参的名字 
		        data: "{}",
		       // contentType: "application/json; charset=utf-8",
		       	dataType: "json",
		        success: function(data) {
		           resultVal = data.ajaxResult;
		        },
		        error: function(err) { //如果出现异常，不做界面提示
		        	resultVal = true;
		        }
		    });
//	    	alert (resultVal);
	    	return 'true' == resultVal;
	    }
);


/**邮箱重复**/
$.validator.addMethod(
	    "emailRepet",
	    function(value, element){
	    	var resultVal = "";
	    	$.ajax({
		        type: "post",
		        async: false,
		        url: "reg/reg!checkEmail.action?email=" + value,
		        //方法传参的写法一定要对，与后台一致，区分大小写，不能为数组等，str1为形参的名字,str2为第二个形参的名字 
		        data: "{}",
		       // contentType: "application/json; charset=utf-8",
		       	dataType: "json",
		        success: function(data) {
		           resultVal = data.ajaxResult;
		        },
		        error: function(err) { //如果出现异常，不做界面提示
		        	resultVal = true;
		        }
		    });
	    	return 'true' == resultVal;
	    }
);

/**验证码是否正确**/
$.validator.addMethod(
	    "randCodeCheck",
	    function(value, element){
	    	var resultVal = "";
	    	$.ajax({
		        type: "post",
		        async: false,
		        url: "reg/reg!checkRandCode.action?randCode=" + value,
		        //方法传参的写法一定要对，与后台一致，区分大小写，不能为数组等，str1为形参的名字,str2为第二个形参的名字 
		        data: "{}",
		       // contentType: "application/json; charset=utf-8",
		       	dataType: "json",
		        success: function(data) {
		           resultVal = data.ajaxResult;
		        },
		        error: function(err) { //如果出现异常，不做界面提示
		        	resultVal = true;
		        }
		    });
	    	return 'true' == resultVal;
	    }
);




