
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/user.tld" prefix="d"%>
<%@ taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
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
<link rel="stylesheet" href="<%=basePath%>/assets/plugin/jquery.sweetDropdown/min/jquery.sweet-dropdown.min.css">
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
<script type="text/javascript"
	src="<%=basePath%>/assets/plugin/jquery.sweetDropdown/min/jquery.sweet-dropdown.min.js"></script>
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
						<div class="box_center" style="height: 500px; overflow-y: auto;">
							<div class="ztree" id="divisionTree"></div>
						</div>

					</div>
				</div>
			</div>
			<div class="box col_9">
				<div class="container">
					<div id="search_bar" class="">
						<div class="box">
							<div class="box_border">
								<form action="./listUsers" method="get" id="serchForm">
									<div class="box_center pt10 pb10">
										<table class="form_table" border="0" cellpadding="0"
											cellspacing="0">
											<input type="hidden" name="divisionId" id="divisionId"
												value="${searchModelOrg.divisionId}">
											<input type="hidden" name="orgId" id="orgId"
												value="${searchModel.orgId}">
											<input type="hidden" id="pageNum" name="thispage" value="">
											<input type="hidden" name="pagesize" value="${page.pageSize}">
											<tr>
												<td>用户名</td>
												<td><input type="text" name="name"
													value="${searchModel.name }" class="input-text lh25"
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

							<div id="button" class="mt10">
								<input type="button"
									onclick="javascript:openPage('./user/new?divisionId=${searchModelOrg.divisionId}&orgId=${searchModel.orgId }');"
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
					<c:set var="apps" value="${d:gCAS() }"></c:set>
					<div id="table" class="mt10">
						<div class="box span10 oh">
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="list_table">
								<tr>
									<th width="30">#</th>
									<th width="30">#</th>
									<th>用户名(登录名)</th>
									<th>所在机构</th>
									<th>最后登录时间</th>
									<th>操作</th>
								</tr>
								<c:forEach items="${page.list}" var="user" varStatus="status">
									<tr class="tr">

										<td class="td_center"><input type="checkbox"
											value='${user.code }'></td>
										<td class="td_center">
											${(page.pageNum-1)*page.pageSize+status.count}</td>
										<td><a
											href="javascript:openPage('user/edit?code=${user.code }&orgId=${user.orgId }&divisionId=${searchModelOrg.divisionId }');">
												${user.name}(${user.loginname })</a></td>
										<td>${user.orgId}</td>
										<td>${user.lastlogintime }</td>
										<td>
											<c:set var="act" value="0" ></c:set>    
											<c:forEach items="${apps}" var="ap" varStatus="status" >
										      <c:if test="${fn:contains(user.systemId,ap.appName)}">
										      	<c:set var="act" value="1" ></c:set>  
										      </c:if> 
											</c:forEach>
											<c:if test="${act =='0'}">
												<a class="ext_btn" href="javascript:viewPage('user/edit?code=${user.code }&orgId=${user.orgId }&divisionId=${searchModelOrg.divisionId }');">查看</a>
											</c:if>
											<c:if test="${act =='1'}">
												<a class="ext_btn ext_btn_success" data-dropdown="#dropdown-standard${user.code }">操作</a>
												<div class="dropdown-menu dropdown-anchor-top-right dropdown-has-anchor" id="dropdown-standard${user.code }">
												<ul>
													<li>
													<a 
														href="javascript:viewPage('user/edit?code=${user.code }&orgId=${user.orgId }&divisionId=${searchModelOrg.divisionId }');">
															查看 </a> </li>
													<li><a
													href="javascript:openPage('user/edit?code=${user.code }&orgId=${user.orgId }&divisionId=${searchModelOrg.divisionId }');">
														编辑 </a></li>
													<li><a href="javascript:dialogPage('设置角色','user/setRole?code=${user.code }&id=${user.orgId }&divisionId=${searchModelOrg.divisionId }')">设置角色</a></li>
													<li><a href="javascript:repassword('${user.code }')">重置密码</a></li>
													<li class="divider"></li>
													<li><a href="javascript:openPage('./listMergeUsers?code=${user.code }&id=${user.orgId }&divisionId=${searchModelOrg.divisionId }');">
													关联用户</a></li>
													<li><a href="javascript:openPage('./listMergeUsers?code=${user.code }&id=${user.orgId }&divisionId=${searchModelOrg.divisionId }');">
													用户授权</a></li>
												</ul>
												</div>
											 </c:if> 		
										</td>

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
			$.fn.zTree.init($("#divisionTree"), settingMenu, zNodesMenu);
			zTree = $.fn.zTree.getZTreeObj("divisionTree");
			selectNodes();
		});
		var settingMenu = {
			check : {
				enable : false
			},
			async : {
				enable : true,
				url : "../division/loadSonDivision",
				autoParam : [ "id=divisionid" ]
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onClick : function(event, treeId, treeNode) {
					var id = treeNode.id;
					openPage("listOrgs?divisionId=" + id);
				}
			}
		};
		var zNodesMenu = ${divisionStr	};
		function selectNodes() {
			var node = zTree.getNodeByParam("id",
					"${searchModelOrg.divisionId}", null);
			zTree.selectNode(node);
			zTree.expandNode(node, true, false);//指定选中ID节点展开  
		}
		function repassword(userId){
			prompt('请输入新密码',function(text){
				alert(text+""+userId);
			var x=false;
			$.ajax({
				url:"resetPass",
				type:'post',
				async: false,
				data:{newp:text,userId:userid},
				beforeSend:function(){
				  layer.msg('请稍后', {icon: 1});
				},
				success:function(data){
					if(data.code==200){
						showSuccess('操作成功');
					}else if(data.code==301){
						window.location.href=root+data.data;
					}
					else{
						layer.msg('操作失败', {icon: 1});
						
					}
				},
				error:function(){
					layer.msg('操作失败', {icon: 1});
					//window.location.reload();
				}
			});
			return x;
		})
		}
	</script>
</body>

</html>
