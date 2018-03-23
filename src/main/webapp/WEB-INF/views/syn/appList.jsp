
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
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
					<form action="./listSynsApp" method="get" id="serchForm">
					<div class="box_center pt10 pb10">
						<table class="form_table" border="0" cellpadding="0"
							cellspacing="0">

							<input type="hidden" id="pageNum" name="thispage" value="">
							<input type="hidden" name="pagesize" value="${page.pageSize}">
							<tr>
								<td>系统名称</td>
								<td><input type="text" name="appName" value="${searchModel.appName }" class="input-text lh25"
									size="20"></td>
								<td>系统编号</td>
								<td><input type="text" name="appCode" value="${searchModel.appCode }" class="input-text lh25"
									size="10"></td>
								<td>系统状态</td>
								<td><span class="fl">
										<div class="select_border">
											<div class="select_containers ">
												<select name="status" class="select">
													<option value="">请选择</option>
													<option <c:if test="${'1' eq searchModel.status}">selected</c:if> value="1">启用</option>
													<option <c:if test="${'2' eq searchModel.status}">selected</c:if> value="2">维护中</option>
													<option <c:if test="${'0' eq searchModel.status}">selected</c:if> value="0">禁用</option>
												</select>
											</div>
										</div>
								</span></td>
								<td><input type="button" onclick="nextPage('0')" class="btn btn82 btn_search"
				value="查询"><td>
							</tr>
						</table>
					</div>
					</form>
				</div>
				
<!-- 				<div id="button" class="mt10"> -->
<!-- 			<input type="button" onclick="javascript:openPage('./form/new');" name="button" class="btn btn82 btn_add" -->
<!-- 				value="新增"> -->
		<!-- <input type="button" name="button"
				class="btn btn82 btn_count" value="统计">
			 <input type="button" name="button" class="btn btn82 btn_del" value="删除"> 
       <input type="button" name="button" class="btn btn82 btn_config" value="配置"> 
       <input type="button" name="button" class="btn btn82 btn_checked" value="全选"> 
       <input type="button" name="button" class="btn btn82 btn_nochecked" value="取消"> 
       <input type="button" name="button" class="btn btn82 btn_export" value="导出">
       <input type="button" name="button" class="btn btn82 btn_recycle" value="回收站"> -->
<!-- 		</div> -->
			</div>
		</div>
		<div id="table" class="mt10">
			<div class="box span10 oh">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th width="30">#</th>
						<th width="30">#</th>
						<th>系统名称</th>
						<th>系统编号</th>
<!-- 						<th>系统同步类型</th> -->
						<th>系统状态</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${page.list}" var="app" varStatus="status">
						<tr class="tr">

							<td class=""><input type="checkbox"
								value='${app.id }'></td>
							<td class="">
								${(page.pageNum-1)*page.pageSize+status.count}</td>
							<td>${app.appName}</td>
							<td>${app.appCode}</td>
<%-- 							<td>${app.synType}</td> --%>
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
							<td><a class="ext_btn" href="javascript:openPage('./listSynOrg?appId=${app.id}')">同步机构
							</a> <a class="ext_btn" href="javascript:openPage('./listSynUser?appId=${app.id }');">  同步用户
							</a> 
						</td>
						</tr>
					</c:forEach>
				</table>
				<%@include file="../include/pageinfo.jsp"%>
				
			</div>
		</div>
</body>

</html>
