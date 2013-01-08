package com.share.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.share.bean.Pager;
import com.share.bean.Pager.OrderType;
import com.share.dao.BaseDao;
/**
 *  数据库操作的基层   实现类
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-5-27 下午8:40:28
 * @version 1.0
 */
@Repository
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {
	private Class<T> entityClass;
	@Resource
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.entityClass = null;
		Class<?> c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
	}

	@Override
	@Transactional
	public Session getSession() {
		// TODO Auto-generated method stub
		//事务必须是开启的(Required)，否则报异常
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(T t){
		Assert.notNull(t);
		this.getSession().save(t);
	}
	
	@Override
	public void update(T t){
		Assert.notNull(t);
		this.getSession().update(t);
	}
	
	@Override
	public void delete(T t){
		Assert.notNull(t);
		this.getSession().delete(t);
	}

	@Override
	public void delete(PK id) {
		// TODO Auto-generated method stub
		Assert.notNull(id, "id is required");
		T entity = get(id);
		delete(entity);
	}

	@Override
	public void delete(PK[] ids) {
		// TODO Auto-generated method stub
		Assert.notEmpty(ids, "ids must not be empty");
		for (PK id : ids) {
			T entity = get(id);
			delete(entity);
		}
	}

	@Override
	public void saveOrUpdate(T t) {
		// TODO Auto-generated method stub
		Assert.notNull(t);
		this.getSession().saveOrUpdate(t);
	}

	@Override
	@Transactional
	public Query hqlQuery(String hql) {
		// TODO Auto-generated method stub
		return getSession().createQuery(hql);
	}

	@Override
	public Criteria criteria(Class<T> clazz) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(clazz);
	}

	@Override
	@Deprecated
	public SQLQuery sqlQuery(String sql) {
		// TODO Auto-generated method stub
		return getSession().createSQLQuery(sql);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.getSession().clear();
	}

	@Override
	public void evict(Object object) {
		// TODO Auto-generated method stub
		this.getSession().evict(object);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		this.getSession().flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(PK id) {
		// TODO Auto-generated method stub
		Assert.notNull(id, "id must not be null");
		return (T) getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T load(PK id) {
		// TODO Auto-generated method stub
		return (T) getSession().load(entityClass, id);
	}

	@Override
	public Integer count() {
		// TODO Auto-generated method stub
		String hql = "select count(*) from " + entityClass.getName();
		return (Integer) this.hqlQuery(hql).uniqueResult();
	}

	@Override
	public <V> Integer countBy(String name, V val) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName()
				+" as model where model."+ name +" = :"+name;
		
		Query hqlQuery = this.hqlQuery(hql);
		return (Integer) hqlQuery.setParameter(name, val).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> list() {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName();
		return this.hqlQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName();
		return this.hqlQuery(hql).iterate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listPart(int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName();
		Query q = hqlQuery(hql);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iteratorPart(int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName();
		Query q = hqlQuery(hql);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		return q.iterate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listOrder(String order) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName() + " as model order by model."+order;
		return this.hqlQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iteratorOrder(String order) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName() + " as model order by model."+order;
		return this.hqlQuery(hql).iterate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listPartOrder(String order, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName() + " as model order by model."+order;
		Query q = hqlQuery(hql);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iteratorPartOrder(String order, int firstResult,
			int maxResults) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName() + " as model order by model."+order;
		Query q = hqlQuery(hql);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		return q.iterate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listBy(String name, Object val) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName()
				+" as model where model."+ name +" = :"+name;
		return (List<T>) hqlQuery(hql).setParameter(name, val).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iteratorBy(String name, Object val) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName()
				+" as model where model."+ name +" = :"+name;
		return hqlQuery(hql).iterate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listByOrder(String name, Object val, String order) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName()
				+" as model where model."+ name +" = :"+name
				+" order by model."+order;
		return (List<T>) hqlQuery(hql).setParameter(name, val).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iteratorByOrder(String name, Object val, String order) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName()
				+" as model where model."+ name +" = :"+name
				+" order by model."+order;
		return hqlQuery(hql).setParameter(name, val).iterate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listPartByOrder(String name, Object val, String order,
			int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName()
				+" as model where model."+ name +" = :"+name
				+" order by model."+order;
		Query q = hqlQuery(hql);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setParameter(name, val);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iteratorPartByOrder(String name, Object val,
			String order, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName()
				+" as model where model."+ name +" = :"+name
				+" order by model."+order;
		Query q = hqlQuery(hql);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setParameter(name, val);
		return q.iterate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listBy(Properties props) {
		// TODO Auto-generated method stub
		StringBuffer hqlBuff = new StringBuffer(" from ");
		hqlBuff.append(entityClass.getName());
		hqlBuff.append(" as model where 1=1 ");
		
		Set<Entry<Object,Object>> set = props.entrySet();
		Iterator<Entry<Object, Object>> iterator = set.iterator();
		while (iterator.hasNext()) {
			//绑定多个参数条件
			Entry<Object,Object> entry = iterator.next();
			hqlBuff.append(" and model.");
			hqlBuff.append(entry.getKey());
			hqlBuff.append(" = :");
			hqlBuff.append(entry.getKey());
		}
		
		String hql = hqlBuff.toString();
		return hqlQuery(hql).setProperties(props).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iteratorBy(Properties props) {
		// TODO Auto-generated method stub
		StringBuffer hqlBuff = new StringBuffer(" from ");
		hqlBuff.append(entityClass.getName());
		hqlBuff.append(" as model where 1=1 ");
		
		Set<Entry<Object,Object>> set = props.entrySet();
		Iterator<Entry<Object, Object>> iterator = set.iterator();
		while (iterator.hasNext()) {
			//绑定多个参数条件
			Entry<Object,Object> entry = iterator.next();
			hqlBuff.append(" and model.");
			hqlBuff.append(entry.getKey());
			hqlBuff.append(" = :");
			hqlBuff.append(entry.getKey());
		}
		
		String hql = hqlBuff.toString();
		return hqlQuery(hql).setProperties(props).iterate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listByOrder(Properties props, String order) {
		// TODO Auto-generated method stub
		StringBuffer hqlBuff = new StringBuffer(" from ");
		hqlBuff.append(entityClass.getName());
		hqlBuff.append(" as model where 1=1 ");
		
		Set<Entry<Object,Object>> set = props.entrySet();
		Iterator<Entry<Object, Object>> iterator = set.iterator();
		while (iterator.hasNext()) {
			//绑定多个参数条件
			Entry<Object,Object> entry = iterator.next();
			hqlBuff.append(" and model.");
			hqlBuff.append(entry.getKey());
			hqlBuff.append(" = :");
			hqlBuff.append(entry.getKey());
		}
		//根据order排序
		hqlBuff.append(" order by model.");
		hqlBuff.append(order);
		
		String hql = hqlBuff.toString();
		return hqlQuery(hql).setProperties(props).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iteratorByOrder(Properties props, String order) {
		// TODO Auto-generated method stub
		StringBuffer hqlBuff = new StringBuffer(" from ");
		hqlBuff.append(entityClass.getName());
		hqlBuff.append(" as model where 1=1 ");
		
		Set<Entry<Object,Object>> set = props.entrySet();
		Iterator<Entry<Object, Object>> iterator = set.iterator();
		while (iterator.hasNext()) {
			//绑定多个参数条件
			Entry<Object,Object> entry = iterator.next();
			hqlBuff.append(" and model.");
			hqlBuff.append(entry.getKey());
			hqlBuff.append(" = :");
			hqlBuff.append(entry.getKey());
		}
		//根据order排序
		hqlBuff.append(" order by model.");
		hqlBuff.append(order);
		
		String hql = hqlBuff.toString();
		return hqlQuery(hql).setProperties(props).iterate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listPartByOrder(Properties props, String order,
			int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		StringBuffer hqlBuff = new StringBuffer(" from ");
		hqlBuff.append(entityClass.getName());
		hqlBuff.append(" as model where 1=1 ");
		
		Set<Entry<Object,Object>> set = props.entrySet();
		Iterator<Entry<Object, Object>> iterator = set.iterator();
		while (iterator.hasNext()) {
			//绑定多个参数条件
			Entry<Object,Object> entry = iterator.next();
			hqlBuff.append(" and model.");
			hqlBuff.append(entry.getKey());
			hqlBuff.append(" = :");
			hqlBuff.append(entry.getKey());
		}
		//根据order排序
		hqlBuff.append(" order by model.");
		hqlBuff.append(order);
		
		//设值结果集
		Query q = hqlQuery(hqlBuff.toString());
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setProperties(props);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iteratorPartByOrder(Properties props, String order,
			int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		StringBuffer hqlBuff = new StringBuffer(" from ");
		hqlBuff.append(entityClass.getName());
		hqlBuff.append(" as model where 1=1 ");
		
		Set<Entry<Object,Object>> set = props.entrySet();
		Iterator<Entry<Object, Object>> iterator = set.iterator();
		while (iterator.hasNext()) {
			//绑定多个参数条件
			Entry<Object,Object> entry = iterator.next();
			hqlBuff.append(" and model.");
			hqlBuff.append(entry.getKey());
			hqlBuff.append(" = :");
			hqlBuff.append(entry.getKey());
		}
		//根据order排序
		hqlBuff.append(" order by model.");
		hqlBuff.append(order);
		
		//设值结果集
		Query q = hqlQuery(hqlBuff.toString());
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setProperties(props);
		return q.iterate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listIn(String name, Object[] vals) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName()
				+" as model where model."+ name +"in (:"+name+")";
		return hqlQuery(hql).setParameterList(name, vals).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iteratorIn(String name, Object[] vals) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName()
				+" as model where model."+ name +"in (:"+name+")";
		return hqlQuery(hql).setParameterList(name, vals).iterate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listRange(String name, Object val1, Object val2) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName()
				+" as model where model."+ name +" between ? and ? ";
		Query q = hqlQuery(hql);
		q.setParameter(0, val1);
		q.setParameter(1, val2);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iteratorRange(String name, Object val1, Object val2) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName()
				+" as model where model."+ name +" between ? and ? ";
		Query q = hqlQuery(hql);
		q.setParameter(0, val1);
		q.setParameter(1, val2);
		return q.iterate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listLike(String name, Object val) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName()
				+" as model where model."+ name +" like %"+val+"%";	
		return hqlQuery(hql).setParameter(name, val).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iteratorLike(String name, Object val) {
		// TODO Auto-generated method stub
		String hql = " from " + entityClass.getName()
				+" as model where model."+ name +" like %"+val+"%";	
		return hqlQuery(hql).setParameter(name, val).iterate();
	}
	
	@Override
	public String toString(){
		return "this is BaseDaoImpl class";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> get(String propertyName, Object value) {
		// TODO Auto-generated method stub
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model." + propertyName + " = ?";
		return getSession().createQuery(hql).setParameter(0, value).list();
	}

	@Override
	public boolean isUnique(String propertyName, Object oldValue,
			Object newValue) {
		// TODO Auto-generated method stub
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(newValue, "newValue is required");
		if (newValue == oldValue || newValue.equals(oldValue)) {
			return true;
		}
		if (newValue instanceof String) {
			if (oldValue != null && StringUtils.equalsIgnoreCase((String) oldValue, (String) newValue)) {
				return true;
			}
		}
		List<T>  results = get(propertyName, newValue);
		return (results.size() == 0);
	}

	@Override
	public boolean isExist(String propertyName, Object value) {
		// TODO Auto-generated method stub
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		List<T>  results = get(propertyName, value);
		return (results.size() > 0);
	}

	@Override
	public Pager<T> findByPager(Pager<T> pager) {
		// TODO Auto-generated method stub
		if (pager == null) {
			pager = new Pager<T>();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass);
		return findByPager(pager, detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<T> findByPager(Pager<T> pager,
			DetachedCriteria detachedCriteria) {
		// TODO Auto-generated method stub
		if (pager == null) {
			pager = new Pager<T>();
		}
		int pageNumber = pager.getPageNumber();
		int pageSize = pager.getPageSize();
		String property = pager.getProperty();
		String keyword = pager.getKeyword();
		String orderBy = pager.getOrderBy();
		OrderType orderType = pager.getOrderType();
		
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		if (StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(keyword)) {
			String propertyString = "";
			if (property.contains(".")) {
				String propertyPrefix = StringUtils.substringBefore(property, ".");
				String propertySuffix = StringUtils.substringAfter(property, ".");
				criteria.createAlias(propertyPrefix, "model");
				propertyString = "model." + propertySuffix;
			} else {
				propertyString = property;
			}
			criteria.add(Restrictions.like(propertyString, "%" + keyword + "%"));
		}
		
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			if (orderType == OrderType.asc) {
				criteria.addOrder(Order.asc(orderBy));
			} else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		pager.setTotalCount(totalCount);
		pager.setList(criteria.list());
		return pager;
	}

}
