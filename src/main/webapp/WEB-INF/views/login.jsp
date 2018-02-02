<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<title>登录</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/login/css/load.css">
<script type="text/javascript" src="<%=basePath%>/assets/login/js/jquery-1.12.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/jquery.validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/jquery.validate/additional-methods.min.js"></script>
</head>
<script type="text/javascript">
        if (self!=top){
             window.top.location.replace(self.location); //打开自己网站的页面
            }
</script>
<body>
	<div class="login">
    	<div class="login-top"><div style="text-align:center; padding-top:50px;"><img src="<%=basePath%>/assets/login/img/load.png" height="62"></div></div>
        <div class="loginDiv">
        	
            <div class="login-con">
                <div class="login-tit">登录</div>
                <div class="login-form">
                    <form id="loginForm" action="<%=basePath%>/j_spring_security_check" method="post">
                        <c:if test="${param.error==true}">
                        	<div class="error">${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}</div>
                        </c:if>
                        <p class="p1"><input type="text" autocomplete="off" class="alterInput" name="j_username" id="username" placeholder="请输入登录名" value=""></p>
                        <p class="p2"><input type="password" autocomplete="off" class="alterInput" name="j_password" id="passwords" placeholder="请输入密码" value=""></p>
	                        <p class="p3 clearfix">
	                            <input type="text" autocomplete="off" class="alterInput" id="acreditcode" placeholder="请输入验证码" value="" name="j_captcha" style="border-left:1px solid #ccc; width:55%; float:left;">
	                            <a href="javascript:void(0);" class="acreditImg"><img src="<%=basePath%>/security/web/captcha"></a>
	                        </p>
                        <p style="padding-left:0px;margin-bottom: 20px;"><input type="submit" value="登录" class="btn btn1"><input type="reset" value="重置" class="btn btn2"></p>
                    </form>	
                </div>
            </div>
        </div>
        <div class="login-footer">copy Right© 2017-2022</div>
    </div>
</body>
<script type="text/javascript">
	
	$(function(){
		$("a[class='acreditImg']").bind("click",function(){
			$(this).find("img").attr("src","<%=basePath%>/security/web/captcha?"+new Date());
			
		})
	});
</script>
</html>
