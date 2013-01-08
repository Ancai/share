/**
 * 
 */
package com.share.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.share.util.WebUtil;

/** 
 * 测试类：Controller测试
 * 
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-8-4 下午8:35:41
 * @version 1.0
 */
@Controller
@RequestMapping("/test")
public class WebTest{
	@RequestMapping("/1.html")
	public void test(HttpServletRequest request, HttpServletResponse response) {
		WebUtil.write(response, String.valueOf(""));
	}
}
