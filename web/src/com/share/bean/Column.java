/**
 * 
 */
package com.share.bean;

import java.io.Serializable;

/**
 * JavaBean：operamasks-ui的试验，表示列头
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-11-29 上午10:27:46
 * @version 1.0
 */
public class Column implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 显示文本 **/
	private String header;
	/** 宽度 **/
	private int width;
	/** 属性名称 **/
	private String name;
	/** 对齐方式 **/
	private String align;
	/** 渲染效果 **/
	private String render;
	
	public Column() {
		
	}

	public Column(String header, int width, String name, String align,
			String render) {
		this.header = header;
		this.width = width;
		this.name = name;
		this.align = align;
		this.render = render;
	}
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getRender() {
		return render;
	}
	public void setRender(String render) {
		this.render = render;
	}
	
}
