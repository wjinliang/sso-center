
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/user.tld" prefix="d"%>
<%@page import="java.util.List"%>
<%@page import="com.topie.ssocenter.freamwork.authorization.model.JKApplicationInfo,com.github.pagehelper.PageInfo"%>
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
					<form action="./gotoMergeUser" method="get" id="serchForm">
					<div class="box_center pt10 pb10">
						<table class="form_table" border="0" cellpadding="0"
							cellspacing="0">

							<input type="hidden" id="pageNum" name="thispage" value="">
							<input type="hidden" id="code" name="code" value="${searchModel.code}">
							<input type="hidden" id="mergeUuid" name="mergeUuid" value="${searchModel.mergeUuid}">
							<input type="hidden" name="pagesize" value="${page.pageSize}">
							<tr>
								<td>用户名</td>
								<td><input type="text" name="name"
									value="${searchModel.name }" class="input-text lh25"
									size="20"></td>
									<td>登录名</td>
								<td><input type="text" name="loginname"
									value="${searchModel.loginname }" class="input-text lh25"
									size="20"></td>
								<%-- <td>机构代码</td>
				<td><input type="text" name="appCode" value="${seachModel.code }" class="input-text lh25"
					size="10"></td> --%>
	
								<td><input type="button" onclick="nextPage('0')"
									class="btn btn82 btn_search" value="查询">
								<td>
							</tr>
						</table>
					</div>
					</form>
				</div>
				
<!-- 				<div id="button" class="mt10"> -->
			
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
			<div><span id="timeCount"></span></div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th width="30">#</th>
						<th width="30">#</th>
						<th>用户名</th>
						<th>机构</th>
						<th>最后登录时间</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${page.list}" var="user" varStatus="status">
						<tr class="tr">

							<td class=""><input type="checkbox"
											value='${user.code }'></td>
										<td class="">
											${(page.pageNum-1)*page.pageSize+status.count}</td>
										<td>${user.name}(${user.loginname })</td>
										<td><c:set var="uorg" value="${d:gOBID(user.orgId)}">
										</c:set>
										${uorg.name}</td>
										<td>${user.lastlogintime }</td>
										<td><a class="ext_btn"
											href="javascript:confirmAction('mergeUser?userId=${searchModel.code }&userIds=${user.code }');">
												关联</a> 
										</td>
						</tr>
					</c:forEach>
				</table>
				<%@include file="../include/pageinfo.jsp"%>
				
			</div>
		</div>
</body>

</html>
