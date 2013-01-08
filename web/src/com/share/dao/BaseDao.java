package com.share.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import com.share.bean.Pager;

/**
 * 数据库操作的基层   接口
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-5-27 下午7:50:39
 * @version 1.0
 */
public interface BaseDao<T, PK extends Serializable> {
	
	/****
	 * 获得Hibernate Session会话
	 * @return
	 */
	public Session getSession();
	
	/****
	 * 持久化一个对象，保存
	 * 
	 * @param t 实体对象
	 */
	public void save(T t);
	
	/****
	 * 持久化一个对象，更新对象的属性
	 * 
	 * @param t 实体对象
	 */
	public void update(T t);
	
	/****
	 * 删除一个持久化的对象
	 * 
	 * @param t 实体对象
	 */
	public void delete(T t);
	
	/**
	 * 根据ID删除实体对象.
	 * 
	 * @param id
	 *            记录ID
	 */
	public void delete(PK id);

	/**
	 * 根据ID数组删除实体对象.
	 * 
	 * @param ids
	 *            ID数组
	 */
	public void delete(PK[] ids);
	
	/****
	 * 将一个对象保存到数据库，如果对象已经存在，就更到数据库
	 * 效率会比save略微低，因为要执行一个SELECT语句检查对象是否存在
	 * 
	 * @param t 
	 */
	public void saveOrUpdate(T t);
	
	/****
	 * Hibernate HQL查询
	 * 
	 * @param hql语句
	 * @return Query
	 */
	public Query hqlQuery(String hql);
	
	/****
	 * Hibernate Criteria 查询
	 * 
	 * @param clazz
	 * @return Criteria
	 */
	public Criteria criteria(Class<T> clazz);
	
	/****
	 * Hibernate 支持的原生态的SQL查询，无法跨数据库平台
	 * 
	 * @param sql sql语句
	 * @return SQLQuery
	 */
	@Deprecated
	public SQLQuery sqlQuery(String sql);
	
	/****
	 * 清空Session会话中加载的所有对象
	 */
	public void clear();
	
	/****
	 * 清除Session中某一对象
	 * 
	 * @param object 清除的对象
	 */
	public void evict(Object object);
	
	/****
	 * 将Session会话中的所有实体对象，同步到数据库
	 */
	public void flush();
	
	/****
	 * 根据ID获取实体对象，对应Hibernate中的get方式
	 * 
	 * @param id 对象唯一标识
	 * @return
	 */
	public T get(PK id);
	
	/****
	 * 根据ID获取实体对象，对应Hibernate中的load方式
	 * 
	 * @param id 对象唯一标识
	 * @return
	 */
	public T load(PK id);
	
	
	/****
	 * 获得所有实体对象集合的数目
	 * 
	 * @return Integer
	 */
	public Integer count();
	/****
	 * 根据条件，获得对象集合的数目
	 * 
	 * @param name 参数名
	 * @param val  参数值
	 * @return Integer
	 */
	public <V> Integer countBy(String name,V val);
	/****
	 * 获得所有的实体对象 集合
	 * 
	 * @return List<T>
	 */
	public List<T> list();
	
	/****
	 * 获得所有的实体对象 迭代器
	 * 
	 * @return Iterator<T>
	 */
	public Iterator<T> iterator();
	
	/****
	 * 获得部分实体对象 集合
	 * 
	 * @param firstResult 开始位置
	 * @param maxResults  结果集大小
	 * @return List<T>
	 */
	public List<T> listPart(int firstResult,int maxResults);
	
	/****
	 * 获得部分实体对象 迭代器
	 * 
	 * @param firstResult 开始位置
	 * @param maxResults 结果集大小
	 * @return List<T>
	 */
	public Iterator<T> iteratorPart(int firstResult,int maxResults);
	
	/****
	 * 按一定的顺序，获得实体对象集合
	 * 
	 * @param order 按照order排序
	 * @return List<T>
	 */
	public List<T> listOrder(String order);
	
	/****
	 * 按一定的顺序，获得实体对象迭代器
	 * 
	 * @param order 按照order排序
	 * @return Iterator<T>
	 */
	public Iterator<T> iteratorOrder(String order);
	
	/****
	 * 按一定的顺序，获得部分实体对象集合
	 * 
	 * @param order 按照order排序
	 * @param firstResult 开始位置
	 * @param maxResults 结果集大小
	 * @return List<T>
	 */
	public List<T> listPartOrder(String order
			,int firstResult,int maxResults);
	
	/****
	 * 按一定的顺序，获得部分实体对象迭代器
	 * 
	 * @param order 按照order排序
	 * @param firstResult 开始位置
	 * @param maxResults 结果集大小
	 * @return Iterator<T>
	 */
	public Iterator<T> iteratorPartOrder(String order
			,int firstResult,int maxResults);
	
	/****
	 * 根据条件，获得对象集合
	 * 
	 * @param name 参数名
	 * @param val  参数值
	 * @return List<T>
	 */
	public List<T> listBy(String name,Object val);
	
	/****
	 * 根据条件，获得对象迭代器
	 * 
	 * param name
	 * param val
	 * @return Iterator<T>
	 */
	public Iterator<T> iteratorBy(String name,Object val);
	
	/****
	 * 根据条件，获得对象集合，并排序
	 * 
	 * @param name 参数名
	 * @param val  参数值
	 * @param order 按照order排序
	 * @return List<T>
	 */
	public List<T> listByOrder(String name,Object val,String order);
	
	/****
	 * 根据条件，获得对象迭代器，并排序
	 * 
	 * @param name 参数名
	 * @param val  参数值
	 * @param order 按照order排序
	 * @return Iterator<T>
	 */
	public Iterator<T> iteratorByOrder(String name,Object val,String order);
	
	/****
	 * 根据条件，获得部分集合，并排序
	 * 
	 * @param name 参数名
	 * @param val 参数值
	 * @param order 按照order排序
	 * @param firstResult 开始位置
	 * @param maxResults 结果集的大小
	 * @return List<T>
	 */
	public List<T> listPartByOrder(String name
			,Object val,String order,int firstResult,int maxResults);
	
	/****
	 * 根据条件，获得部分对象迭代器，并排序
	 * 
	 * @param name 参数名
	 * @param val 参数值
	 * @param order 按照order排序
	 * @param firstResult 开始位置
	 * @param maxResults 结果集的大小
	 * @return Iterator<T>
	 */
	public Iterator<T> iteratorPartByOrder(String name
			,Object val,String order,int firstResult,int maxResults);
	
	/****
	 * 根据多个条件，获得对象集合
	 * 
	 * @param props 参数Key-value集合
	 * @return List<T>
	 */
	public List<T> listBy(Properties props);
	
	/****
	 * 根据多个条件，获得对象迭代器
	 * 
	 * @param props 参数Key-value集合
	 * @return Iterator<T>
	 */
	public Iterator<T> iteratorBy(Properties props);
	
	/****
	 * 根据多个条件，获得对象集合，并排序
	 * 
	 * @param props 参数Key-value集合
	 * @param order 按照order排序
	 * @return List<T> 
	 */
	public List<T> listByOrder(Properties props,String order);
	
	/****
	 * 根据多个条件，获得对象集合，并排序
	 * 
	 * @param props 参数Key-value集合
	 * @param order 按照order排序
	 * @return List<T> 
	 */
	public Iterator<T> iteratorByOrder(Properties props,String order);
	
	/****
	 * 根据多个条件，获得部分对象集合，并排序
	 * 
	 * @param props 参数Key-value集合
	 * @param order 按照order排序
	 * @param firstResult 开始位置
	 * @param maxResults  结果集的大小
	 * @return List<T>
	 */
	public List<T> listPartByOrder(Properties props
			,String order,int firstResult,int maxResults);
	
	/****
	 * 根据多个条件，获得部分对象迭代器，并排序
	 * 
	 * @param props 参数Key-value集合
	 * @param order 按照order排序
	 * @param firstResult 开始位置
	 * @param maxResults  结果集的大小
	 * @return List<T>
	 */
	public Iterator<T> iteratorPartByOrder(Properties props
			,String order,int firstResult,int maxResults);
	
	/****
	 * 根据in条件，获得对象集合， foo.bar in (:value_list)
	 * 
	 * @param name 参数名
	 * @param vals 参数值[集合的形式]
	 * @return List<T>
	 */
	public List<T> listIn(String name,Object[] vals);
	
	/****
	 * 根据in条件，获得对象迭代器， foo.bar in (:value_list)
	 * 
	 * @param name 参数名
	 * @param vals 参数值[集合的形式]
	 * @return Iterator<T>
	 */
	public Iterator<T> iteratorIn(String name,Object[] vals);
	
	/****
	 * 根据between-and条件，获得对象集合
	 * 
	 * @param name 参数名
	 * @param val1  参数值1
	 * @param val2  参数值2
	 * @return List<T>
	 */
	public List<T> listRange(String name
			,Object val1,Object val2);
	
	/****
	 * 根据between-and条件，获得对象迭代器
	 * 
	 * @param name 参数名
	 * @param val1  参数值1
	 * @param val2  参数值2
	 * @return Iterator<T>
	 */
	public Iterator<T> iteratorRange(String name
			,Object val1,Object val2);
	
	/****
	 * 根据模糊条件，获得对象集合
	 * 
	 * @param name
	 *           参数名
	 * @param val
	 *           参数值
	 * @return List<T>
	 */
	public List<T> listLike(String name,Object val);
	
	/****
	 * 根据模糊条件，获得对象集合
	 * 
	 * @param name 参数名
	 * @param val 参数值
	 * @return Iterator<T>
	 */
	public Iterator<T> iteratorLike(String name,Object val);
	
	/**
	 * 根据属性名和属性值获取实体对象.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return List<T>
	 */
	public List<T> get(String propertyName, Object value);
	
	/**
	 * 根据属性名、修改前后属性值判断在数据库中是否唯一(若新修改的值与原来值相等则直接返回true).
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param oldValue
	 *            修改前的属性值
	 * @param oldValue
	 *            修改后的属性值
	 * @return boolean
	 */
	public boolean isUnique(String propertyName, Object oldValue, Object newValue);
	
	/**
	 * 根据属性名判断数据是否已存在.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            值
	 * @return boolean
	 */
	public boolean isExist(String propertyName, Object value);

	/**
	 * 根据Pager对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 *            Pager对象
	 * @return Pager对象
	 */
	public Pager<T> findByPager(Pager<T> pager);
	
	/**
	 * 根据Pager和DetachedCriteria对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 *            Pager对象
	 * @return Pager对象
	 */
	public Pager<T> findByPager(Pager<T> pager, DetachedCriteria detachedCriteria);
	
	@Override
	public String toString();
}
