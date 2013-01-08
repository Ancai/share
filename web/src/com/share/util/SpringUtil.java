package com.share.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/****
 * 从Spring容器获得对象实例
 * @author Ancai
 * @since 2012-4-10
 * @version 1.0
 */
@Component
public final class SpringUtil implements ApplicationContextAware {
	private static ApplicationContext ctx = null;
	/****
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		ctx = applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized <T> T getBean(String name){
		return (T) ctx.getBean(name);
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized <T> T getBean(Class<?> clazz){
		return (T) ctx.getBean(clazz);
	}

}
