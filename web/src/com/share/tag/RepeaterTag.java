package com.share.tag;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.share.util.CoreUtil;

/**
 * 标签类：实现"迭代数据"的功能
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-12 下午5:06:59
 * @version 1.0
 */
public class RepeaterTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private Collection<?> items;
	private Iterator<?> it;
	private String var;
	
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		if (CoreUtil.isNull(items) || 0 == items.size()) {
			return SKIP_BODY;
		}
		it = items.iterator();
		if (it.hasNext()) {
			pageContext.setAttribute(var, it.next());
		}
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doAfterBody() throws JspException {
		// TODO Auto-generated method stub
		if (it.hasNext()) {
			pageContext.setAttribute(var, it.next());
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return EVAL_PAGE;
	}
	
	public void setItems(Collection<?> items) {
		this.items = items;
	}
	public void setVar(String var) {
		this.var = var;
	}
}
