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
    <script type="text/javascript" src="<%=basePath%>/assets/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/assets/js/jquery.SuperSlide.js"></script>
    <script type="text/javascript" src="<%=basePath%>/assets/js/index.js"></script>
    <script type="text/javascript">
        $(function () {
            $(".sideMenu li").click(function () {
                $(".sideMenu li.on").removeClass("on");
                $(this).addClass("on");
            });

            $('.sideMenu h3').click(function () {
                var $ul = $(this).next('ul');
                $('.sideMenu').find('ul').slideUp();
                $(".sideMenu h3.on").removeClass("on");
                if ($ul.is(':visible')) {
                    $(this).next('ul').slideUp();
                } else {
                    $(this).next('ul').slideDown();
                    $(this).addClass("on");
                }
            });
            $('.sideMenu h3.on').next('ul').slideDown();
        });
    </script>
    <script type="text/javascript">
        $(function () {
            $(".sideMenu").slide({
                titCell: "h3",
                targetCell: "ul",
                defaultIndex: 0,
                effect: 'slideDown',
                delayTime: '500',
                trigger: 'click',
                triggerTime: '150',
                defaultPlay: true,
                returnDefault: false,
                easing: 'easeInQuint',
                endFun: function () {
                    scrollWW();
                }
            });
            $(window).resize(function () {
                scrollWW();
            });
        });

        function scrollWW() {
            if ($(".side").height() < $(".sideMenu").height()) {
                $(".scroll").show();
                var pos = $(".sideMenu ul:visible").position().top - 38;
                $('.sideMenu').animate({top: -pos});
            } else {
                $(".scroll").hide();
                $('.sideMenu').animate({top: 0});
                n = 1;
            }
        }

        var n = 1;

        function menuScroll(num) {
            var Scroll = $('.sideMenu');
            var ScrollP = $('.sideMenu').position();
            /*alert(n);
            alert(ScrollP.top);*/
            if (num == 1) {
                Scroll.animate({top: ScrollP.top - 38});
                n = n + 1;
            } else {
                if (ScrollP.top > -38 && ScrollP.top != 0) {
                    ScrollP.top = -38;
                }
                if (ScrollP.top < 0) {
                    Scroll.animate({top: 38 + ScrollP.top});
                } else {
                    n = 1;
                }
                if (n > 1) {
                    n = n - 1;
                }
            }
        }
    </script>
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
    <div id="side_here">
        <div id="side_here_l" class="fl"></div>
        <div id="here_area" class="fl">当前位置:</div>
    </div>
</div>
<div class="side">
    <div class="sideMenu" style="margin:0 auto">
        <h3 class="on">导航菜单</h3>
        <ul>
            <li><a href="http://baidu.com" target="rightMain">百度</a></li>
            <li class="on"><a href="./main.html" target="rightMain">列表</a></li>
        </ul>
        <h3> 导航菜单</h3>
        <ul>
            <li>导航菜单</li>
            <li>导航菜单</li>
            <li>导航菜单</li>
            <li>导航菜单</li>
            <li>导航菜单</li>
        </ul>
    </div>
</div>
<div class="main">

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
