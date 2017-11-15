
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
					<form action="./list" method="get" id="serchForm">
					<div class="box_center pt10 pb10">
						<table class="form_table" border="0" cellpadding="0"
							cellspacing="0">

							<input type="hidden" id="pageNum" name="thispage" value="">
							<input type="hidden" name="pagesize" value="${page.pageSize}">
							<tr>
								<td>角色名称</td>
								<td><input type="text" name="name" value="${searchModel.name }" class="input-text lh25"
									size="20"></td>
								<td>状态</td>
								<td><span class="fl">
										<div class="select_border">
											<div class="select_containers ">
												<select name="enabled" class="select">
													<option value="">请选择</option>
													<option <c:if test="${searchModel.enabled==true}">selected</c:if> value="true">启用</option>
													<option <c:if test="${searchModel.enabled==false}">selected</c:if> value="false">禁用</option>
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
				
				<div id="button" class="mt10">
			<input type="button" onclick="javascript:openPage('./form');" name="button" class="btn btn82 btn_add"
				value="新增">
		<!-- <input type="button" name="button"
				class="btn btn82 btn_count" value="统计">
			 <input type="button" name="button" class="btn btn82 btn_del" value="删除"> 
       <input type="button" name="button" class="btn btn82 btn_config" value="配置"> 
       <input type="button" name="button" class="btn btn82 btn_checked" value="全选"> 
       <input type="button" name="button" class="btn btn82 btn_nochecked" value="取消"> 
       <input type="button" name="button" class="btn btn82 btn_export" value="导出">
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
						<th width="30">#</th>
						<th>角色名称</th>
						<th>主页</th>
						<th>等级</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${page.list}" var="role" varStatus="status">
						<tr class="tr">

							<td class="td_center"><input type="checkbox"
								value='${role.code}'></td>
							<td class="td_center">
								${(page.pageNum-1)*page.pageSize+status.count}</td>
							<td>${role.name}</td>
							<td>${role.homepage}</td>
							<td>${role.level}</td>
							<td><c:choose>
									<c:when test="${role.enabled}">
										<font color="green">启用</font>
									</c:when>
									<c:otherwise>
										<font color="red">禁用</font>
									</c:otherwise>
								</c:choose></td>
							<td>
							<%-- <a class="ext_btn" href="javascript:viewPage('./form?code=${role.code}')">查看
							</a>  --%>
							<a class="ext_btn ext_btn_success" href="javascript:openPage('./form?code=${role.code }');">  修改
							</a>
							 <a class="ext_btn ext_btn_error" href="javascript:deleteAction('./role_del?id=${role.code}')">删除
							</a> <c:choose> 
									<c:when test="${role.enabled}">
										<a class="ext_btn" href="javascript:postAction('./stoprole?code=${role.code}')"> 禁用
										</a>
									</c:when>
									<c:otherwise>
										<a class="ext_btn ext_btn_submit" href="javascript:postAction('./startrole?code=${role.code}')">启用
										</a>
									</c:otherwise>
								</c:choose>
						</td>
						</tr>
					</c:forEach>
				</table>
				<%@include file="../include/pageinfo.jsp"%>
				
			</div>
		</div>
</body>

</html>
