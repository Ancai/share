/**
 * 
 */
package com.share.model;

import java.io.Serializable;

/**
 * 模型类：operamasks-ui的试验
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-11-29 下午2:40:35
 * @version 1.0
 */
public class Staff implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String email;
	private String phone;
	private int gender;
	private String province;
	private String city;
	
	public Staff() {
		// TODO Auto-generated constructor stub
	}

	public Staff(int id, String name, String email, String phone, int gender,
			String province, String city) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.province = province;
		this.city = city;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
