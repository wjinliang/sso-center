
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
<%@include file="../include/ztree.jsp"%>
</head>
<body>
	<div class="container">
		<div id="forms" class="mt10">
        <div class="box">
          <div class="box_border">
            <div class="box_top"><b class="pl15">表单</b></div>
            <div class="box_center">
              <form id="form1" action="./addOrUpdate" method="post" class="jqtransform">
            <div class="row">
            <div class="col_6">
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                 <tr>
                  <td class="td_right">角色名称：</td>
                  <td class=""> 
                  <input type="hidden" name="code" value="${role.code }">
                    <input type="text" name="name" value="${role.name }" class="input-text lh30" size="40">
                  </td>
                  </tr>
                
                <tr >
                  <td class="td_right">主页：</td><td><input type="text" name="homepage" value="${role.homepage }" class="input-text lh30" size="40"></td>
                </tr>
                
                <tr >
                  <td class="td_right">级别：</td>
                  <td class="">
 
                    <span class="fl">
                      <div class="select_border"> 
                        <div class="select_containers "> 
                        <select name="level" class="select"> 
                        <option  <c:if test="${role.level=='0' }">selected</c:if> value="0">0</option> 
                        <option <c:if test="${role.level=='1' }">selected</c:if> value="1">1</option> 
                        <option <c:if test="${role.level=='2' }">selected</c:if> value="2">2</option> 
                        <option <c:if test="${role.level=='3' }">selected</c:if> value="3">3</option> 
                        <option <c:if test="${role.level=='4' }">selected</c:if> value="4">4</option> 
                        <option <c:if test="${role.level=='5' }">selected</c:if> value="5">5</option> 
                        </select> 
                        </div> 
                      </div> 
                    </span>
                  </td>
                  
                 </tr>
                 <tr>
                  <td class="td_right">状态：</td>
                  <td class="">
                  	<span>
	                    <input type="radio" checked="checked"  name="enabled" value="true"> 可用
	                    <input type="radio" <c:if test="${role.enabled==false }"> checked="checked"</c:if> name="enabled" value="false"> 禁用
                    </span>
                  </td>
                  </tr>
                
                 <tr>
                  <td class="td_right">描述：</td>
                  <td class="">
                    <textarea name="detail" id="" cols="30" rows="10" class="textarea">${role.detail }</textarea>
                  </td>
                 </tr>
                
                 <tr>
                   <td class="td_right">&nbsp;</td>
                   <td class="">
                   
                   <c:if test="${!(param.detail=='1')}">
                     <input type="submit" name="button" class="btn btn82 btn_save2" value="保存"> 
                    <input type="reset" name="button" class="btn btn82 btn_res" value="重置"> 
                   </c:if>
                   <input type="button" name="button" onclick="javascript:window.location.href='./list'" class="btn btn82 btn_save2" value="返回"> 
                   </td>
                 </tr>
               </table>
                </div>
               <div class="col_6">
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                 <tr>
                  <td class="td_right">菜单：</td>
                  <td>
                  	<span class="fl">
                  	<input type="hidden" id="menuids" name="menuids">
	                  	<div id="treeMenu" class="ztree">
	                  	</div>
                  	</span>
                  </td>
                </tr>
               </div> 
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
</body>
<script type="text/javascript">
	$(function(){
		// 提交时验证表单
		var validator = $("#form1").validate({
			rules: {
			      name:  {
			        required: true,
			        minlength: 2
			      },
			},
			submitHandler: function(form) {
				//获取修改过得菜单节点
				getchange();
			      form.submit();
			}
		});
		
		//加载树
		$.fn.zTree.init($("#treeMenu"), settingMenu, zNodes);
	});
	
	var settingMenu = {
			view: {
				showIcon : false,
				selectedMulti: true
			},
			check : {
				chkboxType: { "Y": "s", "N": "ps" },
				enable : true,
			},
			data : {
				simpleData : {
					enable : true
				}
			}
		};

		var zNodes =${menus};
		function onCheck(e,treeId,treeNode){
            var treeObj=$.fn.zTree.getZTreeObj("treeMenu"),
            nodes=treeObj.getCheckedNodes(true),
            v=[];
            for(var i=0;i<nodes.length;i++){
            	v.put(nodes[i].id);
            }
        }
		function getchange(){
            var treeObj=$.fn.zTree.getZTreeObj("treeMenu");
            nodes=treeObj.getChangeCheckedNodes(true);
            var v=[];
            for(var i=0;i<nodes.length;i++){
            	v.push(nodes[i].id);
            }
            $("#menuids").val(v);
        }
		
</script>

</html>
