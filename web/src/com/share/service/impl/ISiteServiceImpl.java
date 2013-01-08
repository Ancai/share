/**
 * 
 */
package com.share.service.impl;

import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.share.dao.ISiteDao;
import com.share.model.ISite;
import com.share.model.User;
import com.share.service.ISiteService;

/**
 * Service层实现类：我的网址
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-10-23 上午10:15:23
 * @version 1.0
 */
@Service("iSiteService")
public class ISiteServiceImpl extends BaseServiceImpl<ISite, Long> implements
		ISiteService {
	private ISiteDao iSiteDao;
	
	@Resource
	public void setBaseDao(ISiteDao iSiteDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(iSiteDao);
		this.iSiteDao = iSiteDao;
	}

	@Override
	public void saveISite(ISite iSite, User user) {
		// TODO Auto-generated method stub
		Properties props = new Properties();
		props.put("name", iSite.getName());
		props.put("url", iSite.getUrl());
		props.put("userId", user.getId());
		
		if (this.iSiteDao.listBy(props).size() <= 0) {//网址未被收藏
			iSite.setUserId(user.getId());
			iSite.setUpdated(new Date());
			this.iSiteDao.save(iSite);	
		}
	}
}
