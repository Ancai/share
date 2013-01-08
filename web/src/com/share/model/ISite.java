/**
 * 
 */
package com.share.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 模型类：我的网址
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-10-23 上午9:47:29
 * @version 1.0
 */
@Entity
@Table(name = "sh_isite")
public class ISite extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 编号 **/
	private Long id;
	/** 名称 **/
	private String name;
	/** 网址 **/
	private String url;
	/** 网址类型 **/
	private Integer urlType;
	/** 用户编号  **/
	private Integer userId;
	/** 更新日期 **/
	private Date updated;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "url", nullable = false)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "urlType")
	public Integer getUrlType() {
		return urlType;
	}
	public void setUrlType(Integer urlType) {
		this.urlType = urlType;
	}
	
	@Column(name = "userId")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "updated")
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
}
