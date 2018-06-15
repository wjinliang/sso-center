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
		    	var cloneForm = $(form).clone();
		    	$(document.body).append(cloneForm);
		    	cloneForm.hide();
		    	var b = new Base64();  
		    	var password = cloneForm.find('input[name="j_password"]');
		    	var jp = password.val();
		        password.val(b.encode(b.encode(jp)));
		        var username = cloneForm.find('input[name="j_username"]');
		    	var ju = username.val(); 
		        username.val(b.encode(ju));
		        cloneForm.submit();
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
