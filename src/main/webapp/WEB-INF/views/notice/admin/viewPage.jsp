
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
<link rel="stylesheet" href="<%=basePath%>/assets/plugin/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/plugin/kindeditor/plugins/code/prettify.css" />
<script type="text/javascript" src="<%=basePath%>/assets/plugin/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/kindeditor/lang/zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/kindeditor/plugins/code/prettify.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/datePicker/laydate.js"></script>
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
                  <td class="td_right">标题<span class="red">*</span>：</td>
                  <td class=""> 
                  <input type="hidden" name="id" value="${notice.id }">
                    <input type="text" name="title" value="${notice.title }" class="input-text lh30" size="40">
                  </td>
                  <td class="td_right">作者<span class="red">*</span>：</td><td><input type="text" name="author" value="${notice.author }" class="input-text lh30" size="40"></td>
                </tr>
                <tr>
                  <td class="td_right">发布时间：</td>
                  <td class=""> 
                    <input type="text" role="date-input" id="publishTime" name="publishTime" class="input-text lh30" size="40">
                  </td>
                  <td class="td_right">来源<span class="red">*</span>：</td><td><input type="text" name="origin" value="${notice.origin }" class="input-text lh30" size="40"></td>
                  </tr>
                  
                 <tr>
                  <td class="td_right">内容：</td>
                  <td class="" colspan="3">
                    <textarea name="content" id="content" cols="30" rows="10" class="textarea">${notice.content }</textarea>
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">附件：</td>
                  <td class="" colspan="3"> 
                  <input type="hidden" id="fileIds" value="${fileIds }" name="fileIds">
                  	<input type="file" id="fileupload_input" class="input-text lh30" name="imgFile" size="10">
                    <input type="button" id="upfilebtn" class="ext_btn" value="上传">
                    </br>
	              <span id="files_span">
	                  <c:forEach var="file" items="${files}">
		                  <span> ${file.realname }<a href="javascript:resFile('${file.id }')">删除</a></span>
	                  </c:forEach>
	              </span>
                  </td>
                  </tr>
                 <tr>
                  <td class="td_right">系统：</td>
                  <td colspan="3">
                  <input type="checkbox" name="apps" value="123">单点平台
                  <c:set var="apps" value="${d:gAS('org','','' ) }"></c:set>
                  <c:forEach var="app" items="${apps }">
                   <input type="checkbox" name="apps" <c:if test="${app.checked }"> checked="checked"</c:if> value="${app.id }">
                   ${app.appName }<c:if test="${app.status=='2' }"><span class="green">(维护中...)</span> </c:if> 
                  </c:forEach>
                  </td>
                  
                </tr>
                
                 <tr>
                   <td class="td_right">&nbsp;</td>
                   <td class="">
                   
                   <c:if test="${!(param.detail=='1')}">
                     <input type="submit" name="button" class="btn btn82 btn_save2" value="保存"> 
                    <input type="reset" name="button" class="btn btn82 btn_res" value="重置"> 
                   </c:if>
                   <input type="button" name="button" onclick="javascript:window.location.href='../list'" class="btn btn82 btn_back" value="返回"> 
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
<script type="text/javascript" src="<%=basePath%>/assets/js/ajaxfileupload.js"></script>
<%@include file="../../include/formValidate.jsp"%>
</body>
<script type="text/javascript">
$.validator.setDefaults({
	errorPlacement: function(error, element) {
	// Append error within linked label
		var type=element.attr("type");
		if(type=='radio' || type=="checkbox"){
			layer.tips(error.html(),element.parent(),{tipsMore: true});	
		}else{
			layer.tips(error.html(),element,{tipsMore: true});	
		}
	},
    submitHandler: function(form) {
      form.submit();
    }
});
	$(function(){
		
		// 提交时验证表单
		var validator = $("#form1").validate({
			rules: {
			      title:{required:true},
			      author:{required:true},
			      origin:{required:true},
			      apps:{required:true}
			}
		});
		
		$('input[role="date-input"]').on(
                "click",
                function () {
                    laydate({
                        istime: true,
                        format: 'YYYY-MM-DD hh:mm:ss'
                    });
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
	                   var file = '<span data-id="'+data.id+'">'+data.name+'<a href="javascript:resFile(\''+data.id+'\')">删除</a></span>';
	                   $("#files_span").append(file);
	                   resFile(111111);
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
// 		initFiles();
	});
	
	/* function initFiles(){
		var list = eval('[{name:"附件一：收到甲方的是开发.xml",id:"123"},{name:"附件二：收到甲方的是开发.xml",id:"324"}]');
		for(var i = 0; i < list.length; i++) {
			var data = list[i];
			var file = '<span data-id="'+data.id+'">'+data.name+'<a href="javascript:resFile(\''+data.id+'\')">删除</a></span>';
	        $("#files_span").append(file);
		}
	} */
	
	function resFile(fileId){
		$("#files_span").find('span[data-id="'+fileId+'"]').remove();
		var s = "";
		$("#files_span").find("span").each(function(){
			var id = $(this).data("id");
			s+=","+id;
		});
		if(s!=""){
			s=s.substring(1);
		}
		$("#fileIds").val(s);
	}
	KindEditor.ready(function(K) {
		var editor1 = K.create('textarea[name="content"]', {
			//cssPath : '../plugins/code/prettify.css',
			items:[
			         'undo', 'redo', '|', 'preview', 'print', 'cut', 'copy', 'paste',
			        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
			        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
			        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
			        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
			         'table', 'hr', 'emoticons', 'pagebreak',
			         'source'
			],
			uploadJson : root+'/KE/file_upload',//
			fileManagerJson : root+'/KE/file_manager',//
			allowFileManager : true,
			 afterBlur: function(){this.sync();}
			
		});
		//prettyPrint();
	});
</script>

</html>
