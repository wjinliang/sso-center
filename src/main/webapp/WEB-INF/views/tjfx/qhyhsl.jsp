
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<script type="text/javascript" src="<%=basePath%>/assets/plugin/echarts/echarts.common.min.js"></script>
<script type="text/javascript">
var xAxisData=[],shengData=[],shiData=[],xianData=[],xiangData=[],cunData=[];
</script>
</head>
<body>
	<div class="container">
		<div id="search_bar" class="mt10">
			<div class="box">
			<div class="box_border">
					<form action="" method="get" id="serchForm">
					<div class="box_center pt10 pb10">
						<table class="form_table" border="0" cellpadding="0"
							cellspacing="0">
							<input type="hidden" id="pageNum" name="thispage" value="">
							<tr>
								<td>系统名称</td>
								<td><input type="text" name="appName" value="${searchModel.appName }" class="input-text lh25"
									size="20"></td>
								
								<td><input type="submit" class="btn btn82 btn_search"
				value="查询"><td>
							</tr>
						</table>
					</div>
					</form>
				</div>
				<div id="button" class="mt10">
	       		<input type="button" name="button" class="btn btn82 btn_export" value="导出">
				<input type="button" onclick="" name="button" class="btn btn82 btn_count"
					value="统计图">
		<!-- <input type="button" name="button"
				class="btn btn82 btn_count" value="统计">
			 <input type="button" name="button" class="btn btn82 btn_del" value="删除"> 
       <input type="button" name="button" class="btn btn82 btn_config" value="配置"> 
       <input type="button" name="button" class="btn btn82 btn_checked" value="全选"> 
       <input type="button" name="button" class="btn btn82 btn_nochecked" value="取消"> 
       <input type="button" name="button" class="btn btn82 btn_recycle" value="回收站"> -->
		</div>
			</div>
		</div>
		<div id="charts" style="height:600px;"></div>
		<div id="table" class="mt10">
			<div class="box span10 oh">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th width="30">#</th>
						<th>区划</th>
						<th>省级用户</th>
						<th>市级用户</th>
						<th>县级用户</th>
						<th>乡镇用户</th>
						<th>村级用户</th>
					</tr>
					<c:set value="0" var="sum0" />
								<c:set value="0" var="sum1" />
								<c:set value="0" var="sum2" />
								<c:set value="0" var="sum3" />
								<c:set value="0" var="sum4" />
								<c:forEach items="${page}" var="app" varStatus="status">
									<tr>
										<%-- <td ><input
											type="checkbox" class="checkboxes" name="checkboxes"
											value="${app.id}" /></td> --%>
										<td >
											${thispage*pagesize+status.count}</td>
										<td >${app.divisionName}</td>
										<td >${app.shengcount}</td>
										<td >${app.shicount}</td>
										<td >${app.xiancount}</td>
										<td >${app.xiangcount}</td>
										<td >${app.cuncount}</td>
										<c:set value="${sum0 + app.shengcount}" var="sum0" />
										<c:set value="${sum1 + app.shicount}" var="sum1" />
										<c:set value="${sum2 + app.xiancount}" var="sum2" />
										<c:set value="${sum3 + app.xiangcount}" var="sum3" />
										<c:set value="${sum4 + app.cuncount}" var="sum4" />
									</tr>
									<script >
										xAxisData.push("${app.divisionName}");
										shengData.push(${app.shengcount});
										shiData.push(${app.shicount});
										xianData.push(${app.xiancount});
										xiangData.push(${app.xiangcount});
										cunData.push(${app.cuncount});
									</script>
									<c:if test="${status.last=='true'}">
									<tr style="background-color: #ccc;">
										<%-- <td ><input
											type="checkbox" class="checkboxes" name="checkboxes"
											value="${app.id}" /></td> --%>
										<td ></td>
										<td >合计(${sum0+sum1+sum2+sum3+sum4})</td>
										<td >${sum0}</td>
										<td >${sum1}</td>
										<td >${sum2}</td>
										<td >${sum3}</td>
										<td >${sum4}</td>

									</tr>
									</c:if>
								</c:forEach>
				</table>
				
			</div>
		</div>
</body>
<script>

$(function(){
        // 绘制图表。
        echarts.init(document.getElementById('charts')).setOption(option);
	
});
 var option = {
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    title: {
		        text: '全国各地登录次数统计',
		        subtext: ''
		    },
		    legend: {
		        data: ['省级', '市级','区县级','乡镇级','村级']
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    yAxis:  {
		        type: 'value'
		    },
		    xAxis: {
		        type: 'category',
		        data: xAxisData,//['广东省','广西壮族自治区','青海省','河北省','河南省','浙江省','四川省','辽宁省','福建省','云南省','江西省','吉林省','新疆维吾尔自治区','湖北省','黑龙江省','海南省','山东省','甘肃省','湖南省','重庆市','新疆生产建设兵团','台湾省','陕西省','西藏自治区','江苏省','山西省','内蒙古自治区','上海市','安徽省','北京市','天津市','贵州省','宁夏回族自治区','宁夏农垦','广东农垦','黑龙江省农垦总局']
		    },
		    dataZoom: [
		            {
		                show: true,
		                start: 0,
		                end: 100
		            },
		            {
		                type: 'inside',
		                start: 94,
		                end: 100
		            },
		            {
		                show: true,
		                yAxisIndex: 0,
		                filterMode: 'empty',
		                width: 30,
		                height: '80%',
		                showDataShadow: false,
		                left: '93%'
		            }
		        ],
		    series: [
		        {
		            name: '省级',
		            type: 'bar',
		            stack: '总量',
		            label: {
		                normal: {
		                   // show: true,
		                   // position: 'insideRight'
		                }
		            },
		            data: shengData//[647,404,389,388,386,345,287,276,270,268,261,233,213,203,186,183,162,142,134,133,124,116,114,109,109,107,104,74,68,60,59,55,44,0,0,0]
		        },
		        {
		            name: '市级',
		            type: 'bar',
		            stack: '总量',
		            label: {
		                normal: {
		                    //show: true,
		                   // position: 'insideRight'
		                }
		            },
		            data: shiData//[1567,424,409,760,3977,932,740,794,689,2305,710,955,1124,874,81,103,1504,948,1376,729,786,40,577,38,635,657,455,298,145,443,77,988,48,0,0,0]
		        },
		        {
		            name: '区县级',
		            type: 'bar',
		            stack: '总量',
		            label: {
		                normal: {
		                   // show: true,
		                   // position: 'insideRight'
		                }
		            },
		            data: xianData//[7509,10625,3264,4399,9711,6604,7358,5755,2636,10334,2379,6760,5261,1555,424,1545,2931,6090,6623,122,1315,50,3275,67,3889,2553,1215,14,1140,1080,0,5922,452,0,0,0]
		        },
		        {
		            name: '乡镇级',
		            type: 'bar',
		            stack: '总量',
		            label: {
		                normal: {
		                   // show: true,
		                    position: 'insideRight'
		                }
		            },
		            data: xiangData//[37262,29188,7608,108,7120,1320,13512,11855,593,12928,259,4877,7662,91,542,388,734,10376,17355,0,0,41,3575,0,1375,5787,176,0,0,0,0,8811,0,0,0,0]
		        },
		        {
		            name: '村级',
		            type: 'bar',
		            stack: '总量',
		            label: {
		                normal: {
		                  //  show: true,
		                   // position: 'insideRight'
		                }
		            },
		            data: cunData//[1,0,14,0,0,0,1,5,0,2,0,9,0,0,0,0,0,0,3,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0]
		        }
		    ]
		};
    </script>
</html>
