/**
 * 
 */
package com.share.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.share.bean.UserBean;

/**
 * 控制器：自定义标签(试验)
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-12 下午4:39:12
 * @version 1.0
 */
@Controller
@RequestMapping("/tag")
public class TagDo {
	
	@RequestMapping(value = "/mytag", method = {RequestMethod.POST, RequestMethod.GET})
	public String myTag(ModelMap map) {
		UserBean user = new UserBean();
		user.setNicename("xiaohao");
		user.setUsername("xiaohao");
		user.setBirthday(new Date());
		user.setEmail("ancai0729@gmail.com");
		map.put("user", user);
		
		List<UserBean> userList = new ArrayList<UserBean>();
		userList.add(new UserBean(1, "aa", "***", "AA", true, new Date(), "aa@gmail.com", 1, new Date()));
		userList.add(new UserBean(2, "bb", "***", "BB", true, new Date(), "bb@gmail.com", 0, new Date()));
		userList.add(new UserBean(3, "cc", "***", "CC", true, new Date(), "cc@gmail.com", 1, new Date()));
		userList.add(new UserBean(4, "dd", "***", "DD", true, new Date(), "dd@gmail.com", 0, new Date()));
		userList.add(new UserBean(5, "ee", "***", "EE", true, new Date(), "ee@gmail.com", 1, new Date()));
		userList.add(new UserBean(6, "ff", "***", "FF", true, new Date(), "ff@gmail.com", 0, new Date()));
		map.put("users", userList);
		
		return "taglib/mytaglib";
	}
}
