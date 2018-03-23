
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@page import="java.util.List"%>
<%@page import="com.topie.ssocenter.freamwork.authorization.model.JKApplicationInfo,com.github.pagehelper.PageInfo"%>
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
<script type="text/javascript" src="<%=basePath%>/assets/plugin/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>/assets/js/commonAction.js"></script>
</head>
<body>
	<div class="container">
		<div id="search_bar" class="mt10">
			<div class="box">
				<div class="box_border">
					<form action="./listApp" method="get" id="serchForm">
					<div class="box_center pt10 pb10">
						<table class="form_table" border="0" cellpadding="0"
							cellspacing="0">

							<input type="hidden" id="pageNum" name="thispage" value="">
							<input type="hidden" name="pagesize" value="${page.pageSize}">
							<tr>
								<td>系统名称</td>
								<td><input type="text" name="appName" value="${searchModel.appName }" class="input-text lh25"
									size="20"></td>
								<td>系统编号</td>
								<td><input type="text" name="appCode" value="${searchModel.appCode }" class="input-text lh25"
									size="10"></td>
								<td><input type="button" onclick="nextPage('0')" class="btn btn82 btn_search"
				value="查询"><td>
							</tr>
						</table>
					</div>
					</form>
				</div>
				
				<div id="button" class="mt10">
			<input type="button" onclick="javascript:openPage('./form/new');" name="button" class="btn btn82 btn_add"
				value="新增">
		<!-- <input type="button" name="button"
				class="btn btn82 btn_count" value="统计">
			 <input type="button" name="button" class="btn btn82 btn_del" value="删除"> 
       <input type="button" name="button" class="btn btn82 btn_config" value="配置"> 
       <input type="button" name="button" class="btn btn82 btn_checked" value="全选"> 
       <input type="button" name="button" class="btn btn82 btn_nochecked" value="取消"> 
       <input type="button" name="button" class="btn btn82 btn_export" value="导出">
       <input type="button" name="button" class="btn btn82 btn_recycle" value="回收站"> -->
		</div>
			</div>
		</div>
		<div id="table" class="mt10">
			<div class="box span10 oh">
			<div><span id="timeCount"></span></div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th width="30">#</th>
						<th width="30">#</th>
						<th>系统名称</th>
						<th>系统编号</th>
						<th>系统状态</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${page.list}" var="app" varStatus="status">
						<tr class="tr">

							<td class=""><input type="checkbox"
								value='${app.id }'></td>
							<td class="">
								${(page.pageNum-1)*page.pageSize+status.count}</td>
							<td>${app.appName}</td>
							<td>${app.appCode}</td>
							<td> <span id="cg${app.id}" name="cg">正在获取...</span> </td>
							<td>
							 <a class="ext_btn ext_btn_success" href="javascript:openPage('./form/update?appid=${app.id }');">  修改
							</a> <a class="ext_btn ext_btn_error" href="javascript:deleteAction('./delete?appid=${app.id}')">删除
							</a> <a class="ext_btn ext_btn_error" href="javascript:ping('${app.id}')">ping
							</a> 
						</td>
						</tr>
					</c:forEach>
				</table>
				<%@include file="../include/pageinfo.jsp"%>
				
			</div>
		</div>
</body>
<c:forEach items="${page.list}" var="app" varStatus="status">
		<script>
		function jiankong${app.id}(){
			$.ajax({
		    	type:"POST",
		    	url:"./jianKong?appid=${app.id}",		    	
				success:function(res)   
	                {      
		                if(res.data=='200'){
		               		$("#cg${app.id}").html("<font color='green'>连接成功</font>");
		                }else{
		               		$("#cg${app.id}").html("<font color='red'>连接失败</font>");
		                }
	               		
	                }
		    	}); 
		}
		</script>
	</c:forEach>	
	<script>
	$(function(){
	   	setTimeout(function(){jiankongall();},1000);
		setTimeout(function(){timeCount(58);},2000); 
	  setInterval(function(){
		  $("span[name='cg']").each(function() { 
				$(this).text("正在获取..."); 
			}); 
		   		setTimeout(function(){jiankongall();},1000);
		    	setTimeout(function(){timeCount(58);},2000);  
			},60000);
		});
	
	var timeTemp=0;
	function timeCount(count)
	{
		
		if(timeTemp<count)
		{
		$("#timeCount").html((count-timeTemp)+"秒后再次发送请求！");
			timeTemp++;
			setTimeout(function(){timeCount(count);},1000);
		}else
		{
		 timeTemp=0;
		}		
	}
	
	function jiankongall() {
		<%
			List appList = ((PageInfo)request.getAttribute("page")).getList();
			for (int i = 0; i < appList.size(); i++) {
				JKApplicationInfo app =(JKApplicationInfo)appList.get(i);
				System.out.println(app.getAppName());
		%>
		jiankong<%=app.getId()%>();
		<%
			}
		%>
	}
	
	function ping(id){
		$.ajax({
	    	type:"POST",
	    	url:"./ping?appId="+id,		    	
			success:function(res)   
                {      
                	layer.alert(res.message);
                }
	    	}); 
	}
	</script>
</html>
