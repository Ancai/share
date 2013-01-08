/**
 * 
 */
package com.share.service.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.share.dao.LinkDao;
import com.share.model.Link;
import com.share.model.User;
import com.share.service.LinkService;

/**
 * Service层实现类：链接
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-8-22 下午3:41:43
 * @version 1.0
 */
@Service("linkService")
public class LinkServiceImpl extends BaseServiceImpl<Link, Integer> implements
		LinkService {
	@SuppressWarnings("unused")
	private LinkDao linkDao;
	
	/** 无效的链接 **/
	private static final String[] INVALID_LINK = new String[]
			{"#", "javascript:void(0);"};
	
	@Resource
	public void setBaseDao(LinkDao linkDao) {
		super.setBaseDao(linkDao);
		this.linkDao = linkDao;
	}

	@Override
	public int catchLinks(String url) throws IOException {
		// TODO Auto-generated method stub
		Document doc = Jsoup.connect(url).userAgent("Chrome").post();
		Iterator<Element> elesIterator = doc.getElementsByTag("a").iterator();
		Element ele = null;
		while (elesIterator.hasNext()) {
			ele = elesIterator.next();
			String href = ele.attr("href");
			if (!ArrayUtils.contains(INVALID_LINK, href.trim())) {
				System.out.println(href);
			}
		}

		return 0;
	}

	@Override
	public void saveILinks(Link link, User user) {
		// TODO Auto-generated method stub
		int flag = 0;
		Set<User> userSet = null;
		if (isExist("url", link.getUrl())) {//已存在该链接
			Link link2 = getList("url", link.getUrl()).get(0);
			userSet = link2.getUsers();
			for (User user2 : userSet) {
				if (user2.getId() == user.getId()) {
					flag = 1;
					return; //已经收藏
				}
			}
			if (0 == flag) {//未收藏
				userSet = new HashSet<User>();
				userSet.add(user);
				link2.setUsers(userSet);
				update(link2);
			}
			
		} else {
			userSet = new HashSet<User>();
			userSet.add(user);
			link.setUsers(userSet);
			save(link);
		}
	}
}
