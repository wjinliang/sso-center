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
<title>系统</title>
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
        <div class="headBtm"><span>${d:gON() } -</span><span>【${d:gUN() }】 欢迎使用单点登录云平台！</span><span id="showTime"></span></div>
    </div>
     <div class="systemCon clearfix" style="width:1150px; margin:0 auto;">
    	<div class="pageLeft">
        	<ul class="newsListTwo" id="newsList">
        		<li class='<c:if test="${type eq 'notice'}">activeNav</c:if>'><a href="./notices" >通知公告</a></li>
            	
                <li class='<c:if test="${type eq 'download'}">activeNav</c:if>'><a href="./downloads" >资料下载</a></li>
            </ul>
        </div>
        <div class="pageRight">
        	<div class="newsListCon">	
            	<div class="pagePosition"><a href="../index" >首页</a>&gt;&gt;<span>通知公告</span></div>	
                <div class="newsListTit">
                	<ul>
					<c:forEach items="${page.list}" var="cont" varStatus="status">
                    	<li><a href="./${type}_${cont.id }" target="_blank">${cont.title}</a>
                    	<span><fmt:formatDate value="${cont.publishTime}" pattern="yyyy-MM-dd" /></span><div class="clear"></div></li>
                    </c:forEach>
                    </ul>
                    <div class="pageNav"><span>第${page.pageNum }页/共${page.pages }页（共${page.total }条记录）</span>
                    <a href="./${type}s_${page.prePage}">上一页</a>
                    <a href="./${type}s_${page.nextPage}">下一页</a></div>
                </div>
            </div>
        </div>
    </div>
    <div class="systemFooter">© Copyright 2017 ### | Powered by ### | Top</div>
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
			wordlimit(pText1,26);
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
