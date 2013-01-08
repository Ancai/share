package com.share.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.share.util.CoreUtil;

/**
 * Bean 类  --分页
 * 
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-5-28 下午9:16:06
 * @version 1.0
 */
public class Pager<T> {
	/**
	 * 排序方式
	 */
	public enum OrderType{
		asc, desc
	}
	
	/**每页最大记录数限制**/
	private static final int MAX_PAGE_SIZE = 500;
	/**默认显示的页码数：1 2 3 4 5 [6] 7 8 9 10**/
	private static final int PAGE_LABEL_NUMBER = 10;

	/**当前页码**/
	private int pageNumber = 1;
	/**每页记录数**/
	private int pageSize = 20; 
	/**总记录数**/
	private long totalCount = 0L;
	/**总页数**/
	private int pageCount = 0;
	/**查找属性名称**/
	private String property;
	/**查找关键字**/
	private String keyword; 
	/**排序字段**/
	private String orderBy = "id";
	/**排序方式**/
	private OrderType orderType = OrderType.desc; 
	/** 数据List**/
	private List<T> list;
	/**数据源链接**/
	private String link;
	
	/**构造方法**/
	public Pager() {
		super();
	}
	/**
	 * 适用：从数据库获取分页数据
	 * 		例：[new Pager<User>(request.getParameter(Constant.PAGE_NUMBER), pageLink("/user/")]
	 * 
	 * @param pageNumber 当前页
	 * @param link 页码的链接地址
	 */
	public Pager(String pageNumber, String link) {
		if (CoreUtil.notNull(pageNumber)) {
			this.pageNumber = Integer.valueOf(pageNumber);
		} else {
			this.pageNumber = 1;
		}
		this.link = link;
	}
	
	/**
	 * 适用：人为地制造分页数据
	 * 		例：[new Pager<User>(request.getParameter(Constant.PAGE_NUMBER), pageLink("/user/"), allData)]
	 * 
	 * @param pageNumber 当前页
	 * @param link 页码的链接地址
	 * @param allData 所有数据记录
	 */
	public Pager(String pageNumber, String link, List<T> allData) {
		if (CoreUtil.notNull(pageNumber)) {
			this.pageNumber = Integer.valueOf(pageNumber);
		} else {
			this.pageNumber = 1;
		}
		this.link = link;
		this.list = new ArrayList<T>();
		this.totalCount = allData.size();
		for (int i = (this.pageNumber - 1) * pageSize; 
				(i < this.pageNumber * pageSize && i < allData.size()); i++) {
			this.list.add(allData.get(i));
		}
	}
	
	public int getPageNumber() {
		if (pageNumber < 1) {
			pageNumber = 1;
		}
		return this.pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if(pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}
	
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		pageCount = (int) (totalCount / pageSize);
		if (totalCount % pageSize > 0) {
			pageCount ++;
		}
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}

	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}

	public String getLink() {
		return StringUtils.contains(link, "?") ? link : link + "?1=1";
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	/**
	 * 获得分页html代码，如：<div class="pages"><span>共<font>380</font>条.....</div>
	 * 
	 * @return String
	 */
	public String getLabel() {
		StringBuffer sbuff = new StringBuffer("<div class='pages'><span>共<font>");
		sbuff.append(this.getTotalCount()).append("</font>条</span>");
		
		//计算页码的开始和结束位置
		int start, end;
		int tempStart = getPageNumber() - PAGE_LABEL_NUMBER / 2;
		start = tempStart < 1 ? 1 : tempStart;
		int tempEnd = getPageNumber() + PAGE_LABEL_NUMBER / 2;
		end = tempEnd > getPageCount() ? getPageCount() : tempEnd;
		
		//拼接页码标签，如<a href="">2</a><a href="">3</a><a href="">4</a>
		for (int i = start; i <= end; i++) {
			if (i == getPageNumber()) {
				sbuff.append("<b>"+ getPageNumber() +"</b>");
			
			} else {
				sbuff.append("<a href=\""+ getLink() +"&pageNumber="+ i +"\">"+ i +"</a>");
			}
		}
		
		//拼接页码标签，如 <a href="">下一页>></a><a href="">最后一页>></a>
		sbuff.append("<a href=\""+ getLink() +"&pageNumber="+ (getPageNumber() >= getPageCount() ? getPageCount() : (getPageNumber()+1) )+"\">下一页>></a>");
		sbuff.append("<a href=\""+ getLink() +"&pageNumber="+ getPageCount() +"\">最后一页>></a>");
		sbuff.append("</div>");
		
		return sbuff.toString();
	}
}
