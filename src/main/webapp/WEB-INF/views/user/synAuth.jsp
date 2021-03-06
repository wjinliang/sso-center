
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/user.tld" prefix="d"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!doctype html>
<html lang="zh-CN">
<head>
<title>用户授权</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=basePath%>/assets/css/common.css">
<link rel="stylesheet" href="<%=basePath%>/assets/css/main.css">
<link rel="stylesheet"
	href="<%=basePath%>/assets/plugin/zTree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript">var root = "<%=path%>";
</script>
<script type="text/javascript"
	src="<%=basePath%>/assets/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/assets/js/colResizable-1.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/js/common.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/assets/plugin/layer/layer.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/assets/js/commonAction.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/assets/plugin/zTree/js/jquery.ztree.core-3.5.js"></script>
</head>
<body>
	<div class="container">
	<div id="button" class="mt10">
		
       <input type="button" onclick="openPage('${backUrl}');" name="button" class="btn btn82 btn_back" value="返回"> 
							</div>
					<div id="table" class="mt10">
						<div class="box span10 oh">
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="list_table">
								<tr>
									<th width="30">#</th>
									<th>系统</th>
									<th>状态</th>
									<th>操作</th>
								</tr>
								<c:if test="${page.total eq 0 }">
									<tr class="tr">
										<td class="" colspan="4">您没有可操作的系统！</td>
									</tr>
								</c:if>
								<c:forEach items="${page.list}" var="app" varStatus="status">
									<tr class="tr">

										<td class="">
											${status.count}</td>
										<td>${app.appName}</td>
										<td>
											<c:choose>
												<c:when test="${app.status=='1'}">
													<font color="green">启用</font>
												</c:when>
												<c:when test="${app.status=='2'}">
													<font color="green">维护中...</font>
												</c:when>
												<c:otherwise>
													<font color="red">禁用</font>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:if test="${app.isUserAuthorize}">
												<a class="ext_btn" href="javascript:dialogPage('用户授权-${app.appName}','../../syn/ssoServiceBySession?xtbs=${app.appCode}&TYPE=1&ID=${id}');">授权</a>
											</c:if>
											<c:if test="${!app.isUserAuthorize}">
												该系统不需要授权
											</c:if>
										</td>

									</tr>
								</c:forEach>
							</table>
<%-- 							<%@include file="../include/pageinfo.jsp"%> --%>

						
			</div>
		</div>
	</div>
	</div>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			
		});
		
	</script>
</body>

</html>
