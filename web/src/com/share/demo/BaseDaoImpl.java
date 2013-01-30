//package com.vrv.ccp.base;
//
//import java.lang.reflect.ParameterizedType;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.hibernate.Query;
//import org.hibernate.SQLQuery;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import com.vrv.ccp.bean.GridData;
//import com.vrv.ccp.utils.HQLBuilderUtil;
///**
// * @author glw linwu_gao@163.com
// * @version V1.0 2011-12-27 上午09:31:08
// * @description Dao层基本接口的实现类
// * 
// * @param <T>
// *            泛型类型
// */
//public abstract class BaseDaoImpl<T> implements BaseDao<T> {
//
//	/* 反射类 */
//	protected Class<T> clazz;
//
//	/* SessionFactory获取入口处 */
//	@Resource
//	private SessionFactory sessionFactory;
//	/** Sping JdbcTemplate  **/
//	@Resource protected JdbcTemplate jdbcTemp;
//
//	protected Session getSession() {
//		return sessionFactory.getCurrentSession();
//	}
//
//	/* 默认构造方法，用来反射出实体类 */
//	@SuppressWarnings("unchecked")
//	public BaseDaoImpl() {
//		try {
//			// 1.获得泛型化超类
//			ParameterizedType parameterizedType = (ParameterizedType) this
//					.getClass().getGenericSuperclass();
//			// 2.获得真实的第一个类型参数
//			this.clazz = (Class) parameterizedType.getActualTypeArguments()[0];
//			Logger.getLogger(this.getClass()).info("clazz-->" + this.clazz);
//		} catch (Exception e) {
//			throw new RuntimeException("Dao层反射实体类时出错了", e);
//		}
//	}
//
//	@Override
//	public void delete(String id) {
//		// TODO Auto-generated method stub
//		this.getSession().delete(this.getSession().get(this.clazz, id));
//	}
//
//	@Override
//	public void delete(Integer id) {
//		// TODO Auto-generated method stub
//		this.getSession().delete(this.getSession().get(this.clazz, id));
//	}
//	
//	@Override
//	public void deleteByIds(String[] ids) {
//		// TODO Auto-generated method stub
//		this.getSession().createQuery(
//				" DELETE " + this.clazz.getName()
//						+ " this WHERE this.id IN(:ids) ")//
//				.setParameterList("ids", ids)//
//				.executeUpdate();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<T> queryAll() {
//		// TODO Auto-generated method stub
//		return this.getSession().createQuery(" FROM " + this.clazz.getName())
//				.list();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public T queryById(Integer id) {
//		// TODO Auto-generated method stub
//		return (T) this.getSession().get(this.clazz, id);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public T queryById(String id) {
//		// TODO Auto-generated method stub
//		return (T) this.getSession().get(this.clazz, id);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<T> queryByIds(List<Integer> ids) {
//		// TODO Auto-generated method stub
//		return this.getSession().createQuery(
//				" FROM " + this.clazz.getName()
//						+ " this WHERE this.id IN(:ids)")//
//				.setParameterList("ids", ids)//
//				.list();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<T> queryByIds(String[] ids) {
//		// TODO Auto-generated method stub
//		return this.getSession().createQuery(
//				" FROM " + this.clazz.getName()
//						+ " this WHERE this.id IN(:ids)")//
//				.setParameterList("ids", ids)//
//				.list();
//	}
//
//	@Override
//	public void save(T entity) {
//		// TODO Auto-generated method stub
//		this.getSession().save(entity);
//		// this.hibernateTemplate.save(entity);
//	}
//
//	@Override
//	public void saveOrUpdate(T entity) {
//		this.getSession().saveOrUpdate(entity);
//	}
//
//	@Override
//	public void update(T entity) {
//		// TODO Auto-generated method stub
//		this.getSession().update(entity);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public GridData<T> getPageView(HQLBuilderUtil queryBuilder, int pageNum,
//			int pageSize) {
//		// TODO Auto-generated method stub
//		List<Object> paramList = queryBuilder.getParamList();
//
//		// 一、查询总记录数
//		// a, 生成查询recordCount的Query对象，并设置参数
//		Query countQuery = this.getSession().createQuery(
//				queryBuilder.toQueryCountHql());
//		for (int i = 0; i < paramList.size(); i++) {
//			countQuery.setParameter(i, paramList.get(i));
//		}
//
//		// b, 查询出总记录数
//		int recordCount = ((Number) countQuery.uniqueResult()).intValue();
//
//		// 计算总页数
//		int maxPageNum = (recordCount + pageSize - 1) / pageSize;
//		if (pageNum > maxPageNum)
//			pageNum = maxPageNum;
//
//		List recordlist = null;
//		if (recordCount > 0) {
//			// ===============
//			// 二、查询指定页的数据列表
//			// a, 生成查询recordList的Query对象，并设置参数
//			Query listQuery = getSession().createQuery(
//					queryBuilder.toQueryListHql());
//			for (int i = 0; i < paramList.size(); i++) {
//				listQuery.setParameter(i, paramList.get(i));
//			}
//			listQuery.setFirstResult((pageNum - 1) * pageSize);
//			listQuery.setMaxResults(pageSize);
//
//			// b, 查询出当前页的数据列表
//			recordlist = listQuery.list();
//		}
//
//		return new GridData<T>(recordCount, recordlist);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<T> queryByHQLBuilder(HQLBuilderUtil queryBuilder) {
//		// TODO Auto-generated method stub
//		List<Object> paramList = queryBuilder.getParamList();
//		Query query = this.getSession().createQuery(
//				queryBuilder.toQueryListHql());
//		for (int i = 0; i < paramList.size(); i++) {
//			query.setParameter(i, paramList.get(i));
//		}
//		return query.list();
//	}
//
//	@Override
//	public boolean haveData() {
//		return Integer.valueOf(
//				this.getSession().createQuery("SELECT COUNT(*) FROM " + this.clazz.getName()).uniqueResult()
//					.toString()) > 0;
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Object[]> queryByHql(HQLBuilderUtil hqlBuilder) {
//		List<Object> paramList = hqlBuilder.getParamList();
//		Query query = this.getSession().createQuery(
//				hqlBuilder.toQueryListHql());
//		for (int i = 0; i < paramList.size(); i++) {
//			query.setParameter(i, paramList.get(i));
//		}
//		return query.list();
//	}
//	
//	@Override
//	public List<Object[]> queryByHql(HQLBuilderUtil hqlBuilder, int pageNum, int pageSize) {
//		int fromIndex = (pageNum -1) * pageSize;
//		int toIndex = pageNum * pageSize - 1;
//		return queryByHql(hqlBuilder).subList(fromIndex, toIndex);
//	}
//	
//	@Override
//	public Long countByHql(HQLBuilderUtil hqlBuilder) {
//		List<Object> paramList = hqlBuilder.getParamList();
//		Query query = this.getSession().createQuery(
//				hqlBuilder.toQueryListHql());
//		for (int i = 0; i < paramList.size(); i++) {
//			query.setParameter(i, paramList.get(i));
//		}
//		
//		return (Long) query.uniqueResult();
//	} 
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Object[]> queryBySql(String sql, Object... params) {
//		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
//		for (int i = 0; i < params.length; i++) {
//			sqlQuery.setParameter(i, params[i]);
//		}
//		
//		return sqlQuery.list();
//	}
//}
