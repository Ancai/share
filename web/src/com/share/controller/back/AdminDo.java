package com.share.controller.back;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.share.common.Constant;
import com.share.common.SystemConfig;


/**
 * 控制器(后台)：管理员访问页面
 * 
 * @author Ancai
 * @since 2012-4-10
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminDo {
	
	/**视图根路径**/
	private static final String VIEW_DEFAULT = "admin/";
	
	/**页面传递的参数：管理员帐户名**/
	private static final String PARAM_ADMIN_NAME = "adminName";
	/**页面传递的参数：管理员密码**/
	private static final String PARAM_ADMIN_PASS = "adminPass";
	
	/**
	 * 后台主页面
	 */
	@RequestMapping(value="/index")
	public String index(HttpServletRequest request) {
		return VIEW_DEFAULT + "index";
	}
	
	/**
	 * 后台欢迎页面(默认页)
	 */
	@RequestMapping(value = "/default")
	public String welcomePage(ModelMap map) {
		map.put(Constant.SERVER_DATE, new Date());
		return VIEW_DEFAULT + "default";
	}
	
	/**
	 * 管理员登录页面
	 */
	@RequestMapping("/loginUI")
	public String adminLoginUI(ModelMap map) {
		map.put(Constant.SERVER_DATE, new Date());
		return VIEW_DEFAULT + "loginUI";
	}
	
	/**
	 * 管理员登录验证
	 */
	@RequestMapping(value = "/checkLogin")
	public String adminLogin(@RequestParam(PARAM_ADMIN_NAME)String adminName,
			@RequestParam(PARAM_ADMIN_PASS)String adminPass, HttpSession session) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String tempKey = sdf.format(new Date());
		if (adminName.equals(SystemConfig.getConfig(SystemConfig.CONFIG_ADMIN_NAME) + tempKey)
				&& adminPass.equals(SystemConfig.getConfig(SystemConfig.CONFIG_ADMIN_PASS) + tempKey)) {
			session.setAttribute(Constant.SESSION_ADMIN, adminName);
			return Constant.VIEW_REDIRECT + "default";
		} else {
			return VIEW_DEFAULT + "loginUI";
		}	
	}
}
