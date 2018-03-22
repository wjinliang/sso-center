
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
</head>
<body>
	<div class="container">
		<p style="font-size:20px;color:#ccc;margin-left:20px;">单点登录</p>
		<div id="table" class="mt10">
			<div class="box span10 oh">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th width="30">#</th>
						<th>系统名称</th>
						<th>系统状态</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${page.list}" var="app" varStatus="status">
						<tr class="tr">

							<td class="td_center">
								${(page.pageNum-1)*page.pageSize+status.count}</td>
							<td>${app.appName}</td>
							<td><c:choose>
									<c:when test="${app.status=='1'}">
										<font color="green">启用</font>
									</c:when>
									<c:when test="${app.status=='2'}">
										<font color="red">维护中</font>
									</c:when>
									<c:otherwise>
										<font color="red">禁用</font>
									</c:otherwise>
								</c:choose></td>
							<td>
							<c:if test="${app.status=='1'}">
								<a class="ext_btn" href="<%=path%>/syn/ssoServiceBySession?xtbs=${app.appCode}" target="_blank">登录</a>
							</c:if>
						</td>
						</tr>
					</c:forEach>
				</table>
				<%@include file="../include/pageinfo.jsp"%>
				
			</div>
		</div>
</body>

</html>
