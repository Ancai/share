/**
 * 
 */
package com.share.bean;

import java.util.Date;

/**
 * Bean类：文件或目录
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-8-29 下午5:13:37
 * @version 1.0
 */
public final class FileBean {
	/**类型枚举**/
	public enum Type {
		FILE, DIRECTORY
	}
	
	/**
	 * 文件名
	 */
	private String name;
	/**
	 * 文件大小
	 */
	private long size;
	/**
	 * 上传时间
	 */
	private Date uploadTime;
	/**
	 * 最后更新时间
	 */
	private Date updateTime;
	/**
	 * 文件类型
	 */
	private Type fileType;
	/**
	 * 相对(WEB根目录)路径
	 */
	private String webPath;
	
	public FileBean() {
	}
	public FileBean(String name, long size, Date uploadTime,
			Date updateTime, Type fileType, String webPath) {
		this.name = name;
		this.size = size;
		this.uploadTime = uploadTime;
		this.updateTime = updateTime;
		this.fileType = fileType;
		this.webPath = webPath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Type getFileType() {
		return fileType;
	}
	public void setFileType(Type fileType) {
		this.fileType = fileType;
	}
	public String getWebPath() {
		return webPath;
	}
	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}
	
}
