
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
	<p style="font-size:20px;color:#ccc;margin-left:20px;">机构用户同步统计</p>
		<!-- <div id="search_bar" class="mt10">
			<div class="box">
				<div id="button" class="mt10">
       		<input type="button" name="button" class="btn btn82 btn_export" value="导出">
			<input type="button" onclick="" name="button" class="btn btn82 btn_add"
				value="统计图">
		<input type="button" name="button"
				class="btn btn82 btn_count" value="统计">
			 <input type="button" name="button" class="btn btn82 btn_del" value="删除"> 
       <input type="button" name="button" class="btn btn82 btn_config" value="配置"> 
       <input type="button" name="button" class="btn btn82 btn_checked" value="全选"> 
       <input type="button" name="button" class="btn btn82 btn_nochecked" value="取消"> 
       <input type="button" name="button" class="btn btn82 btn_recycle" value="回收站">
		</div>
			</div>
		</div> -->
		<div id="table" class="mt10">
			<div class="box span10 oh">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th width="30">#</th>
						<th>系统名称</th>
						<th>系统状态</th>
						<th>同步组织数</th>
						<th>同步用户数</th>
					</tr>
					<c:forEach items="${page}" var="app" varStatus="status">
						<tr class="tr">

							<td class="td_center">
								${status.count}</td>
							<td>${app.app.appName}</td>
							<td>
								<c:choose>
									<c:when test="${app.app.status=='1'}">
										<font color="green">启用</font>
									</c:when>
									<c:otherwise>
										<font color="red">禁用</font>
									</c:otherwise>
								</c:choose>
							</td>
							<td>${app.orgCount}(个)</td>
							<td>${app.userCount}(个)</td>
						</tr>
					</c:forEach>
				</table>
				
			</div>
		</div>
</body>

</html>
