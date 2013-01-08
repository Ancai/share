/**
 * 
 */
package com.share.common;

import java.io.Serializable;

/**
 * 公共类：最简单的JavaBean，传递数据，测试，转化json格式的数据
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-6 下午5:50:45
 * @version 1.0
 */
public class Json implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String key;
	private String val;
	
	public Json(String key, String val) {
		this.key = key;
		this.val = val;
	}
	
	public Json() {
		// TODO Auto-generated constructor stub
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	
}
