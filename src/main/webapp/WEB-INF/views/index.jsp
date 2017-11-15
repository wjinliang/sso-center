<%--
  Created by IntelliJ IDEA.
  User: cgj
  Date: 2015/10/14
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=basePath%>/assets/css/common.css">
    <link rel="stylesheet" href="<%=basePath%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=basePath%>/assets/css/index.css">
    <script type="text/javascript">var root = "<%=path%>";</script>
    <script type="text/javascript" src="<%=basePath%>/assets/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/assets/js/jquery.SuperSlide.js"></script>
    <script type="text/javascript" src="<%=basePath%>/assets/plugin/layer/layer.js"></script>
    <script type="text/javascript" src="<%=basePath%>/assets/js/index.js"></script>
<!--[if lt IE 9]>
<script src="<%=basePath%>/assets/js/IE9.js"></script>
<![endif]-->
    <title>后台首页</title>
</head>
<body>
<div class="top">
    <div id="top_t">
        <div id="logo" class="fl"></div>
        <div id="photo_info" class="fr">
            <div id="photo" class="fl">
                <a href="#"><img src="<%=basePath%>/assets/images/a.png" alt="" width="60" height="60"></a>
            </div>
            <div id="base_info" class="fr">
                <div class="help_info">
                    <a href="1" id="hp">&nbsp;</a>
                    <a href="2" id="gy">&nbsp;</a>
                    <a href="<%=basePath%>/security/logout" id="out">&nbsp;</a>
                </div>
                <div class="info_center">
                    admin
                    <span id="nt">通知</span><span><a href="#" id="notice">3</a></span>
                </div>
            </div>
        </div>
    </div>
    
</div>
<div class="side">
    <div class="menubtn"><a href="javascript:menuHide();"></a></div>
    <div class="sideMenu" id="side-menu" style="margin:0 auto">
        
    </div>
</div>
<div class="main">
<iframe name="rightMain" id="rightMain" src="orgAndUser/listOrgs" frameborder="no" scrolling="auto" width="100%" height="auto" allowtransparency="true"></iframe>

</div>

<div class="bottom">
    <div id="bottom_bg">版权</div>
</div>
<div class="scroll">
    <a href="javascript:;" class="per" title="使用鼠标滚轴滚动侧栏" onclick="menuScroll(1);"></a>
    <a href="javascript:;" class="next" title="使用鼠标滚轴滚动侧栏" onclick="menuScroll(2);"></a>
</div>

</body>

</html>
