
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
<link rel="stylesheet" href="<%=basePath%>/assets/plugin/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/plugin/kindeditor/plugins/code/prettify.css" />
<script type="text/javascript" src="<%=basePath%>/assets/plugin/kindeditor/kindeditor-all.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/kindeditor/lang/zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/plugin/kindeditor/plugins/code/prettify.js"></script>
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
              <form id="form1" action="save" method="post" class="jqtransform">
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
                  <td class="td_right">发布时间<span class="red">*</span>：</td>
                  <td class=""> 
					<span class="fl">
                      <div class="select_border"> 
                        <div class="select_containers "> 
                        <select name="synType" class="select"> 
                        <option <c:if test="${notice.synType=='axis1' }">selected</c:if> value="axis1">axis1</option> 
                        <option <c:if test="${notice.synType=='axis2' }">selected</c:if> value="axis2">axis2</option> 
                        <option <c:if test="${notice.synType=='http' }">selected</c:if> value="http">http</option> 
                        </select> 
                        </div> 
                      </div> 
                    </span>
                  </td>
                  <td class="td_right">来源<span class="red">*</span>：</td><td><input type="text" name="origin" value="${notice.origin }" class="input-text lh30" size="40"></td>
                  </tr>
                  
                 <tr>
                  <td class="td_right">通知内容<span class="red">*</span>：</td>
                  <td class="">
                    <textarea name="content" id="" cols="30" rows="10" class="textarea">${notice.content }</textarea>
                  </td>
                 </tr>
                 
                
                 <tr>
                   <td class="td_right">&nbsp;</td>
                   <td class="">
                   
                   <c:if test="${!(param.detail=='1')}">
                     <input type="submit" name="button" class="btn btn82 btn_save2" value="保存"> 
                    <input type="reset" name="button" class="btn btn82 btn_res" value="重置"> 
                   </c:if>
                   <input type="button" name="button" onclick="javascript:window.location.href='./list'" class="btn btn82 btn_back" value="返回"> 
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
			      title:{required:true},
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
			uploadJson : '../jsp/upload_json.jsp',//{"error":0,"url":"\/ke4\/attached\/W020091124524510014093.jpg"}
			fileManagerJson : '../jsp/file_manager_json.jsp',///var/www/kindsoft/ke4/attached{"moveup_dir_path":"","current_dir_path":"","current_url":"\/ke4\/php\/..\/attached\/","total_count":5,"file_list":[{"is_dir":false,"has_file":false,"filesize":208736,"dir_path":"","is_photo":true,"filetype":"jpg","filename":"1241601537255682809.jpg","datetime":"2016-08-01 22:50:09"}
			allowFileManager : true
			
		});
		//prettyPrint();
	});
</script>

</html>
