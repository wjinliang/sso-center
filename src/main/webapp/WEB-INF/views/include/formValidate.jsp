
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/jquery.validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/jquery.validate/additional-methods.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/jquery.validate/localization/messages_zh.min.js"></script>
<script type="text/javascript">
$.validator.setDefaults({
	errorPlacement: function(error, element) {
	// Append error within linked label
		var type=element.attr("type");
		if(type=='radio' || type=="checkbox"){
			layer.tips(error.html(),element.parent(),{tipsMore: true});	
		}else{
			layer.tips(error.html(),element,{tipsMore: true});	
		}
	},
    submitHandler: function(form) {
      form.submit();
    }
});
</script>
