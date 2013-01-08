/**
 * 
 */
package com.share.service;

import java.util.List;

import com.share.model.Ip;

/**
 * Service层接口：Ip(operamasks-ui的试验)
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-11-29 上午11:10:36
 * @version 1.0
 */
public interface IpService {
	
	/**
	 * 获得所有的IP信息
	 * 
	 * @return List<Ip>
	 */
	public List<Ip> ipInfos();
	
	/**
	 * 根据城市获得IP
	 * 
	 * @param paramString 城市
	 * @return List<Ip>
	 */
	public List<Ip> getIpsByCity(String paramString);
}
