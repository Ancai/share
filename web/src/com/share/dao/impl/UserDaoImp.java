package com.share.dao.impl;

import org.springframework.stereotype.Repository;

import com.share.dao.UserDao;
import com.share.model.User;

/**
 * 用户的  dao 实现
 * 
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-5-28 下午9:05:16
 * @version 1.0
 */
@Repository("userDao")
public class UserDaoImp extends BaseDaoImpl<User, Integer> implements UserDao{

}
