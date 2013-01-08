//获得后台传递的JSON格式的数据
var getAjaxJson = function(url, params) {
	var jsonData = null;
	$.ajax({
		type: "post",
		url: url,
		data: params,
		dataType: "json",
		async: false,
		success: function(resp) {
			jsonData = resp;
		}
	});
	return jsonData;
};

//统计图表>柱状图
var columnChart = function(chartData, title, divId) {
    // SERIAL CHART
    var chart = new AmCharts.AmSerialChart();
    chart.dataProvider = chartData;
    chart.categoryField = "categoryField";    
    chart.fontFamily = "宋体";
    if (title) {
    	chart.addTitle(title, 15);
	}
//    chart.addTitle("柱状图2", 18, "#FF0000", 0.5, false); //添加第二个标题
    // the following two lines makes chart 3D
    chart.depth3D = 20;
    chart.angle = 30;

    // AXES
    // category (区域  相当于X轴)
    var categoryAxis = chart.categoryAxis;
   // categoryAxis.title = "字段";
    categoryAxis.labelRotation = 45;
    categoryAxis.dashLength = 5;
    categoryAxis.fontSize = 13;
    categoryAxis.gridPosition = "start";

    // value   (值相当于Y轴)
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.dashLength = 4;
    valueAxis.axisAlpha = 0.5;
    valueAxis.axisColor = "#FF6600";
    valueAxis.offset = 0;
    valueAxis.axisThickness = 2;
    valueAxis.fontSize = 13;
    //valueAxis.title = "数量(个)";
    valueAxis.titleColor = "#FF0000";
    valueAxis.integersOnly = true;
    valueAxis.position = "left";
    //valueAxis.recalculateToPercents = true;
    chart.addValueAxis(valueAxis);
    chart.borderAlpha = 0.2; //边框透明度
    chart.startDuration = 2; //动画时间

    // GRAPH  图层          
    var graph = new AmCharts.AmGraph();
    graph.title = "graph1";
    graph.valueField = "valueField";
    graph.colorField = "colorField";
    graph.balloonText = "[[category]]: [[value]]%";
    graph.type = "column";
    graph.lineAlpha = 0;
    graph.fillAlphas = 1;
    graph.legendValueText = "[[value]]";
    chart.addGraph(graph);
    
    // GRAPH  图层 2
    var graph2 = new AmCharts.AmGraph();
    graph2.type = "column";
    graph2.title = "Expenses";
    graph2.valueField = "valueField";
    graph2.colorField = "colorField";
    graph2.lineThickness = 2;
    graph2.bullet = "round";
    graph2.fillAlphas = 1;
    graph2.lineAlpha = 0;
    chart.addGraph(graph2);

    // LEGEND                
    var legend = new AmCharts.AmLegend();
    chart.addLegend(legend);
    
    // LEGEND
    var legend = new AmCharts.AmLegend();
    legend.bulletType = "round";
    legend.equalWidths = false;
    legend.valueWidth = 120;
    legend.color = "#FFCCCC";
    chart.addLegend(legend);
   
    if (divId) {// WRITE  将图表绘制在id为chartdiv的Div层中
        chart.write(divId);
	} else {
		return chart;
	}
//    chart.addLabel(50, 40, "标签Y", "left", 24, "#FF0000", 45, 0.5, true); //添加标签
//    chart.addLabel(750, 460, "标签X", "left", 24, "#FF0000", 45, 0.5, true); //添加标签  
};

//曲线图
var lineChart = function(chartData, title, divId) {
	var chart = new AmCharts.AmSerialChart();

	chart.fontFamily = "宋体";
    chart.dataProvider = chartData;
    chart.pathToImages = "../../js/amcharts/images/";
    chart.categoryField = "categoryField";
    if (title) {
    	chart.addTitle(title, 15);
	}

    // AXES
    // category                
    var categoryAxis = chart.categoryAxis;
    categoryAxis.dashLength = 2;
    categoryAxis.gridAlpha = 0.15;    
    categoryAxis.axisAlpha = 0.5;
    categoryAxis.fontSize = 13;
    categoryAxis.title = "字段";

    // value
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.dashLength = 4;
    valueAxis.axisAlpha = 0.5;
    valueAxis.axisColor = "#FF6600";
    valueAxis.offset = 0;
    valueAxis.axisThickness = 2;
    valueAxis.fontSize = 13;
    valueAxis.title = "数量";
    valueAxis.integersOnly = true;
    chart.addValueAxis(valueAxis);

    // GRAPH
    var graph = new AmCharts.AmGraph();
    graph.type = "line";
    graph.valueField = "valueField";
    graph.lineColor = "#D8E63C";
    graph.customBullet = "../../images/star.gif"; // bullet for all data points
    graph.bulletSize = 14; // bullet image should be a rectangle (width = height)
    chart.addGraph(graph);

    // CURSOR
    var chartCursor = new AmCharts.ChartCursor();
    chart.addChartCursor(chartCursor);

    if (divId) {// WRITE
        chart.write(divId);
	} else {
		return chart;
	}
};

//饼状图
var pieChart = function(chartData, title, divId) {
	// PIE CHART
    var chart = new AmCharts.AmPieChart();
    chart.dataProvider = chartData;
    chart.titleField = "categoryField";
    chart.valueField = "valueField";
    if (title) {
    	chart.addTitle(title, 15);
	}
    chart.outlineColor = "#FFFFFF";
    chart.outlineAlpha = 0.8;
    chart.outlineThickness = 2;
    chart.startAlpha = 0;
    chart.startRadius = "50%";
    chart.pullOutRadius = "9%";
    // this makes the chart 3D
    chart.depth3D = 15;
    chart.angle = 30;
    // LEGEND
    legend = new AmCharts.AmLegend();
    legend.align = "center";
    legend.markerType = "circle";
    chart.addLegend(legend);

    if (divId) {// WRITE
        chart.write(divId);
	} else {
		return chart;
	}
};
