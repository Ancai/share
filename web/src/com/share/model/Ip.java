/**
 * 
 */
package com.share.model;

import java.io.Serializable;

/**
 * 模型类：operamasks-ui的试验
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-11-29 上午11:00:25
 * @version 1.0
 */
public class Ip implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String start;
	private String end;
	private String city;
	private String address;
	
	public Ip() {
	}

	public Ip(int id, String start, String end, String city, String address) {
		this.id = id;
		this.start = start;
		this.end = end;
		this.city = city;
		this.address = address;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean equals(Object paramObject)
	{
	    if ((paramObject == null) || (!(paramObject instanceof Ip)))
	      return false;
	    Ip localIp = (Ip)paramObject;
	    return this.id == localIp.id;
	 }
	
	public int hashCode()
	{
	    return new Integer(this.id).hashCode();
	}
}
