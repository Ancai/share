package com.share.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 组织HQL语句的工具类
 * @author glw
 *
 */
public class HqlUtil {
	/**
	 * SELECT 子句
	 */
	private String select; 
	
	/**
	 * FROM 子句
	 */
	private String from;
	
	/**
	 * JOIN子句
	 */
	private String join;
	
	/**
	 * WHERE 子句
	 */
	private String where;
	
	/**
	 * ORDERBY 子句
	 */
	private String orderBy;
	
	/**
	 * GROUPBY 子句
	 */
	private String groupBy;

	/**
	 * HAVING 子句
	 */
	private String having;
	
	/**
	 * 参数的列表
	 */
	private List<Object> paramList = new ArrayList<Object>();
	
	/**
	 * 获取参数列表
	 * @return 参数集合
	 */
	public List<Object> getParamList() {
		return paramList;
	}
	
	/**
	 * 通过构造方法创建 FROM字句
	 * @param entityClass 实体类.class
	 */
	public HqlUtil(Class<?> entityClass){
		this.from = " FROM " + entityClass.getName() + " this ";
	}
	
	/**
	 * 指定要查询的属性，拼接SELECT子句
	 * @param property  要查询的属性
	 * @return 工具类本身
	 */
	public HqlUtil addSelect(String property) {
		if (StringUtils.isBlank(select)) {
			select = " SELECT " + property;
		} else {
			select += " , " + property;
		}
		return this;
	}
	/**
	 *  添加左外连接语句，组织JOIN语句
	 * @param join 连接条件
	 * @param params 参数列表
	 * @return 工具类本身
	 */
	public HqlUtil addJoin(String join, Object... params){
		//1.组织语句
		if(StringUtils.isBlank(this.join)){
			this.join = " LEFT JOIN  " + join;
		}else{
			this.join += " LEFT JOIN  " + join;
		}
		//2.添加参数
		if(params != null && params.length>0){
			for(Object param : params){
				paramList.add(param);
			}
		}
		return this;
	}
	/**
	 * 添加过滤条件，组织WHERE语句(可变参数附条件)
	 * @param condition 过滤的条件
	 * @param params 参数列表
	 * @return 工具类本身
	 */
	public HqlUtil addWhere(String condition, Object... params){
		//1.组织语句
		if(StringUtils.isBlank(where)){
			where = " WHERE " + condition;
		}else{
			where += " AND " + condition;
		}
		//2.添加参数
		if(params != null && params.length>0){
			for (Object param : params) {
				paramList.add(param);
			}
		}
		return this;
	}
	
	/**
	 * 添加过滤条件，组织WHERE语句(可变参数附条件)
	 * @param condition 过滤的条件
	 * @param andOror 是且(即AND)还是或(即OR)： true AND,false or 
	 * @param params 参数列表
	 * @return 工具类本身
	 */
	public HqlUtil addWhere(String condition,boolean andOror, Object... params){
		//1.组织语句
		if(StringUtils.isBlank(where)){
			where = " WHERE " + condition;
		}else{
			where += (andOror?" AND ":" OR ") + condition;
		}
		//2.添加参数
		if(params != null && params.length>0){
			for (Object param : params) {
				paramList.add(param);
			}
		}
		return this;
	}
	/**
	 * 添加排序规则，组织ORDERBY子句
	 * @param condition 排序条件
	 * @param reverse 是否降序排列： true 降序,false 升序
	 * @return 工具类本身
	 */
	public HqlUtil addOrderByProperty(String condition,boolean reverse){
		//1.组织语句
		if(StringUtils.isBlank(orderBy)){
			orderBy = " ORDER BY " + condition +(reverse ? " DESC " : " ASC ");
		}else{
			orderBy += " , " + condition +(reverse ? " DESC " : " ASC ");
		}
		return this;
	}
	
	/**
	 * 添加分组语句，组织GROUPBY子句
	 * @param conditon 分组条件
	 * @param params 分组条件中的参数
	 * @return 工具类本身
	 */
	public HqlUtil addGroupBy(String conditon,Object... params){
		//1.组织语句
		if(StringUtils.isBlank(groupBy)){
			groupBy = " GROUP BY " + conditon;
		}else{
			groupBy += "," + conditon;
		}
		//2.添加参数
		if(params != null && params.length>0){
			for (Object param : params) {
				paramList.add(param);
			}
		}
		return this;
	}
	
	public HqlUtil addHaving(String condition,Object...params){
		//1.组织语句
		if(StringUtils.isBlank(condition)){
			having = " HAVING " + condition;
		}else{
			having += ","+condition;
		}
		//2.添加参数
		if(params != null && params.length>0){
			for (Object param : params) {
				paramList.add(param);
			}
		}
		return this;
	}
	/**
	 * 添加IF NOT NULL子句 
	 * @param hql
	 * @param from2
	 */
	private void addIfNotNull(StringBuffer hql, String clause) {
		if (clause != null) {
			hql.append(" ").append(clause).append(" ");
		}
	}
	/**
	 * 生成查询总记录数的HQL
	 * @return 生成总记录数的HQL语句
	 */
	public String toQueryCountHql() {
		StringBuffer hql = new StringBuffer();

		if (select == null) {
			hql.append(" SELECT COUNT(*) ");
		} else {
			String tempSelect = " SELECT COUNT(" + select.substring("SELECT ".length()) + ") ";
			hql.append(tempSelect);
		}
		addIfNotNull(hql, from);
		addIfNotNull(hql, join);
		addIfNotNull(hql, where);
		// 查询数量不需要排序，所以不加OrderBy子句
		return hql.toString();
	}
	
	/**
	 * 生成查询某页数据的HQL
	 * @return
	 */
	public String toQueryListHql() {
		StringBuffer hql = new StringBuffer();

		addIfNotNull(hql, select);
		addIfNotNull(hql, from);
		addIfNotNull(hql, join);
		addIfNotNull(hql, where);
		addIfNotNull(hql, groupBy);
		addIfNotNull(hql, having);
		addIfNotNull(hql, orderBy);

		return hql.toString();
	}
}
