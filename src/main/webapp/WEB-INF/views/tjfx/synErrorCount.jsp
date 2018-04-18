
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
var edata=[],ydata=[];
</script>
</head>
<body>
	<div class="container">
	<p style="font-size:20px;color:#ccc;margin-left:20px;">同步结果分析</p>
		<div id="chartsPanel" class="">
			<div id="charts" style="height:510px;"></div>
		</div>
		<div id="table" class="mt10">
			<div class="box span10 oh">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th>结果类型</th>
						<th>数量</th>
					</tr>
					<c:forEach items="${list}" var="app" varStatus="status">
						<tr class="tr">

							<td>${app.msg}</td>
							<td>${app.count}</td>
							<script >
								ydata.push("${app.msg}");
								edata.push({value:"${app.count}",name:"${app.msg}"});
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
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        x: 'left',
	        data:ydata
	    },
	    series: [
	        {
	            name:'结果类型',
	            type:'pie',
	            radius: ['50%', '70%'],
	            avoidLabelOverlap: false,
	            label: {
	                normal: {
	                    show: false,
	                    position: 'center'
	                },
	                emphasis: {
	                    show: true,
	                    textStyle: {
	                        fontSize: '30',
	                        fontWeight: 'bold'
	                    }
	                }
	            },
	            labelLine: {
	                normal: {
	                    show: false
	                }
	            },
	            data:edata
	        }
	    ]
	};
	</script>
</html>
