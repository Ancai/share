package com.share.controller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.share.common.Constant;
import com.share.model.Link;
import com.share.model.User;
import com.share.service.UserService;
import com.share.util.CoreUtil;
import com.share.util.WebUtil;

/**
 * 控制器(web)：前台主页面的请求和流转操作
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-9-28 下午5:10:05
 * @version 1.0
 */
@Controller
@RequestMapping("/test/index")
public class IndexDoTest extends com.share.controller.BaseDo{
	@Resource
	private UserService userService;
	
	/**页面的传递的参数名称**/
	private static final String PARAM_USERNAME = "username"; //用户名
	private static final String PARAM_PASSWORD = "password"; //密码
	private static final String PARAM_EMAIL = "email"; //邮箱
	
	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/login", method = {RequestMethod.POST})
	@ResponseBody
	public String login(HttpServletRequest request, @RequestParam(PARAM_USERNAME)String username, 
			@RequestParam(PARAM_PASSWORD)String password) {
		User user = userService.checkLogin(username, CoreUtil.encrypt(password));
		if (null != user) {
			//将登录用户的信息存放在会话范围
			WebUtil.sessionAttr(request, Constant.SESSION_USER, user);
			return Constant.MES_SUCCESS;
		} else {
			return Constant.MES_FAIL;
		}
	}
	
	/**
	 * 用户注册
	 */
	@RequestMapping(value = "/register", method = {RequestMethod.POST})
	@ResponseBody
	public String regist(@RequestParam(PARAM_USERNAME)String username, 
			@RequestParam(PARAM_PASSWORD)String password, @RequestParam(PARAM_EMAIL)String email) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(CoreUtil.encrypt(password));
		user.setStatus(1);
		user.setEmail(email);
		user.setRegistDt(new Date());
		this.userService.save(user);
		
		return Constant.MES_SUCCESS;
	}
	
	/**
	 * 用户注销
	 */
	@RequestMapping(value = "/exit")
	@ResponseBody
	public String exit(HttpServletRequest request) {
		WebUtil.sessionAttr(request, Constant.SESSION_USER, null);
		return Constant.MES_SUCCESS;
	}
	
	/**
	 * 用户名是否被注册
	 */
	@RequestMapping(value = "/exist")
	@ResponseBody
	public String usernameExist(@RequestParam(PARAM_USERNAME)String username) {
		if (userService.isExist("username", username)) {
			return Constant.MES_FAIL; //已被注册，返回“失败”消息
		} else {
			return Constant.MES_SUCCESS;
		}
	}
	
	/**
	 * 邮箱是否被注册
	 */
	@RequestMapping(value = "/exist/email")
	@ResponseBody
	public String emailExist(@RequestParam(PARAM_EMAIL)String email) {
		if (userService.isExist("email", email)) {
			return Constant.MES_FAIL; //已被注册，返回“失败”消息
		} else {
			return Constant.MES_SUCCESS;
		}
	}
	
	/**
	 * "我的网址"功能
	 */
	@RequestMapping(value = "/isites", method = {RequestMethod.POST, RequestMethod.GET})	
	@ResponseBody
	public List<Link> iLinks(HttpServletRequest request) {
		List<Link> list = new ArrayList<Link>();
		Link link;
		for (int i = 0; i < 10; i++) {
			link = new Link();
			link.setName("ii"+i);
			link.setUrl("http://www.baidu.com");
			list.add(link);
		}
		System.out.println(CoreUtil.json(list, "name", "url"));
		return list;
	}
}
