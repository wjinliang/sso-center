
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
              <form id="form1" action="../saveApp" method="post" class="jqtransform">
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                 <tr>
                  <td class="td_right">系统名称<span class="red">*</span>：</td>
                  <td class=""> 
                    <input type="text" name="appName" class="input-text lh30" size="40">
                  </td>
                  <td class="td_right">系统标识<span class="red">*</span>：</td><td><input type="text" name="appCode" class="input-text lh30" size="40"></td>
                </tr>
                <tr>
                  <td class="td_right">同步类型<span class="red">*</span>：</td>
                  <td class=""> 
					<span class="fl">
                      <div class="select_border"> 
                        <div class="select_containers "> 
                        <select name="synType" class="select"> 
                        <option value="axis1">axis1</option> 
                        <option value="axis2">axis2</option> 
                        <option value="http">http</option> 
                        </select> 
                        </div> 
                      </div> 
                    </span>
                  </td>
                  <td class="td_right">命名空间<span class="red">*</span>：</td><td><input type="text" name="packagename" class="input-text lh30" size="40"></td>
                  </tr>
                  <tr>
                  <td class="td_right">参数名：</td><td><input type="text" name="paramName" class="input-text lh30" size="40"></td>
                  <td class="td_right">同步地址<span class="red">*</span>：</td><td><input type="text" name="synPath" class="input-text lh30" size="40"></td>
                  </tr>
                  <tr>
                  <td class="td_right">单点地址(联通)<span class="red">*</span>：</td><td><input type="text" name="appPath" class="input-text lh30" size="40"></td>
                  <td class="td_right">单点地址(电信)：</td><td><input type="text" name="appPath1" class="input-text lh30" size="40"></td>
                </tr>
                <tr >
                  <td class="td_right">操作级别<span class="red">*</span>：</td>
                  <td class="">
 
                    <span class="fl">
                      <div class="select_border"> 
                        <div class="select_containers "> 
                        <select name="opLevel" class="select"> 
                        <option value="0">部</option> 
                        <option value="1">省</option> 
                        <option value="2">市</option> 
                        <option value="3">县</option> 
                        <option value="4">乡镇</option> 
                        <option value="5">村</option> 
                        </select> 
                        </div> 
                      </div> 
                    </span>
                  </td>
                  <td class="td_right">用户级别<span class="red">*</span>：</td>
                  <td class="">
 
                    <span class="fl">
                      <div class="select_border"> 
                        <div class="select_containers "> 
                        <select name="userLevel" class="select"> 
                        <option value="">请选择</option> 
                        <option value="0">部</option> 
                        <option value="1">省</option> 
                        <option value="2">市</option> 
                        <option value="3">县</option> 
                        <option value="4">乡镇</option> 
                        <option value="5">村</option> 
                        </select> 
                        </div> 
                      </div> 
                    </span>
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">系统描述：</td>
                  <td class="">
                    <textarea name="description" id="" cols="30" rows="10" class="textarea"></textarea>
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">状态<span class="red">*</span>：</td>
                  <td class="">
                  	<span>
	                    <input type="radio" name="status" value="1"> 启用
	                    <input type="radio" name="status" value="2"> 维护中
	                    <input type="radio" name="status" value="0"> 禁用
                    </span>
                  </td>
                  <td class="td_right">同步操作<span></span>：</td>
                  <td class="">
                  	<span>
	                    <input type="checkbox" name="isOrgSyn" value="true"> 同步机构
	                    <input type="checkbox" name="isOrgAuthorize" value="true"> 机构授权
	                    <input type="checkbox" name="isUserSyn" value="true"> 同步用户
	                    <input type="checkbox" name="isUserAuthorize" value="true"> 用户授权
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
<script type="text/javascript" src="<%=basePath%>/assets/plugin/layer/layer.js"></script>
<%@include file="../include/formValidate.jsp"%>
				
<script type="text/javascript">
$().ready(function() {
	// 提交时验证表单
	var validator = $("#form1").validate({
		rules: {
		      appName:  {
		        required: true,
		        minlength: 2,
			    remote:{url:"../checkunique",data:{'name':'appName','param':function(){return $("input[name='appName']").val();}}}
		      },
		      appCode:  {
			        required: true,
			        minlength: 2,
			        remote:{url:"../checkunique",data:{'name':'appCode','param':function(){return $("input[name='appCode']").val();}}}
		      },
		      synType:{required:true},
		      packagename:{required:true},
		      synPath:{required:true},
		      appPath:{required:true},
		      opLevel:{required:true},
		      userLevel:{required:true},
		      status:'required'
		},
		messages:{appName:{remote:"名称重复，请重新填写！"},appCode:{remote:"编码重复，请重新填写！"}}
	});
});
</script>

</body>

</html>
