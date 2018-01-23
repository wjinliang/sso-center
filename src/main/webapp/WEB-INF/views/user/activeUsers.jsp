
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/user.tld" prefix="d"%>
<%@ taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
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
				
				<div id="button" class="mt10">
			<input type="button" onclick="javascript:confirmItemAction('kickUser')" name="button" class="btn btn82 btn_del"
				value="踢出">
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
						<th>用户</th>
						<th>上次活跃</th>
						<th>上次登录时间</th>
						<th>上次登录ip</th>
						<th>sessionId</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${page.list}" var="user" varStatus="status">
						<tr class="tr">

							<td class="td_center"><input type="checkbox"
								value='${user.sessionId }'></td>
							<td class="td_center">
								${status.count}</td>
							<td>${user.userName}(${user.userLoginname})</td>
							<td>${user.lastActivityDate}</td>
							<td>${d:gLL(user.userCode)}</td>
							<td>${d:gIp(user.userCode)}</td>
							<td>${user.sessionId}</td>
							
							<td><a class="ext_btn" href="javascript:confirmAction('kickUser?ids=${user.sessionId}')">踢出
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
