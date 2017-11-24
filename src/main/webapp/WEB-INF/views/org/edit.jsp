
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
              <form id="form1" action="../save" method="post" class="jqtransform">
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                 <tr>
                  <td class="td_right">机构名称：</td>
                  <td class=""> 
	                  <input type="hidden" name="id" value="${org.id }"/>
					  <input type="hidden" name="parentId" value="${org.parentId}"/>
                    <input type="text" name="name" class="input-text lh30" value="${org.name }" size="40">
                  </td>
                  <td class="td_right">机构编码：</td><td><input readonly="true" type="text" name="code" value="${org.code }" class="input-text lh30" size="40"></td>
                </tr>
                <tr>
                  <td class="td_right">所属区划：</td>
                  <td class=""> 
	                  <input type="hidden" name="divisionId" class="input-text lh30" value="${division.id }" size="40">
	                  <input type="hidden" name="divisionCode" class="input-text lh30" value="${division.code }" size="40">
	                  <input type="hidden" name="seq" class="input-text lh30" value="${org.seq }" size="40">
					<input type="text" readonly="true" name="divisionName" class="input-text lh30" value="${division.name }" size="40">
                  </td>
                  <td class="td_right">机构类型：</td>
                  <td>
                  
                    <span class="fl">
                      <div class="select_border"> 
                        <div class="select_containers "> 
                  			<select name="type" id="type" class="select">
								<option value="0"  <c:if test="${org.type=='0'}">selected="selected" </c:if>>部</option>
								<option value="1"  <c:if test="${org.type=='1'}">selected="selected" </c:if>>省</option>
								<option value="2"  <c:if test="${org.type=='2'}">selected="selected" </c:if>>市</option>
								<option value="3"  <c:if test="${org.type=='3'}">selected="selected" </c:if>>县</option>
								<option value="4"  <c:if test="${org.type=='4'}">selected="selected" </c:if>>镇（乡）</option>
								<option value="5"  <c:if test="${org.type=='5'}">selected="selected" </c:if>>村</option>
							</select>
							</div>
						</div>
					</span>
					</td>
                  </tr>
                  <tr>
                  <td class="td_right">主管人：</td><td><input type="text" name="leadName" value="${org.leadName}" class="input-text lh30" size="40"></td>
                  <td class="td_right">联系人：</td><td><input type="text" name="linkMan" value="${org.linkMan}" class="input-text lh30" size="40"></td>
                  </tr>
                  <c:if test="${mode=='edit'}">
                  <tr>
                  <td class="td_right">创建人：</td><td><input type="text" readonly="true" name="createUser" value="${org.createUser}" class="input-text lh30" size="40"></td>
                  <td class="td_right">创建时间：</td><td><input type="text" readonly="true" name="createDate" value="${org.createDate}" class="input-text lh30" size="40"></td>
                </tr>
                </c:if>
                <tr>
                  <td class="td_right">邮编地址：</td><td><input type="text" name="postalAddress" value="${org.postalAddress}" class="input-text lh30" size="40"></td>
                  <td class="td_right">邮编：</td><td><input type="text" name="postalCode" value="${org.postalCode}" class="input-text lh30" size="40"></td>
                </tr>
                <tr>
                  <td class="td_right">传真：</td><td><input type="text" name="faxNo" value="${org.faxNo}" class="input-text lh30" size="40"></td>
                  <td class="td_right">电话：</td><td><input type="text" name="phoneNo" value="${org.phoneNo}" class="input-text lh30" size="40"></td>
                </tr>
                
                 <tr>
                   <td class="td_right">&nbsp;</td>
                   <td class="">
                     <input type="submit" name="button" class="btn btn82 btn_save2" value="保存"> 
                    <input type="reset" name="button" class="btn btn82 btn_res" value="重置"> 
                    <input type="button" name="button" onclick="javascript:window.location.href='../listOrgs?divisionId=${org.divisionId}&parentId=${org.parentId }'" class="btn btn82 btn_back" value="返回"> 
                   
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
		      name:  {
		        required: true,
		        minlength: 2
		      },
		      type:{required:true}
		}
	});
});
</script>

</body>

</html>
