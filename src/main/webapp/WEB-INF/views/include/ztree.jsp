
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<link rel="stylesheet"
	href="<%=basePath%>/assets/plugin/zTree/css/zTreeStyle/zTreeStyle.css"
	type="text/css"><style>
div#rMenu {
	position: absolute;
	visibility: hidden;
	top: 0;
	text-align: center;
	padding: 2px;
}

div#rMenu ul li {
	cursor: pointer;
}
ul.ztree {
	height: 290px;
}
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.ztree li span.button2{
	line-height:0; margin:0; width:16px; height:16px; display: inline-block; vertical-align:middle;
	border:0 none; cursor: pointer;outline:none;
	background-color:transparent; background-repeat:no-repeat; background-attachment: scroll;
}
.ztree li span.button2.up {
	background-image:url("<%=basePath%>/assets/plugin/zTree/css/zTreeStyle/img/diy/arrow_up.png");
}
.ztree li span.button2.down {
	background-image:url("<%=basePath%>/assets/plugin/zTree/css/zTreeStyle/img/diy/arrow_down.png");
}
.ztree li span.button2.edit {
	background-image:url("<%=basePath%>/assets/plugin/zTree/css/zTreeStyle/img/diy/page_edit.png");
}
.ztree li span.button2.add {
	background-image:url("<%=basePath%>/assets/plugin/zTree/css/zTreeStyle/img/diy/page_add.png");
}
.ztree li span.button2.delete {
	background-image:url("<%=basePath%>/assets/plugin/zTree/css/zTreeStyle/img/diy/page_delete.png");
}
.ztree li span.button2.view {
	background-image:url("<%=basePath%>/assets/plugin/zTree/css/zTreeStyle/img/diy/page.png");
}

</style>

<script type="text/javascript"
	src="<%=basePath%>/assets/plugin/zTree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript"
	src="<%=basePath%>/assets/plugin/zTree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>/assets/plugin/zTree/js/jquery.ztree.exedit-3.5.js"></script>

