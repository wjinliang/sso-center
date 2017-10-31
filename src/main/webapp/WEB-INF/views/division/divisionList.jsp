
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
	type="text/css"><style>
div#rMenu {
	position: absolute;
	visibility: hidden;
	top: 0;
	text-align: center;
	padding: 2px;
}

div#rMenu ul li {
	cursor: pointer;
}
ul.ztree {
	height: 290px;
}
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.ztree li span.button2{
	line-height:0; margin:0; width:16px; height:16px; display: inline-block; vertical-align:middle;
	border:0 none; cursor: pointer;outline:none;
	background-color:transparent; background-repeat:no-repeat; background-attachment: scroll;
}
.ztree li span.button2.up {
	background-image:url("<%=basePath%>/assets/plugin/zTree/css/zTreeStyle/img/diy/arrow_up.png");
}
.ztree li span.button2.down {
	background-image:url("<%=basePath%>/assets/plugin/zTree/css/zTreeStyle/img/diy/arrow_down.png");
}
.ztree li span.button2.edit {
	background-image:url("<%=basePath%>/assets/plugin/zTree/css/zTreeStyle/img/diy/page_edit.png");
}
.ztree li span.button2.add {
	background-image:url("<%=basePath%>/assets/plugin/zTree/css/zTreeStyle/img/diy/page_add.png");
}
.ztree li span.button2.delete {
	background-image:url("<%=basePath%>/assets/plugin/zTree/css/zTreeStyle/img/diy/page_delete.png");
}
.ztree li span.button2.view {
	background-image:url("<%=basePath%>/assets/plugin/zTree/css/zTreeStyle/img/diy/page.png");
}

</style>
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
	src="<%=basePath%>/assets/plugin/zTree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>/assets/plugin/zTree/js/jquery.ztree.exedit-3.5.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="box col_5">
				<div class="container">
					<div class="box_border">
						<div class="box_top">
							<b class="pl15">区划树</b>
						</div>
						<div class="box_center">
							<div class="ztree" id="divisionTree" style="height: 500px;overflow-y: auto;"></div>
						</div>
						<div id="rMenu">
			<ul class="dropdown-menu dropdown-context">
				<li class="nav-header" id="m_add" onclick="addMenu();">增加子级区划</li>
				<li class="nav-header" id="m_addroot" onclick="addTopMenu();">增加顶级区划</li>
				<li class="nav-header" id="m_del" onclick="deleteMenu();">删除区划</li>
				<li class="nav-header" id="m_edit" onclick="editMenu();">编辑区划</li>
			</ul>
			</div>
					</div>
				</div>
			</div>
			<div class="box col_7">
				<div class="container">
					<div class="box_border">
						<div class="box_top">
							<b class="pl15">编辑</b>
						</div>
						<div class="box_center">
							<form id="form1" action="save" method="post" class="jqtransform">
				               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
				                 <tr>
				                  <td class="td_right">区划全称：</td>
				                  <td class=""> 
				                  <input type="hidden" name="id">
				                  <input type="hidden" name="parentId">
				                  
				                    <input type="text" name="fullname" class="input-text lh30" size="40">
				                  </td></tr>
				                <tr >
				                  <td class="td_right">区划简称：</td><td><input type="text" name="name" class="input-text lh30" size="40"></td>
				                </tr>
				                <tr >
				                  <td class="td_right">行政编码：</td><td><input type="text" name="code" class="input-text lh30" size="40"></td>
				                  </tr>
				                <tr>
				                  <td class="td_right">区划级别：</td>
				                  <td class=""> 
									<span class="fl">
				                      <div class="select_border"> 
				                        <div class="select_containers "> 
				                        <select name="type" class="select"> 
				                        	<option value="0">中央</option>
											<option value="1">省级</option>
											<option value="2">直辖市</option>
											<option value="3">计划单列市</option>
											<option value="4">市级</option>
											<option value="5">区县</option>
											<option value="6">乡镇</option>
											<option value="7">村</option>
										</select>
				                        </div> 
				                      </div> 
				                    </span>
				                  </td></tr>
				                <tr >
				                  <td class="td_right">所属大区：</td>
				                  <td class="">
				 
				                    <span class="fl">
				                      <div class="select_border"> 
				                        <div class="select_containers "> 
				                        <select name="bigdivision" class="select"> 
				                        	<option value="1" >华北</option>
											<option value="2" >东北</option>
											<option value="3" >华东</option>
											<option value="4" >华中</option>
											<option value="5" >西南</option>
											<option value="6" >西北</option>
											<option value="7" >华南</option>
											<option value="8" >港澳台地区</option>
											<option value="9" >兵团</option>
										</select>
				                        </div> 
				                      </div> 
				                    </span>
				                  </td></tr>
				                <tr >
				                  <td class="td_right">区划等级：</td>
				                  <td class="">
				 
				                    <span class="fl">
				                      <div class="select_border"> 
				                        <div class="select_containers "> 
				                        <select name="level" class="select"> 
				                        	<option value="0" >0</option>
											<option value="1" >1</option>
											<option value="2" >2</option>
											<option value="3" >3</option>
											<option value="4" >4</option>
											<option value="5" >5</option>
				                        </select> 
				                        </div> 
				                      </div> 
				                    </span>
				                  </td>
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
	<%@include file="../include/formValidate.jsp"%>
	<script type="text/javascript">
	var zNodes =${divisionStr};
	var COOKIE_LASRNODEID = "LAST_DIVISION_ID_7";
	var zTree;
	jQuery(document).ready(function() {
		$.fn.zTree.init($("#divisionTree"), settingMenu, zNodes);
		zTree = $.fn.zTree.getZTreeObj("divisionTree");
		 zTree.setting.edit.drag.isCopy = false;
		zTree.setting.edit.drag.isMove = true;
		zTree.setting.edit.drag.prev = true;
		zTree.setting.edit.drag.inner = true;
		zTree.setting.edit.drag.next = true;
		
		var currentTableId = '1';
		var node = zTree.getNodeByParam('id', currentTableId);//获取id为1的点  
		//tableTree.selectNode(node);//选择点  
		if(node){
			 if(!node.isParent){
				node = node.getParentNode();
			}
		}
		//console.log(node);
		zTree.expandNode(node, true, false);//指定选中ID节点展开  
		// 提交时验证表单
		var validator = $("#form1").validate({
			rules: {
			      fullname:  {
			        required: true,
			        minlength: 2
			      },
			      name:  {
				        required: true,
				        minlength: 2
				      },
			      code:{required:true,
			    	  remote:{  
		                url: "checkCode",  
		                data: {  
		                    'code': function () { return $("input[name='code']").val();},
		                    'id':function(){return $("input[name='id']").val(); }
		                }  
		            }  },
			      level:{required:true},
			      bigDivision:{required:true},
			      type:{required:true}
			},
			messages:{code:{remote:"编码重复，请重新填写！"}}
		});
	});
	var zTree;
	var rMenu;
	var settingMenu = {
	view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			showIcon : false,
			selectedMulti: false
		},
	edit: {
			enable: false,
			showRemoveBtn: false,
			showRenameBtn: false
	},
	check : {
		enable : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	async : {
		<%-- enable : true,
		dataType : "text",
		url : "<%=basePath%>division/load",
		autoParam : [ "id", "name", "pId" ] --%>
		enable: true,
		url:"loadSonDivision",
		autoParam:["id=divisionid"]
	},
	callback : {
		onRightClick : OnRightClick,
		beforeDrag: beforeDrag,
		beforeDrop: beforeDrop,
		onDrop: onDrop,
		onClick : function(event, treeId, treeNode) {
			currentTableId = treeNode.id;
			if(treeNode.pId==1)
				$.cookie(COOKIE_LASRNODEID,currentTableId,{expires:7});
			//alert(currentTableId);
		}
	}
};

function changeGrag(){
	var fl = document.getElementById("isdrag").checked;
	zTree.setting.edit.enable=fl;
}

function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr ="<span class='button2 add' id='addBtn_" + treeNode.tId
			+ "' title='增加子级区划' onfocus='this.blur();'></span>"+
			"<span class='button2 edit' id='editBtn_" + treeNode.tId
			+ "' title='编辑区划' onfocus='this.blur();'></span>"+
			"<span class='button2 view' id='viewBtn_" + treeNode.tId
			+ "' title='查看区划' onfocus='this.blur();'></span>"+
			"<span class='button2 delete' id='removeBtn_" + treeNode.tId
			+ "' title='删除区划' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			zTree.selectNode(treeNode);
			addMenu();
			return false;
		});
		var btn2 = $("#editBtn_"+treeNode.tId);
		if (btn2) btn2.bind("click", function(){
			zTree.selectNode(treeNode);
			editMenu();
			return false;
		});
		var btn3 = $("#removeBtn_"+treeNode.tId);
		if (btn3) btn3.bind("click", function(){
			zTree.selectNode(treeNode);
			deleteMenu();
			return false;
		});
		var btn4 = $("#viewBtn_"+treeNode.tId);
		if (btn4) btn4.bind("click", function(){
			zTree.selectNode(treeNode);
			viewMenu();
			return false;
		});
		var btn5 = $("#upBtn_"+treeNode.tId);
		if (btn5) btn5.bind("click", function(){
			zTree.selectNode(treeNode);
			moveUp();
			return false;
		});
		var btn6 = $("#downBtn_"+treeNode.tId);
		if (btn6) btn6.bind("click", function(){
			zTree.selectNode(treeNode);
			moveDown();
			return false;
		});
}
function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
		$("#editBtn_"+treeNode.tId).unbind().remove();
		$("#removeBtn_"+treeNode.tId).unbind().remove();
		$("#viewBtn_"+treeNode.tId).unbind().remove();
		$("#upBtn_"+treeNode.tId).unbind().remove();
		$("#downBtn_"+treeNode.tId).unbind().remove();
}

function beforeDrag(treeId, treeNodes) {
		for (var i=0,l=treeNodes.length; i<l; i++) {
			if (treeNodes[i].drag === false) {
				return false;
			}
		}
		return true;
}
var moveMode;
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
	if(treeNodes[0].getParentNode()!=null&&targetNode.getParentNode()!=null){
		if(treeNodes[0].getParentNode().id==targetNode.getParentNode().id){
			moveMode="same";
		}else{
			moveMode="diffrent";
		}
	}else if(treeNodes[0].getParentNode()==null&&targetNode.getParentNode()==null){
		moveMode="same";
	}else{
		moveMode="diffrent";
	}
	return targetNode ? targetNode.drop !== false : true;
		
}
function moveUp() {
	var Node = zTree.getSelectedNodes()[0];
	if (Node) {
		if(Node.isFirstNode){
			return;
		}else {
			$.ajax({
			type : "POST",
			data : "currentid=" + Node.id+"&targetid="+Node.getPreNode().id+"&moveType=prev&moveMode=same",
			url : '<%=basePath%>division/setseq',
			success : function(data) {
				if (data == "ok") {
					refreshorgtree();
				}
			}
		});
		}
	} 
}


function moveDown() {
	var Node = zTree.getSelectedNodes()[0];
	if (Node) {
		if(Node.isLastNode){
			return;
		}else {
			$.ajax({
			type : "POST",
			data : "currentid=" + Node.id+"&targetid="+Node.getNextNode().id+"&moveType=next&moveMode=same",
			url : '<%=basePath%>division/setseq',
			success : function(data) {
				if (data == "ok") {
					refreshorgtree();
				}
			}
		});
		}
	} 
}

function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	if(targetNode!=null){
	$.ajax({
			type : "POST",
			data : "currentid=" + treeNodes[0].id+"&targetid="+targetNode.id+"&moveType="+moveType+"&moveMode="+moveMode,
			url : '<%=basePath%>division/setseq',
			success : function(data) {
				if (data == "ok") {
					refreshorgtree();
				}
			}
	});
	}
}

function OnRightClick(event, treeId, treeNode) {
	var x=event.pageX||(event.clientX+(document.documentElement.scrollLeft||document.body.scrollLeft));
	var y=event.pageY||(event.clientY+(document.documentElement.scrollTop||document.body.scrollTop));
	if (!treeNode && event.target.tagName.toLowerCase() != "button"
			&& $(event.target).parents("a").length == 0) {
		zTree.cancelSelectedNode();
		showRMenu("root", x, y);
	} else if (treeNode && !treeNode.noR) {
		zTree.selectNode(treeNode);
		showRMenu("node", x, y);
	}
}
function showRMenu(type, x, y) {
	$("#rMenu ul").show();
	if (type == "root") {
		$("#m_del").hide();
		$("#m_edit").hide();
		$("#m_add").hide();
		$("#m_addroot").show();
	} else {
		$("#m_del").show();
		$("#m_edit").show();
		$("#m_add").show();
		$("#m_addroot").hide();
	}
// 	rMenu.css({
// 		"top" : y + "px",
// 		"left" : x + "px",
// 		"visibility" : "visible"
// 	});
	$("body").bind("mousedown", onBodyMouseDown);
}
function hideRMenu() {
	/* if (rMenu)
		rMenu.css({
			"visibility" : "hidden"
		});
	$("body").unbind("mousedown", onBodyMouseDown); */
}
function onBodyMouseDown(event) {
	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
		rMenu.css({
			"visibility" : "hidden"
		});
	}
}

function opendg(dgurl, dgw, dgh) {
	$.dialog({
		title : '',
		width : dgw,
		height : dgh,
		lock : true,
		max : false,
		min : false,
		id : 'menupop',
		content : 'url:' + dgurl
	});
}
function addMenu() {
	hideRMenu();
	if (zTree.getSelectedNodes()[0]) {
		var orgid = zTree.getSelectedNodes()[0].id;
		newDivsion(orgid);
	} else {
		alert("请选中父区划节点");
	}
}
function addTopMenu() {
	hideRMenu();
	newDivsion('');
}
function editMenu() {
	hideRMenu();
	if (zTree.getSelectedNodes()[0]) {
		var orgid = zTree.getSelectedNodes()[0].id;
		editDivsion(orgid);
	}
}
function viewMenu(){
	hideRMenu();
	if (zTree.getSelectedNodes()[0]) {
		var orgid = zTree.getSelectedNodes()[0].id;
		opendg('<%=basePath%>division/form/view?orgid=' + orgid, 600,
				500);
	}
}
function refreshorgtree() {
	$("#alert").show();
	var zTree2 = $.fn.zTree.getZTreeObj("treeOrg");
	zTree2.reAsyncChildNodes(null, "refresh");
	setTimeout("alertHide()",2000);
}
function alertHide(){
	$("#alert").fadeOut();
}
function expandAll(flag){
	zTree.expandAll(flag);
}
function deleteMenu() {
	hideRMenu();
	window.parent.bootbox.confirm("确定删除吗？", function(result) {
		if (result) {
			if (zTree.getSelectedNodes()[0]) {
				var orgid = zTree.getSelectedNodes()[0].id;
				$.ajax({
					type : "POST",
					data : "orgid=" + orgid,
					url : '<%=basePath%>syndivision/delete',
					success : function(data) {
						if (data == "ok") {
							window.location.reload();
						}else if(data == "false"){
							alert("删除时出现错误！");
						}else{
							alert(data);
							window.location.reload();
						}
					}
				});
			}
		}
	});
}

var form1 = $("#form1");
function newDivsion(pid){
	form1[0].reset();
	form1.find("input[name='parentId']").val(pid);	
	form1.find("input[name='id']").val('');	
}
function editDivsion(id){
	form1[0].reset();
	//form.setForm({fullname:'北京市朝阳区',name:'朝阳区',level:'5'});
	$.ajax({
		url:"loadOne?divisionId="+id,
		type:'get',
		success:function(res){
			if(res.code="200"){
				form1.setForm(res.data);
			}
		}
	})
}


	</script>
</body>

</html>