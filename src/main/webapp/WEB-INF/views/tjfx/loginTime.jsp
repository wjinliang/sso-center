
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!doctype html>
<html lang="zh-CN">
<head>
<title>管理页面</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=basePath%>/assets/css/common.css">
<link rel="stylesheet" href="<%=basePath%>/assets/css/main.css">
<script type="text/javascript">var root = "<%=path%>";</script>
<script type="text/javascript"
	src="<%=basePath%>/assets/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/assets/js/colResizable-1.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/js/commonAction.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/echarts/echarts.common.min.js"></script>
<script type="text/javascript">
var xAxisData=[],yAxisData=[];
</script>
</head>
<body>
	<div class="container">
	<p style="font-size:20px;color:#ccc;margin-left:20px;">一天24小时内用户累计登录次数</p>
		<div id="chartsPanel" class="">
			<div id="charts" style="height:510px;"></div>
		</div>
		<div id="table" class="mt10">
			<div class="box span10 oh">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th width="400">时间</th>
						<th>用户累计登录次数</th>
					</tr>
					<c:forEach items="${list}" var="app" varStatus="status">
						<tr class="tr">

							<td>${app.time}时</td>
							<td>${app.count}</td>
							<script >
								xAxisData.push("${app.time}时");
								yAxisData.push("${app.count}");
							</script>
						</tr>
					</c:forEach>
				</table>
				
			</div>
		</div>
</body>
<script>

$(function(){
        // 绘制图表。
       echartsInstance = echarts.init(document.getElementById('charts')).setOption(option);
	
});
var option = {
	    xAxis: {
	        type: 'category',
	        data: xAxisData,
	        name:"时间"
	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    yAxis: {
	        type: 'value',
	        name:"用户登录次数"
	    },
	    series: [{
	        data: yAxisData,
	        type: 'line',
	        smooth: true
	    }]
	};
	</script>
</html>
