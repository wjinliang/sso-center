
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!doctype html>
<html lang="zh-CN">
<head>
<title>管理页面</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=basePath%>/assets/css/common.css">
<link rel="stylesheet" href="<%=basePath%>/assets/css/main.css">
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
</head>
<body>
	<div class="container">
		<div id="search_bar" class="mt10">
			<div class="box">
				<div class="box_border">
					<form action="./${type }s" method="get" id="serchForm">
						<div class="box_center pt10 pb10">
							<table class="form_table" border="0" cellpadding="0"
								cellspacing="0">

								<input type="hidden" id="pageNum" name="thispage" value="">
								<input type="hidden" name="pagesize" value="${page.pageSize}">
								<tr>
									<td>标题</td>
									<td><input type="text" name="title"
										value="${searchModel.title }" class="input-text lh25"
										size="20"></td>
									<td>发布状态</td>
									<td><span class="fl">
											<div class="select_border">
												<div class="select_containers ">
													<select name="isPublish" class="select">
														<option value="">请选择</option>
														<option
															<c:if test="${'1' eq searchModel.isPublish}">selected</c:if>
															value="1">已发布</option>
														<option
															<c:if test="${'0' eq searchModel.isPublish}">selected</c:if>
															value="0">未发布</option>
													</select>
												</div>
											</div>
									</span></td>
									<td><input type="button" onclick="nextPage('0')"
										class="btn btn82 btn_search" value="查询">
									<td>
								</tr>
							</table>
						</div>
					</form>
				</div>

				<div id="button" class="mt10">
					<input type="button" onclick="javascript:openPage('./${type }/new');"
						name="button" class="btn btn82 btn_add" value="新增">
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
						<th>标题</th>
						<th>发布时间</th>
						<th>发布状态</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${page.list}" var="cont" varStatus="status">
						<tr class="tr">

							<td class=""><input type="checkbox"
								value='${cont.id }'></td>
							<td class="">
								${(page.pageNum-1)*page.pageSize+status.count}</td>
							<td>${cont.title}</td>
							<td><fmt:formatDate value="${cont.publishTime}" pattern="yyyy-MM-dd" /></td>
							<td><c:choose>
									<c:when test="${cont.isRevoke}">
										<font>已撤回</font>
									</c:when>
									<c:when test="${cont.isPublish}">
										<font color="green">已发布</font>
									</c:when>
									<c:otherwise>
										<font>未发布</font>
									</c:otherwise>
								</c:choose></td>
							<td> <c:choose>
									<c:when test="${cont.isRevoke}">
									<a class="ext_btn" href="../${type }_${cont.id}" target="_blank">预览</a>
										<a class="ext_btn"
											href="javascript:openPage('./${type }/update?noticeId=${cont.id}')">编辑
										</a>
										<a class="ext_btn ext_btn_submit"
											href="javascript:postAction('./publish?id=${cont.id}')">重新发布
										</a>
										<a class="ext_btn ext_btn_error"
											href="javascript:deleteAction('./delete?id=${cont.id}')">删除
										</a>
									</c:when>
									<c:when test="${cont.isPublish}">
									<a class="ext_btn" href="../${type }_${cont.id}" target="_blank">查看</a>
										<a class="ext_btn ext_btn_error"
											href="javascript:postAction('./revoke?id=${cont.id}')">撤回
										</a>
									</c:when>
									<c:otherwise>
									<a class="ext_btn" href="../${type }_${cont.id}" target="_blank">预览</a>
										<a class="ext_btn"
											href="javascript:openPage('./form/update?noticeId=${cont.id}')">编辑
										</a>
										<a class="ext_btn ext_btn_submit"
											href="javascript:postAction('./publish?id=${cont.id}')">发布
										</a>
										<a class="ext_btn ext_btn_error"
											href="javascript:deleteAction('./delete?id=${cont.id}')">删除
										</a>
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>
				</table>
				<%@include file="../../include/pageinfo.jsp"%>

			</div>
		</div>
</body>

</html>
