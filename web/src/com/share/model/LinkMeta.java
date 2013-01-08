package com.share.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 链接的元信息
 * 
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-8-4 下午8:18:38
 * @version 1.0
 */

@Entity
@Table(name = "wp_linkmeta")
@Cache(usage =  CacheConcurrencyStrategy.READ_WRITE)
public class LinkMeta extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**编号**/
	private Integer id;
	/**元信息-键**/
	private String key;
	/**元信息-值**/
	private String value;
	/**链接对象**/
	private Link link;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "meta_id", nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "meta_key")
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	@Column(name = "meta_value")
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@ManyToOne()
	public Link getLink() {
		return link;
	}
	public void setLink(Link link) {
		this.link = link;
	}
	
}
