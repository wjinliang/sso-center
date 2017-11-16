
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
		<div id="search_bar" class="mt10">
			<div class="box">
			<div class="box_border">
					<form action="" method="get" id="serchForm">
					<div class="box_center pt10 pb10">
						<table class="form_table" border="0" cellpadding="0"
							cellspacing="0">
							<input type="hidden" id="pageNum" name="thispage" value="">
							<tr>
								<td>系统名称</td>
								<td><input type="text" name="appName" value="${searchModel.appName }" class="input-text lh25"
									size="20"></td>
								
								<td><input type="submit" class="btn btn82 btn_search"
				value="查询"><td>
							</tr>
						</table>
					</div>
					</form>
				</div>
				<div id="button" class="mt10">
	       		<input type="button" name="button" class="btn btn82 btn_export" value="导出">
				<input type="button" onclick="" name="button" class="btn btn82 btn_count"
					value="统计图">
		<!-- <input type="button" name="button"
				class="btn btn82 btn_count" value="统计">
			 <input type="button" name="button" class="btn btn82 btn_del" value="删除"> 
       <input type="button" name="button" class="btn btn82 btn_config" value="配置"> 
       <input type="button" name="button" class="btn btn82 btn_checked" value="全选"> 
       <input type="button" name="button" class="btn btn82 btn_nochecked" value="取消"> 
       <input type="button" name="button" class="btn btn82 btn_recycle" value="回收站"> -->
		</div>
			</div>
		</div>
		<div id="table" class="mt10">
			<div class="box span10 oh">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th width="30">#</th>
						<th>区划</th>
						<th>省级用户</th>
						<th>市级用户</th>
						<th>县级用户</th>
						<th>乡镇用户</th>
						<th>村级用户</th>
					</tr>
					<c:set value="0" var="sum0" />
								<c:set value="0" var="sum1" />
								<c:set value="0" var="sum2" />
								<c:set value="0" var="sum3" />
								<c:set value="0" var="sum4" />
								<c:forEach items="${page}" var="app" varStatus="status">
									<tr>
										<%-- <td ><input
											type="checkbox" class="checkboxes" name="checkboxes"
											value="${app.id}" /></td> --%>
										<td >
											${thispage*pagesize+status.count}</td>
										<td >${app.divisionName}</td>
										<td >${app.shengcount}</td>
										<td >${app.shicount}</td>
										<td >${app.xiancount}</td>
										<td >${app.xiangcount}</td>
										<td >${app.cuncount}</td>
										<c:set value="${sum0 + app.shengcount}" var="sum0" />
										<c:set value="${sum1 + app.shicount}" var="sum1" />
										<c:set value="${sum2 + app.xiancount}" var="sum2" />
										<c:set value="${sum3 + app.xiangcount}" var="sum3" />
										<c:set value="${sum4 + app.cuncount}" var="sum4" />
									</tr>
									<c:if test="${status.last=='true'}">
									<tr style="background-color: #ccc;">
										<%-- <td ><input
											type="checkbox" class="checkboxes" name="checkboxes"
											value="${app.id}" /></td> --%>
										<td ></td>
										<td >合计(${sum0+sum1+sum2+sum3+sum4})</td>
										<td >${sum0}</td>
										<td >${sum1}</td>
										<td >${sum2}</td>
										<td >${sum3}</td>
										<td >${sum4}</td>

									</tr>
									</c:if>
								</c:forEach>
				</table>
				
			</div>
		</div>
</body>

</html>
