<link href="../css/jquery.autocomplete.css" type="text/css" rel="stylesheet" />
<link href="../css/jquery-ui.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/jquery.autocomplete.min.js"></script>
<script type="text/javascript" src="../js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui.js"></script>
<script type="text/javascript"> 
var availableTags = ${applicationScope.stocksItems};
$(function() {
	//从后台获取数据.
	$(function() {
	    $("#code").autocomplete({
	    	source:availableTags,
	    	minLength:2,
	    	max:10,
	    	focus: function() {
	            // 防止在获得焦点时插入值
	            return false;
	        },
	    	select: function( event, ui ){
		    	var code = $("#code").val(ui.item.value);
		    	
		    	//$(document).fillColums(code);
		    	
		    	
		    	
		    	//$("#search_f").submit();
		    }
	    })
	 });
  });
  </script>