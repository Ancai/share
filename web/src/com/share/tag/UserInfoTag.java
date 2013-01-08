/**
 * 
 */
package com.share.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.share.bean.UserBean;
import com.share.util.CoreUtil;

/**
 * 标签类：实现"用表格显示用户信息"的功能
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-12 下午2:55:42
 * @version 1.0
 */
public class UserInfoTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private UserBean user;
	
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		JspWriter out = this.pageContext.getOut();
		try {
			if (CoreUtil.isNull(user)) {
				out.print("No UserInfo Found...");
				return SKIP_BODY;
			}
			
			out.println("<table width='500px' border='1' align='center'>");
            out.println("<tr>");
            out.println("<td width='20%'>Username:</td>");
            out.println("<td>" + user.getUsername() + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Age:</td>");
            out.println("<td>" + user.getBirthday() + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Email:</td>");
            out.println("<td>" + user.getEmail() + "</td>");
            out.println("</tr>");
            out.println("</table>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new JspException(e.getMessage());
		}
		
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return EVAL_PAGE;
	}
	
	@Override
	public void release() {
		// TODO Auto-generated method stub
		super.release();
		this.user = null;
	}

	//属性方法
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
}
