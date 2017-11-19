
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
                  <td class="td_right">系统名称：</td>
                  <td class=""> 
                  <input type="hidden" name="id" value="${appInfo.id }">
                    <input type="text" name="appName" value="${appInfo.appName }" class="input-text lh30" size="40">
                  </td>
                  </tr>
                  <tr>
                  <td class="td_right">系统标识：</td><td><input type="text" name="appCode" value="${appInfo.appCode }" class="input-text lh30" size="40"></td>
                </tr>
                 <tr>
                  <td class="td_right">系统地址：</td><td><input type="text" name="appPath" value="${appInfo.appPath }" class="input-text lh30" size="40"></td>
                </tr>
                 <tr>
                  <td class="td_right">系统描述：</td>
                  <td class="">
                    <textarea name="description" id="" cols="30" rows="10" class="textarea">${appInfo.description }</textarea>
                  </td>
                 </tr>
                 </tr>
                
                 <tr>
                   <td class="td_right">&nbsp;</td>
                   <td class="">
                   
                   <c:if test="${!(param.detail=='1')}">
                     <input type="submit" name="button" class="btn btn82 btn_save2" value="保存"> 
                    <input type="reset" name="button" class="btn btn82 btn_res" value="重置"> 
                   </c:if>
                   <input type="button" name="button" onclick="javascript:window.location.href='../listApp'" class="btn btn82 btn_save2" value="返回"> 
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
</body>
<script type="text/javascript">
	$(function(){
		
		// 提交时验证表单
		var validator = $("#form1").validate({
			rules: {
			      appName:  {
			        required: true,
			        minlength: 2,
			        remote:{url:"../checkunique",data:{'name':'appName','param':function(){return $("input[name='appName']").val();},'id':function(){return $('input[name="id"]').val();}}}
			      },
			      appCode:  {
				        required: true,
				        minlength: 2,
				        remote:{url:"../checkunique",data:{'name':'appCode','param':function(){return $("input[name='appCode']").val();},'id':function(){return $('input[name="id"]').val();}}}
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

</html>
