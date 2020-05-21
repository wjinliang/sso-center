<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/user.tld" prefix="d" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<title>兽医卫生综合信息平台单点登录</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/index/css/systemstyle.css">
<link rel="stylesheet" href="<%=basePath%>/assets/css/common.css">
<link rel="stylesheet" href="<%=basePath%>/assets/css/main.css">
<script type="text/javascript" src="<%=basePath%>/assets/index/js/jquery-1.12.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/layer/layer.js"></script>
<!--[if lt IE 9]>
<style>
	.news-con .titbg1, .news-con .titbg2{background:#d7f1fe !important;}
</style>
<![endif]-->
<script type="text/javascript"></script>
</head>

<body >
	<div class="systemHead">
    	<div class="headTop"></div>
        <div class="headBtm"><span>${d:gON() } -</span><span>【${d:gUN() }】 欢迎使用单点登录云平台！    <a href="<%=basePath%>/security/logout" class="logoutBtn">【退出登录】</a></span><span id="showTime"></span></div>
    </div>
    <div class="systemCon clearfix">
    	<div style="padding: 20px;">
    	<h2 style="">${msg} 请重新设置密码!</h2>
								<form id="form2" action="./setPassword" method="post"
							class="jqtransform" style="width:45%; margin:0 auto;">
							<table class="form_table pt15 pb15" width="100%" border="0"
								cellpadding="0" cellspacing="0">
								<tr>
									<td class="td_right">原密码：</td>
									<td class="input-icon"> <input
										type="text" name="oldPassword" class="input-text lh30"
										value="" size="40"></td>
									
								</tr>
								<tr>
									<td class="td_right">新密码：</td>
									<td class="input-icon"> <input
										type="password" name="newPassword" class="input-text lh30"
										value="" size="40"></td>
									<!-- <td class="input-icon"  style="display:none;"> <input
										type="text" name="newPassword111" class="input-text lh30"
										value="" size="40"><i class="fa fa-eyes"/></td> -->
								</tr>
								<tr>
									<td class="td_right">确认密码：</td>
									<td class="input-icon"> <input type="password" name="checkpwd" class="input-text lh30"
										value="" size="40"></td>
									<!-- <td class="input-icon" style="display:none;"> 
										<input type="text" name="checkpwd111" class="input-text lh30"
										value="" size="40"><i class="fa fa-eyes"/></td> -->
									
								</tr>
								<tr>
									<td class="td_right">&nbsp;</td>
									<td class="">
											<input type="submit" name="button"
												class="btn btn82 btn_save2" value="保存">
										</td>
								</tr>
								</table>
								</form>
							</div>
    </div>
    <div class="systemFooter">版权&copy;-中国动物疫病预防控制中心</div>
    <script type="text/javascript" src="<%=basePath%>/assets/js/commonAction.js"></script>
	<%@include file="./include/formValidate.jsp"%>
    <script type="text/javascript">
    jQuery.validator
	.addMethod(
			"isPassword",
			function(value, element) {
				var tel = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={} :";'<>?,.\/]).{8,30}$/;
				if(value=='1password!'){
					layer.alert("请勿使用初始密码！");
					return false;
				}
				return this.optional(element) || (tel.test(value));
			}, "必须字母/数字/符号三种混合且大于8位");
	$().ready(function() {
		
		// 提交时验证表单
		var validator = $("#form2").validate({
			rules : {
				oldpassword : {
					required : true
				},
				newPassword : {
					required : true,
					isPassword : true
				},
				checkpwd : {
					required : true,
					equalTo : "input[name='newPassword']"
				}
				
			},
			messages : {
				checkpwd : {
					equalTo : "两次输入的密码必须相同"
				}
			}
		});
	});
    </script>
</body>
</html>
