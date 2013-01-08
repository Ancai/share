/**
 * 
 */
package com.share.aop;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * AOP：在线用户统计(监听器)
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-10-26 上午11:38:36
 * @version 1.0
 */
public final class OnLineListener implements HttpSessionListener  {
	private int onlineCount = 0;
	private static final String ON_LINE_USERS = "onLineUsers";

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		onlineCount++;
		se.getSession().getServletContext().setAttribute(ON_LINE_USERS, new Integer(onlineCount));
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		onlineCount--;
		se.getSession().getServletContext().setAttribute(ON_LINE_USERS, new Integer(onlineCount));
	}
}
