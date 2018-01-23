
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
					<form action="listToSynOrg" method="get" id="serchForm">
					<div class="box_center pt10 pb10">
						<table class="form_table" border="0" cellpadding="0"
							cellspacing="0">

							<input type="hidden" id="pageNum" name="thispage" value="">
							<input type="hidden" id="appId" name="appId" value="${appId }">
							<input type="hidden" name="pagesize" value="${page.pageSize}">
							<tr>
								<td>机构名称</td>
								<td><input type="text" name="name"
									value="${searchModel.name }" class="input-text lh25"
									size="20"></td>
								<td><input type="button" onclick="nextPage('0')" class="btn btn82 btn_search"
				value="查询"><td>
							</tr>
						</table>
					</div>
					</form>
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
									<th>组织机构名称</th>
									<th>组织机构编码</th>
									<th>操作</th>
								</tr>
								<c:forEach items="${page.list}" var="org" varStatus="status">
									<tr class="tr">

										<td class="td_center"><input type="checkbox"
											value='${org.id }'></td>
										<td class="td_center">
											${(page.pageNum-1)*page.pageSize+status.count}</td>
										<td>${org.name}</td>
										<td>${org.code}</td>
										<td><a class="ext_btn"
											href="javascript:confirmAction('synOrg?appId=${appId }&orgId=${org.id }');">
												同步</a></td>

									</tr>
								</c:forEach>
				</table>
				<%@include file="../include/pageinfo.jsp"%>
				
			</div>
		</div>
</body>

</html>
