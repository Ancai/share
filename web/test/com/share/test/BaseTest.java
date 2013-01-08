package com.share.test;

import org.jboss.logging.Logger;

/**
 * base test class：所有测试类的根类
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-7 上午11:10:39
 * @version 1.0
 */
public abstract class BaseTest {
	protected Logger log = Logger.getLogger(getClass());
	
	protected final static String INF_START = "start test!";
	protected final static String INF_END = "end test!";
}
