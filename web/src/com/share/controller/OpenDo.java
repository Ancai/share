package com.share.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.share.common.Constant;
import com.share.util.WebUtil;

/**
 * 控制器：公共的/常用的操作
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-10-12 上午11:05:52
 * @version 1.0
 */
@Controller
@RequestMapping("/open")
public class OpenDo {
	/**页面的传递的参数名称**/
	
	/**
	 * 校验验证码
	 */
	@RequestMapping(value = "/checkcode")
	@ResponseBody
	public String checkcode(HttpServletRequest request) {
		String inputCode = request.getParameter(Constant.SESSION_CHECKCODE);
		if (WebUtil.sessionAttr(request, Constant.SESSION_CHECKCODE).equals(inputCode)) {
			return Constant.MES_SUCCESS; //输入的验证码，校验通过
		} else {
			return Constant.MES_FAIL;
		}
	}
}
