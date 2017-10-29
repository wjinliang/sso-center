
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
					<div class="box_border">
						<div class="box_top">
							<b class="pl15">编辑</b>
						</div>
						<div class="box_center">
							<div id="orgList"></div>
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
