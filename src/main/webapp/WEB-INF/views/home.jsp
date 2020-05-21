<%--
  Created by IntelliJ IDEA.
  User: cgj
  Date: 2015/10/14
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/user.tld" prefix="d" %>
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
    <title>兽医卫生综合信息平台单点登录</title>
</head>
<body>
<div class="top">
    <div id="top_t">
        <a href="./index"><div id="logo" class="fl"></div></a>
        <div id="photo_info" class="fr">
            <div id="photo" class="fl">
<%--                 <a href="#"><img src="<%=basePath%>/assets/images/a.png" alt="" width="60" height="60"></a> --%>
            </div>
            <div id="base_info" class="fr">
                <div class="help_info">
                    <a href="javascript:void();" id="hp" style="color:#fff"> ${d:gON() }-${d:gUN() }</a>
                </div>
                <div class="info_center">
                    <a href="./index" id="index" style="color:#fff">返回主页</a>&nbsp;&nbsp;&nbsp;||&nbsp;&nbsp;
                    <a href="<%=basePath%>/security/logout" id="out" style="color:#fff">退出登录</a>
                   
<!--                     <span id="nt">通知</span><span><a href="#" id="notice">3</a></span> -->
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
<iframe name="rightMain" id="rightMain" src="infoCenter" frameborder="no" scrolling="auto" width="100%" height="auto" allowtransparency="true"></iframe>

</div>

<div class="bottom">
    <div id="bottom_bg">版权&copy;-中国动物疫病预防控制中心</div>
</div>
<div class="scroll">
    <a href="javascript:;" class="per" title="使用鼠标滚轴滚动侧栏" onclick="menuScroll(2);"></a>
    <a href="javascript:;" class="next" title="使用鼠标滚轴滚动侧栏" onclick="menuScroll(1);"></a>
</div>

</body>
<script type="text/javascript">
var index;
var html_iframe = document.getElementById('rightMain');
var interval = setInterval(function () {
    var iframeDoc = html_iframe.contentDocument || html_iframe.contentWindow.document;
    if(iframeDoc.readyState == 'complete' || iframeDoc.readyState == 'interactive'){
    	if(index)
    		layer.close(index);
    }else{
    		index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
    		
    }
    
},300)
</script>
</html>
