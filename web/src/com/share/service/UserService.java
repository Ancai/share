package com.share.service;

import com.share.model.User;

/**
 * Service层接口：用户
 * 
 * @author Ancai
 * @since 2012-4-8
 * @version 1.0
 */
public interface UserService extends BaseService<User, Integer> {
	/**
	 * 登陆验证
	 * @param username 账户
	 * @param password 密码
	 * @return User 用户
	 */
	public User checkLogin(String username, String password);
}
