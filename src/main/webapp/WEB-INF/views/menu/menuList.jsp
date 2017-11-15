
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
	src="<%=basePath%>/assets/plugin/jquery.cookie/jquery.cookie.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/assets/js/commonAction.js"></script>
<%@include file="../include/ztree.jsp"%>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="box col_5">
				<div class="container">
					<div class="box_border">
						<div class="box_top">
							<b class="pl15">菜单树</b>
							<span class="fr pr5">
								<a class="ext_btn"
											href="javascript:addTopMenu();">
												添加顶级菜单 </a>
							</span>
						</div>
						<div class="box_center">
							<div class="ztree" id="menuTree" style="height: 500px;overflow-y: auto;"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="box col_7">
				<div class="container">
					<div class="box_border">
						<div class="box_top">
							<b class="pl15" id='viewTitle'>查看</b>
						</div>
						<div class="box_center">
							<form id="form1" action="insertOrUpdate" method="post" class="jqtransform">
				               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
				                 <tr>
				                  <td class="td_right">菜单名称：</td>
				                  <td class=""> 
					                  <input type="hidden" name="id">
					                  <input type="hidden" name="pid">
				                      <input type="text" name="name" class="input-text lh30" size="40">
				                  </td>
				                </tr>
				                <tr >
				                  <td class="td_right">菜单地址：</td><td><input type="text" name="menuurl" class="input-text lh30" size="40"></td>
				                  </tr>
				                <tr >
				                  <td class="td_right">是否展示：</td>
				                  <td>
				                  	<span>
					                    <input type="radio" name="isshow" value="true"> 是
					                    <input type="radio" name="isshow" value="false"> 否
				                    </span>
				                  </td>
				                </tr>
				                <tr >
				                  <td class="td_right">菜单描述：</td>
				                  <td>
				                  <textarea name="detail" id="" cols="30" rows="10" class="textarea"></textarea>
				                  </td>
				                </tr>
				                <tr >
				                  <td class="td_right">排序：</td><td><input type="text" name="seq" class="input-text lh30" size="40"></td>
				                  </tr>
				                <tr >
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
	var zNodes =${menuStr};
	var COOKIE_LASRNODEID = "LAST_menu_ID_7";
	var zTree;
	jQuery(document).ready(function() {
		$.fn.zTree.init($("#menuTree"), settingMenu, zNodes);
		zTree = $.fn.zTree.getZTreeObj("menuTree");
			var currentTableId = $.cookie(COOKIE_LASRNODEID);
			var node = zTree.getNodeByParam('id', currentTableId);//获取id为1的点  
			if(node){
				 if(!node.isParent){
					 zTree.selectNode(node);//选择点  
				}else{
					zTree.expandNode(node, true, false);//指定选中ID节点展开  
				}
			}
			//console.log(node);
		// 提交时验证表单
		var validator = $("#form1").validate({
			rules: {
			      name:  {
				        required: true,
				        minlength: 2
				      }
			     
			}
		})
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
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false,
			drag:{
				isCopy : false,
				isMove : true,
				prev : true,
				inner : true,
				next : true
				}
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
		enable: true
	},
	callback : {
		beforeDrag: beforeDrag,
		beforeDrop: beforeDrop,
		onDrop: onDrop,
		onExpand: zTreeOnExpand,
		onClick : function(event, treeId, treeNode) {
			currentTableId = treeNode.id;
			//if(treeNode.pid==1)
				$.cookie(COOKIE_LASRNODEID,currentTableId,{expires:7});
				editDivsion(currentTableId);
			//alert(currentTableId);
		}
	}
};
	function zTreeOnExpand(event, treeId, treeNode) {
		currentTableId = treeNode.id;
	};
function changeGrag(){
	var fl = document.getElementById("isdrag").checked;
	zTree.setting.edit.enable=fl;
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

function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr ="<span class='button2 add' id='addBtn_" + treeNode.tId
		+ "' title='增加子级菜单' onfocus='this.blur();'></span>"+
		"<span class='button2 edit' id='editBtn_" + treeNode.tId
		+ "' title='编辑菜单' onfocus='this.blur();'></span>"+

		"<span class='button2 delete' id='removeBtn_" + treeNode.tId
		+ "' title='删除菜单' onfocus='this.blur();'></span>"+
		"<span class='button2 up' id='upBtn_" + treeNode.tId
		+ "' title='上移' onfocus='this.blur();'></span>"+
		"<span class='button2 down' id='downBtn_" + treeNode.tId
		+ "' title='下移' onfocus='this.blur();'></span>";
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
function moveUp() {
	var Node = zTree.getSelectedNodes()[0];
	if (Node) {
		if(Node.isFirstNode){
			return;
		}else {
			var url = "setseq?currentid=" + Node.id+"&targetid="+Node.getPreNode().id+"&moveType=prev&moveMode=same";
			postAction(url);
		}
	} 
}


function moveDown() {
	var Node = zTree.getSelectedNodes()[0];
	if (Node) {
		if(Node.isLastNode){
			return;
		}else {
			var url = "setseq?currentid=" + Node.id+"&targetid="+Node.getNextNode().id+"&moveType=next&moveMode=same";
			postAction(url);
		}
	} 
}

function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	if(targetNode!=null){
		var url = "setseq?currentid=" + treeNodes[0].id+"&targetid="+targetNode.id+"&moveType="+moveType+"&moveMode="+moveMode;
		postAction(url);	
	}
}

/* function OnRightClick(event, treeId, treeNode) {
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
 */


function addMenu() {
	if (zTree.getSelectedNodes()[0]) {
		var orgid = zTree.getSelectedNodes()[0].id;
		newDivsion(orgid);
	} else {
		alert("请选中父菜单节点");
	}
}
function addTopMenu() {
	newDivsion('');
}
function editMenu() {
	if (zTree.getSelectedNodes()[0]) {
		var orgid = zTree.getSelectedNodes()[0].id;
		editDivsion(orgid);
	}
}
 
function expandAll(flag){
	zTree.expandAll(flag);
}
function deleteMenu() {
	if (zTree.getSelectedNodes()[0]) {

		var id = zTree.getSelectedNodes()[0].id;
		deleteAction("menu_del?id="+id);
	}
}

var form1 = $("#form1");
function newDivsion(pid){
	setTitle('新增');
	form1[0].reset();
	form1.find("input[name='pid']").val(pid);	
	form1.find("input[name='id']").val('');	
}
function editDivsion(id){
	setTitle('编辑');
	form1[0].reset();
	var url = "menu_load?id="+id;
	getAction(url,function(res){form1.setForm(res.data);});
}
function setTitle(title){
	$('#viewTitle').html(title);
}

	</script>
</body>

</html>
