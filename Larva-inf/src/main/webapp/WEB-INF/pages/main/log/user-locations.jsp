<%--
  Created by IntelliJ IDEA.
  User: sxjun
  Date: 15-9-15
  Time: 下午1:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<html>
<head>
<title>加载闪烁点</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<style type="text/css">
#map {
	width: 100%;
	height: 100%;
}
</style>
<script src="${ctxAssets}/echarts/echarts.min.js"></script>
</head>
<body>
	<style type="text/css">
#map {
	width: 100%;
	height: 100%;
}
</style>

	<div id="map"></div>
	<script type="text/javascript">
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('map'));

		// 指定图表的配置项和数据
		var option = {
			title : {
				text : 'ECharts 入门示例'
			},
			tooltip : {},
			legend : {
				data : [ '销量' ]
			},
			xAxis : {
				data : [ "衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子" ]
			},
			yAxis : {},
			series : [ {
				name : '销量',
				type : 'bar',
				data : [ 5, 20, 36, 10, 10, 20 ]
			} ]
		};

		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	</script>
</body>
</html>

