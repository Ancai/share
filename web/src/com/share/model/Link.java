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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


/**
 * 模型类：导航链接
 * 
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-8-4 上午11:20:39
 * @version 1.0
 */

@Entity
@Table(name = "wp_links")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Link extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**编号**/
	private Integer id;
	/**名称(如“搜狐”，“谷歌”)**/
	private String name;
	/**地址(如"http://www.google.com")**/
	private String url;
	/**图片路径**/
	private String image;
	/**打开方式**/
	private String target;
	/**描述**/
	private String description;
	/**是否显示**/
	private Boolean visible;
	/**等级**/
	private Integer rating;
	/**更新时间**/
	private Date updated;
	
	/**链接的元信息**/
	private Set<LinkMeta> linkMetas;
	/**栏目/板块/分类**/
	private Section section;
	/**拥有该链接的用户**/
	private Set<User> users;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "link_name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "link_url", unique = true)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "link_image")
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Column(name = "link_target")
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	@Column(name = "link_description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "link_visible")
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	@Column(name = "link_rating")
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	@Column(name = "link_updated")
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	@OneToMany(mappedBy = "link", fetch = FetchType.LAZY)
	public Set<LinkMeta> getLinkMetas() {
		return linkMetas;
	}
	public void setLinkMetas(Set<LinkMeta> linkMetas) {
		this.linkMetas = linkMetas;
	}
	
	@ManyToOne
	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}
	
	@ManyToMany
	@Cascade(value = CascadeType.SAVE_UPDATE)
	@JoinTable(name = "wp_users_links", inverseJoinColumns = @JoinColumn(name = "user_id"), joinColumns = @JoinColumn(name = "link_id"))
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
