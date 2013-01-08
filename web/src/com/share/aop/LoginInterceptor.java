package com.share.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.share.common.Constant;

/**
 * AOP：登录验证(普通用户)
 * 
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-6-3 上午8:46:29
 * @version 1.0
 */
@Component
public final class LoginInterceptor implements HandlerInterceptor{
	/**视图：登录页面**/
	private static final String VIEW_LOGIN = "/html/login.html";
	
	/**过滤的路径**/
	private static final String[] URL_FILTER = new String[] {
		"/Share/user/login", //前台登录
		"/Share/back/login"  //后台登陆
	};
	
	@Override
	public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception{
		boolean flag = false;
		//验证用户是否登录
		if (null != request.getSession().getAttribute(Constant.SESSION_USER)) {
			flag = true;
		} else if (ArrayUtils.contains(URL_FILTER, request.getRequestURI())) {
			flag = true;
		} else {
			response.sendRedirect(request.getContextPath() + VIEW_LOGIN);
		}
		
		return flag;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
