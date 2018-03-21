
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
</head>
<body>
	<div class="container">
		<div id="forms" class="mt10">
        <div class="box">
          <div class="box_border">
            <div class="box_top"><b class="pl15">表单</b></div>
            <div class="box_center">
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                 
                <tr>
                  <td class="td_right">操作类型<span class="red">*</span>：</td>
                  <td class=""> 
					<span class="fl">
                      <div class="select_border"> 
                        <div class="select_containers "> 
                        <select name="action" class="select"> 
                        <option value="/api/addSynUser">添加用户同步记录</option> 
                        <option value="/api/deleteSynUser">删除用户同步记录</option> 
                        <option value="/api/addSynOrg">添加机构同步记录</option> 
                        <option value="/api/deleteSynOrg">删除机构同步记录</option> 
                        <option value="/api/addSynDivision">添加区划同步记录</option> 
                        <option value="/api/deleteSynDivision">删除区划同步记录</option> 
                        <option value="/api/mvOrg">机构修改所属区划</option> 
                        <option value="/api/mvDivision">区划修改所属区划</option> 
                        <option value="/api/addRole">用户添加角色</option> 
                        <option value="/api/deleteRole">用户删除角色</option> 
                        </select> 
                        </div> 
                      </div> 
                    </span>
                  </td>
                  </tr>
                  <tr>
                  <td class="td_right">主键<span class="red">*</span>：</td><td><input type="text" name="id" class="input-text lh30" size="40"></td>
                  </tr>
                  <tr>
                  <td class="td_right">更改值<span class="red">*</span>：</td><td><input type="text" name="appId" class="input-text lh30" size="40"></td>
                </tr>
                
                
                 <tr>
                   <td class="td_right">&nbsp;</td>
                   <td class="">
                     <input type="button" id="commit" name="button" class="btn btn82 btn_save2" value="提交"> 
                    <input type="reset" name="button" class="btn btn82 btn_res" value="重置"> 
                   </td>
                 </tr>
               </table>
            </div>
          </div>
        </div>
     </div>
   </div> 
</div>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/layer/layer.js"></script>
				
<script type="text/javascript">
	$(function(){
		$("#commit").bind("click",function(){
			var url=root+$("select[name='action']").val();
			var id = $("input[name='id']").val();
			var appId = $("input[name='appId']").val();
			$.post(url,{id:id,appId:appId},function(res){
				if(res.code==200){
					layer.alert("操作成功");
				}
			});
			
		});
		
	});


</script>

</body>

</html>
