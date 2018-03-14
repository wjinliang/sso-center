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
<title>全国动物疫病防控与动物卫生监督工作云平台单点登录</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/index/css/systemstyle.css">
<script type="text/javascript" src="<%=basePath%>/assets/index/js/jquery-1.12.1.min.js"></script>
<script type="text/javascript">  
	var days=new  Array ("日", "一", "二", "三", "四", "五", "六");  
	function showDT() {  
	  var currentDT = new Date();  
	  var y,m,date,day,hs,ms,ss,theDateStr;  
	  y = currentDT.getFullYear(); //四位整数表示的年份  
	  m = currentDT.getMonth()+1; //月  
	  date = currentDT.getDate(); //日  
	  day = currentDT.getDay(); //星期  
	  hs = currentDT.getHours(); //时  
	  ms = currentDT.getMinutes(); //分 
	  if (m >= 1 && m <= 9) {
			m = "0" + m;
		} 
	  if (date >= 0 && date <= 9) {
			date = "0" + date;
		}
	  if (hs >= 0 && hs <= 9) {
			hs = "0" + hs;
		}
	  if (ms >= 0 && ms <= 9) {
			ms = "0" + ms;
		}
	  theDateStr = y+"年"+  m +"月"+date+"日 星期"+days[day]+" "+hs+":"+ms; 
	  document.getElementById("showTime").innerHTML =theDateStr;  
	  // setTimeout 在执行时,是在载入后延迟指定时间后,去执行一次表达式,仅执行一次  
	  window.setTimeout(showDT, 500);  
	}  
</script>
<script type="text/javascript"></script>
</head>

<body onLoad="showDT()">
	<div class="systemHead">
    	<div class="headTop"></div>
        <div class="headBtm"><span>${d:gON() } -</span><span>【${d:gUN() }】 欢迎使用单点登录云平台！  <a href="<%=basePath%>/security/logout" class="logoutBtn">【退出登录】</a></span><span id="showTime"></span></div>
    </div>
    <div class="systemCon clearfix">
    	<div class="left">
        	<div class="newsWrapper">
                <div class="news-con">
                    <h1 class="tith1 titbg1"><a href="./notice/notices" target="_blank">通知公告</a></h1>
                    <div class="newsList">
                        <ul>
                        <c:set var="page" value="${d:gNEWS(1,5,'notice')}"></c:set>
                        <c:forEach items="${page.list}" var="cont" varStatus="status">
                    		<li title="${cont.title}"><a href="./notice/notice_${cont.id }" target="_blank" class="pText1">${cont.title}</a>
                    			<span><fmt:formatDate value="${cont.publishTime}" pattern="MM-dd" /></span>
                    		</li>
                    	</c:forEach>
                        </ul>
                    </div>
                </div>	
                <div class="news-con" style="margin-top:20px;">
                    <h1 class="tith1 titbg2"><a href="./notice/downloads" target="_blank">资料下载</a></h1>
                    <div class="newsList">
                        <ul>
                            <c:set var="page" value="${d:gNEWS(1,5,'download')}"></c:set>
	                        <c:forEach items="${page.list}" var="cont" varStatus="status">
	                    		<li title="${cont.title}"><a href="./notice/download_${cont.id }" target="_blank" class="pText1">${cont.title}</a>
	                    			<span><fmt:formatDate value="${cont.publishTime}" pattern="MM-dd" /></span>
	                    		</li>
	                    	</c:forEach>
                        </ul>
                    </div>
                </div>	
            </div>
        </div>
    	<div class="right">
        	 <c:set var="synAppList" value="${d:gCAS()}"></c:set>
        	<ul class="systemList systemList${ fn:length(synAppList)} clearfix">
        	<c:forEach items="${synAppList}" var="app" varStatus="status">
            	<li>
            	<!-- 维护中 -->
            	<c:if test="${app.status=='2'}">
	            	<a target="_blank" href="javascript:return false;"><span>
	            	<img src="<%=basePath%>/assets/index/img/${app.appCode}.png"></span></a>
	            	<p><a target="_blank" href="javascript:return false;">${app.appName}<span class="protect">[维护中]</span></a></p>
            	</c:if>
            	<c:if test="${app.status=='1'}">
	            	<a target="_blank" href="<%=basePath%>/syn/ssoServiceBySession?xtbs=${app.appCode}"><span>
	            	<img src="<%=basePath%>/assets/index/img/${app.appCode}.png"></span></a>
	            	<p><a target="_blank" href="<%=basePath%>/syn/ssoServiceBySession?xtbs=${app.appCode}">${app.appName}</a></p>
            	</c:if>
            	<c:set var="file" value="${d:gAF(app.id)}"></c:set>
            	<c:if test="${file!=null}"><a href="javascript:download('<%=basePath%>/KE/download/${file.id }')">用户操作手册</a></c:if>
            	</li>
            </c:forEach>
                <li><a href="./home"><span><img src="<%=basePath%>/assets/index/img/icon13.png"></span><p><a href="./home">单点登录</a></p></a></li>
                
            </ul>
        </div>
    </div>
    <div class="systemFooter">版权&copy;-中国动物疫病预防控制中心</div>
    <script type="text/javascript" src="<%=basePath%>/assets/js/commonAction.js"></script>
    <script type="text/javascript">
    	<!--截取字段--> 
			function wordlimit(cname,wordlength){
		　		 for(var i=0;i<cname.length;i++){
		　　　　		var nowLength=cname[i].innerHTML.length;
		　　　　			if(nowLength>wordlength){
		　　　　　　cname[i].innerHTML=cname[i].innerHTML.substr(0,wordlength)+'...';
			　　　　}
			　　}　
			}
			var pText1 = $(".pText1");
			wordlimit(pText1,13);
		<!--截取字段结束-->
		var screenHeight =  $(document).height();
		if(screenHeight<600){
			screenHeight=600;
		}
		var headHeight = $(".systemHead").innerHeight();
		var footerHeight = $(".systemFooter").height();
		var systemConHeight = screenHeight-headHeight-footerHeight;
		$(".systemCon").css("height",systemConHeight+'px');
	</script>
</body>
</html>
