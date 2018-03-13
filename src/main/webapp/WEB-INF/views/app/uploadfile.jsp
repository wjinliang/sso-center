
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/user.tld" prefix="d" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!doctype html>
<html lang="zh-CN">
<head>
<title>上传用户手册</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=basePath%>/assets/css/common.css">
<link rel="stylesheet" href="<%=basePath%>/assets/css/main.css">
<script type="text/javascript">var root = "<%=path%>";</script>
<script type="text/javascript"
	src="<%=basePath%>/assets/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/assets/js/colResizable-1.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/js/commonAction.js"></script>
</head>
<body>
	<div class="container">
		<p style="font-size:20px;color:#ccc;margin-left:20px;">单点登录</p>
		<div id="table" class="mt10">
			<div class="box span10 oh">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th width="30">#</th>
						<th>系统名称</th>
						<th>用户手册</th>
						<th>上传时间</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${page.list}" var="app" varStatus="status">
						<tr class="tr">

							<td class="td_center">
								${(page.pageNum-1)*page.pageSize+status.count}</td>
							<td>${app.appName}</td>
							<td><c:set var="file" value="${d:gAF(app.id)}"></c:set>
            	<c:if test="${file!=null}">${file.name }</c:if><c:if test="${file==null}"><font style="color:#f11">未上传用户手册</font></c:if></td>
            				<td><c:if test="${file!=null}">${file.cdate }</c:if></td>
							<td><a href="javascript:uploadfile('${app.id }','${app.appName }')">上传用户手册</a>
						</td>
						</tr>
					</c:forEach>
				</table>
				<%@include file="../include/pageinfo.jsp"%>
				
			</div>
		</div>
</body>
<script type="text/javascript" src="<%=basePath%>/assets/js/ajaxfileupload.js"></script>
<script type="text/javascript">
	function uploadfile(appId,appName){
		layer.open({
			  type: 1,
			  title: appName+"-上传用户手册",
			  closeBtn: 1,
			  area: ['500px', '300px'],
			  shadeClose: false,
			  content: '<div style="padding:50px;"><input type="hidden" id="fileIds" value="" name="fileIds">'+
			  '<input type="file" id="fileupload_input" class="input-text lh30" name="imgFile" size="10">'+
			  '<input type="button" id="upfilebtn" class="ext_btn" value="上传">'+
			  '</br>'+
	            '<span id="files_span">'+
	            '</span>'+
	            '</div>'
			});
		$("#upfilebtn").on("click",function(){
			if($("#fileupload_input").val()==null ||$("#fileupload_input").val()==''){
				layer.alert("请选择文件后点击上传");
				return false;
			}else{
			}
				
			$.ajaxFileUpload({
                url:root+'/KE/file_upload?dir=file',//用于文件上传的服务器端请求地址
                secureuri:false ,//一般设置为false
                fileElementId:'fileupload_input',//文件上传控件的id属性  <input type="file" id="upload" name="upload" />
                dataType: 'json',//返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                	if(data.error=="0"){
                		$("#fileIds").val(data.id);
                		bindFile(data.id,appId);
                	}else{
                		layer.alert(data.message);
                	}
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                    layer.alert(e);
                }
            });
		});
	}
	function bindFile(fileId,appId){
		var url=root+"/app/bindFile";
		$.ajax({url:url,type:"post",data:{fileId:fileId,appId:appId},success:function(data){
			if(data.code=='200'){
				layer.alert("上传成功！");
			}else{
				layer.alert(data.message);
			}
		},error:function(){alert("网络错误");}});
		
	}
</script>
</html>
