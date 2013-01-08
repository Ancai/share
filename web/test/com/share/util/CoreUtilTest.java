/**
 * 
 */
package com.share.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.share.common.Json;
import com.share.test.BaseTest;

/**
 * 测试类：CoreUtil
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-6 下午5:09:44
 * @version 1.0
 */
public class CoreUtilTest extends BaseTest {	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		log.info(INF_START);
	}

	/**
	 * Test method for {@link com.share.util.CoreUtil#encrypt(java.lang.String)}.
	 */
	@Test
	public void testEncrypt() {
		assertEquals("E10ADC3949BA59ABBE56E057F20F883E", CoreUtil.encrypt("123456").toUpperCase());
	}

	/**
	 * Test method for {@link com.share.util.CoreUtil#isNull(java.lang.Object)}.
	 */
	@Test
	public void testIsNull() {
		Object obj = null;
		assertEquals(true, CoreUtil.isNull(obj));
		obj = "test";
		assertEquals(false, CoreUtil.isNull(obj));
	}

	/**
	 * Test method for {@link com.share.util.CoreUtil#notNull(java.lang.Object)}.
	 */
	@Test
	public void testNotNull() {
		Object obj = null;
		assertEquals(false, CoreUtil.notNull(obj));
		obj = "test";
		assertEquals(true, CoreUtil.notNull(obj));
	}

	/**
	 * Test method for {@link com.share.util.CoreUtil#formatDate(java.util.Date, java.lang.String)}.
	 */
	@Ignore
	public void testFormatDate() {
		Date dt = new Date();
		String pattern = "yyyy-MM-dd";
		assertEquals("2012-12-6", CoreUtil.formatDate(dt, pattern));
	}

	/**
	 * Test method for {@link com.share.util.CoreUtil#parseDate(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	public void testParseDate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.share.util.CoreUtil#json(java.util.List, java.lang.String[])}.
	 */
	@Test
	public void testJson() {
		List<Json> jsonData = new ArrayList<Json>();
		for (int i = 0; i < 10; i++) {
			jsonData.add(new Json("00"+i, "00"+i));
		}
		System.out.println(CoreUtil.json(jsonData));
		assertEquals("", CoreUtil.json(jsonData));
	}

}
