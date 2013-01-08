/**
 * 
 */
package com.share.util;

import org.junit.Before;
import org.junit.Test;

import com.share.test.BaseTest;


/**
 * 测试类：ZipUtil
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-7 上午10:05:14
 * @version 1.0
 */
public class ZipUtilTest extends BaseTest {
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		log.info(INF_START);
	}

	/**
	 * Test method for {@link com.share.util.ZipUtil#zipFile(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testZipFile() {
		ZipUtil.zipFile("E:/test","music");
	}

	/**
	 * Test method for {@link com.share.util.ZipUtil#unzipFile(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testUnzipFile() {
		ZipUtil.unzipFile("music", "E:/test");
	}

}
