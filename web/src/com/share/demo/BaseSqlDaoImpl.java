package com.share.demo;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

/**
 * DAO实现: 使用SQL操纵数据库
 *
 * @author liangancai email：ancai0729@gmail.com
 * @since 2013-1-30 下午5:05:47
 * @version 1.0
 */
public abstract class BaseSqlDaoImpl<T> implements BaseSqlDao<T> {
	/** Sping JdbcTemplate  **/
	@Resource protected JdbcTemplate jdbcTemp;
	
	/** 泛型化的类型  **/
	protected Class<T> clazz;

	@SuppressWarnings("unchecked")
	public BaseSqlDaoImpl() {
		// TODO Auto-generated constructor stub
		//获得超类的泛型参数的实际类型
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}
	
	@Override
	public List<T> list(String sql, Object... params) {
		return this.jdbcTemp.query(sql, 
				ParameterizedBeanPropertyRowMapper.newInstance(clazz), params);
	}
	
	@Override
	public List<T> list(String sql, Class<T> clazz, Object... params) {
		return this.jdbcTemp.query(sql, 
				ParameterizedBeanPropertyRowMapper.newInstance(clazz), params);
	}
	
	@Override
	public T getById(String sql, Integer id) {
		return this.jdbcTemp.queryForObject(sql, clazz, id);
	}
	
	@Override
	public T getById(String sql, Class<T> clazz, Integer id) {
		return this.jdbcTemp.queryForObject(sql, clazz, id);
	}

}
