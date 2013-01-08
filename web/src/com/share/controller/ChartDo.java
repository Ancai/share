package com.share.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.share.bean.AmChart;
import com.share.util.WebUtil;

/** 
 *         控制器(web)TEST：amcharts图表组件
 *
 * @author 作      者：lac
 *		  E-mail: liangancai@vrvmail.com.cn 
 * @version V1.0
 *         创建时间：2012-8-4 上午11:17:34 
 */
@Controller
@RequestMapping("/chart")
public class ChartDo {
	/**
	 * 柱状图数据
	 */
	@RequestMapping("/columnData")
	public void columnData(HttpServletRequest request, 
			HttpServletResponse response) {
		List<AmChart> chartData = new ArrayList<AmChart>();
		AmChart amChart = null;
		String[] categories = new String[] {"中国", "美国", "韩国", "法国", "英国", "德国", "意大利", "朝鲜", "俄罗斯", "南非"};
		for (int i = 0; i < 10; i++) {
			amChart = new AmChart(categories[i], (10-i)*10, AmChart.colors[i]);
			chartData.add(amChart);
		}
		WebUtil.write(response, JSONArray.fromObject(chartData).toString());
	}
	
	/**
	 * 曲线图数据
	 */
	@RequestMapping("/lineData")
	public void lineData(HttpServletRequest request, HttpServletResponse response) {
		List<AmChart> chartData = new ArrayList<AmChart>();
		AmChart amChart = null;
		for (int i = 0; i < 10; i++) {
			amChart = new AmChart((i+1)+"", (10-i)*10, AmChart.colors[i]);
			chartData.add(amChart);
		}
		WebUtil.write(response, JSONArray.fromObject(chartData).toString());
	}
	
	/**
	 * 饼图数据
	 */
	@RequestMapping("/pieData")
	@ResponseBody
	public List<AmChart> pieData() {
		List<AmChart> chartData = new ArrayList<AmChart>();
		chartData.add(new AmChart("立陶宛", 260, null));
		chartData.add(new AmChart("爱尔兰", 201, null));
		chartData.add(new AmChart("德国", 65, null));
		chartData.add(new AmChart("澳大利亚", 39, null));
		chartData.add(new AmChart("英国", 19, null));
		chartData.add(new AmChart("拉脱维亚", 10, null));
		
		return chartData;
	}
}
