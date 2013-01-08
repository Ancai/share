/**
 * 
 */
package com.share.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaBean：operamasks-ui的试验，表示一个网格
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-11-29 上午10:24:14
 * @version 1.0
 */
public class GridData<T> {
	
	/** 总行数  **/
	private int total;
	/** 行数据  **/
	private List<T> rows = new ArrayList<T>();
	/** 列头项 **/
	private List<Column> colmodel = new ArrayList<Column>();
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public List<Column> getColmodel() {
		return colmodel;
	}
	public void setColmodel(List<Column> colmodel) {
		this.colmodel = colmodel;
	}
	
}
