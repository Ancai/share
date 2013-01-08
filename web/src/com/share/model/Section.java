package com.share.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * 模型类：栏目/板块/分类
 * 
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-8-6 上午10:04
 * @version 1.0
 */
@Entity
@Table(name = "wp_sections")
public class Section extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**编号**/
	private Integer id;
	/**名称**/
	private String name;
	/**更新日期**/
	private Date updated;
	
	/**父级**/
	private Section parent;
	/**包含的链接**/
	private Set<Link> links;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "updated")
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@ManyToOne
	@JoinColumn(name = "pid")
	public Section getParent() {
		return parent;
	}
	public void setParent(Section parent) {
		this.parent = parent;
	}
	
	@OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
	public Set<Link> getLinks() {
		return links;
	}
	public void setLinks(Set<Link> links) {
		this.links = links;
	}
	
	
}
