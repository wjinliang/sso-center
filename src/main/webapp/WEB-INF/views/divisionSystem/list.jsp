
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
			<div class="box col_4">
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
			<div class="box col_8">
				<div class="container">
					<div id="search_bar" class="">
						<div class="box">
							<div class="box_border">
								<form action="./list" method="get" id="serchForm">
									<div class="box_center pt10 pb10">
										<table class="form_table" border="0" cellpadding="0"
											cellspacing="0">
											<input type="hidden" name="divisionId" id="divisionId"
												value="${searchModel.divisionId}">
											<input type="hidden" id="pageNum" name="thispage" value="">
											<input type="hidden" name="pagesize" value="${page.pageSize}">
											<tr>
												<td>系统ID</td>
												<td><span class="fl">
													<div class="select_border">
														<div class="select_containers ">
															<select name="systemId" class="select">
																	<option value="">全部</option>	
																<c:forEach items="${appPage.list}" var="app" varStatus="status">
																	<option 
																	<c:if test="${app.id eq searchModel.systemId}">selected
																	</c:if> value="${app.id }">${app.appName }</option>						
																</c:forEach>
															</select>
														</div>
													</div>
												</span></td>
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
								<input type="button" class="btn btn82 btn_add"
									onclick="javascript:newForm();" value="新增"> 
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
									<th width="270" style="text-align: left;">系统ID</th>
									<th width="127">单点区划编码</th>
									<th width="167">系统区划编码</th>
									<th width="130">操作</th>
								</tr>
								<c:forEach items="${page.list}" var="org" varStatus="status">
									<tr class="tr">

										<td class=""><input type="checkbox"
											value='${org.id }'></td>
										<td class="">
											${(page.pageNum-1)*page.pageSize+status.count}</td>
										<td class="td_left">
											${org.systemId}
										</td>
										<td>${org.divisionCode}</td>
										<td>${org.newDivisionCode}</td>
										<td><a class="ext_btn"
											href="javascript:editForm('${org.id }');">编辑
										</a> <input
									type="button"
									onclick="javascript:deleteAction('./delete?divisionSystemId=${org.id}');"
									name="button" class="ext_btn" value="删除"></td>
										
									</tr>
								</c:forEach>
							</table>
							<%@include file="../include/pageinfo.jsp"%>

						</div>
					</div>
					<div class="box_border">
						<div class="box_top">
							<b class="pl15" id='viewTitle'>编辑</b>
						</div>
						<div class="box_center">
							<form id="form1" action="save" method="post" class="jqtransform">
				               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
				                 <tr>
				                  <td class="td_right">区划ID<span class="red">*</span>：</td>
				                  <td class=""> 
				                    <input type="text" name="divisionId" class="input-text lh30" size="40">
				                  </td></tr>
				                 <tr>
				                  <td class="td_right">区划全称<span class="red">*</span>：</td>
				                  <td class=""> 
				                  <input type="hidden" name="id">
				                    <input type="text" name="divisionName" class="input-text lh30" size="40">
				                  </td></tr>
				                <tr >
				                  <td class="td_right">单点区划编码<span class="red">*</span>：</td>
				                  <td><input type="text" name="divisionCode" class="input-text lh30" size="40"></td>
				                  </tr>
				                <tr>
				                  <td class="td_right">所属系统<span class="red">*</span>：</td>
				                  <td class=""> 
									<span class="fl">
				                      <div class="select_border"> 
				                        <div class="select_containers "> 
				                        <select name="systemId" class="select">
											<c:forEach items="${appPage.list}" var="app" varStatus="status">
												<option value="${app.id }">${app.appName }</option>						
											</c:forEach>
										</select>
				                        </div> 
				                      </div> 
				                    </span>
				                  </td></tr>
				                  <tr >
				                  <td class="td_right">业务系统区划编码<span class="red">*</span>：</td>
				                  <td><input type="text" name="newDivisionCode" class="input-text lh30" size="40"></td>
				                </tr>
				                 <tr>
				                   <td class="td_right">&nbsp;</td>
				                   <td class="">
				                   
				                     <input type="submit" name="button" class="btn btn82 btn_save2" value="保存"> 
				                    <input type="reset" name="button" class="btn btn82 btn_res" value="重置"> 
				                   
				                   </td>
				                 </tr>
				               </table>
				             </form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/layer/layer.js"></script>
<%@include file="../include/formValidate.jsp"%>
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
			// 提交时验证表单
			var validator = $("#form1").validate({
				rules: {
				      divisionId:  {required: true},
				      divisionName:{required:true},
				      divisionCode:{required:true},
				      newDivisionCode:{required:true},
				      systemId:{required:true}
				}
			});
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
					openPage("list?divisionId=" + id);
				}
			}
		};
		var zNodesMenu = ${divisionStr};
		var selectNode;
		function selectNodes() {
			selectNode = zTree.getNodeByParam("id", "${searchModel.divisionId}",
					null);
			zTree.selectNode(selectNode);
			zTree.expandNode(selectNode, true, false);//指定选中ID节点展开  
		}
		var form1 = $("#form1");
		function newForm(){
			setTitle('新增');
			form1[0].reset();
			console.log(selectNode);
			form1.find("input[name='id']").val('');	
			form1.find("input[name='divisionId']").val(selectNode.id);	
			form1.find("input[name='divisionCode']").val(selectNode.code);	
			form1.find("input[name='divisionName']").val(selectNode.name);	
		}
		function editForm(id){
			setTitle('编辑');
			form1[0].reset();
			var url = "loadOne?divisionSystemId="+id;
			getAction(url,function(res){form1.setForm(res.data);});
		}
		function setTitle(title){
			$('#viewTitle').html(title);
		}
	</script>
</body>

</html>
