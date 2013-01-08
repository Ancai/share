/**
 * 
 */
package com.share.service;

import com.share.model.ISite;
import com.share.model.User;

/**
 * Service层接口：我的网址
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-10-23 上午10:12:28
 * @version 1.0
 */
public interface ISiteService extends BaseService<ISite, Long> {
	/**
	 * "我的网址:收藏"功能
	 * 
	 * @param iSite 我的网址(模型)
	 * @param user 用户
	 */
	public void saveISite(ISite iSite, User user);
}
