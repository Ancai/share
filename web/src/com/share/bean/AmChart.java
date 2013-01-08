package com.share.bean;

/** 
 * Bean类：使用Amcharts组件的辅助JavaBean
 *
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-6-13 下午01:56:21
 * @version 1.0
 */
public class AmChart {
	//X轴字段
	private String categoryField;
	//Y轴字段
	private int valueField;
	//柱子的颜色
	private String colorField;
	//预先设定的17种颜色
	public static final String[] colors = new String[]{
		"#FF0F00", "#FF6600", "#FF9E01", "#FCD202", "#F8FF01", 
		"#B0DE09", "#04D215", "#0D8ECF", "#0D52D1", "#2A0CD0",
		"#8A0CCF", "#CD0D74", "#754DEB", "#DDDDDD", "#999999",
		"#333333", "#000000"};
	
	public AmChart() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AmChart(String categoryField, int valueField, String colorField) {
		super();
		this.categoryField = categoryField;
		this.valueField = valueField;
		this.colorField = colorField;
	}
	
	public String getCategoryField() {
		return categoryField;
	}
	public void setCategoryField(String categoryField) {
		this.categoryField = categoryField;
	}
	public int getValueField() {
		return valueField;
	}
	public void setValueField(int valueField) {
		this.valueField = valueField;
	}
	public String getColorField() {
		return colorField;
	}
	public void setColorField(String colorField) {
		this.colorField = colorField;
	}
}
