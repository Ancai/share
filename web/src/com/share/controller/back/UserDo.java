package com.share.controller.back;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.share.bean.Pager;
import com.share.common.Constant;
import com.share.controller.BaseDo;
import com.share.model.User;
import com.share.service.UserService;
import com.share.util.CoreUtil;

/**
 * 控制器(后台)：用户
 * 
 * @author Ancai
 * @since 2012-4-9
 * @version 1.0
 */
@Controller
@RequestMapping(value="/user")
public class UserDo extends BaseDo{
	@Resource
	private UserService userService;
	
	/**视图根路径**/
	private static final String VIEW_DEFAULT = "user/";
	
	/**页面的传递的参数名称**/
	private static final String PARAM_USERNAME = "username"; //用户名
	private static final String PARAM_PASSWORD = "password"; //密码
	
	/**
	 * 分页列表
	 */
	@RequestMapping(Constant.URL_LIST)
	public String list(Model model, HttpServletRequest request) {
		model.addAttribute(Constant.PAGE_DATA, userService.findByPager(
				new Pager<User>(request.getParameter(Constant.PAGE_NUMBER), pageLink("/user/"))));
		
		return VIEW_DEFAULT + Constant.VIEW_LIST;
	}
	
	/**
	 * 编辑页面
	 */
	@RequestMapping(Constant.URL_EDIT + "/{"+ Constant.ID +"}")
	public String editUI(ModelMap map, @PathVariable(Constant.ID)Integer id) {
		map.put(Constant.PAGE_MODEL, userService.get(id));
		return VIEW_DEFAULT + Constant.VIEW_EDIT;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(Constant.URL_UPDATE)
	public String update(User user) {
		User tempUser = userService.get(user.getId());
		tempUser.setUsername(user.getUsername());
		tempUser.setNicename(user.getNicename());
		tempUser.setEmail(user.getEmail());
		tempUser.setBirthday(user.getBirthday());
		userService.update(tempUser);
		
		return Constant.VIEW_REDIRECT + Constant.URL_LIST;
	}
	
	/**
	 * 添加页面
	 */
	@RequestMapping(Constant.URL_ADD)
	public String addUI() {
		return VIEW_DEFAULT + Constant.VIEW_ADD;
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(Constant.URL_SAVE)
	public String save(User user) {
		user.setPassword(CoreUtil.encrypt(user.getPassword()));
		user.setStatus(1);
		user.setRegistDt(new Date());
		this.userService.save(user);
		
		return Constant.VIEW_REDIRECT + Constant.URL_LIST;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(Constant.URL_DEL)
	public String delete(@RequestParam(Constant.ID) Integer id) {
		userService.delete(id);
		return Constant.VIEW_REDIRECT + Constant.URL_LIST;
	}
	
	/**
	 * 登录验证
	 */
	@RequestMapping("/login")
	public String login(@RequestParam(PARAM_USERNAME)String username, @RequestParam(PARAM_PASSWORD)String password,
			HttpSession session) {
		User user = userService.checkLogin(username, CoreUtil.encrypt(password));
		if (null != user) {
			//将登录用户的信息存放在会话范围
			session.setAttribute(Constant.SESSION_USER, user);
			return Constant.VIEW_REDIRECT + Constant.VIEW_INDEX;
		} else {
			return Constant.VIEW_REDIRECT + Constant.VIEW_LOGIN;
		}
	}
	
	/**
	 * 用户注册
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	@ResponseBody
	public String register(User user) {
		user.setPassword(CoreUtil.encrypt(user.getPassword()));
		user.setStatus(1);
		user.setRegistDt(new Date());
		this.userService.save(user);
		
		return Constant.MES_SUCCESS;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat(Constant.FORMAT_DATE);
		sdf.setLenient(false);//严格匹配(否)
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
