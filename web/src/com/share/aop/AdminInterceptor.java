/**
 * 
 */
package com.share.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.share.common.Constant;
import com.share.util.CoreUtil;

/**
 * AOP：管理员页面的权限验证
 *
 * @author Ancai email：ancai0729@gmail.com
 * @since 2012-8-26 上午8:40:11
 * @version 1.0
 */
public final class AdminInterceptor implements HandlerInterceptor {
	/**视图：管理员登录页面**/
	private static final String VIEW_LOGIN = "/admin/loginUI";
	
	/**过滤的路径**/
	private static final String[] URL_FILTER = new String[] {
		"/Share/admin/loginUI", //登录页面
		"/Share/admin/checkLogin"  //登录验证
	};
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		System.out.println(request.getRequestURI());
		//验证用户是否登录
		if (CoreUtil.notNull(request.getSession().getAttribute(Constant.SESSION_ADMIN))) {
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
