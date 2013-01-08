/**
 * 
 */
package com.share.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.share.common.Constant;
import com.share.model.User;
import com.share.service.UserService;
import com.share.util.CoreUtil;
import com.share.util.SpringUtil;
import com.share.util.WebUtil;

/**
 * 控制器：根类
 *
 * @author Ancai email：ancai0729@gmail.com
 * @since 2012-8-26 下午5:05:54
 * @version 1.0
 */
public abstract class BaseDo {
	/** 日志记录器 **/
	protected Logger log = Logger.getLogger(getClass());
	/**
	 * 获得分页的链接路径
	 * 
	 * @param mapping 控制器映射的根路径(如："/user/")
	 * @return String 如 "/Share/user/list"
	 */
	protected String pageLink(String mapping) {
		return Constant.SERVER_ROOT_PATH + mapping + Constant.URL_LIST;
	}
	
	/**
	 * 获得在线用户
	 * 
	 * @param request
	 * @return User
	 */
	protected User onlineUser(HttpServletRequest request) {
		User currentUser = null ;
		if (CoreUtil.isNull(WebUtil.sessionAttr(request, Constant.SESSION_USER))) {//Session中的User已过期
			int userId = Integer.valueOf(WebUtil.cookieAttr(request, Constant.COOKIE_USER_ID));
			UserService userService = (UserService)SpringUtil.getBean(UserService.class);
			currentUser = userService.get(userId);
			//重新放入Session中
			WebUtil.sessionAttr(request, Constant.SESSION_USER, currentUser);
		} else {
			currentUser = (User) WebUtil.sessionAttr(request, Constant.SESSION_USER);
		}
		
		return currentUser;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat(Constant.FORMAT_DATATIME);
		sdf.setLenient(false);//严格匹配(否)
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
