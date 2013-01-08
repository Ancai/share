/**
 * 
 */
package com.share.service;

import java.io.IOException;

import com.share.model.Link;
import com.share.model.User;

/**
 * Service层接口：链接
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-8-22 下午3:36:51
 * @version 1.0
 */
public interface LinkService extends BaseService<Link, Integer> {
	/**
	 * 捕获网址链接
	 * 
	 * @param url 网址链接(如：http://www.sohu.com)
	 * @return int 链接数量
	 * @throws IOException 
	 */
	public int catchLinks(String url) throws IOException; 
	
	/**
	 * "我的网址:收藏"功能
	 * 
	 * @param link 链接
	 * @param user 用户
	 */
	public void saveILinks(Link link, User user);
}
