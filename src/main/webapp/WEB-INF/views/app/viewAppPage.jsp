
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
              <form action="../updateApp" method="post" class="jqtransform">
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                 <tr>
                  <td class="td_right">系统名称：</td>
                  <td class=""> 
                  <input type="hidden" name="id" value="${appInfo.id }">
                    <input type="text" name="appName" value="${appInfo.appName }" class="input-text lh30" size="40">
                  </td>
                  <td class="td_right">系统标识：</td><td><input type="text" name="appCode" value="${appInfo.appCode }" class="input-text lh30" size="40"></td>
                </tr>
                <tr>
                  <td class="td_right">同步类型：</td>
                  <td class=""> 
					<span class="fl">
                      <div class="select_border"> 
                        <div class="select_containers "> 
                        <select name="synType" class="select"> 
                        <option <c:if test="${appInfo.synType=='axis1' }">selected</c:if> value="axis1">axis1</option> 
                        <option <c:if test="${appInfo.synType=='axis2' }">selected</c:if> value="axis2">axis2</option> 
                        <option <c:if test="${appInfo.synType=='http' }">selected</c:if> value="http">http</option> 
                        </select> 
                        </div> 
                      </div> 
                    </span>
                  </td>
                  <td class="td_right">命名空间：</td><td><input type="text" name="packagename" value="${appInfo.packagename }" class="input-text lh30" size="40"></td>
                  </tr>
                  <tr>
                  <td class="td_right">参数名：</td><td><input type="text" name="paramName" value="${appInfo.paramName }" class="input-text lh30" size="40"></td>
                  <td class="td_right">同步地址：</td><td><input type="text" name="synPath" value="${appInfo.synPath }" class="input-text lh30" size="40"></td>
                  </tr>
                  <tr>
                  <td class="td_right">单点地址(联通)：</td><td><input type="text" name="appPath" value="${appInfo.appPath }" class="input-text lh30" size="40"></td>
                  <td class="td_right">单点地址(电信)：</td><td><input type="text" name="appPath1" value="${appInfo.appPath1 }" class="input-text lh30" size="40"></td>
                </tr>
                <tr >
                  <td class="td_right">操作级别：</td>
                  <td class="">
 
                    <span class="fl">
                      <div class="select_border"> 
                        <div class="select_containers "> 
                        <select name="opLevel" class="select"> 
                        <option  <c:if test="${appInfo.opLevel=='0' }">selected</c:if> value="0">部</option> 
                        <option <c:if test="${appInfo.opLevel=='1' }">selected</c:if> value="1">省</option> 
                        <option <c:if test="${appInfo.opLevel=='2' }">selected</c:if> value="2">市</option> 
                        <option <c:if test="${appInfo.opLevel=='3' }">selected</c:if> value="3">县</option> 
                        <option <c:if test="${appInfo.opLevel=='4' }">selected</c:if> value="4">乡镇</option> 
                        <option <c:if test="${appInfo.opLevel=='5' }">selected</c:if> value="5">村</option> 
                        </select> 
                        </div> 
                      </div> 
                    </span>
                  </td>
                  <td class="td_right">用户级别：</td>
                  <td class="">
 
                    <span class="fl">
                      <div class="select_border"> 
                        <div class="select_containers "> 
                        <select name="userLevel" class="select"> 
                        <option  <c:if test="${appInfo.userLevel=='0' }">selected</c:if> value="0">部</option> 
                        <option <c:if test="${appInfo.userLevel=='1' }">selected</c:if> value="1">省</option> 
                        <option <c:if test="${appInfo.userLevel=='2' }">selected</c:if> value="2">市</option> 
                        <option <c:if test="${appInfo.userLevel=='3' }">selected</c:if> value="3">县</option> 
                        <option <c:if test="${appInfo.userLevel=='4' }">selected</c:if> value="4">乡镇</option> 
                        <option <c:if test="${appInfo.userLevel=='5' }">selected</c:if> value="5">村</option> 
                        </select> 
                        </div> 
                      </div> 
                    </span>
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">系统描述：</td>
                  <td class="">
                    <textarea name="description" id="" cols="30" rows="10" class="textarea">${appInfo.description }</textarea>
                  </td>
                 </tr>
                 
                
                 <tr>
                   <td class="td_right">&nbsp;</td>
                   <td class="">
                   <c:if test="${param.detail=='1' }">
                   	<input type="button" name="button" onclick="javascript:window.location.href='../listApp'" class="btn btn82 btn_save2" value="返回"> 
                   </c:if>
                   <c:if test="${!(param.detail=='1')}">
                     <input type="submit" name="button" class="btn btn82 btn_save2" value="保存"> 
                    <input type="reset" name="button" class="btn btn82 btn_res" value="重置"> 
                   </c:if>
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
<script type="text/javascript" src="<%=basePath%>/assets/plugin/jquery.validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/jquery.validate/additional-methods.min.js"></script>
</body>
<script type="text/javascript">
	var detail = ${param.detail=="1"};
	$(function(){
		if(detail){
			$("table").find('input').each(function(){
				if($(this).attr('name')!='button')
					$(this).attr("disabled","true");
			});
			$("table").find('select').each(function(){
				$(this).attr("disabled","true");
			});
			$("table").find('textarea').each(function(){
				$(this).attr("disabled","true");
			});
		}
		
	});
</script>

</html>
