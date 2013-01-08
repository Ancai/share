package com.share.common;

import java.util.ResourceBundle;

/**
 * 公共类：  系统配置
 * 
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-5-30 下午8:27:29
 * @version 1.0
 */
public final class SystemConfig {
	/**资源文件(resources/systemConfig.properties):系统配置**/
	private static final String CONFIG_FILE = "systemConfig";
	
	/**配置项：系统配置 > 系统名称**/
	public static final String CONFIG_SYSTEM_NAME = "systemName";
	/**配置项：系统配置 > 系统版本**/
	public static final String CONFIG_SYSTEM_VERSION = "systemVersion";
	/**配置项：系统配置 > 系统描述**/
	public static final String CONFIG_SYSTEM_DESCRIPTION = "systemDescription";
	
	/**配置项：备案号**/
	public static final String CONFIG_CERTTEXT = "certtext";
	
	/**配置项：文件上传  > 文件上传最大值,0表示无限制,单位KB**/
	public static final String CONFIG_UPLOAD_LIMIT = "";
	/**配置项：文件上传 > 允许上传的图片文件扩展名（为空表示不允许上传图片文件）**/
	public static final String CONFIG_UPLOAD_IMAGE = "";
	/**配置项：文件上传 > 允许上传的文件扩展名（为空表示不允许上传文件）**/
	public static final String CONFIG_UPLOAD_FILE = "";
	
	/**超级管理员帐户**/
	public static final String CONFIG_ADMIN_NAME = "admin_name";
	public static final String CONFIG_ADMIN_PASS = "admin_password";
	
	/**
	 * 从资源文件(resources/systemConfig.properties)中，获取资源配置项
	 * 
	 * @param key 键
	 * @return String 值
	 */
	public static String getConfig(String key) {
		ResourceBundle rb = ResourceBundle.getBundle(CONFIG_FILE);
		return rb.getString(key);
	}
}
