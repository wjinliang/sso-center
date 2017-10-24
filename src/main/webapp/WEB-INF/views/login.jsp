<%--
  Created by IntelliJ IDEA.
  User: cgj
  Date: 2015/9/1
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=basePath%>/assets/css/login.css">
    <script type="text/javascript" src="<%=basePath%>/assets/js/jquery.min.js"></script>
    <title>后台登陆</title>
</head>
<script type="text/javascript">
        if (self!=top){
             window.top.location.replace(self.location); //打开自己网站的页面
            }
</script>
<body>
<div id="login_top">
    <div id="welcome">
        管理系统
    </div>
    <div id="back">
        <a href="#">返回首页</a>&nbsp;&nbsp; | &nbsp;&nbsp;
        <a href="#">帮助</a>
    </div>
</div>
<div id="login_center">
    <c:if test="${param.error==true}">
        <div class="alert alert-danger display-show">
            <button class="close" data-close="alert"></button>
            <span>${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message} </span>
        </div>
    </c:if>
    <div id="login_area">
        <div id="login_form">
            <form action="<%=basePath%>/j_spring_security_check" method="post">
                <div id="login_tip">
                    用户登录&nbsp;&nbsp;UserLogin
                </div>
                <div><input type="text" class="username" name="j_username"></div>
                <div><input type="text" class="pwd" name="j_password"></div>
                <div id="btn_area">
                    <input type="submit" name="submit" id="sub_btn" value="登&nbsp;&nbsp;录">&nbsp;&nbsp;
                    <input type="text" class="verify" name="j_captcha">
                    <img src="<%=basePath%>/security/web/captcha" alt="" width="80" height="40">
                </div>
            </form>
        </div>
    </div>
</div>
<div id="login_bottom">
    版权所有
</div>
</body>
</html>
