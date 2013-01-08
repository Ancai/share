package com.share.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * JavaBean：User, 传递数据, 测试
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-12 下午2:48:56
 * @version 1.0
 */
public class UserBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**编号**/
	private Integer id;
	/**用户名**/
	private String username;
	/**密码**/
	private String password;
	/**昵称**/
	private String nicename;
	/**性别(1|true:男，0|false：女)**/
	private Boolean sex;
	/**出生日期**/
	private Date birthday;
	/**邮箱**/
	private String email;
	/**用户状态(0：禁用，1：正常)**/
	private Integer status;
	/**注册日期**/
	private Date registDt;
	
	public UserBean() {
		// TODO Auto-generated constructor stub
	}
	
	public UserBean(Integer id, String username, String password,
			String nicename, Boolean sex, Date birthday, String email,
			Integer status, Date registDt) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.nicename = nicename;
		this.sex = sex;
		this.birthday = birthday;
		this.email = email;
		this.status = status;
		this.registDt = registDt;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNicename() {
		return nicename;
	}
	public void setNicename(String nicename) {
		this.nicename = nicename;
	}
	public Boolean getSex() {
		return sex;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getRegistDt() {
		return registDt;
	}
	public void setRegistDt(Date registDt) {
		this.registDt = registDt;
	}
}
