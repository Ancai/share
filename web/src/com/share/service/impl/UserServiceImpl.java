package com.share.service.impl;

import java.util.Properties;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.share.dao.UserDao;
import com.share.model.User;
import com.share.service.UserService;

/**
 * @author Ancai
 * @since 2012-4-8
 * @version 1.0
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements UserService {
	private UserDao userDao;
	@Resource
	public void setBaseDao(UserDao userDao) {
		super.setBaseDao(userDao);
		this.userDao = userDao;
	}

	@Override
	public User checkLogin(String username, String password) {
		// TODO Auto-generated method stub
		Properties props = new Properties();
		props.put("username", username);
		props.put("password", password);
		
		return this.userDao.listBy(props).size() > 0 ? this.userDao.listBy(props).get(0) : null;
	}
}
