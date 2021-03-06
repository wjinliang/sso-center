
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
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
					<form action="./list" method="get" id="serchForm">
					<div class="box_center pt10 pb10">
						<table class="form_table" border="0" cellpadding="0"
							cellspacing="0">

							<input type="hidden" id="pageNum" name="thispage" value="">
							<input type="hidden" name="pagesize" value="${page.pageSize}">
							<tr>
								<td>内容</td>
								<td><input type="text" name="appName" value="${searchModel.content }" class="input-text lh25"
									size="20"></td>
								<td>操作用户</td>
								<td><input type="text" name="user" value="${searchModel.user }" class="input-text lh25"
									size="20"></td>
								<td>类型</td>
								<td><span class="fl">
										<div class="select_border">
											<div class="select_containers ">
												<select name="type" class="select">
													<option value="">请选择</option>
													<option <c:if test="${'1' eq searchModel.type}">selected</c:if> value="1">操作</option>
													<option <c:if test="${'0' eq searchModel.type}">selected</c:if> value="0">登录</option>
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
						<th>标题</th>
						<th>内容</th>
						<th>日志类型</th>
						<th>操作用户</th>
						<th>时间</th>
						<!-- <th>操作</th> -->
					</tr>
					<c:forEach items="${page.list}" var="log" varStatus="status">
						<tr class="tr">

							<td class=""><input type="checkbox"
								value='${log.id }'></td>
							<td class="">
								${(page.pageNum-1)*page.pageSize+status.count}</td>
							<td>${log.title}</td>
							<td title="${log.content}">
							<c:if test="${log.type=='0'}">
								${log.content}
							</c:if> 
							<c:if test="${log.type=='1'}">
								<a href="javascript:showLog('${log.content }')";>查看内容</a>
							</c:if> 
							<td>${log.type=='0'?'登录':'操作'}</td>
							<td>${log.user}</td>
							<td>${log.date}</td>
							<!-- <td><a class="ext_btn ext_btn_error" href="javascript:deleteAction('./delete?id=${log.id}')">删除
							</a> 
							</td> -->
						</tr>
					</c:forEach>
				</table>
				<%@include file="../include/pageinfo.jsp"%>
				
			</div>
		</div>
</body>
<script>
	function showLog(content){
		layer.open({
			  type: 1,
			  skin: 'layui-layer-rim', //加上边框
			  area: ['600px', '240px'], //宽高
			  content: content
			});
	}
</script>
</html>
