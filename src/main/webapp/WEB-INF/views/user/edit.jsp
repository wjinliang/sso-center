
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/user.tld" prefix="d"%>
<%@ taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
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
              <form id="form1" action="../user/save" method="post" class="jqtransform">
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                 <tr>
                  <td class="td_right">用户名<span class="red">*</span>：</td>
                  <td class=""> 
	                  <input type="hidden" name="code" value="${userAccount.code }"/>
					  <input type="hidden" name="orgId" value="${userAccount.orgId}"/>
					  <input type="hidden" name="divisionId" value="${org.divisionId}"/>
					  <input type="hidden" id="systemId" name="systemId"
								value="${userAccount.systemId}" />
					  <input type="hidden"
								id="appCheckList" name="appCheckList" value="" />
                    <input type="text" name="name" autocomplete="off" class="input-text lh30" value="${userAccount.name }" size="40">
                  </td>
                  <td class="td_right">用户登录名：</td><td><input type="text" name="loginname" autocomplete="off" readonly="true" value="${userAccount.loginname }" class="input-text lh30" size="40"></td>
                </tr>
                <c:if test="${mode=='new' }">
                	<tr>
	                  <td class="td_right">密码<span class="red">*</span>：</td><td><input type="password" name="password" autocomplete="off" class="input-text lh30" size="40"></td>
	                  <td class="td_right">确认密码<span class="red">*</span>：</td><td><input type="password" name="checkpassword" autocomplete="off" class="input-text lh30" size="40"></td>
                  </tr>
                  </c:if>
                  <c:if test="${mode=='edit'}">
                  <tr>
                  <td class="td_right">创建人：</td><td><input type="text" readonly="true" name="createUser" value="${userAccount.createUser}" class="input-text lh30" size="40"></td>
                  <td class="td_right">创建时间：</td><td><input type="text" readonly="true" name="createDate" value="${userAccount.createDate}" class="input-text lh30" size="40"></td>
                </tr>
                </c:if>
                
                 <tr>
                 <td class="td_right">是否启用<span class="red">*</span>：</td>
                 <td class="">
                  	<span>
	                    <input type="radio" checked="checked" name="enabled" value="true"> 可用
	                    <input type="radio" <c:if test="${!userAccount.enabled}"> checked="checked"</c:if> name="enabled" value="false"> 禁用
                    </span>
                  </td>
                 <td class="td_right">性别<span class="red">*</span>：</td>
                 <td class="">
                  	<span>
	                    <input type="radio" checked="checked" name="gender" value="男"> 男
	                    <input type="radio" name="gender" <c:if test="${userAccount.gender=='女'}"> checked="checked"</c:if>  value="女"> 女
                    </span>
                  </td>
                 </tr>
                <tr>
                  <td class="td_right">联系电话：</td><td><input type="text" name="mobile" value="${userAccount.mobile}" class="input-text lh30" size="40"></td>
                  <td class="td_right">电子邮箱：</td><td><input type="text" name="email" value="${userAccount.email}" class="input-text lh30" size="40"></td>
                </tr>
                <tr>
                  <td class="td_right">办公电话：</td><td><input type="text" name="bizphoneNo" value="${userAccount.bizphoneNo}" class="input-text lh30" size="40"></td>
                  <td class="td_right">传真：</td><td><input type="text" name="faxNo" value="${userAccount.faxNo}" class="input-text lh30" size="40"></td>
                </tr>
				<tr>
					<td class="td_right">职务：</td>
					<td><input type="text" name="duty"
						value="${userAccount.duty}" class="input-text lh30" size="40"></td>
					<td class="td_right">学历：</td>
					<td class=""><span class="fl">
							<div class="select_border">
								<div class="select_containers ">
									<select name="schoolAge" id="schoolAge" class="select">
										<option value="高中及以下"
											<c:if test="${userAccount.schoolAge=='高中及以下'}">selected="selected" </c:if>>高中及以下
										</option>
										<option value="中专"
											<c:if test="${userAccount.schoolAge=='中专'}">selected="selected" </c:if>>中专
										</option>
										<option value="专科"
											<c:if test="${userAccount.schoolAge=='专科'}">selected="selected" </c:if>>
											专科</option>
										<option value="本科"
											<c:if test="${userAccount.schoolAge=='本科'}">selected="selected" </c:if>>
											本科</option>
										<option value="硕士"
											<c:if test="${userAccount.schoolAge=='硕士'}">selected="selected" </c:if>>硕士
										</option>
										<option value="博士"
											<c:if test="${userAccount.schoolAge=='博士'}">selected="selected" </c:if>>博士
										</option>
										<option value="博士后"
											<c:if test="${userAccount.schoolAge=='博士后'}">selected="selected" </c:if>>博士后</option>
									</select>
								</div>
							</div>
					</span></td>
				</tr>
				<tr>
					<td class="td_right">专业：</td>
					<td><input type="text" name="speciality"
						value="${userAccount.speciality}" class="input-text lh30" size="40"></td>
					<td class="td_right">职称：</td>
					<td class=""><span class="fl">
							<div class="select_border">
								<div class="select_containers ">
									<select name="title" id="title" class="select">
										<option value="高级"
											<c:if test="${userAccount.title=='高级'}">selected="selected" </c:if>>高级</option>
										<option value="中级"
											<c:if test="${userAccount.title=='中级'}">selected="selected" </c:if>>中级
										</option>
										<option value="初级"
											<c:if test="${userAccount.title=='初级'}">selected="selected" </c:if>>
											初级</option>
										<option value="技术员"
											<c:if test="${userAccount.title=='技术员'}">selected="selected" </c:if>>
											技术员</option>
										<option value="推广研究员"
											<c:if test="${userAccount.title=='推广研究员'}">selected="selected" </c:if>>推广研究员</option>
										<option value="无技术职称"
											<c:if test="${userAccount.title=='无技术职称'}">selected="selected" </c:if>>无技术职称</option>
									</select>
								</div>
							</div>
					</span></td>
				</tr>
				<tr>
					<td class="td_right">地址：</td>
					<td><input type="text" name="address"
						value="${userAccount.address}" class="input-text lh30" size="40"></td>
					<td class="td_right">权限：</td>
					<td><input type="checkbox" name="isAdmin" <c:if test="${isAdmin=='true' }"> checked="checked"</c:if> 
						value="true" >机构管理员 <span class="red">(机构管理员可增删改本级用户和下级用户)</span></td>
				</tr>
				<tr>
                  <td class="td_right">同步到系统：</td>
                  <td colspan="3">
                  <c:set var="apps" value="${d:gAS('user',userAccount.orgId,userAccount.code ) }"></c:set>
                  <c:if test="${ fn:length(apps) == 0}">
                  	您没有同步到其他系统得权限！
                  </c:if>
                  <c:forEach var="app" items="${apps }">
                    <input type="checkbox" name="synApps" <c:if test="${app.status=='2' }"> onclick="return false;" </c:if><c:if test="${app.checked }"> checked="checked"</c:if> value="${app.id }">
                    ${app.appName }<c:if test="${app.status=='2' }"><span class="red">(维护中...)</span></c:if> 
                  </c:forEach>
                  	</br><span class="red">提示: 完成提交同步及用户授权后，此新建用户方可通过单点登录访问业务系统，维护中的系统将不会有更新操作。</span>
                  </td>
                  
                </tr>
				<tr> 
                   <td class="td_right">&nbsp;</td>
                   <td class="">
                   	
                   <c:if test="${!(param.detail=='1')}">
                     <input type="submit" name="button" class="btn btn82 btn_save2" value="保存"> 
                    <input type="reset" name="button" class="btn btn82 btn_res" value="重置"> 
                   </c:if>
                     <input type="button" name="button" onclick="javascript:window.location.href='../listUsers?orgId=${userAccount.orgId}&id=${userAccount.orgId}'" class="btn btn82 btn_back" value="返回"> 
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
jQuery.validator.addMethod("isPassword", function(value, element) {    
    var tel = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={} :";'<>?,.\/]).{8,30}$/;
    return this.optional(element) || (tel.test(value));
}, "必须字母数字符号汇合且大于8位");
$().ready(function() {
	// 提交时验证表单
	var validator = $("#form1").validate({
		rules: {
		      name:  {
		        required: true,
		        minlength: 2
		      },
		      password:{isPassword:true},
		      checkpassword:  {
			        required: true,
			        equalTo:"input[name='password']"
		      },
		      enabled : {
					required : true
				},
				email : {
					email : true
				}
		},
		messages:{checkpassword:{equalTo:"两次输入的密码必须相同"}}
	});
});
</script>

</body>

</html>
