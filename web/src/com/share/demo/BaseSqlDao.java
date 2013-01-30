package com.share.demo;

import java.util.List;

/**
 * DAO接口: 使用SQL操纵数据库
 *
 * @author liangancai email：ancai0729@gmail.com
 * @since 2013-1-30 下午5:02:58
 * @version 1.0
 */
public interface BaseSqlDao<T> {
	/**
	 * 查询全部，使用sql
	 * 
	 * @param sql 
	 * @param params
	 * @return List<T>
	 */
	public List<T> list(String sql, Object... params);
	
	/**
	 * 查询全部，使用sql
	 * 
	 * @param sql
	 * @param clazz 关联的模型类型
	 * @param params
	 * @return List<T>
	 */
	public List<T> list(String sql, Class<T> clazz, Object... params);
	
	/**
	 * 查询对象，根据ID
	 * 
	 * @param sql
	 * @param id
	 * @return T
	 */
	public T getById(String sql, Integer id);
	
	/**
	 * 查询对象，根据ID
	 * 
	 * @param sql
	 * @param clazz 关联的模型类型
	 * @param id
	 * @return T
	 */
	public T getById(String sql, Class<T> clazz, Integer id);
}
