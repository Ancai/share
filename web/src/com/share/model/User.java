package com.share.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.stereotype.Component;


/**
 * 模型类：用户
 * 
 * @author Ancai
 * @since 2012-4-8
 * @version 1.0
 */
@Component
@Entity
@Table(name = "wp_users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends BaseModel {

	/**
	 * 
	 */
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
	//private Date updateDt;
	
	/**用户自定义网址**/
	private Set<Link> links;
	
	/**
	 * 构造方法
	 */
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(Integer id, String username, String password, String nicename,
			Boolean sex, Date birthday, String email, Integer status,
			Date registDt) {
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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_login", nullable = false, unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "user_pass", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="user_nicename")
	public String getNicename() {
		return nicename;
	}

	public void setNicename(String nicename) {
		this.nicename = nicename;
	}

	@Column(name = "sex")
	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	@Column(name = "birthday")
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "user_email", unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "user_status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "user_registered")
	public Date getRegistDt() {
		return registDt;
	}

	public void setRegistDt(Date registDt) {
		this.registDt = registDt;
	}
	
	@ManyToMany
	@JoinTable(name = "wp_users_links", inverseJoinColumns = @JoinColumn(name = "link_id"), joinColumns = @JoinColumn(name = "user_id"))
	public Set<Link> getLinks() {
		return links;
	}
	public void setLinks(Set<Link> links) {
		this.links = links;
	}
	
}
