package com.share.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.share.bean.Pager;
import com.share.dao.BaseDao;
import com.share.service.BaseService;

/**
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-5-28 下午9:22:29
 * @version 1.0
 */
public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {
	
	private BaseDao<T, PK> baseDao;
	
	public BaseDao<T, PK> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public T get(PK id) {
		// TODO Auto-generated method stub
		return baseDao.get(id);
	}

	@Override
	public T load(PK id) {
		// TODO Auto-generated method stub
		return baseDao.load(id);
	}


	@Override
	public List<T> getList(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return baseDao.listBy(propertyName, value);
	}

	@Override
	public List<T> getAll() {
		// TODO Auto-generated method stub
		return baseDao.list();
	}

	@Override
	public Integer getTotalCount() {
		// TODO Auto-generated method stub
		return baseDao.count();
	}

	@Override
	public boolean isUnique(String propertyName, Object oldValue,
			Object newValue) {
		// TODO Auto-generated method stub
		return baseDao.isUnique(propertyName, oldValue, newValue);
	}

	@Override
	public boolean isExist(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return baseDao.isExist(propertyName, value);
	}

	@Override
	public PK save(T entity) {
		// TODO Auto-generated method stub
		baseDao.save(entity);
		return null;
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		baseDao.update(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		// TODO Auto-generated method stub
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		baseDao.delete(entity);
	}

	@Override
	public void delete(PK id) {
		// TODO Auto-generated method stub
		baseDao.delete(id);
	}

	@Override
	public void delete(PK[] ids) {
		// TODO Auto-generated method stub
		baseDao.delete(ids);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		baseDao.flush();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		baseDao.clear();
	}

	@Override
	public void evict(Object object) {
		// TODO Auto-generated method stub
		baseDao.evict(object);
	}

	@Override
	public Pager<T> findByPager(Pager<T> pager) {
		// TODO Auto-generated method stub
		return baseDao.findByPager(pager);
	}

	@Override
	public Pager<T> findByPager(Pager<T> pager,
			DetachedCriteria detachedCriteria) {
		// TODO Auto-generated method stub
		return baseDao.findByPager(pager, detachedCriteria);
	}

}
