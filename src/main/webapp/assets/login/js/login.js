;
(function ($, window, document, undefined) {
	function initLogin(form){
		var $form = $(form);
		$.validator.setDefaults({
			errorPlacement: function(error, element) {
			// Append error within linked label
				$form.find(".error").html(error.html());
				
			},
		    submitHandler: function(form) {
		      form.submit();
		    }
		});
		var validator = $(form).validate({
			rules: {
				j_username:  {
			        required: true
			      },
			      j_password:  {
				        required: true
				      },
				  j_captcha:{
				        required: true
				  }
			},
			messages:{j_username:{required:"请输入用户名！"},j_password:{required:"请输入密码！"},j_captcha:{required:"请输入验证码！"}}
		});
		
	}


    $(document).ready(function () {
        initLogin("#loginForm");
    });
})(jQuery, window, document);
