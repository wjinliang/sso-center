
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!doctype html>
<html lang="zh-CN">
<head>
<title>个人中心</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=basePath%>/assets/css/common.css">
<link rel="stylesheet" href="<%=basePath%>/assets/css/main.css">
<script type="text/javascript">var root = "<%=path%>";</script>
<script type="text/javascript"
	src="<%=basePath%>/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/js/common.js"></script>

<title></title>

</head>
<body>
	<div class="container">
		<div>
			<div class="layui-layer-page layui-layer-tab" type="page" 
				contype="string" style="border: #E6E6E6 solid 1px;    box-shadow:none">
				<div class="layui-layer-title" style="cursor: move;">
					<span class="layui-this">个人信息</span>
					<span class="">修改</span>
					<span class="">修改密码</span>
				</div>
				<div id="" class="layui-layer-content" >
					<ul class="layui-layer-tabmain">
						<li class="layui-layer-tabli" style="display: list-item;">
						<div style="padding: 20px;">
								<table class="form_table pt15 pb15" width="100%" border="0"
								cellpadding="0" cellspacing="0">
								<tr>
									<td class="td_right">用户名：</td>
									<td class=""> ${userAccount.name }</td>
								</tr>
								<tr>
									<td class="td_right">用户登录名：</td>
									<td>${userAccount.loginname }</td>
								</tr>
								<tr>
									<td class="td_right">性别：</td>
									<td class="">${userAccount.gender}</td>
								</tr>
								<tr>
									<td class="td_right">联系电话：</td>
									<td>${userAccount.mobile}</td>
								</tr>
								<tr>
									<td class="td_right">电子邮箱：</td>
									<td>${userAccount.email}</td>
								</tr>
								<tr>
									<td class="td_right">办公电话：</td>
									<td>${userAccount.bizphoneNo}</td>
								</tr>
								<tr>
									<td class="td_right">传真：</td>
									<td>${userAccount.faxNo}</td>
								</tr>
								<tr>
									<td class="td_right">职务：</td>
									<td>${userAccount.duty}</td>
								</tr>
								<tr>
									<td class="td_right">学历：</td>
									<td class="">${userAccount.schoolAge}</td>
								</tr>
								<tr>
									<td class="td_right">专业：</td>
									<td>${userAccount.speciality}</td>
								</tr>
								<tr>
									<td class="td_right">职称：</td>
									<td class="">${userAccount.title}</td>
								</tr>
								<tr>
									<td class="td_right">地址：</td>
									<td>${userAccount.address}</td>
								</tr>
								</tr>
								</table>
							</div></li>
						<li class="layui-layer-tabli" style="display: none;"><div
								style="padding: 20px;">
								<div class="box_center">
						<form id="form1" action="./saveInfo" method="post"
							class="jqtransform">
							<table class="form_table pt15 pb15" width="100%" border="0"
								cellpadding="0" cellspacing="0">
								<tr>
									<td class="td_right">用户名：</td>
									<td class="">${userAccount.name }</td>
									
								</tr>

								<tr>
									
									<td class="td_right">性别：</td>
									<td class=""><span> <input type="radio"
											checked="checked" name="gender" value="男"> 男 <input
											type="radio" name="gender"
											<c:if test="${userAccount.gender=='女'}"> checked="checked"</c:if>
											value="女"> 女
									</span></td>
								</tr>
								<tr>
									<td class="td_right">联系电话：</td>
									<td><input type="text" name="mobile"
										value="${userAccount.mobile}" class="input-text lh30"
										size="40"></td>
								</tr>
								<tr>
									<td class="td_right">电子邮箱：</td>
									<td><input type="text" name="email"
										value="${userAccount.email}" class="input-text lh30" size="40"></td>
								</tr>
								<tr>
									<td class="td_right">办公电话：</td>
									<td><input type="text" name="bizphoneNo"
										value="${userAccount.bizphoneNo}" class="input-text lh30"
										size="40"></td>
								</tr>
								<tr>
									<td class="td_right">传真：</td>
									<td><input type="text" name="faxNo"
										value="${userAccount.faxNo}" class="input-text lh30" size="40"></td>
								</tr>
								<tr>
									<td class="td_right">职务：</td>
									<td><input type="text" name="duty"
										value="${userAccount.duty}" class="input-text lh30" size="40"></td>
								</tr>
								<tr>
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
										value="${userAccount.speciality}" class="input-text lh30"
										size="40"></td>
								</tr>
								<tr>
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
										value="${userAccount.address}" class="input-text lh30"
										size="40"></td>
								</tr>
								<tr>
									<td class="td_right">&nbsp;</td>
									<td class="">
											<input type="submit" name="button"
												class="btn btn82 btn_save2" value="修改">
											<input type="reset" name="button" class="btn btn82 btn_res"
												value="重置">
										</td>
								</tr>
							</table>
						</form>
					</div>
								
								</div></li>
						<li class="layui-layer-tabli" style="display: none;">
							<div style="padding: 20px;">
								<form id="form2" action="./updatePassword" method="post"
							class="jqtransform">
							<table class="form_table pt15 pb15" width="100%" border="0"
								cellpadding="0" cellspacing="0">
								<tr>
									<td class="td_right">原密码：</td>
									<td class=""> <input
										type="text" name="oldPassword" class="input-text lh30"
										value="" size="40"></td>
									
								</tr>
								<tr>
									<td class="td_right">新密码：</td>
									<td class=""> <input
										type="text" name="newPassword" class="input-text lh30"
										value="" size="40"></td>
									
								</tr>
								<tr>
									<td class="td_right">确认密码：</td>
									<td class=""> <input
										type="text" name="checkpwd" class="input-text lh30"
										value="" size="40"></td>
									
								</tr>
								<tr>
									<td class="td_right">&nbsp;</td>
									<td class="">
											<input type="submit" name="button"
												class="btn btn82 btn_save2" value="修改">
										</td>
								</tr>
								</table>
								</form>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		
		
	</div>
	</div>
	<script type="text/javascript"
		src="<%=basePath%>/assets/plugin/layer/layer.js"></script>
	<%@include file="../include/formValidate.jsp"%>

	<script type="text/javascript">
		jQuery.validator
				.addMethod(
						"isPassword",
						function(value, element) {
							var tel = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={} :";'<>?,.\/]).{8,30}$/;
							return this.optional(element) || (tel.test(value));
						}, "必须字母数字符号汇合且大于8位");
		$().ready(function() {
			// 提交时验证表单
			var validator = $("#form1").validate({
				rules : {
					name : {
						required : true,
						minlength : 2
					},
					enabled : {
						required : true
					},
					email : {
						email : true
					}
				}
			});
			// 提交时验证表单
			var validator = $("#form2").validate({
				rules : {
					oldpassword : {
						required : true
					},
					newpassword : {
						required : true,
						isPassword : true
					},
					checkpwd : {
						required : true,
						equalTo : "input[name='newPassword']"
					}
					
				},
				messages : {
					checkpwd : {
						equalTo : "两次输入的密码必须相同"
					}
				}
			});
			
			var layero = $(".layui-layer-tab");
			var btn = layero.find('.layui-layer-title').children();
		    var main = layero.find('.layui-layer-tabmain').children();
		      btn.on('mousedown', function(e){
		        e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
		        var othis = $(this), index = othis.index();
		        othis.addClass('layui-this').siblings().removeClass("layui-this");
		        main.eq(index).show().siblings().hide();
		      });
		      
		});
		var msg="${infomsg}";
		if(msg!=""){
			layer.alert(msg);
		}
		
	</script>

</body>

</html>
