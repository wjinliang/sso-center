
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
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
		<div class="row">
			<div class="box col_3">
				<div class="container">
					<div class="box_border">
						<div class="box_top">
							<b class="pl15">区划树</b>
						</div>
						<div class="box_center">
							<div class="ztree" id="divisionTree"></div>
						</div>
						
			</div>
					</div>
				</div>
			</div>
			<div class="box col_9">
				<div class="container">
					<div id="search_bar" class="mt10">
			<div class="box">
				<div class="box_border">
					<form action="./listApp" method="get" id="serchForm">
					<div class="box_center pt10 pb10">
						<table class="form_table" border="0" cellpadding="0"
							cellspacing="0">
							<input type="hidden" name="divisionid" id="divisionid" value="${seachModel.divisionid}">
							<input type="hidden" id="pageNum" name="thispage" value="">
							<input type="hidden" name="pagesize" value="${page.pageSize}">
							<tr>
								<td>机构名称</td>
								<td><input type="text" name="appName" value="${seachModel.appName }" class="input-text lh25"
									size="20"></td>
								<td>机构代码</td>
								<td><input type="text" name="appCode" value="${seachModel.appCode }" class="input-text lh25"
									size="10"></td>
								<td>系统状态</td>
								<td><span class="fl">
										<div class="select_border">
											<div class="select_containers ">
												<select name="status" class="select">
													<option value="">请选择</option>
													<option <c:if test="${'1' eq seachModel.status}">selected</c:if> value="1">启用</option>
													<option <c:if test="${'0' eq seachModel.status}">selected</c:if> value="0">禁用</option>
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
			<input type="button" onclick="javascript:openPage('./form/new');" name="button" class="btn btn82 btn_add"
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
						<th>组织机构名称</th>
						<th>组织机构编码</th>
						<th>查看下级组织机构</th>
						<th>查看用户</th>
					</tr>
					<c:forEach items="${page.list}" var="org" varStatus="status">
						<tr class="tr">

							<td class="td_center"><input type="checkbox"
								value='${org.id }'></td>
							<td class="td_center">
								${(page.pageNum-1)*page.pageSize+status.count}</td>
							<td>${org.name}</td>
							<td>${org.code}</td>
							<td><a class="ext_btn" href="${root}/orgAndUser/listSon?orgid=${org.id}&tj=${param.tj}">查看
							</a></td>
							<td> <a class="ext_btn ext_btn_success" href="${root}/orgAndUser/listSon?orgid=${org.id}&tj=${param.tj}">  查看
							</a></td>
							
						</tr>
					</c:forEach>
				</table>
				<%@include file="../include/pageinfo.jsp"%>
				
			</div>
		</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	jQuery(document).ready(function() {
			/*jQuery('#useraccount_table .group-checkable').change(function() {
				var set = jQuery(this).attr("data-set");
				var checked = jQuery(this).is(":checked");
				jQuery(set).each(function() {
					if (checked) {
						$(this).attr("checked", true);
					} else {
						$(this).attr("checked", false);
					}
				});
				jQuery.uniform.update(set);
			});*/
			$.fn.zTree.init($("#treeOrg"), settingMenu, zNodesMenu);
			zTree = $.fn.zTree.getZTreeObj("treeOrg");
			selectNodes();
		});
		var settingMenu = {
				check : {
					enable : false
				},
				async: {
					enable: true,
					url:"${root}/orgAndUser/loadSonDivision",
					autoParam:["id=divisionid"]
				},
			data : {
				simpleData : {
					enable : true
					}
				},
			callback : {
				onClick : goorgpage
			}
			};
			var zNodesMenu=${orgStr};
		function selectNodes(){
 				var node = zTree.getNodeByParam("id", "${orgid}", null);
 				zTree.selectNode(node);
 			}
	</script>
</body>

</html>
