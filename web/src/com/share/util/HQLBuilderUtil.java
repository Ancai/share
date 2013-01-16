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
public class HQLBuilderUtil {
	/**
	 * SELECT 子句
	 */
	private String selectClause; 
	
	/**
	 * FROM 子句
	 */
	private String fromClause;
	
	/**
	 * JOIN子句
	 */
	private String joinClause;
	
	/**
	 * WHERE 子句
	 */
	private String whereClause;
	
	/**
	 * ORDERBY 子句
	 */
	private String orderByClause;
	
	/**
	 * GROUPBY 子句
	 */
	private String groupByClause;

	/**
	 * HAVING 子句
	 */
	private String havingClause;
	
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
	@SuppressWarnings("unchecked")
	public HQLBuilderUtil(Class entityClass){
		this.fromClause = " FROM " + entityClass.getName() + " this ";
	}
	
	/**
	 * 指定要查询的属性，拼接SELECT子句
	 * @param property  要查询的属性
	 * @return 工具类本身
	 */
	public HQLBuilderUtil addSelectClause(String property) {
		if (StringUtils.isBlank(selectClause)) {
			selectClause = " SELECT " + property;
		} else {
			selectClause += " , " + property;
		}
		return this;
	}
	/**
	 *  添加左外连接语句，组织JOIN语句
	 * @param joinClause 连接条件
	 * @param params 参数列表
	 * @return 工具类本身
	 */
	public HQLBuilderUtil addJoinClause(String joinClause, Object... params){
		//1.组织语句
		if(StringUtils.isBlank(this.joinClause)){
			this.joinClause = " LEFT JOIN  " + joinClause;
		}else{
			this.joinClause += " LEFT JOIN  " + joinClause;
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
	public HQLBuilderUtil addWhereClause(String condition, Object... params){
		//1.组织语句
		if(StringUtils.isBlank(whereClause)){
			whereClause = " WHERE " + condition;
		}else{
			whereClause += " AND " + condition;
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
	public HQLBuilderUtil addWhereClause(String condition,boolean andOror, Object... params){
		//1.组织语句
		if(StringUtils.isBlank(whereClause)){
			whereClause = " WHERE " + condition;
		}else{
			whereClause += (andOror?" AND ":" OR ") + condition;
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
	public HQLBuilderUtil addOrderByProperty(String condition,boolean reverse){
		//1.组织语句
		if(StringUtils.isBlank(orderByClause)){
			orderByClause = " ORDER BY " + condition +(reverse ? " DESC " : " ASC ");
		}else{
			orderByClause += " , " + condition +(reverse ? " DESC " : " ASC ");
		}
		return this;
	}
	
	/**
	 * 添加分组语句，组织GROUPBY子句
	 * @param conditon 分组条件
	 * @param params 分组条件中的参数
	 * @return 工具类本身
	 */
	public HQLBuilderUtil addGroupByClause(String conditon,Object... params){
		//1.组织语句
		if(StringUtils.isBlank(groupByClause)){
			groupByClause = " GROUP BY " + conditon;
		}else{
			groupByClause += "," + conditon;
		}
		//2.添加参数
		if(params != null && params.length>0){
			for (Object param : params) {
				paramList.add(param);
			}
		}
		return this;
	}
	
	public HQLBuilderUtil addHavingClause(String condition,Object...params){
		//1.组织语句
		if(StringUtils.isBlank(condition)){
			havingClause = " HAVING " + condition;
		}else{
			havingClause += ","+condition;
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
	 * @param fromClause2
	 */
	private void addIfNotNullClause(StringBuffer hql, String clause) {
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

		if (selectClause == null) {
			hql.append(" SELECT COUNT(*) ");
		} else {
			String tempSelectClause = " SELECT COUNT(" + selectClause.substring("SELECT ".length()) + ") ";
			hql.append(tempSelectClause);
		}
		addIfNotNullClause(hql, fromClause);
		addIfNotNullClause(hql, joinClause);
		addIfNotNullClause(hql, whereClause);
		// 查询数量不需要排序，所以不加OrderBy子句
		return hql.toString();
	}
	
	/**
	 * 生成查询某页数据的HQL
	 * @return
	 */
	public String toQueryListHql() {
		StringBuffer hql = new StringBuffer();

		addIfNotNullClause(hql, selectClause);
		addIfNotNullClause(hql, fromClause);
		addIfNotNullClause(hql, joinClause);
		addIfNotNullClause(hql, whereClause);
		addIfNotNullClause(hql, groupByClause);
		addIfNotNullClause(hql, havingClause);
		addIfNotNullClause(hql, orderByClause);

		return hql.toString();
	}
}
